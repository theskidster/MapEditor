package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import java.util.List;

/**
 * Created: Aug 10, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
class DropDown extends MenuElement {

    final Rectangle bounds;
    final List<MenuOption> options;
    
    DropDown(List<MenuOption> options, Rectangle bounds) {
        this.options = options;
        this.bounds  = bounds;
    }
    
    @Override
    void update(Mouse mouse) {
        hovered = bounds.contains(mouse.cursorPos);
        options.forEach(option -> option.update(mouse));
    }

    @Override
    void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_LIGHT_GRAY, uiProgram);
        options.forEach(option -> option.render(uiProgram, background, font));
    }

}