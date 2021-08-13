package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.scene.GameObject;
import dev.theskidster.shadercore.GLProgram;
import java.util.Map;

/*
 * Created: Aug 11, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
class TreeGroup extends Control {

    private final int index;
    
    private final String name;
    private final TreeView treeView;
    
    public TreeGroup(float treeViewWidth, String name, int index, TreeView treeView, Map<Integer, GameObject> gameObjects) {
        super(0, 0, treeViewWidth, 28);
        this.name     = name;
        this.index    = index;
        this.treeView = treeView;
    }

    @Override
    public Command update(Mouse mouse) {
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
    }

}
