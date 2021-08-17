package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;

/**
 * Created: Aug 10, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class LabelButton extends Control {

    private boolean prevPressed;
    
    String text;
    private Color color;
    private final Command command;
    private final Rectangle middle;
    private final Icon iconLeft;
    private final Icon iconRight;
    private final Vector2f textPos;
    
    public LabelButton(float xOffset, float yOffset, float width, String text, Command command) {
        super(xOffset, yOffset, width, 30);
        this.text    = text;
        this.command = command;
        
        middle = new Rectangle(xOffset + 15, yOffset, width - 30, bounds.height);
        
        iconLeft  = new Icon(15, 30, 8, 0);
        iconRight = new Icon(15, 30, 9, 0);
        
        iconLeft.position.set(xOffset, yOffset);
        iconRight.position.set(xOffset + width - 15, yOffset);
        
        textPos = new Vector2f(xOffset + 15, yOffset + 8);
    }

    @Override
    public Command update(Mouse mouse) {
        if(prevPressed != mouse.clicked) {
            if(bounds.contains(mouse.cursorPos) && (prevPressed && !mouse.clicked)) {
                prevPressed = mouse.clicked;
                return command;
            }
        }
        
        if(bounds.contains(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_HAND_CURSOR);
            color = (mouse.clicked) ? Color.UI_CYAN : Color.UI_BLUE;
        } else {
            color = Color.UI_MEDIUM_GRAY;
        }
        
        prevPressed = mouse.clicked;
        
        iconLeft.setColor(color);
        iconRight.setColor(color);
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(middle, color, uiProgram);
        iconLeft.render(uiProgram);
        iconRight.render(uiProgram);
        
        font.drawString(text, (textPos.x + (middle.width / 2)) - (Font.getLengthInPixels(text) / 2), textPos.y, Color.UI_WHITE, uiProgram);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = xOffset + parentPosX;
        bounds.yPos = yOffset + parentPosY;
        
        middle.xPos = bounds.xPos + 15;
        middle.yPos = bounds.yPos;
        
        iconLeft.position.set(bounds.xPos, bounds.yPos);
        iconRight.position.set(bounds.xPos + bounds.width - 15, bounds.yPos);
        
        textPos.set(bounds.xPos + 15, bounds.yPos + 8);
    }
    
    public boolean clickedOnce(Mouse mouse) {
        return clickedOnce(bounds, mouse);
    }

}