package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.shadercore.GLProgram;
import org.joml.Matrix4f;

/**
 * Created: Jul 27, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
final class UI {

    private Font font;
    
    private final Matrix4f projMatrix = new Matrix4f();
    
    UI(Window window, String fontFilename, int fontSize) {
        setFont(fontFilename, fontSize);
    }
    
    void update() {
        
    }
    
    void render(GLProgram uiProgram) {
        uiProgram.setUniform("uProjection", false, projMatrix);
        font.drawString("The quick brown fox jumped over the lazy dog.", 40, 100, Color.RED, uiProgram);
    }
    
    void setFont(String filename, int size) {
        this.font = new Font(filename, size);
    }
    
    String getFontFilename() {
        return font.filename;
    }
    
    int getFontSize() {
        return font.size;
    }
    
}