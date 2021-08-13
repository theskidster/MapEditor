package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import java.util.Map;

/**
 * Created: Aug 7, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class ScrollBar extends Control {
    
    private final int length;
    
    private final float viewportLength;
    private float currTotalContentLength;
    private float prevTotalContentLength;
    private float contentOffset;
    private float prevCursorChange;
    
    public boolean parentHovered;
    
    private final Rectangle topBtn;
    private final Rectangle slider;
    private final Rectangle botBtn;
    
    private Color topBtnColor = Color.UI_SLATE_GRAY;
    private Color sliderColor = Color.UI_MEDIUM_GRAY;
    private Color botBtnColor = Color.UI_SLATE_GRAY;
    
    private final Icon topIcon;
    private final Icon botIcon;
    
    public ScrollBar(float xOffset, float yOffset, int length, float viewportLength) {
        super(xOffset, yOffset + 15, 15, length - 30);
        this.length         = length;
        this.viewportLength = viewportLength;
        
        topBtn = new Rectangle(xOffset, yOffset, 15, 15);
        slider = new Rectangle(xOffset, yOffset, 15, 30);
        botBtn = new Rectangle(xOffset, yOffset, 15, 15);
        
        topIcon = new Icon(20, 20, 0, 3);
        botIcon = new Icon(20, 20, 1, 3);
    }

    private void scroll(float change, float contentScale) {
        boolean minLimitReached = (slider.yPos + slider.height) + change > bounds.yPos + bounds.height;
        boolean maxLimitReached = slider.yPos + change < bounds.yPos;
        
        if(!minLimitReached && !maxLimitReached) {
            slider.yPos += change;
        }
    }
    
    @Override
    public Command update(Mouse mouse) {
        float contentScale = currTotalContentLength / viewportLength;
        
        if(contentScale <= 1) {
            slider.yPos   = bounds.yPos + ((bounds.height - slider.height) / 2);
            slider.height = bounds.height;
        } else {
            slider.height = length / contentScale;
            
            float change = 0;
            
            if(slider.contains(mouse.cursorPos) && mouse.clicked && mouse.button.equals("left")) {
                change = mouse.cursorPos.y - prevCursorChange;
                scroll(change, contentScale);
            } else if(parentHovered && mouse.scrolled) {
                change = mouse.scrollValue * 5;
                scroll(change, contentScale);
            } else if(topBtn.contains(mouse.cursorPos) && mouse.clicked && mouse.button.equals("left")) {
                scroll(2, contentScale);
            } else if(botBtn.contains(mouse.cursorPos) && mouse.clicked && mouse.button.equals("left")) {
                scroll(-2, contentScale);
            }
            
            prevCursorChange = mouse.cursorPos.y;
        }
        
        float scaleFactor = ((currTotalContentLength / slider.height) / contentScale);
        contentOffset     = (slider.yPos - bounds.yPos) * (1 + scaleFactor);
        
        topBtnColor = (topBtn.contains(mouse.cursorPos)) ? Color.UI_MEDIUM_GRAY : Color.UI_SLATE_GRAY;
        sliderColor = (slider.contains(mouse.cursorPos)) ? Color.UI_LIGHT_GRAY : Color.UI_MEDIUM_GRAY;
        botBtnColor = (botBtn.contains(mouse.cursorPos)) ? Color.UI_MEDIUM_GRAY : Color.UI_SLATE_GRAY;
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        background.drawRectangle(bounds, Color.UI_SLATE_GRAY, uiProgram);
        background.drawRectangle(topBtn, topBtnColor, uiProgram);
        background.drawRectangle(slider, sliderColor, uiProgram);
        background.drawRectangle(botBtn, botBtnColor, uiProgram);
        
        topIcon.render(uiProgram);
        botIcon.render(uiProgram);
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        bounds.xPos = parentPosX + xOffset;
        bounds.yPos = parentPosY + yOffset;
        
        topBtn.xPos = bounds.xPos;
        topBtn.yPos = bounds.yPos + bounds.height;
        topIcon.position.set(topBtn.xPos - 2, topBtn.yPos - 2);
        
        slider.xPos = bounds.xPos;
        
        botBtn.xPos = bounds.xPos;
        botBtn.yPos = bounds.yPos - 15;
        botIcon.position.set(botBtn.xPos - 2, botBtn.yPos - 3);
    }
    
    public void setContentLength(Map<Integer, Float> widgetLengths) {
        prevTotalContentLength = currTotalContentLength;
        currTotalContentLength = 0;
                
        widgetLengths.forEach((index, elementLength) -> {
            currTotalContentLength += elementLength;
        });
    }
    
    public void setContentLength(float length) {
        currTotalContentLength = length;
    }
    
    public int getContentScrollOffset() {
        return (int) -(contentOffset);
    }
    
}