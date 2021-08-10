package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.controls.Control;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 9, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
class MenuBar extends Control {

    MenuBar() {
        super(0, 0, 0, 28);
    }
    
    @Override
    public Command update(Mouse mouse) {
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_DARK_GRAY, uiProgram);
    }

    @Override
    public void relocate(float windowWidth, float windowHeight) {
        bounds.yPos  = windowHeight - bounds.height;
        bounds.width = windowWidth;
        
    }
    
}