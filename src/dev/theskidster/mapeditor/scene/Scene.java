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

    public final Map<Integer, GameObject> visibleGeometry = new HashMap<>();
    public final Map<Integer, GameObject> boundingVolumes = new HashMap<>();
    public final Map<Integer, GameObject> triggerBoxes    = new HashMap<>();
    public final Map<Integer, GameObject> lightSources    = new HashMap<>();
    public final Map<Integer, GameObject> entities        = new HashMap<>();
    public final Map<Integer, GameObject> instances       = new HashMap<>();
    
    public Scene(String name) {
        //Just some temporary stuff to test the UI.
        
        visibleGeometry.put(0, new GameObject("geom 1"));
        visibleGeometry.put(1, new GameObject("geom 2"));
        visibleGeometry.put(2, new GameObject("geom 3"));
        visibleGeometry.put(3, new GameObject("geom 4"));
        
        boundingVolumes.put(0, new GameObject("volume 1"));
        boundingVolumes.put(1, new GameObject("volume 2"));
        
        triggerBoxes.put(0, new GameObject("trigger 1"));
        
        lightSources.put(0, new GameObject("light 1"));
        lightSources.put(1, new GameObject("light 2"));
        lightSources.put(2, new GameObject("light 3"));
        lightSources.put(3, new GameObject("light 4"));
        
        entities.put(0, new GameObject("entity 1"));
        entities.put(1, new GameObject("entity 2"));
        entities.put(2, new GameObject("entity 3"));
        entities.put(3, new GameObject("entity 4"));
        entities.put(4, new GameObject("entity 5"));
        
        instances.put(0, new GameObject("instance 1"));
        instances.put(1, new GameObject("instance 2"));
        instances.put(2, new GameObject("instance 3"));
        instances.put(3, new GameObject("instance 4"));
        instances.put(4, new GameObject("instance 5"));
        instances.put(5, new GameObject("instance 6"));
        instances.put(6, new GameObject("instance 7"));
        instances.put(7, new GameObject("instance 8"));
    }
    
    void update() {
        
    }
    
    void render() {
        
    }
    
}