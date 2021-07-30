package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.utils.Relocatable;
import dev.theskidster.mapeditor.utils.Renderable;
import dev.theskidster.mapeditor.utils.Updatable;
import dev.theskidster.mapeditor.widgets.Widget;

/**
 * Created: Jul 30, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public abstract class Container extends Widget implements Updatable, Renderable, Relocatable {

    protected String title;
    protected Icon icon;
    

}