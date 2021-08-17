package dev.theskidster.mapeditor.scene;

import java.util.HashMap;
import java.util.Map;

/**
 * Created: Aug 3, 2021
 */

/**
 * @author J Hoffman
 * @since  0.0.0
 */
public final class Scene {

    public GameObject selectedObject;
    
    public final Map<Integer, GameObject> visibleGeometry = new HashMap<>();
    public final Map<Integer, GameObject> boundingVolumes = new HashMap<>();
    public final Map<Integer, GameObject> triggerBoxes    = new HashMap<>();
    public final Map<Integer, GameObject> lightSources    = new HashMap<>();
    public final Map<Integer, GameObject> entities        = new HashMap<>();
    public final Map<Integer, GameObject> instances       = new HashMap<>();
    
    public Scene(String name) {
        lightSources.put(0, new GameObject("World Light"));
    }
    
    void update() {
        
    }
    
    void render() {
        
    }
    
}