package dev.theskidster.mapeditor.tabs;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.shadercore.GLProgram;
import java.util.ArrayList;
import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;

/**
 * Created: Aug 3, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public final class TestTab extends Tab {
    
    public TestTab(int xPos, int yPos) {
        super((xPos / 2) - 250, (yPos / 2) - 200, 500, 400, "Title", 3, 4);
        
        controls = new ArrayList<>() {{
            
        }};
    }

    @Override
    public Command update(Mouse mouse) {
        
        if(!controlHovered(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_ARROW_CURSOR);
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_MEDIUM_GRAY, uiProgram);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
    }

}