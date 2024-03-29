package dev.theskidster.mapeditor.controls;

import dev.theskidster.mapeditor.commands.Command;
import dev.theskidster.mapeditor.graphics.Background;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Icon;
import dev.theskidster.mapeditor.main.App;
import dev.theskidster.mapeditor.main.Font;
import dev.theskidster.mapeditor.main.Mouse;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.mapeditor.utils.TextInput;
import dev.theskidster.shadercore.GLProgram;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created: Aug 3, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class TextArea extends TextInput {

    private final boolean borderVisible;
    
    private Rectangle outline;
    private final Icon leftBorder  = new Icon(15, 30);
    private final Icon rightBorder = new Icon(15, 30);
    
    public TextArea(float xOffset, float yOffset, float width, String text, boolean borderVisible) {
        super(xOffset, yOffset, width);
        if(text != null) setText(text);
        this.borderVisible = borderVisible;
        
        if(borderVisible) {
            outline = new Rectangle(xOffset - 1, yOffset - 1, width + 2, bounds.height + 2);
            
            leftBorder.setSubImage(8, 1);
            leftBorder.setColor(Color.WHITE);
            rightBorder.setSubImage(9, 1);
            rightBorder.setColor(Color.WHITE);
        }
    }

    @Override
    protected void validate() {
        
    }
    
    @Override
    public void processKeyInput(int key, int action) {
        if(action == GLFW_PRESS || action == GLFW_REPEAT) {
            caratIdle  = false;
            caratBlink = true;
            caratTimer.restart();
            
            keyChars.forEach((k, c) -> {
                if(key == k) {
                    if(highlight.width > 0) {
                        int min = Math.min(firstIndex, lastIndex);
                        int max = Math.max(firstIndex, lastIndex);
                        
                        typed.replace(min, max, "");
                        
                        setIndex(min);
                        scroll();
                        
                        highlight.width = 0;
                    }
                    
                    insertChar(c.getChar(shiftHeld));
                }
            });
            
            switch(key) {
                case GLFW_KEY_BACKSPACE -> {
                    if(getIndex() > 0) {
                        if(highlight.width > 0) {
                            deleteSection();
                        } else {
                            setIndex(getIndex() - 1);
                            typed.deleteCharAt(getIndex());
                            scroll();
                        }
                    } else {
                        if(highlight.width > 0) deleteSection();
                    }
                }
                    
                case GLFW_KEY_RIGHT -> {
                    if(highlight.width > 0) {
                        setIndex(Math.max(firstIndex, lastIndex));
                        scroll();
                        highlight.width = 0;
                    } else {
                        setIndex((getIndex() > typed.length() - 1) ? typed.length() : getIndex() + 1);
                    }
                    
                    scroll();
                }
                    
                case GLFW_KEY_LEFT -> {
                    if(highlight.width > 0) {
                        setIndex(Math.min(firstIndex, lastIndex));
                        scroll();
                        highlight.width = 0;
                    } else {
                        setIndex((getIndex() <= 0) ? 0 : getIndex() - 1);
                    }
                    
                    scroll();
                }
                
                case GLFW_KEY_ENTER -> {
                    unfocus();
                }
            }
        } else {
            caratTimer.start();
        }
        
        switch(key) {
            case GLFW_KEY_LEFT_SHIFT, GLFW_KEY_RIGHT_SHIFT -> shiftHeld = action == GLFW_PRESS;
        }
    }

    @Override
    public Command update(Mouse mouse) {
        prevPressed = currPressed;
        currPressed = mouse.clicked;
        
        textPos.set(xOffset + parentPosX + PADDING, 
                    yOffset + parentPosY + 7);
        
        caratTimer.update();
        clickTimer.update();
        if(caratTimer.finished()) caratIdle = true;
        if(App.tick(0, 18) && caratIdle) caratBlink = !caratBlink;
        
        if(hovered(mouse.cursorPos) && !disabled) {
            mouse.setCursorShape(GLFW_IBEAM_CURSOR);
            
            if((prevPressed != currPressed && !prevPressed)) {
                clickCount = (!clickTimer.finished() && clickTimer.started()) ? clickCount + 1 : 0;
                int leftIndex = 0;
                
                if(clickCount > 0 && !clickTimer.finished() && hasFocus()) {
                    String[] words = typed.toString().split("\\s|/|-|\\\\");
                    int rightIndex = 0;
                    
                    for(String word : words) {
                        leftIndex  = typed.toString().indexOf(word);
                        rightIndex = leftIndex + word.length() - 1;
                        
                        if(getIndex() >= leftIndex && getIndex() <= rightIndex) {
                            break;
                        }
                    }
                    
                    setIndex(rightIndex + 1);
                    scroll();
                }
                
                clickTimer.restart();
                
                if(!hasFocus()) {
                    focus();
                    prevCursorX = (int) mouse.cursorPos.x;
                    scroll();
                }
                
                if(typed.length() > 0) {
                    if(clickCount == 0) {
                        int newIndex = findClosestIndex(mouse.cursorPos.x - bounds.xPos - PADDING);
                        setIndex(newIndex);
                        firstIndex = getIndex();
                        highlight.width = 0;
                    } else {
                        firstIndex = leftIndex;
                    }
                    
                    firstIndexSet = true;
                }
                
                scroll();
            } else {
                if(clickCount > 0) {
                    highlightText(carat.position.x);
                } else {
                    if(mouse.cursorPos.x != prevCursorX) highlightText(mouse.cursorPos.x);
                }
            }
        } else {
            if((prevPressed != currPressed && !prevPressed)) {
                if(hasFocus()) {
                    unfocus();
                    highlight.width = 0;
                }
                
                firstIndexSet = false;
            } else {
                highlightText(mouse.cursorPos.x);
            }
        }
        
        return null;
    }

    @Override
    public void render(GLProgram uiProgram, Background background, Font font) {
        if(borderVisible) background.drawRectangle(outline, Color.UI_LIGHT_GRAY, uiProgram);
        background.drawRectangle(bounds, (disabled) ? Color.UI_MEDIUM_GRAY : Color.UI_SLATE_GRAY, uiProgram);
        
        glEnable(GL_SCISSOR_TEST);
        glScissor((int) bounds.xPos, (int) bounds.yPos, (int) bounds.width, (int) bounds.height);
            background.drawRectangle(highlight, Color.UI_BLUE, uiProgram);
            font.drawString(typed.toString(), textPos.x + getTextOffset(), textPos.y, Color.UI_WHITE, uiProgram);
            if(hasFocus() && caratBlink) carat.render(uiProgram);
        glDisable(GL_SCISSOR_TEST);
        
        if(borderVisible) {
            leftBorder.render(uiProgram);
            rightBorder.render(uiProgram);
        }
    }

    @Override
    public void relocate(float parentPosX, float parentPosY) {
        this.parentPosX = parentPosX;
        this.parentPosY = parentPosY;
        
        bounds.xPos = xOffset + parentPosX;
        bounds.yPos = yOffset + parentPosY;
        
        highlight.yPos = bounds.yPos + 2;
        
        if(borderVisible) {
            outline.xPos   = bounds.xPos - 1;
            outline.yPos   = bounds.yPos - 1;
            outline.width  = bounds.width + 2;
            outline.height = bounds.height + 2;
            
            leftBorder.position.set(outline.xPos, outline.yPos);
            rightBorder.position.set(outline.xPos + (outline.width - 15), leftBorder.position.y);
        }
    }

}