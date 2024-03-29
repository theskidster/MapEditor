package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.mapeditor.controls.Control;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.shadercore.GLProgram;
import java.util.List;
import org.joml.Vector2f;

/**
 * Created: Jul 30, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public abstract class Container extends Control {

    private final float minWidth;
    private final float minHeight;
    private float initialPosY;
    
    private boolean allowMove;
    private boolean allowResize;
    private boolean collapsed;
    private boolean prevClicked;
    
    protected String title;
    private final Rectangle titleBar;
    private final Vector2f intersection = new Vector2f();
    
    private Color closeColor    = Color.UI_SLATE_GRAY;
    private Color collapseColor = Color.UI_SLATE_GRAY;
    
    private final Rectangle[] buttons = new Rectangle[3];
    private final Icon[] icons        = new Icon[4];
    
    protected List<Control> controls;
    
    protected Container(float xPos, float yPos, float minWidth, float minHeight, String title, int cellX, int cellY) {
        super(xPos, yPos, minWidth, minHeight);
        this.title     = title;
        this.minWidth  = minWidth;
        this.minHeight = minHeight;
        
        titleBar   = new Rectangle(xPos, yPos + minHeight - 34, minWidth, 34);
        buttons[0] = new Rectangle((xPos + minWidth) - 34, titleBar.yPos, 34, 34);
        buttons[1] = new Rectangle((xPos + minWidth) - 68, titleBar.yPos, 34, 34);
        buttons[2] = new Rectangle((xPos + minWidth) - 15, yPos, 15, 15);
        
        icons[0] = new Icon(20, 20);
        icons[0].setSubImage(cellX, cellY);
        icons[0].position.set(titleBar.xPos + 7, titleBar.yPos + 7);
        
        icons[1] = new Icon(20, 20);
        icons[1].position.set(buttons[0].xPos + 7, buttons[0].yPos + 7);
        
        icons[2] = new Icon(20, 20);
        icons[2].setSubImage(4, 5);
        icons[2].position.set(buttons[1].xPos + 7, buttons[1].yPos + 7);
        
        icons[3] = new Icon(20, 20);
        icons[3].setSubImage(6, 5);
        icons[3].setColor(Color.UI_LIGHT_GRAY);
        icons[3].position.set(buttons[2].xPos - 5, buttons[2].yPos);
    }
    
    private void configure() {
        titleBar.xPos  = bounds.xPos;
        titleBar.yPos  = bounds.yPos + bounds.height - 34;
        titleBar.width = bounds.width;
        
        icons[0].position.set(bounds.xPos + 7, titleBar.yPos + 7);
        
        buttons[0].xPos = (titleBar.xPos + titleBar.width) - 34;
        buttons[0].yPos = titleBar.yPos;
        
        buttons[1].xPos = (titleBar.xPos + titleBar.width) - 68;
        buttons[1].yPos = titleBar.yPos;
        
        buttons[2].xPos = (bounds.xPos + bounds.width) - 15;
        buttons[2].yPos = bounds.yPos;
        
        icons[1].position.set(buttons[0].xPos + 7, buttons[0].yPos + 7);
        icons[2].position.set(buttons[1].xPos + 7, buttons[1].yPos + 7);
        icons[3].position.set(buttons[2].xPos - 5, buttons[2].yPos);
        
        controls.forEach(control -> control.relocate(bounds.xPos, bounds.yPos));
    }
    
    protected abstract Command updateAdjunct(Mouse mouse);
    
    protected abstract void renderAdjunct(GLProgram uiProgram, Background background, Font font);
    
    protected abstract void relocateAdjunct(float windowWidth, float windowHeight);
    
    protected boolean controlHovered(Vector2f cursorPos) {
        return controls.stream().anyMatch(control -> control.hovered(cursorPos));
    }
    
    @Override
    public Command update(Mouse mouse) {
        if(mouse.clicked && !collapsed) {
            if(buttons[2].contains(mouse.cursorPos) && !allowResize) {
                allowResize = true;
                initialPosY = bounds.yPos;
            }
            
            if(allowResize) {
                bounds.width  = mouse.cursorPos.x - bounds.xPos;
                bounds.height += bounds.yPos - mouse.cursorPos.y;
                
                if(bounds.width < minWidth) bounds.width = minWidth;
                
                if(bounds.height < minHeight) {
                    bounds.height = minHeight;
                    bounds.yPos   = initialPosY;
                } else {
                    bounds.yPos   = mouse.cursorPos.y;
                }
                
                configure();
            }
        } else {
            allowResize = false;
        }
        
        if(!buttons[0].contains(mouse.cursorPos) && !buttons[1].contains(mouse.cursorPos)) {
            if(mouse.clicked) {
                if(titleBar.contains(mouse.cursorPos) && !allowMove) {
                    intersection.x = bounds.xPos - mouse.cursorPos.x;
                    intersection.y = bounds.yPos - mouse.cursorPos.y;

                    allowMove = true;
                }

                if(allowMove) {
                    bounds.xPos = mouse.cursorPos.x + intersection.x;
                    bounds.yPos = mouse.cursorPos.y + intersection.y;

                    configure();
                }
            } else {
                allowMove = false;
            }
        }
        
        if(!allowMove) {
            if(buttons[0].contains(mouse.cursorPos) && (prevClicked && !mouse.clicked)) {
                remove();
            } else {
                if(buttons[0].contains(mouse.cursorPos) && mouse.clicked) {
                    closeColor = Color.UI_RED;
                } else {
                    closeColor = (buttons[0].contains(mouse.cursorPos)) ? Color.UI_MAROON : Color.UI_SLATE_GRAY;
                }
            }

            if(buttons[1].contains(mouse.cursorPos) && (prevClicked && !mouse.clicked)) {
                collapsed = !collapsed;

                if(collapsed) icons[2].setSubImage(4, 6);
                else          icons[2].setSubImage(4, 5);
            } else {
                if(buttons[1].contains(mouse.cursorPos) && mouse.clicked) {
                    collapseColor = Color.UI_LIGHT_GRAY;
                } else {
                    collapseColor = (buttons[1].contains(mouse.cursorPos)) ? Color.UI_MEDIUM_GRAY : Color.UI_SLATE_GRAY;
                }
            }
        }
        
        prevClicked = mouse.clicked;
        
        if(!collapsed) return updateAdjunct(mouse);
        else           return null;
    }
    
    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        if(!collapsed) background.drawRectangle(bounds, Color.UI_DARK_GRAY, uiProgram);
        background.drawRectangle(titleBar, Color.UI_SLATE_GRAY, uiProgram);
        background.drawRectangle(buttons[0], closeColor, uiProgram);
        background.drawRectangle(buttons[1], collapseColor, uiProgram);
        
        for(int i = 0; i < icons.length; i++) {
            if(i == 3) {
                if(!collapsed) icons[i].render(uiProgram);
            } else {
                icons[i].render(uiProgram);
            }
        }
        
        font.drawString(title, titleBar.xPos + 34, titleBar.yPos + 10, Color.UI_WHITE, uiProgram);
        
        if(!collapsed) renderAdjunct(uiProgram, background, font);
    }
    
    @Override
    public void relocate(float windowWidth, float windowHeight) {
        configure();
        relocateAdjunct(windowWidth, windowHeight);
    }

}