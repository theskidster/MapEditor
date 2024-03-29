package dev.theskidster.mapeditor.graphics;

import java.util.HashMap;
import java.util.Map;
import org.joml.Vector2f;
import org.joml.Vector2i;

/**
 * Created: Jul 30, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public final class Atlas {

    public final int cellWidth;
    public final int cellHeight;
    public final int rows;
    public final int columns;
    public final int subImageCount;
    
    public final float subImageWidth;
    public final float subImageHeight;
    
    public final Vector2f texCoords = new Vector2f();
    
    public final Map<Vector2i, Vector2f> subImageOffsets;
    
    public Atlas(Texture texture, int cellWidth, int cellHeight) {
        this.cellWidth  = cellWidth;
        this.cellHeight = cellHeight;
        
        subImageWidth  = (float) cellWidth / texture.getWidth();
        subImageHeight = (float) cellHeight / texture.getHeight();
        rows           = texture.getWidth() / cellWidth;
        columns        = texture.getHeight() / cellHeight;
        subImageCount  = rows * columns;
        
        subImageOffsets = new HashMap<>() {{
            for(int x = 0; x < rows; x++) {
                for(int y = 0; y < columns; y++) {
                    put(new Vector2i(x, y), new Vector2f(subImageWidth * x, subImageHeight * y));
                }
            }
        }};
    }
    
}