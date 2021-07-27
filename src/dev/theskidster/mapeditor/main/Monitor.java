package dev.theskidster.mapeditor.main;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import org.lwjgl.glfw.GLFWVidMode;

/**
 * Created: Jul 26, 2021
 */

/**
 * Represents the peripheral display device the application window will appear
 * on.
 * 
 * @author J Hoffman
 * @since  0.0.0
 */
final class Monitor {

    final int width;
    final int height;
    final int refreshRate;
    
    final long handle;
    
    private final GLFWVidMode videoMode;
    
    /**
     * Parses information about the primary display of the system and provides 
     * it as an object.
     */
    Monitor() {
        handle    = glfwGetPrimaryMonitor();
        videoMode = glfwGetVideoMode(handle);
        
        width       = videoMode.width();
        height      = videoMode.height();
        refreshRate = videoMode.refreshRate();
    }
    
}