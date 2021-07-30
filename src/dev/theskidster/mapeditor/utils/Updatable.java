package dev.theskidster.mapeditor.utils;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.main.Mouse;

/**
 * Created: Jul 28, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public interface Updatable {
    
    Command update(Mouse mouse);
    
}