package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.utils.Relocatable;
import dev.theskidster.mapeditor.utils.Renderable;
import dev.theskidster.mapeditor.utils.Updatable;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 8, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
class MenuBar implements Updatable, Renderable, Relocatable {

    private class SubMenu {
        
    }
    
    MenuBar() {
        
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