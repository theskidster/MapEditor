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

    TextArea textArea1;
    TextArea textArea2;
    
    public TestContainer(int xPos, int yPos) {
        super((xPos / 2) - 250, (yPos / 2) - 200, 500, 400, "Title", 3, 4);
        
        textArea1 = new TextArea(20, 20, 120);
        textArea2 = new TextArea(20, 100, 300);
        
        textArea1.setText("check 123");
        textArea2.setText("/dev/theskidster/assets/");
        
        widgets = new ArrayList<>() {{
            add(textArea1);
            add(textArea2);
        }};
    }

    @Override
    public Command update(Mouse mouse) {
        textArea1.update(mouse);
        textArea2.update(mouse);
        
        if(!widgetHovered(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_ARROW_CURSOR);
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.WHITE, uiProgram);
        renderTitleBar(uiProgram, background, font);
        
        textArea1.render(uiProgram, background, font);
        textArea2.render(uiProgram, background, font);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        textArea1.relocate(bounds.xPos, bounds.yPos);
        textArea2.relocate(bounds.xPos, bounds.yPos);
    }

}