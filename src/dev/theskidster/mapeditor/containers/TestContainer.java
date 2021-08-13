package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.controls.ScrollBar;
import dev.theskidster.mapeditor.controls.TreeGroup;
import dev.theskidster.mapeditor.controls.TreeView;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.scene.GameObject;
import dev.theskidster.shadercore.GLProgram;
import java.util.ArrayList;
import java.util.HashMap;
import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;

/**
 * Created: Aug 3, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public final class TestContainer extends Container {
    
    TreeView treeView;
    ScrollBar scrollBar;
    
    public TestContainer(int xPos, int yPos) {
        super((xPos / 3f) - 250, (yPos / 2) - 100, 360, 400, "Title", 1, 5);
        
        var objects = new HashMap<Integer, GameObject>() {{
            put(0, new GameObject("object 1"));
            put(1, new GameObject("object 2"));
            put(2, new GameObject("object 3"));
        }};
        
        TreeGroup[] groups = new TreeGroup[3];
        groups[0] = new TreeGroup("Group 1", 0, objects);
        groups[1] = new TreeGroup("Group 2", 1, objects);
        groups[2] = new TreeGroup("Group 3", 2, objects);
        
        treeView  = new TreeView(15, 15, 200, 170, groups);
        scrollBar = new ScrollBar(230, 15, 170, 200);
        
        controls = new ArrayList<>() {{
            add(treeView);
            add(scrollBar);
        }};
    }

    @Override
    public Command updateAdjunct(Mouse mouse) {
        scrollBar.parentHovered = hovered(mouse.cursorPos);
        
        controls.forEach(control -> control.update(mouse));
        scrollBar.setContentLength(400);
        
        if(!controlHovered(mouse.cursorPos)) {
            mouse.setCursorShape(GLFW_ARROW_CURSOR);
        }
        
        return null;
    }

    @Override
    public void renderAdjunct(GLProgram uiProgram, Background background, Font font) {
        controls.forEach(control -> control.render(uiProgram, background, font));
    }

    @Override
    protected void relocateAdjunct(float windowWidth, float windowHeight) {
        controls.forEach(control -> control.relocate(bounds.xPos, bounds.yPos));
    }

}