package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.utils.Observable;
import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;

/**
 * Created: Jul 28, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public final class Mouse {

    public float scrollValue;
    
    public boolean clicked;
    public boolean scrolled;
    
    public String button = "";
    public Vector2f cursorPos = new Vector2f();
    
    private final Observable observable;
    
    Mouse(Window window) {
        observable = new Observable(this);
        observable.properties.put("cursorShape", GLFW_ARROW_CURSOR);
        observable.addObserver(window);
    }
    
    public void setCursorShape(int cursorShape) {
        observable.notifyObservers("cursorShape", cursorShape);
    }
    
}