package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.controls.LabelButton;
import dev.theskidster.mapeditor.controls.ScrollBar;
import dev.theskidster.mapeditor.controls.TextArea;
import dev.theskidster.mapeditor.controls.TreeView;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.shadercore.GLProgram;

/**
 * Aug 15, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public final class SceneExplorer extends Container {

    private TreeView treeView;
    private ScrollBar scrollBar;
    private LabelButton AddButton;
    private LabelButton RemoveButton;
    private TextArea textArea;
    
    
    public SceneExplorer(float xPos, float yPos, float minWidth, float minHeight, String title, int cellX, int cellY) {
        super(xPos, yPos, minWidth, minHeight, title, cellX, cellY);
    }

    @Override
    protected Command updateAdjunct(Mouse mouse) {
        return null;
    }

    @Override
    protected void renderAdjunct(GLProgram uiProgram, Background background, Font font) {
    }

    @Override
    protected void relocateAdjunct(float windowWidth, float windowHeight) {
    }

    
    
}
