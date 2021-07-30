package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.commands.CommandHistory;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.shadercore.GLProgram;
import org.joml.Matrix4f;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created: Jul 27, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
final class UI {

    private Font font;
    private final Mouse mouse;
    
    private final Matrix4f projMatrix   = new Matrix4f();
    private final Background background = new Background();
    
    UI(Window window, String fontFilename, int fontSize) {
        mouse = new Mouse(window);
        
        setFont(fontFilename, fontSize);
        configure(window.getWidth(), window.getHeight());
    }
    
    void configure(int windowWidth, int windowHeight) {
        projMatrix.setOrtho(0, windowWidth, 0, windowHeight, 0, Integer.MAX_VALUE);
    }
    
    void update(CommandHistory cmdHistory) {
        mouse.scrolled = false;
    }
    
    void render(GLProgram uiProgram) {
        uiProgram.setUniform("uProjection", false, projMatrix);
        
        background.drawRectangle(200, 140, 120, 80, Color.BLUE, uiProgram);
        font.drawString("The quick brown fox jumped over the lazy dog.", 40, 100, Color.RED, uiProgram);
    }
    
    String getFontFilename() {
        return font.filename;
    }
    
    int getFontSize() {
        return font.size;
    }
    
    void setFont(String filename, int size) {
        this.font = new Font(filename, size);
    }
    
    void setMouseCursorPos(double xPos, double yPos) {
        mouse.cursorPos.set((int) xPos, (int) yPos);
    }
    
    void setMouseAction(int button, int action) {
        switch(button) {
            case GLFW_MOUSE_BUTTON_RIGHT  -> mouse.button = "right";
            case GLFW_MOUSE_BUTTON_MIDDLE -> mouse.button = "middle";
            default -> mouse.button = "left";
        }
        
        mouse.clicked = (action == GLFW_PRESS);
    }
    
    void setMouseScroll(double value) {
        mouse.scrollValue = (float) value;
        mouse.scrolled    = true;
    }
    
}