package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.scene.GameObject;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import java.util.Map;

/*
 * Created: Aug 11, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class TreeGroup extends Control {

    private final int index;
    
    private final String name;
    private final Icon arrowIcon; 
    private final Rectangle arrowBounds; 
    private TreeView treeView;
    private Color fontColor;
    
    public TreeGroup(String name, int index, Map<Integer, GameObject> gameObjects) {
        super(0, 0, 0, 28);
        this.name  = name;
        this.index = index;
        
        arrowIcon   = new Icon(20, 20, 4, 0);
        arrowBounds = new Rectangle(0, 0, 16, 16);
        fontColor   = Color.UI_WHITE;
    }

    void setTreeView(TreeView treeView) {
        this.treeView = treeView;
        bounds.width  = treeView.bounds.width;
    }
    
    @Override
    public Command update(Mouse mouse) {
        
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        font.drawString(name, bounds.xPos, bounds.yPos, fontColor, uiProgram);
        
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = parentPosX;
        bounds.yPos = parentPosY;
    }

}
