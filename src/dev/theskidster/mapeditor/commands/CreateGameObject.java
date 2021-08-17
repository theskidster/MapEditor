/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.theskidster.mapeditor.commands;

import dev.theskidster.mapeditor.scene.GameObject;
import java.util.AbstractMap;
import java.util.Map;

/**
 *
 * @author thesk
 */
public class CreateGameObject extends Command {
    
    private final Map.Entry<Integer, GameObject> entry;
    private final Map<Integer, GameObject> collection;
    
    public CreateGameObject(Map<Integer, GameObject> collection, GameObject gameObject) {
        this.collection = collection;
        entry = new AbstractMap.SimpleEntry(gameObject.index, gameObject);
    }
    
    @Override
    void execute() {
        collection.put(entry.getKey(), entry.getValue());
    }

    @Override
    void undo() {
        collection.remove(entry.getKey());
    }
    
}
