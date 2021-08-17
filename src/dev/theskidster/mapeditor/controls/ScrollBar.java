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
    
    private final float viewportLength;
    private float sliderOffsetY;
    private float sliderPrevHeight;
    private float currContentLength;
    private float prevContentLength;
    private float contentOffset;
    private float prevCursorChange;
    
    public boolean parentHovered;
    private boolean initialUpdate = true;
    
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
        this.viewportLength = viewportLength;
        
        topBtn = new Rectangle(xOffset, yOffset, 15, 15);
        slider = new Rectangle(xOffset, yOffset, 15, 30);
        botBtn = new Rectangle(xOffset, yOffset, 15, 15);
        
        topIcon = new Icon(20, 20, 0, 3);
        botIcon = new Icon(20, 20, 1, 3);
    }

    private void scroll(float change) {
        boolean minLimitReached = (slider.yPos + slider.height) + change > bounds.yPos + bounds.height;
        boolean maxLimitReached = slider.yPos + change < bounds.yPos;
        
        if(!minLimitReached && !maxLimitReached) {
            slider.yPos += change;
        }
    }
    
    @Override
    public Command update(Mouse mouse) {
        float contentScale = currContentLength / viewportLength;
        
        if(initialUpdate) {
            slider.yPos = bounds.yPos + Math.abs(slider.height - bounds.height);
            initialUpdate = false;
        }
        
        sliderPrevHeight = slider.height;
        
        if(contentScale <= 1) {
            slider.height = bounds.height;
            slider.yPos   = bounds.yPos;
        } else {
            float change = 0;
            slider.height = bounds.height / contentScale;
            
            if(slider.contains(mouse.cursorPos) && mouse.clicked && mouse.button.equals("left")) {
                change = mouse.cursorPos.y - prevCursorChange;
                scroll(change);
            } else if(parentHovered && mouse.scrolled || bounds.contains(mouse.cursorPos) && mouse.scrolled) {
                change = mouse.scrollValue * 5;
                scroll(change);
            } else if(topBtn.contains(mouse.cursorPos) && mouse.clicked && mouse.button.equals("left")) {
                scroll(2);
            } else if(botBtn.contains(mouse.cursorPos) && mouse.clicked && mouse.button.equals("left")) {
                scroll(-2);
            }
            
            prevCursorChange = mouse.cursorPos.y;
            
            /*
            Included this hack just in case the slider manages to extend beyond 
            the top of the scroll bar when a group is collapsed.
            */
            if(prevContentLength != currContentLength) {
                if(slider.yPos + slider.height > bounds.yPos + bounds.height) {
                    slider.yPos = bounds.yPos + Math.abs(slider.height - bounds.height);
                }
            }
        }
        
        /*
        Another hack that will offset the position of the slider whenever the 
        size of the content has changed.
        */
        if(prevContentLength != currContentLength && prevContentLength < currContentLength) {
            slider.yPos += (sliderPrevHeight - slider.height);
        }
        
        float scaleFactor = ((currContentLength / slider.height) / contentScale);
        float excess      = viewportLength - 28;
        
        contentOffset = (((slider.yPos + slider.height) - (bounds.yPos + bounds.height)) * scaleFactor) - excess;
        sliderOffsetY = (slider.yPos - bounds.yPos);
        
        sliderColor = (slider.contains(mouse.cursorPos)) ? Color.UI_LIGHT_GRAY : Color.UI_MEDIUM_GRAY;
        
        if(topBtn.contains(mouse.cursorPos) && mouse.clicked && mouse.button.equals("left")) {
            topBtnColor = Color.UI_LIGHT_GRAY;
        } else {
            topBtnColor = (topBtn.contains(mouse.cursorPos)) ? Color.UI_MEDIUM_GRAY : Color.UI_SLATE_GRAY;
        }
        
        if(botBtn.contains(mouse.cursorPos) && mouse.clicked && mouse.button.equals("left")) {
            botBtnColor = Color.UI_LIGHT_GRAY;
        } else {
            botBtnColor = (botBtn.contains(mouse.cursorPos)) ? Color.UI_MEDIUM_GRAY : Color.UI_SLATE_GRAY;
        }
        
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
        slider.yPos = bounds.yPos + sliderOffsetY;
        
        botBtn.xPos = bounds.xPos;
        botBtn.yPos = bounds.yPos - 15;
        botIcon.position.set(botBtn.xPos - 2, botBtn.yPos - 3);
    }
    
    public void setContentLength(Map<Integer, Float> groupLengths) {
        prevContentLength = currContentLength;
        currContentLength = 0;
        
        groupLengths.forEach((index, memberLength) -> {
            currContentLength += memberLength;
        });
    }
    
    public void setContentLength(float length) {
        prevContentLength = currContentLength;
        currContentLength = length;
    }
    
    public int getContentScrollOffset() {
        return (int) -(contentOffset);
    }
    
}