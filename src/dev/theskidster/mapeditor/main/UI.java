package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.graphics.Font;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Jul 26, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
final class UI {

    private String fontFilename;
    private Font font;
    
    UI(Window window, String fontFilename) {
        this.fontFilename = fontFilename;
        
        font = new Font(fontFilename);
        
    }
    
    void render(GLProgram uiProgram) {
        
    }
    
    String getFontFilename() {
        return fontFilename;
    }
    
}