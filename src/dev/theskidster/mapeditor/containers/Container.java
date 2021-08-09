package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.mapeditor.controls.Control;
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

    protected String title;
    protected Icon icon;
    protected Rectangle titleBar;
    
    public List<Control> controls;
    
    protected Container(float xPos, float yPos, float width, float height, String title, int cellX, int cellY) {
        super(xPos, yPos, width, height);
        this.title = title;
        
        titleBar = new Rectangle(xPos, yPos + height - 34, width, 34);
        
        icon = new Icon(20, 20);
        icon.setSubImage(cellX, cellY);
        icon.position.set(titleBar.xPos + 7, titleBar.yPos + 7);
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
        
        titleBar.xPos  = bounds.xPos;
        titleBar.yPos  = bounds.yPos + bounds.height;
        titleBar.width = bounds.width;
        
        icon.position.set(bounds.xPos + 7, bounds.yPos + bounds.height + 7);
        
        controls.forEach(control -> control.relocate(bounds.xPos, bounds.yPos));
    }
    
    protected void renderTitleBar(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(titleBar, Color.UI_SLATE_GRAY, uiProgram);
        icon.render(uiProgram);
        font.drawString(title, titleBar.xPos + 34, titleBar.yPos + 10, Color.UI_WHITE, uiProgram);
    }
    
    protected boolean controlHovered(Vector2f cursorPos) {
        return controls.stream().anyMatch(control -> control.hovered(cursorPos));
    }
    
    public void moveTo(int xPos, int yPos) {
        bounds.xPos = xPos;
        bounds.yPos = yPos;
        
        controls.forEach(control -> control.relocate(bounds.xPos, bounds.yPos));
    }

}