package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 7, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class ScrollBar extends Control {
    
    private Control parent;
    
    private final Rectangle topBtn;
    private final Rectangle slider;
    private final Rectangle botBtn;
    
    public ScrollBar(float xOffset, float yOffset, float length, Control parent) {
        super(xOffset, yOffset + 15, 15, length - 30);
        this.parent = parent;
        
        topBtn = new Rectangle(xOffset, yOffset, 15, 15);
        slider = new Rectangle(xOffset, yOffset, 15, 30);
        botBtn = new Rectangle(xOffset, yOffset, 15, 15);
    }

    @Override
    public Command update(Mouse mouse) {
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_SLATE_GRAY, uiProgram);
        background.drawRectangle(topBtn, Color.UI_GREEN, uiProgram);
        background.drawRectangle(slider, Color.UI_MEDIUM_GRAY, uiProgram);
        background.drawRectangle(botBtn, Color.UI_RED, uiProgram);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = parentPosX + xOffset;
        bounds.yPos = parentPosY + yOffset;
        
        topBtn.xPos = bounds.xPos;
        topBtn.yPos = bounds.yPos + bounds.height;
        
        slider.xPos = bounds.xPos;
        
        botBtn.xPos = bounds.xPos;
        botBtn.yPos = bounds.yPos - 15;
    }
    
}