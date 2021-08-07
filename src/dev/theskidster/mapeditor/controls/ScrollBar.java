package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Relocatable;
import dev.theskidster.mapeditor.utils.Renderable;
import dev.theskidster.mapeditor.utils.Updatable;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 7, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class ScrollBar extends Control implements Updatable, Renderable, Relocatable {

    private Control parent;
    
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