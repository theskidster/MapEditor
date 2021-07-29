package dev.theskidster.mapeditor.utils;

import org.joml.Vector2f;

/**
 * Created: Jul 28, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public final class Rectangle {
    
    public float xPos;
    public float yPos;
    public float width;
    public float height;
    
    public Rectangle() {}
    
    public Rectangle(float xPos, float yPos, float width, float height) {
        this.xPos   = xPos;
        this.yPos   = yPos;
        this.width  = width;
        this.height = height;
    }
    
    public boolean contains(Vector2f point) {
        return (point.x > xPos && point.x < xPos + width) && 
               (point.y > yPos && point.y < yPos + height);
    }
    
}