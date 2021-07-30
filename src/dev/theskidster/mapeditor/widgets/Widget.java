package dev.theskidster.mapeditor.widgets;

import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Rectangle;
import org.joml.Vector2f;

/**
 * Created: Jul 28, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public class Widget {

    private final boolean[] prevClicked = new boolean[2];
    private final boolean[] currClicked = new boolean[2];
    protected boolean remove;
    
    protected final Rectangle bounds;
    
    protected Widget(float xPos, float yPos, float width, float height) {
        bounds = new Rectangle(xPos, yPos, width, height); 
    }
    
    protected boolean clickedOnce(Rectangle rectangle, Mouse mouse) {
        if(!rectangle.contains(mouse.cursorPos)) return false;
        
        int index = (rectangle.equals(bounds)) ? 0 : 1;
        
        prevClicked[index] = currClicked[index];
        currClicked[index] = mouse.clicked;
        
        return (prevClicked[index] != currClicked[index] && !prevClicked[index]);
    }
    
    public boolean removalRequested() {
        return remove;
    }
    
    public boolean hovered(Vector2f cursorPos) {
        return bounds.contains(cursorPos);
    }
    
    public void remove() {
        remove = true;
    }
    
}