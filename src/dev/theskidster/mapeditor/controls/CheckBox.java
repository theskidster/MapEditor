package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.shadercore.GLProgram;

/**
 * Aug 16, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class CheckBox extends Control {

    private Icon iconBox;
    private Icon iconCheck;
    
    boolean value;
    
    public CheckBox(float xOffset, float yOffset, boolean value) {
        super(xOffset, yOffset, 20, 20);
        
        iconBox = new Icon(20, 20, 6, 6);
        iconBox.setColor(Color.WHITE);
        
        iconCheck = new Icon(20, 20, 5, 6);
    }

    @Override
    public Command update(Mouse mouse) {
        if(clickedOnce(bounds, mouse)) {
            value = !value;
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_SLATE_GRAY, uiProgram);
        iconBox.render(uiProgram);
        if(value) iconCheck.render(uiProgram);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = parentPosX + xOffset;
        bounds.yPos = parentPosY + yOffset;
        
        iconBox.position.set(bounds.xPos, bounds.yPos);
        iconCheck.position.set(bounds.xPos + 1, bounds.yPos + 2);
    }
    
    void setValue(boolean value) {
        this.value = value;
    }
    
    boolean getValue(boolean value) {
        return value;
    }
    
}
