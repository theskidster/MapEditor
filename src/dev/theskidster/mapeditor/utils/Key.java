package dev.theskidster.mapeditor.utils;

/**
 * Created: Aug 3, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public final class Key {
    
    private final char c;
    private final char C;

    Key(char c, char C) {
        this.c = c;
        this.C = C;
    }

    public char getChar(boolean shiftHeld) {
        return (!shiftHeld) ? c : C;
    }
    
}