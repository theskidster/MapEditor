package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.scene.GameObject;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;

/*
 * Created: Aug 11, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
class TreeMember {

    private final int groupIndex;
    
    boolean selected;
    
    private final Icon icon = new Icon(20, 20, 0, 1);
    final Rectangle bounds   = new Rectangle(0, 0, 0, 28);
    
    GameObject gameObject;
    
    TreeMember(int groupIndex) {
        this.groupIndex = groupIndex;
    }
    
    void update(GameObject gameObject, Rectangle groupBounds, int order, Mouse mouse, TreeView treeView) {
        this.gameObject = gameObject;
        
        bounds.xPos  = groupBounds.xPos;
        bounds.yPos  = groupBounds.yPos - (28 * order);
        bounds.width = treeView.bounds.width;
        
        icon.position.set(bounds.xPos + 34, bounds.yPos + 4);
        
        if(bounds.contains(mouse.cursorPos) && mouse.clicked && mouse.button.equals("left")) {
            treeView.currGroupIndex = groupIndex;
            treeView.selectedObject = gameObject;
            selected = true;
        }
        
        selected = (gameObject == treeView.selectedObject);
        
        //TODO: change icon sub image
    }
    
    void render(GLProgram uiProgram, Background background, Font font, Color fontColor) {
        if(gameObject != null) {
            if(selected) background.drawRectangle(bounds, Color.UI_MEDIUM_GRAY, uiProgram);
            
            font.drawString(gameObject.getName(), bounds.xPos + 61, bounds.yPos + 8, fontColor, uiProgram);
            icon.setColor(fontColor);
            icon.render(uiProgram);
        }
    }
    
}
