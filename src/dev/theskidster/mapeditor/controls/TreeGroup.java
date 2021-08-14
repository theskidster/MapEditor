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
    private int length;
    
    private float verticalOffset;
    private float parentPosY;
    
    private boolean collapsed = true;
    
    private final String name;
    private final Icon arrowIcon; 
    private final Rectangle arrowBounds; 
    private TreeView treeView;
    private Color fontColor;
    
    private Map<Integer, GameObject> gameObjects;
    
    public TreeGroup(String name, int index, Map<Integer, GameObject> gameObjects) {
        super(0, 0, 0, 28);
        this.name        = name;
        this.index       = index;
        this.gameObjects = gameObjects;
        
        arrowIcon   = new Icon(20, 20, 4, 0);
        arrowBounds = new Rectangle(0, 0, 16, 16);
        fontColor   = Color.UI_WHITE;
    }

    void setTreeView(TreeView treeView) {
        this.treeView = treeView;
        bounds.width  = treeView.bounds.width;
    }
    
    void setVerticalOffset(float verticalOffset) {
        this.verticalOffset = verticalOffset;
    }
    
    @Override
    public Command update(Mouse mouse) {
        bounds.yPos = parentPosY + verticalOffset;
        
        if(!collapsed) {
            length = gameObjects.size();
        } else {
            length = 1;
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        //background.drawRectangle(bounds, Color.random(), uiProgram);
        font.drawString(name, bounds.xPos, bounds.yPos + 8, fontColor, uiProgram);
        background.drawRectangle(arrowBounds, Color.RED, uiProgram);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = parentPosX;
        bounds.yPos = parentPosY;
        
        this.parentPosY = parentPosY;
    }
    
    public int getLength() {
        return length;
    }
    
}
