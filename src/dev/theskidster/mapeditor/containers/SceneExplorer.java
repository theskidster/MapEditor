package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.controls.CheckBox;
import dev.theskidster.mapeditor.controls.LabelButton;
import dev.theskidster.mapeditor.controls.ScrollBar;
import dev.theskidster.mapeditor.controls.TextArea;
import dev.theskidster.mapeditor.controls.TreeGroup;
import dev.theskidster.mapeditor.controls.TreeView;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.scene.GameObject;
import dev.theskidster.mapeditor.scene.Scene;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;

/**
 * Aug 15, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public final class SceneExplorer extends Container implements PropertyChangeListener {
    
    private Scene scene;
    
    private ScrollBar scrollBar;
    private TreeView treeView;
    private LabelButton addButton;
    private LabelButton removeButton;
    private TextArea textArea;
    private CheckBox checkBox;
    
    public SceneExplorer(float xPos, float yPos, Scene scene) {
        super(xPos, yPos, 360, 423, "Scene Explorer", 3, 4);
        this.scene = scene;
        
        TreeGroup[] groups = {
            new TreeGroup("Visible Geometry", 0, scene.visibleGeometry),
            new TreeGroup("Bounding Volumes", 1, scene.boundingVolumes),
            new TreeGroup("Trigger Boxes",    2, scene.triggerBoxes),
            new TreeGroup("Light Sources",    3, scene.lightSources),
            new TreeGroup("Entities",         4, scene.entities),
            new TreeGroup("Instances",        5, scene.instances)
        };
        
        scrollBar    = new ScrollBar(330, 114, 260, 260);
        treeView     = new TreeView(15, 114, 315, 260, scrollBar, groups, this);
        textArea     = new TextArea(188, 65, 155, null, true);
        checkBox     = new CheckBox(197, 22, false);
        addButton    = new LabelButton(15, 65, 92, "Add", null);
        removeButton = new LabelButton(15, 20, 92, "Remove", null);
        
        controls = new ArrayList<>() {{
            add(treeView);
            add(textArea);
            add(checkBox);
            add(addButton);
            add(removeButton);
        }};
    }

    @Override
    protected Command updateAdjunct(Mouse mouse) {        
        controls.forEach(control -> control.update(mouse));
        
        if(scene.selectedObject != null) {
            scene.selectedObject.setName(textArea.getText());
            scene.selectedObject.setHidden(checkBox.getValue());
        }
        
        if(!controlHovered(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_ARROW_CURSOR);
        }
        
        return null;
    }

    @Override
    protected void renderAdjunct(GLProgram uiProgram, Background background, Font font) {
        controls.forEach(control -> control.render(uiProgram, background, font));
        font.drawString("Name:", bounds.xPos + 120, bounds.yPos + 73, Color.UI_WHITE, uiProgram);
        font.drawString("Hidden:", bounds.xPos + 120, bounds.yPos + 27, Color.UI_WHITE, uiProgram);
        
        if(scene.selectedObject != null) {
            font.drawString("ID: (" + scene.selectedObject.index + ")", 
                            bounds.xPos + 260, 
                            bounds.yPos + 27, 
                            Color.UI_WHITE, 
                            uiProgram);
        }
    }

    @Override
    protected void relocateAdjunct(float windowWidth, float windowHeight) {
        controls.forEach(control -> control.relocate(bounds.xPos, bounds.yPos));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()) {
            case "gameObject" -> {
                if(evt.getNewValue() != null) {
                    GameObject selectedObject = (GameObject) evt.getNewValue();
                    scene.selectedObject      = selectedObject;
                    
                    if(!selectedObject.getName().equals("World Light")) {
                        textArea.disabled = false;
                        checkBox.disabled = false;
                        textArea.setText(selectedObject.getName());
                        checkBox.setValue(selectedObject.getHidden());
                    }
                } else {
                    if(!textArea.disabled) {
                        scene.selectedObject = null;
                        
                        textArea.setText("");
                        checkBox.setValue(false);
                        textArea.disabled = true;
                        checkBox.disabled = true;
                    }
                }
            }
        }
    }
    
}
