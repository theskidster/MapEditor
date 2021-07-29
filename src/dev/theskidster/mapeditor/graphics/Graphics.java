package dev.theskidster.mapeditor.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created: Jul 27, 2021
 */

/**
 * Convenience class that contains several data buffers useful for interfacing
 * with the graphics pipeline.
 * 
 * @author J Hoffman
 * @since  0.0.0
 */
public class Graphics {

    public final int vao = glGenVertexArrays();
    public final int vbo = glGenBuffers();
    public final int ibo = glGenBuffers();
    
    public FloatBuffer vertices;
    public IntBuffer indices;
    
    public Matrix4f modelMatrix = new Matrix4f();
    
    /**
     * Sets the data buffers to occupy their corresponding binding targets. 
     * While attached, all subsequent vertex layout definitions will be bound 
     * to their respective buffers until a new target is specified.
     */
    public void bindBuffers() {
        glBindVertexArray(vao);
        
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        
        if(indices != null) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        }
    }
    
    /**
     * Frees the data buffers allocated by the graphics object. This should 
     * only be used when the object is no longer needed.
     */
    public void freeBuffers() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ibo);
    }
}