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
    
    private boolean selected;
    private boolean collapsed = true;
    
    final String name;
    private final Icon arrowIcon; 
    private final Rectangle arrowBounds;
    private TreeView treeView;
    private Color fontColor;
    private Color bgColor = Color.random();
    
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
        
        arrowBounds.xPos = bounds.xPos + 6;
        arrowBounds.yPos = bounds.yPos + 6;
        
        arrowIcon.position.set(arrowBounds.xPos - 2, arrowBounds.yPos - 2);
        arrowIcon.setColor(fontColor);
        
        if(clickedOnce(bounds, mouse) && !arrowBounds.contains(mouse.cursorPos) && mouse.button.equals("left")) {
            treeView.currGroupIndex = index;
            treeView.selectedObject = null;
            selected = true;
        }
        
        if(index == treeView.currGroupIndex) {
            fontColor = Color.YELLOW;
        } else {
            fontColor = Color.UI_WHITE; 
            selected  = false;
        }
        
        if(clickedOnce(arrowBounds, mouse) && mouse.button.equals("left")) {
            toggleCollapsed();
        }
        
        if(collapsed) arrowIcon.setSubImage(4, 0);
        else          arrowIcon.setSubImage(5, 0);
        
        if(!collapsed) {
            length = gameObjects.size();
        } else {
            length = 1;
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        if(selected) {
            background.drawRectangle(bounds, Color.UI_MEDIUM_GRAY, uiProgram);
        }
        
        font.drawString(name, bounds.xPos + 28, bounds.yPos + 8, fontColor, uiProgram);
        arrowIcon.render(uiProgram);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = parentPosX;
        bounds.yPos = parentPosY;
        
        this.parentPosY = parentPosY;
        
        arrowBounds.xPos = bounds.xPos + 6;
        arrowBounds.yPos = bounds.xPos + 6;
        
        arrowIcon.position.set(arrowBounds.xPos - 2, arrowBounds.yPos - 2);
    }
    
    public int getLength() {
        return length;
    }
    
    public void toggleCollapsed() {
        collapsed = !collapsed;
    }
    
}
