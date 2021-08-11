package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.controls.TextArea;
import dev.theskidster.mapeditor.graphics.Background;
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
public final class TestContainer extends Container {
    
    TextArea textArea;
    
    public TestContainer(int xPos, int yPos) {
        super((xPos / 3f) - 250, (yPos / 2) - 100, 360, 400, "Title", 1, 5);
        
        textArea = new TextArea(30f, 30f, 100f, null, true);
        
        controls = new ArrayList<>() {{
            add(textArea);
        }};
    }

    @Override
    public Command updateAdjunct(Mouse mouse) {
        textArea.update(mouse);
        
        if(!controlHovered(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_ARROW_CURSOR);
        }
        
        return null;
    }

    @Override
    public void renderAdjunct(GLProgram uiProgram, Background background, Font font) {
        textArea.render(uiProgram, background, font);
    }

}