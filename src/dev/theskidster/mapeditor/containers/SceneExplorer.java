package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.controls.CheckBox;
import dev.theskidster.mapeditor.controls.LabelButton;
import dev.theskidster.mapeditor.controls.ScrollBar;
import dev.theskidster.mapeditor.controls.TextArea;
import dev.theskidster.mapeditor.controls.TreeGroup;
import dev.theskidster.mapeditor.controls.TreeView;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.scene.Scene;
import dev.theskidster.shadercore.GLProgram;
import java.util.ArrayList;
import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;

/**
 * Aug 15, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public final class SceneExplorer extends Container {

    private ScrollBar scrollBar;
    private TreeView treeView;
    private LabelButton addButton;
    private LabelButton removeButton;
    private TextArea textArea;
    private CheckBox checkBox;
    
    public SceneExplorer(float xPos, float yPos, Scene scene) {
        super(xPos, yPos, 360, 414, "Scene Explorer", 3, 4);
        
        TreeGroup[] groups = {
            new TreeGroup("Visible Geometry", 0, scene.visibleGeometry),
            new TreeGroup("Bounding Volumes", 1, scene.boundingVolumes),
            new TreeGroup("Trigger Boxes",    2, scene.triggerBoxes),
            new TreeGroup("Light Sources",    3, scene.lightSources),
            new TreeGroup("Entities",         4, scene.entities),
            new TreeGroup("Instances",        5, scene.instances)
        };
        
        scrollBar = new ScrollBar(330, 105, 260, 260);
        treeView  = new TreeView(15, 105, 312, 260, scrollBar, groups);
        
        controls = new ArrayList<>() {{
            add(treeView);
            //add(addButton);
            //add(removeButton);
        }};
    }

    @Override
    protected Command updateAdjunct(Mouse mouse) {
        controls.forEach(control -> control.update(mouse));
        
        if(!controlHovered(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_ARROW_CURSOR);
        }
        
        return null;
    }

    @Override
    protected void renderAdjunct(GLProgram uiProgram, Background background, Font font) {
        controls.forEach(control -> control.render(uiProgram, background, font));
    }

    @Override
    protected void relocateAdjunct(float windowWidth, float windowHeight) {
        controls.forEach(control -> control.relocate(bounds.xPos, bounds.yPos));
    }
    
}
