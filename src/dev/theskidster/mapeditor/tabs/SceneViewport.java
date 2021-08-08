package dev.theskidster.mapeditor.tabs;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 7, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class SceneViewport extends Tab {

    public SceneViewport(float xPos, float yPos, float width, float height, String title, int cellX, int cellY) {
        super(xPos, yPos, width, height, title, cellX, cellY);
    }

    @Override
    public Command update(Mouse mouse) {
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
    }

}