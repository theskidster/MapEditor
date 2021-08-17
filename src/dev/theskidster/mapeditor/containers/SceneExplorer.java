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
import dev.theskidster.mapeditor.scene.Scene;
import dev.theskidster.mapeditor.utils.Rectangle;
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
    
    private Rectangle outline = new Rectangle(15, 15, 223, 84);
    private Rectangle overlay = new Rectangle(16, 16, 221, 82);
    
    public SceneExplorer(float xPos, float yPos, Scene scene) {
        super(xPos, yPos, 360, 423, "Scene Explorer", 3, 4);
        
        TreeGroup[] groups = {
            new TreeGroup("Visible Geometry", 0, scene.visibleGeometry),
            new TreeGroup("Bounding Volumes", 1, scene.boundingVolumes),
            new TreeGroup("Trigger Boxes",    2, scene.triggerBoxes),
            new TreeGroup("Light Sources",    3, scene.lightSources),
            new TreeGroup("Entities",         4, scene.entities),
            new TreeGroup("Instances",        5, scene.instances)
        };
        
        scrollBar    = new ScrollBar(330, 114, 260, 260);
        treeView     = new TreeView(15, 114, 312, 260, scrollBar, groups);
        textArea     = new TextArea(90, 62, 140, null, true);
        checkBox     = new CheckBox(99, 26, false);
        addButton    = new LabelButton(253, 65, 92, "Add", null);
        removeButton = new LabelButton(253, 20, 92, "Remove", null);
        
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
        outline.xPos = bounds.xPos + 15;
        outline.yPos = bounds.yPos + 15;
        overlay.xPos = bounds.xPos + 16;
        overlay.yPos = bounds.yPos + 16;
        
        controls.forEach(control -> control.update(mouse));
        
        if(!controlHovered(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_ARROW_CURSOR);
        }
        
        return null;
    }

    @Override
    protected void renderAdjunct(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(outline, Color.UI_LIGHT_GRAY, uiProgram);
        background.drawRectangle(overlay, Color.UI_DARK_GRAY, uiProgram);
        controls.forEach(control -> control.render(uiProgram, background, font));
        font.drawString("Name:", bounds.xPos + 30, bounds.yPos + 70, Color.UI_WHITE, uiProgram);
        font.drawString("Hidden:", bounds.xPos + 30, bounds.yPos + 30, Color.UI_WHITE, uiProgram);
    }

    @Override
    protected void relocateAdjunct(float windowWidth, float windowHeight) {
        outline.xPos = bounds.xPos + 15;
        outline.yPos = bounds.yPos + 15;
        overlay.xPos = bounds.xPos + 16;
        overlay.yPos = bounds.yPos + 16;
        
        controls.forEach(control -> control.relocate(bounds.xPos, bounds.yPos));
    }
    
}
