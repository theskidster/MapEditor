package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.controls.Control;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private final boolean[] activeMenu  = new boolean[5];
    private final boolean[] hoveredMenu = new boolean[5];
    
    private final ArrayList<MenuOption> buttons;
    private final HashMap<Integer, DropDown> menus = new HashMap<>();
    
    MenuBar() {
        super(0, 0, 0, 28);
        
        //Initialize menu bar buttons.
        {            
            Vector2i padding = new Vector2i(8, 1);
            
            buttons = new ArrayList<>() {{
                add(new MenuOption("File",  new Rectangle(0,   0, 46, bounds.height), padding));
                add(new MenuOption("Edit",  new Rectangle(45,  0, 50, bounds.height), padding));
                add(new MenuOption("View",  new Rectangle(94,  0, 54, bounds.height), padding));
                add(new MenuOption("Map",   new Rectangle(147, 0, 52, bounds.height), padding));
                add(new MenuOption("Tools", new Rectangle(198, 0, 59, bounds.height), padding));
            }};
        }
        
        //Initialize File drop-down options.
        {
            Rectangle[] rectangles = {
                new Rectangle(1, (bounds.height * 4) + 3, 318, bounds.height),
                new Rectangle(1, (bounds.height * 3) + 3, 318, bounds.height),
                new Rectangle(1, (bounds.height * 2) + 2, 318, bounds.height),
                new Rectangle(1, (bounds.height) + 2,     318, bounds.height),
                new Rectangle(1, 1,                       318, bounds.height)
            };
            
            Vector2i padding = new Vector2i(34, 1);
            
            ArrayList<MenuOption> options = new ArrayList<>() {{
                add(new MenuOption("New Map...",  rectangles[0], padding, new Icon(20, 20, 1, 0)));
                add(new MenuOption("Open Map...", rectangles[1], padding, new Icon(20, 20, 2, 0)));
                add(new MenuOption("Save",        rectangles[2], padding, new Icon(20, 20, 3, 0)));
                add(new MenuOption("Save As...",  rectangles[3], padding));
                add(new MenuOption("Exit",        rectangles[4], padding, new Icon(20, 20, 2, 4)));
            }};
            
            menus.put(0, new DropDown(options, new Rectangle(0, 100, 320, (bounds.height * 5) + 4)));
        }
        
        //Initialize Edit drop-down options.
        {
            menus.put(1, new DropDown(new ArrayList<>(), new Rectangle(45, bounds.height, 280, 200)));
        }
        
        //Initialize View drop-down options.
        {
            menus.put(2, new DropDown(new ArrayList<>(), new Rectangle(94, bounds.height, 315, 213)));
        }
        
        //Initialize Map drop-down options.
        {
            menus.put(3, new DropDown(new ArrayList<>(), new Rectangle(147, bounds.height, 300, 312)));
        }
        
        //Initialize Tools drop-down options.
        {
            menus.put(4, new DropDown(new ArrayList<>(), new Rectangle(198, bounds.height, 280, 350)));
        }
    }
    
    private void setActiveMenu(int index) {
        for(int m = 0; m < buttons.size(); m++) {
            activeMenu[m] = (m == index);
            if(m == index) currMenuIndex = index;
        }
    }
    
    private boolean getAnyMenuHovered() {
        for(int m = 0; m < buttons.size(); m++) {
            if(hoveredMenu[m] || menus.get(currMenuIndex).hovered) {
                return true;
            }
        }
        
        return false;
    }
    
    boolean getMenuBarActive() {
        return openSubMenus && getAnyMenuHovered();
    }
    
    void resetState() {
        openSubMenus = false;
        Arrays.fill(activeMenu, false);
    }
    
    @Override
    public Command update(Mouse mouse) {
        for(int m = 0; m < buttons.size(); m++) {
            buttons.get(m).update(mouse, this, activeMenu[m]);
            
            hoveredMenu[m] = buttons.get(m).hovered;
            if(openSubMenus && buttons.get(m).hovered) setActiveMenu(m);
        }
        
        if(openSubMenus) {
            boolean hovered = hovered(mouse.cursorPos);
            
            menus.get(currMenuIndex).update(mouse);
            
            for(MenuOption option : menus.get(currMenuIndex).options) {
                if(option.hovered) hovered = true;
                
                if(option.clicked) {
                    switch(option.text) {
                        case "New Map..." -> System.out.println("Create new map");
                        case "Exit"       -> System.out.println("Exit pressed");
                    }
                    
                    resetState();
                }
            }
            
            if(!hovered && mouse.clicked) resetState();
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_DARK_GRAY, uiProgram);
        
        buttons.forEach(button -> button.render(uiProgram, background, font));
        
        if(openSubMenus) {
            menus.get(currMenuIndex).render(uiProgram, background, font);
        }
    }

    @Override
    public void relocate(float windowWidth, float windowHeight) {
        bounds.yPos  = windowHeight - bounds.height;
        bounds.width = windowWidth;
        
        buttons.forEach(button -> {
            button.bounds.yPos = bounds.yPos;
        });
        
        menus.values().forEach(menu -> {
            menu.bounds.yPos = bounds.yPos - menu.bounds.height;
            
            menu.options.forEach(option -> {
                option.bounds.yPos = menu.bounds.yPos + option.yOffset;
                
                if(option.icon != null) {
                    option.icon.position.set(option.bounds.xPos + 7, option.bounds.yPos + 4);
                }
            });
        });
    }
    
}