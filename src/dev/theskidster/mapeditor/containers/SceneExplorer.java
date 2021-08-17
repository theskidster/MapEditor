package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.commands.CreateGameObject;
import dev.theskidster.mapeditor.commands.DeleteGameObject;
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
import dev.theskidster.mapeditor.scene.VisibleGeometry;
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
    
    TreeGroup[] groups;
    
    public SceneExplorer(float xPos, float yPos, Scene scene) {
        super(xPos, yPos, 360, 423, "Scene Explorer", 3, 4);
        this.scene = scene;
        
        groups = new TreeGroup[] {
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
        } else {
            textArea.disabled = true;
            checkBox.disabled = true;
        }
        
        if(addButton.clickedOnce(mouse) && treeView.getCurrGroupIndex() != -1) {
            groups[treeView.getCurrGroupIndex()].setCollapsed(false);
            
            switch(treeView.getCurrGroupIndex()) {
                default -> { return new CreateGameObject(scene.visibleGeometry, new VisibleGeometry()); }
                case 1  -> { return new CreateGameObject(scene.boundingVolumes, new VisibleGeometry()); }
                case 2  -> { return new CreateGameObject(scene.triggerBoxes,    new VisibleGeometry()); }
                case 3  -> { return new CreateGameObject(scene.lightSources,    new VisibleGeometry()); }
                case 4  -> { return new CreateGameObject(scene.entities,        new VisibleGeometry()); }
                case 5  -> { return new CreateGameObject(scene.instances,       new VisibleGeometry()); }
            }
        }
        
        if(removeButton.clickedOnce(mouse) && treeView.getCurrGroupIndex() != -1 && 
           scene.selectedObject != null && !scene.selectedObject.getName().equals("World Light")) {
            textArea.setText("");
            checkBox.setValue(false);
            
            treeView.selectedObject = null;
            
            switch(treeView.getCurrGroupIndex()) {
                default -> { return new DeleteGameObject(scene.visibleGeometry, scene.selectedObject); }
                case 1  -> { return new DeleteGameObject(scene.boundingVolumes, scene.selectedObject); }
                case 2  -> { return new DeleteGameObject(scene.triggerBoxes,    scene.selectedObject); }
                case 3  -> { return new DeleteGameObject(scene.lightSources,    scene.selectedObject); }
                case 4  -> { return new DeleteGameObject(scene.entities,        scene.selectedObject); }
                case 5  -> { return new DeleteGameObject(scene.instances,       scene.selectedObject); }
            }
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
                    
                    checkBox.disabled = false;
                    checkBox.setValue(selectedObject.getHidden());
                    
                    if(!selectedObject.getName().equals("World Light")) {
                        textArea.disabled = false;
                        textArea.setText(selectedObject.getName());
                    } else {
                        textArea.disabled = true;
                        textArea.setText(selectedObject.getName());
                    }
                } else {
                    if(scene.selectedObject != null) {
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
