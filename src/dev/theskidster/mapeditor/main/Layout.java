package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.utils.Rectangle;
import java.util.HashMap;
import java.util.Map;

/**
 * Created: Aug 7, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
class Layout {

    private Map<String, Rectangle> areas;
    
    Layout() {
        areas = new HashMap<>() {{
            put("", new Rectangle());
            put("", new Rectangle());
            put("", new Rectangle());
            put("", new Rectangle());
        }};
    }
    
}