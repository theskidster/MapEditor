package dev.theskidster.mapeditor.main;

import dev.theskidster.mapeditor.graphics.Graphics;
import dev.theskidster.shadercore.GLProgram;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.MemoryStack;

/**
 * Created: Jul 27, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
final class Triangle {

    Vector3f position;
    Graphics g;
    
    Triangle(float x, float y, float z, float size) {
        position = new Vector3f(x, y, z);
        g        = new Graphics();
        
        try(MemoryStack stack = MemoryStack.stackPush()) {
            g.vertices = stack.mallocFloat(18);
            
            //(vec3 position), (vec3 color)
            g.vertices.put(-size).put(-size).put(0) .put(1).put(0).put(0);
            g.vertices.put(0)    .put(size) .put(0) .put(0).put(1).put(0);
            g.vertices.put(size) .put(-size).put(0) .put(0).put(0).put(1);
            
            g.vertices.flip();
        }
        
        g.bindBuffers();
        
        glVertexAttribPointer(0, 3, GL_FLOAT, false, (6 * Float.BYTES), 0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, (6 * Float.BYTES), (3 * Float.BYTES));
        
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
    }
    
    void update() {
        g.modelMatrix.translation(position);
    }
    
    void render(GLProgram sceneProgram) {
        glBindVertexArray(g.vao);
        
        sceneProgram.setUniform("uType", 0);
        sceneProgram.setUniform("uModel", false, g.modelMatrix);
        
        glDrawArrays(GL_TRIANGLES, 0, 3);
        App.checkGLError();
    }
    
}