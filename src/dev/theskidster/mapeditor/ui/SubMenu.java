package dev.theskidster.mapeditor.ui;

import dev.theskidster.mapeditor.graphics.TrueTypeFont;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.util.Rectangle;
import dev.theskidster.mapeditor.util.Mouse;
import dev.theskidster.mapeditor.util.Color;
import dev.theskidster.mapeditor.main.ShaderProgram;
import java.util.List;

/**
 * @author J Hoffman
 * Created: Jan 8, 2021
 */

class SubMenu extends Element {

    private final Background background;
    private final Rectangle rectangle;

    final List<ElementMenuOption> buttons;

    SubMenu(List<ElementMenuOption> buttons, Rectangle rectangle) {
        this.buttons   = buttons;
        this.rectangle = rectangle;
        
        background = new Background(buttons.size() + 1);
    }

    @Override
    void update(Mouse mouse) {
        hovered = rectangle.intersects(mouse.cursorPos);
        buttons.forEach(button -> button.update(mouse));
    }
    
    void render(ShaderProgram program, TrueTypeFont text) {
        background.batchStart();
            background.drawRectangle(rectangle, Color.RGM_LIGHT_GRAY);
            buttons.forEach(button -> button.renderBackground(background));
        background.batchEnd(program);

        buttons.forEach(button -> button.renderText(program, text));
    }

    @Override
    void renderBackground(Background background) {}

    @Override
    void renderIcon(ShaderProgram program) {}

    @Override
    void renderText(ShaderProgram program, TrueTypeFont font) {}

}