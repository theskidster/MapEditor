package dev.theskidster.mapeditor.containers;

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

    private boolean allowMove;
    
    protected String title;
    protected Icon icon;
    protected Rectangle titleBar;
    private final Vector2f intersection = new Vector2f();
    
    protected List<Control> controls;
    
    protected Container(float xPos, float yPos, float width, float height, String title, int cellX, int cellY) {
        super(xPos, yPos, width, height);
        this.title = title;
        
        titleBar = new Rectangle(xPos, yPos + height - 34, width, 34);
        
        icon = new Icon(20, 20);
        icon.setSubImage(cellX, cellY);
        icon.position.set(titleBar.xPos + 7, titleBar.yPos + 7);
    }
    
    public void renderContainer(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_DARK_GRAY, uiProgram);
        background.drawRectangle(titleBar, Color.UI_SLATE_GRAY, uiProgram);
        icon.render(uiProgram);
        font.drawString(title, titleBar.xPos + 34, titleBar.yPos + 10, Color.UI_WHITE, uiProgram);
    }
    
    protected boolean controlHovered(Vector2f cursorPos) {
        return controls.stream().anyMatch(control -> control.hovered(cursorPos));
    }
    
    private void configure() {
        titleBar.xPos  = bounds.xPos;
        titleBar.yPos  = bounds.yPos + bounds.height - 34;
        titleBar.width = bounds.width;
        
        icon.position.set(bounds.xPos + 7, titleBar.yPos + 7);
        
        controls.forEach(control -> control.relocate(bounds.xPos, bounds.yPos));
    }
    
    @Override
    public void relocate(float windowWidth, float windowHeight) {
        if(bounds.xPos + bounds.width > windowWidth) {
            if(bounds.xPos > 0) {
                float xOutOfBounds = windowWidth - (bounds.xPos + bounds.width);
                bounds.xPos = bounds.xPos + xOutOfBounds;
            } else {
                bounds.xPos = 0;
            }
        }
        
        //if(bounds.yPos - bounds.height > )
        
        configure();
    }
    
    public void drag(Mouse mouse) {
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

}