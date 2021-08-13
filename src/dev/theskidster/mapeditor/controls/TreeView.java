package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Observable;
import dev.theskidster.shadercore.GLProgram;

/**
 * Created: Aug 10, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class TreeView extends Control {

    Observable observable = new Observable(this);
    
    private final TreeGroup[] groups;
    
    public TreeView(float xPos, float yPos, float width, float height, TreeGroup[] groups) {
        super(xPos, yPos, width, height);
        this.groups = groups;
        
        associateTreeGroups();
    }
    
    final void associateTreeGroups() {
        for(TreeGroup group : groups) group.setTreeView(this);
    }

    @Override
    public Command update(Mouse mouse) {
        
        for(TreeGroup group : groups) {
            group.update(mouse);
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_SLATE_GRAY, uiProgram);
        
        for(TreeGroup group : groups) group.render(uiProgram, background, font);
        
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = parentPosX + xOffset;
        bounds.yPos = parentPosY + yOffset;
        
        for(TreeGroup group : groups) group.relocate(bounds.xPos, bounds.yPos);
    }

}