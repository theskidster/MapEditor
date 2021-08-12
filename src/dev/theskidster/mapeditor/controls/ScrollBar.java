package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
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
    
    private Color topBtnColor = Color.UI_SLATE_GRAY;
    private Color sliderColor = Color.UI_MEDIUM_GRAY;
    private Color botBtnColor = Color.UI_SLATE_GRAY;
    
    private final Icon topIcon;
    private final Icon botIcon;
    
    public ScrollBar(float xOffset, float yOffset, float length, Control parent) {
        super(xOffset, yOffset + 15, 15, length - 30);
        this.parent = parent;
        
        topBtn = new Rectangle(xOffset, yOffset, 15, 15);
        slider = new Rectangle(xOffset, yOffset, 15, 30);
        botBtn = new Rectangle(xOffset, yOffset, 15, 15);
        
        topIcon = new Icon(20, 20, 0, 3);
        botIcon = new Icon(20, 20, 1, 3);
    }

    @Override
    public Command update(Mouse mouse) {
        
        topBtnColor = (topBtn.contains(mouse.cursorPos)) ? Color.UI_MEDIUM_GRAY : Color.UI_SLATE_GRAY;
        sliderColor = (slider.contains(mouse.cursorPos)) ? Color.UI_LIGHT_GRAY : Color.UI_MEDIUM_GRAY;
        botBtnColor = (botBtn.contains(mouse.cursorPos)) ? Color.UI_MEDIUM_GRAY : Color.UI_SLATE_GRAY;
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_SLATE_GRAY, uiProgram);
        background.drawRectangle(topBtn, topBtnColor, uiProgram);
        background.drawRectangle(slider, sliderColor, uiProgram);
        background.drawRectangle(botBtn, botBtnColor, uiProgram);
        
        topIcon.render(uiProgram);
        botIcon.render(uiProgram);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = parentPosX + xOffset;
        bounds.yPos = parentPosY + yOffset;
        
        topBtn.xPos = bounds.xPos;
        topBtn.yPos = bounds.yPos + bounds.height;
        topIcon.position.set(topBtn.xPos - 2, topBtn.yPos - 2);
        
        slider.xPos = bounds.xPos;
        
        botBtn.xPos = bounds.xPos;
        botBtn.yPos = bounds.yPos - 15;
        botIcon.position.set(botBtn.xPos - 2, botBtn.yPos - 3);
    }
    
}