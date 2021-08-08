package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.commands.CommandHistory;
import dev.theskidster.mapeditor.tabs.Tab;
import dev.theskidster.mapeditor.tabs.TestTab;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.utils.TextInput;
import dev.theskidster.shadercore.GLProgram;
import java.util.LinkedHashSet;
import org.joml.Matrix4f;
import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
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
public final class UI {

    private static float viewportHeight;
    
    private Font font;
    private final Mouse mouse;
    private static TextInput textInput;
    
    private final Matrix4f projMatrix   = new Matrix4f();
    private final Background background = new Background();
    
    private final LinkedHashSet<Tab> tabs;
    
    UI(Window window, String fontFilename, int fontSize) {
        mouse = new Mouse(window);
        
        setFont(fontFilename, fontSize);
        
        tabs = new LinkedHashSet<>() {{
            add(new TestTab(window.getWidth(), window.getHeight()));
        }};
        
        configure(window.getWidth(), window.getHeight());
    }
    
    void configure(int windowWidth, int windowHeight) {
        viewportHeight = windowHeight;
        
        tabs.forEach(tab -> tab.relocate(windowWidth, windowHeight));
        
        projMatrix.setOrtho(0, windowWidth, 0, windowHeight, 0, Integer.MAX_VALUE);
    }
    
    void update(CommandHistory cmdHistory) {
        tabs.forEach(tab -> {
            Command command = tab.update(mouse);
            if(command != null) cmdHistory.executeCommand(command);
        });
        
        tabs.removeIf(tab -> tab.removalRequested());
        
        if(!tabHovered()) mouse.setCursorShape(GLFW_ARROW_CURSOR);
        
        mouse.scrolled = false;
    }
    
    void render(GLProgram uiProgram) {
        uiProgram.setUniform("uProjection", false, projMatrix);
        tabs.forEach(tab -> tab.render(uiProgram, background, font));
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
    
    void captureKeyInput(int key, int action) {
        if(textInput != null && textInput.hasFocus()) {
            textInput.processKeyInput(key, action);
        }
    }
    
    public static void setTextInputWidget(TextInput input) {
        textInput = input;
    }
    
    public static float getViewportHeight() {
        return viewportHeight;
    }
    
    public boolean tabHovered() {
        return tabs.stream().anyMatch(tab -> tab.hovered(mouse.cursorPos));
    }
    
}