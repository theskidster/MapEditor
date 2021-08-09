package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import org.joml.Vector2f;

/**
 * Created: Jul 28, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public abstract class Control {

    private final boolean[] prevClicked = new boolean[2];
    private final boolean[] currClicked = new boolean[2];
    protected boolean remove;
    
    protected final Rectangle bounds;
    
    protected Control(float xPos, float yPos, float width, float height) {
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

    public abstract Command update(Mouse mouse);
    
    public abstract void render(GLProgram uiProgram, Background background, Font font);
    
    public abstract void relocate(float parentPosX, float parentPosY);
    
}