package dev.theskidster.mapeditor.widgets;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.TextInput;
import dev.theskidster.shadercore.GLProgram;
import static org.lwjgl.glfw.GLFW.GLFW_IBEAM_CURSOR;

/**
 * Created: Aug 3, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class TextArea extends TextInput {

    public TextArea(float xOffset, float yOffset, float width) {
        super(xOffset, yOffset, width);
    }

    @Override
    public void processKeyInput(int key, int action) {
        
    }

    @Override
    public Command update(Mouse mouse) {
        if(hovered(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_IBEAM_CURSOR);
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.SILVER, uiProgram);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = xOffset + parentPosX;
        bounds.yPos = yOffset + parentPosY;
    }

}