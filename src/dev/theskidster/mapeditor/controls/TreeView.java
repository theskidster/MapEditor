package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Observable;
import dev.theskidster.shadercore.GLProgram;
import java.util.HashMap;
import java.util.Map;

/**
 * Created: Aug 10, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class TreeView extends Control {

    int currGroupIndex = -1;
    
    public Object selectedObject;
    
    Observable observable = new Observable(this);
    
    private final ScrollBar scrollBar;
    private final TreeGroup[] groups;
    
    private final Map<Integer, Float> groupLengths = new HashMap<>();
    
    public TreeView(float xPos, float yPos, float width, float height, ScrollBar scrollBar, TreeGroup[] groups) {
        super(xPos, yPos, width, height);
        this.scrollBar = scrollBar;
        this.groups    = groups;
        
        /*
        TODO: remove stand alone scrollbars and let the objects that need them 
              determine their position
        */
        scrollBar.bounds.xPos = bounds.xPos + bounds.width - 15;
        
        associateTreeGroups();
    }
    
    final void associateTreeGroups() {
        for(TreeGroup group : groups) group.setTreeView(this);
    }

    @Override
    public Command update(Mouse mouse) {
        
        int verticalOffset = scrollBar.getContentScrollOffset();
        
        for(int i = 0; i < groups.length; i++) {
            TreeGroup group = groups[i];
            
            group.setVerticalOffset(verticalOffset);
            group.update(mouse);
            
            /*
            TODO:            
              - Add selectable members/groups
            
              - disable selection and interaction with members that
                fall outside of the viewport
            
              - create a scissor box around the viewport
            */
            
            verticalOffset -= 28 * group.getLength();
            groupLengths.put(i, 28f * group.getLength());
        }
        
        scrollBar.setContentLength(groupLengths);
        scrollBar.parentHovered = hovered(mouse.cursorPos);
        scrollBar.update(mouse);
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_SLATE_GRAY, uiProgram);
        
        for(TreeGroup group : groups) group.render(uiProgram, background, font);
        
        scrollBar.render(uiProgram, background, font);
        
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = parentPosX + xOffset;
        bounds.yPos = parentPosY + yOffset;
        
        for(TreeGroup group : groups) group.relocate(bounds.xPos, bounds.yPos);
        
        scrollBar.relocate(parentPosX, parentPosY);
    }

}