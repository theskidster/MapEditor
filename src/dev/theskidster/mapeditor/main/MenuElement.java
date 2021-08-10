package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 10, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
abstract class MenuElement {
    
    protected float xOffset;
    protected float yOffset;
    
    protected boolean prevPressed;
    protected boolean currPressed;
    protected boolean hovered;
    protected boolean clicked;
    
    abstract void update(Mouse mouse);
    
    abstract void render(GLProgram uiProgram, Background background, Font font);
    
}