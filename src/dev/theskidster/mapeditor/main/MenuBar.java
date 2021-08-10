package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.controls.Control;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import java.util.ArrayList;
import java.util.Arrays;
import org.joml.Vector2i;

/**
 * Created: Aug 9, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
class MenuBar extends Control {

    private int currMenuIndex;
    
    boolean openSubMenus;
    private final boolean[] activeMenu = new boolean[5];
    
    private final ArrayList<MenuOption> buttons;
    
    MenuBar() {
        super(0, 0, 0, 28);
        
        //Initialize menu bar buttons
        {            
            Vector2i padding = new Vector2i(8, 2);
            
            buttons = new ArrayList<>() {{
                add(new MenuOption("File",  new Rectangle(0,   0, 46, bounds.height), padding));
                add(new MenuOption("Edit",  new Rectangle(45,  0, 50, bounds.height), padding));
                add(new MenuOption("View",  new Rectangle(94,  0, 54, bounds.height), padding));
                add(new MenuOption("Map",   new Rectangle(147, 0, 52, bounds.height), padding));
                add(new MenuOption("Tools", new Rectangle(198, 0, 59, bounds.height), padding));
            }};
        }
        
    }
    
    private void setActiveMenu(int index) {
        for(int m = 0; m < buttons.size(); m++) {
            activeMenu[m] = (m == index);
            if(m == index) currMenuIndex = index;
        }
    }
    
    void resetState() {
        openSubMenus = false;
        Arrays.fill(activeMenu, false);
    }
    
    @Override
    public Command update(Mouse mouse) {
        for(int m = 0; m < buttons.size(); m++) {
            buttons.get(m).update(mouse, this, activeMenu[m]);
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_DARK_GRAY, uiProgram);
        
        buttons.forEach(button -> button.render(uiProgram, background, font));
        
    }

    @Override
    public void relocate(float windowWidth, float windowHeight) {
        bounds.yPos  = windowHeight - bounds.height;
        bounds.width = windowWidth;
        
        buttons.forEach(button -> {
            button.bounds.yPos = bounds.yPos;
        });
    }
    
}