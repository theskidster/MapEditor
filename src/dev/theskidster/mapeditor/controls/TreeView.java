package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 10, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class TreeView extends Control {

    public TreeView(float xPos, float yPos, float width, float height) {
        super(xPos, yPos, width, height);
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