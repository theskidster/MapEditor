package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.widgets.TextArea;
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
        super((xPos / 2) - 250, (yPos / 2) - 200, 500, 400, "Title", 3, 4);
        
        textArea = new TextArea(20, 20, 120);
        
        widgets = new ArrayList<>() {{
            add(textArea);
        }};
    }

    @Override
    public Command update(Mouse mouse) {
        textArea.update(mouse);
        
        if(!widgetHovered(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_ARROW_CURSOR);
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.WHITE, uiProgram);
        renderTitleBar(uiProgram, background, font);
        
        textArea.render(uiProgram, background, font);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        textArea.relocate(bounds.xPos, bounds.yPos);
    }

}