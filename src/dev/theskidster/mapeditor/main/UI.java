package dev.theskidster.mapeditor.main;

/**
 * Created: Jul 27, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
final class UI {

    private Font font;
    
    UI(Window window, String fontFilename, int fontSize) {
        setFont(fontFilename, fontSize);
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