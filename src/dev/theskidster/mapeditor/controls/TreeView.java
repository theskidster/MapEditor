package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.scene.GameObject;
import dev.theskidster.mapeditor.utils.Observable;
import dev.theskidster.shadercore.GLProgram;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glScissor;

/**
 * Created: Aug 10, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class TreeView extends Control {

    int currGroupIndex = -1;
    
    public GameObject selectedObject;
    
    private Observable observable = new Observable(this);
    
    private final ScrollBar scrollBar;
    private final TreeGroup[] groups;
    
    private final Map<Integer, Float> groupLengths = new HashMap<>();
    
    public TreeView(float xPos, float yPos, float width, float height, ScrollBar scrollBar, TreeGroup[] groups, PropertyChangeListener observer) {
        super(xPos, yPos, width, height);
        this.scrollBar = scrollBar;
        this.groups    = groups;
        
        /*
        TODO: remove stand alone scrollbars and let the objects that need them 
              determine their position
        */
        scrollBar.bounds.xPos = bounds.xPos + bounds.width - 15;
        
        associateTreeGroups();
        
        observable.properties.put("gameObject", null);
        
        observable.addObserver(observer);
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
            
            verticalOffset -= 28 * group.getLength();
            groupLengths.put(i, 28f * group.getLength());
        }
        
        observable.notifyObservers("gameObject", selectedObject);
        
        scrollBar.setContentLength(groupLengths);
        scrollBar.parentHovered = hovered(mouse.cursorPos);
        scrollBar.update(mouse);
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_SLATE_GRAY, uiProgram);
        
        glEnable(GL_SCISSOR_TEST);
        glScissor((int) bounds.xPos, (int) bounds.yPos, (int) bounds.width, (int) bounds.height);
            for(TreeGroup group : groups) group.render(uiProgram, background, font);
        glDisable(GL_SCISSOR_TEST);
        
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