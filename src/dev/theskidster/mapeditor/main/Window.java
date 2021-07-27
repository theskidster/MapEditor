package dev.theskidster.mapeditor.main;

import java.nio.IntBuffer;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created: Jul 26, 2021
 */

/**
 * Represents the applications window.
 * 
 * @author J Hoffman
 * @since  0.0.0
 */
final class Window {

    private int xPos;
    private int yPos;
    private int width;
    private int height;
    
    public final long handle;
    
    private boolean minimized;
    private boolean mouseLeftHeld;
    private boolean mouseMiddleHeld;
    private boolean mouseRightHeld;
    
    final String title;
    
    /**
     * Creates a new object that represents the applications window.
     * 
     * @param title   the title that will display at the top of the window
     * @param monitor the monitor the window will appear on
     * @param width   the width of the window
     * @param height  the height of the window
     */
    Window(String title, Monitor monitor, int width, int height) {
        this.title  = title;
        this.width  = width;
        this.height = height;
        
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer xStartBuf = stack.mallocInt(1);
            IntBuffer yStartBuf = stack.mallocInt(1);
            
            glfwGetMonitorPos(monitor.handle, xStartBuf, yStartBuf);
            
            xPos = Math.round((monitor.width - width) / 2) + xStartBuf.get();
            yPos = Math.round((monitor.height - height) / 2) + yStartBuf.get();
        }
        
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        
        handle = glfwCreateWindow(width, height, title, NULL, NULL);
    }
    
    /**
     * Exposes the window to the user.
     * 
     * @param monitor the monitor the window will appear on
     * @param vSync   if true, vertical sync will be enabled
     */
    void show(Monitor monitor, boolean vSync, Camera camera) {
        glfwSetWindowMonitor(handle, NULL, xPos, yPos, width, height, monitor.refreshRate);
        glfwSetWindowPos(handle, xPos, yPos);
        glfwSwapInterval((vSync) ? 1 : 0);
        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        glfwShowWindow(handle);
        
        glfwSetWindowPosCallback(handle, (window, xpos, ypos) -> {
            xPos = xpos;
            yPos = ypos;
        });
        
        glfwSetWindowSizeCallback(handle, (window, w, h) -> {
            width  = w;
            height = h;
            
            minimized = (w == 0) && (h == 0);
            glViewport(0, 0, width, height);
        });
        
        glfwSetCursorPosCallback(handle, (window, x, y) -> {
            if(mouseLeftHeld ^ mouseMiddleHeld ^ mouseRightHeld) {
                if(mouseMiddleHeld) camera.setPosition(x, y);
                if(mouseRightHeld)  camera.setDirection(x, y);
            } else {
                camera.prevX = x;
                camera.prevY = y;
            }
        });
        
        glfwSetMouseButtonCallback(handle, (window, button, action, mods) -> {
            switch(button) {
                case GLFW_MOUSE_BUTTON_LEFT -> {
                    
                }
                
                case GLFW_MOUSE_BUTTON_MIDDLE -> mouseMiddleHeld = (action == GLFW_PRESS);
                case GLFW_MOUSE_BUTTON_RIGHT  -> mouseRightHeld = (action == GLFW_PRESS);
            }
        });
        
        glfwSetScrollCallback(handle, (window, xOffset, yOffset) -> {
            camera.dolly((float) yOffset);
        });
    }
    
    /**
     * Obtains the current x-coordinate of the windows upper-left corner.
     * 
     * @return the x position (in pixels) of the window on the monitor
     */
    int getPosX() {
        return xPos;
    }
    
    /**
     * Obtains the current y-coordinate of the windows upper-left corner.
     * 
     * @return the y position (in pixels) of the window on the monitor
     */
    int getPosY() {
        return yPos;
    }
    
    /**
     * Obtains the current width of the windows content area.
     * 
     * @return the width (in pixels) of the content area
     */
    int getWidth() {
        return width;
    }
    
    /**
     * Obtains the current height of the windows content area.
     * 
     * @return the height (in pixels) of the content area
     */
    int getHeight() {
        return height;
    }
    
    /**
     * Obtains a value that indicates whether the window has been minimized. A
     * Window is considered minimized if its content area exhibits a width and 
     * height of zero even if its border is still visible to the user.
     * 
     * @return if true, the window has been minimized
     */
    boolean getMinimized() {
        return minimized;
    }
    
}