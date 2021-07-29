package dev.theskidster.mapeditor.graphics;

import dev.theskidster.mapeditor.main.App;
import dev.theskidster.mapeditor.utils.Rectangle;
import dev.theskidster.shadercore.GLProgram;
import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.MemoryStack;

/**
 * Created: Jul 28, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public final class Background {

    private final int FLOATS_PER_RECTANGLE = 12;
    
    private final int vao = glGenVertexArrays();
    private final int vbo = glGenBuffers();
    
    public Background() {
        glBindVertexArray(vao);
        
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, FLOATS_PER_RECTANGLE * Float.BYTES, GL_DYNAMIC_DRAW);
        
        glVertexAttribPointer(0, 2, GL_FLOAT, false, (2 * Float.BYTES), 0);
        
        glEnableVertexAttribArray(0);
    }
    
    public void drawRectangle(float xPos, float yPos, float width, float height, Color color, GLProgram uiProgram) {
        glBindVertexArray(vao);
        
        uiProgram.setUniform("uType", 1);
        uiProgram.setUniform("uColor", color.asVec3());
        
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer vertexBuf = stack.mallocFloat(FLOATS_PER_RECTANGLE);
            
            //(vec2 position)
            vertexBuf.put(xPos)        .put(yPos);
            vertexBuf.put(xPos + width).put(yPos);
            vertexBuf.put(xPos + width).put(yPos + height);
            vertexBuf.put(xPos + width).put(yPos + height);
            vertexBuf.put(xPos)        .put(yPos + height);
            vertexBuf.put(xPos)        .put(yPos);
            
            vertexBuf.flip();
            
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuf);
        }
        
        glDrawArrays(GL_TRIANGLES, 0, 6);
        App.checkGLError();
    }
    
    public void drawRectangle(Rectangle r, Color color, GLProgram uiProgram) {
        drawRectangle(r.xPos, r.yPos, r.width, r.height, color, uiProgram);
    }
    
    public void freeBuffers() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
    }
    
}