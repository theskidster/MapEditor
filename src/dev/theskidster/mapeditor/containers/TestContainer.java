package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 3, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public class TestContainer extends Container {

    public TestContainer() {
        super(0, 0, 500, 300, "Title", 5, 1);
        
        
    }

    @Override
    public Command update(Mouse mouse) {
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.EDIT_MEDIUM_GRAY, uiProgram);
        //renderTitleBar(uiProgram, background, font);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        //relocateTitleBar();
    }
    
}