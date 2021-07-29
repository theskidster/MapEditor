package dev.theskidster.mapeditor.commands;

/**
 * Created: Jul 28, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public abstract class Command {

    abstract void execute();
    abstract void undo();
    
}