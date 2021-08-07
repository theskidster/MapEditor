package dev.theskidster.mapeditor.widgets;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Relocatable;
import dev.theskidster.mapeditor.utils.Renderable;
import dev.theskidster.mapeditor.utils.Updatable;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 6, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class ScrollBar extends Widget implements Updatable, Renderable, Relocatable {

    public ScrollBar(float xPos, float yPos, float width, float height) {
        super(xPos, yPos, width, height);
    }

    @Override
    public Command update(Mouse mouse) {
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        
    }
    
}