package dev.theskidster.mapeditor.containers;

import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.mapeditor.utils.Relocatable;
import dev.theskidster.mapeditor.utils.Renderable;
import dev.theskidster.mapeditor.utils.Updatable;
import dev.theskidster.mapeditor.widgets.Widget;
import dev.theskidster.shadercore.GLProgram;
import java.util.List;
import org.joml.Vector2f;

/**
 * Created: Jul 30, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public abstract class Container extends Widget implements Updatable, Renderable, Relocatable {

    protected String title;
    protected Icon icon;
    protected Rectangle titleBar;
    
    public List<Widget> widgets;
    
    protected Container(float xPos, float yPos, float width, float height, String title, int cellX, int cellY) {
        super(xPos, yPos, width, height);
        this.title = title;
        
        titleBar = new Rectangle(xPos, yPos + height, width, 34);
        
        icon = new Icon(20, 20);
        icon.setSubImage(cellX, cellY);
        icon.position.set(titleBar.xPos + 8, titleBar.yPos + 7);
    }
    
    protected void renderTitleBar(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(titleBar, Color.UI_BLUE, uiProgram);
        icon.render(uiProgram);
        font.drawString(title, titleBar.xPos + 36, titleBar.yPos + 10, Color.WHITE, uiProgram);
    }
    
    protected void relocateTitleBar() {
        titleBar.xPos  = bounds.xPos;
        titleBar.yPos  = bounds.yPos;
        titleBar.width = bounds.width;
        
        icon.position.set(bounds.xPos + 10, bounds.yPos + 30);
    }
    
    protected boolean widgetHovered(Vector2f cursorPos) {
        return widgets.stream().anyMatch(widget -> widget.hovered(cursorPos));
    }

}