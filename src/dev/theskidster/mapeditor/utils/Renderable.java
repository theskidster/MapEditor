package dev.theskidster.mapeditor.utils;

import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Jul 28, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public interface Renderable {
    
    void render(GLProgram uiProgram, Background background, Font font);
    
}