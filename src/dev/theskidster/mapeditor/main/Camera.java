package dev.theskidster.mapeditor.main;

import dev.theskidster.shadercore.GLProgram;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created: Jul 27, 2021
 */

/**
 * Represents a camera that can freely traverse the scene using input from the 
 * keyboard.
 * 
 * @author J Hoffman
 * @since  0.0.0
 */
final class Camera {

    private float pitch;
    private float yaw = -90f;
    
    double prevX;
    double prevY;
    
    final Vector3f position  = new Vector3f();
    final Vector3f direction = new Vector3f(0, 0, -1);
    final Vector3f up        = new Vector3f(0, 1, 0);
    
    private final Vector3f tempVec1 = new Vector3f();
    private final Vector3f tempVec2 = new Vector3f();

    private final Matrix4f viewMatrix = new Matrix4f();
    private final Matrix4f projMatrix = new Matrix4f();
    
    /**
     * Applies whatever changes that occurred to the cameras projection matrix 
     * between this frame and the last.
     * 
     * @param viewportWidth  the width (in pixels) of the viewport
     * @param viewportHeight the height (in pixels) of the viewport
     */
    void update(int viewportWidth, int viewportHeight) {
        projMatrix.setPerspective((float) Math.toRadians(60f), 
                                  (float) viewportWidth / viewportHeight, 
                                  0.1f, 
                                  Float.POSITIVE_INFINITY);
    }
    
    /**
     * Renders the scene from the perspective of the camera.
     * 
     * @param sceneProgram the shader program used for rendering the scene
     */
    void render(GLProgram sceneProgram) {
        viewMatrix.setLookAt(position, position.add(direction, tempVec1), up);
        
        sceneProgram.setUniform("uView", false, viewMatrix);
        sceneProgram.setUniform("uProjection", false, projMatrix);
    }
    
    /**
     * Applies sensitivity preferences to the cameras directional changes.
     * 
     * @param currValue   the current position of the mouse cursor along an axis
     * @param prevValue   the previous position of the mouse cursor along an 
     *                    axis
     * @param sensitivity the value used to dampen the final calculated output
     * 
     * @return a value denoting the intensity of the cameras look movement
     */
    private float getChangeIntensity(double currValue, double prevValue, float sensitivity) {
        return (float) (currValue - prevValue) * sensitivity;
    }
    
    /**
     * Sets the position of the camera.
     * 
     * @param cursorX the current x-coordinate of the mouse cursor
     * @param cursorY the current y-coordinate of the mouse cursor
     */
    public void setPosition(double cursorX, double cursorY) {
        if(cursorX != prevX || cursorY != prevY) {
            float speedX = getChangeIntensity(-cursorX, -prevX, 0.027f);
            float speedY = getChangeIntensity(-cursorY, -prevY, 0.027f);
            
            position.add(direction.cross(up, tempVec1).normalize().mul(speedX));
            
            tempVec1.set(
                    (float) (Math.cos(Math.toRadians(yaw + 90)) * Math.cos(Math.toRadians(pitch))), 
                    0, 
                    (float) (Math.sin(Math.toRadians(yaw + 90)) * Math.cos(Math.toRadians(pitch))));
            
            position.add(0, direction.cross(tempVec1, tempVec2).normalize().mul(speedY).y, 0);
            
            prevX = cursorX;
            prevY = cursorY;
        }
    }
    
    /**
     * Sets the current direction the camera will face.
     * 
     * @param cursorX the current x-coordinate of the mouse cursor
     * @param cursorY the current y-coordinate of the mouse cursor
     */
    public void setDirection(double cursorX, double cursorY) {
        if(cursorX != prevX || cursorY != prevY) {
            yaw   += getChangeIntensity(cursorX, prevX, 0.25f);
            pitch += getChangeIntensity(cursorY, prevY, 0.25f);
            
            if(pitch > 89f)  pitch = 89f;
            if(pitch < -89f) pitch = -89f;
            
            direction.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
            direction.y = (float) Math.sin(Math.toRadians(pitch)) * -1;
            direction.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
            
            prevX = cursorX;
            prevY = cursorY;
        }
    }
    
    /**
     * Moves the camera backwards and forwards along the current directions 
     * it's facing.
     * 
     * @param speed the speed at which the camera will move
     */
    public void dolly(float speed) {
        position.add(direction.mul(speed, tempVec1));
    }
    
}