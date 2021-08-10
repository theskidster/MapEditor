package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import org.joml.Vector2i;

/**
 * Created: Aug 10, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
class MenuOption extends MenuElement {

    final String text;
    final Rectangle bounds;
    private final Vector2i padding;
    private Color color;
    
    MenuOption(String text, Rectangle bounds, Vector2i padding) {
        this.bounds  = bounds;
        this.text    = text;
        this.padding = padding;
        
        //TODO: provide additional constructor that displays the key shortcut?
    }
    
    void update(Mouse mouse, MenuBar menuBar, boolean active) {
        if(bounds.contains(mouse.cursorPos)) {
            hovered = true;
            prevPressed = currPressed;
            currPressed = mouse.clicked && mouse.button.equals("left");
            
            if(prevPressed != currPressed && !prevPressed) {
                menuBar.openSubMenus = !menuBar.openSubMenus;
                if(!menuBar.openSubMenus) menuBar.resetState();
            }
            
            color = (currPressed || menuBar.openSubMenus) ? Color.UI_BLUE : Color.UI_MEDIUM_GRAY;
        } else {
            hovered = false;
            color   = (active) ? Color.UI_BLUE : Color.UI_DARK_GRAY;
        }
    }

    @Override
    void update(Mouse mouse) {
        hovered = bounds.contains(mouse.cursorPos);
        color   = (hovered) ? Color.UI_BLUE : Color.UI_DARK_GRAY;
        clicked = (hovered && mouse.clicked);
    }

    @Override
    void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, color, uiProgram);
        
        xOffset = bounds.xPos + padding.x;
        yOffset = bounds.yPos + padding.y + (font.size / 2);

        font.drawString(text, xOffset, yOffset, Color.UI_WHITE, uiProgram);
    }

}