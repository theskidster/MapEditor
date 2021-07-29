package dev.theskidster.mapeditor.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * Created: Jul 28, 2021
 */

/**
 * Used to relay information about state changes occurring within the 
 * implementing object to one or more observers located anywhere in the 
 * codebase.
 * 
 * @author J Hoffman
 * @since  0.0.0
 */
public final class Observable {

    private final PropertyChangeSupport observable;
    public Map<String, Object> properties = new HashMap<>();
    
    /**
     * Creates a new observable object that will look for state changes in the 
     * object provided and supply it to other parts of the codebase.
     * 
     * @param object the implementing object that will broadcast its state 
     *               changes
     */
    public Observable(Object object) {
        observable = new PropertyChangeSupport(object);
    }
    
    /**
     * Exposes the state changes of the implementing object to another class. 
     * Observer objects must implement 
     * {@link java.beans.PropertyChangeListener PropertyChangeListener}.
     * 
     * @param observer the observer class that will be notified when a state 
     *                 change occurs
     */
    public void addObserver(PropertyChangeListener observer) {
        observable.addPropertyChangeListener(observer);
    }
    
    /**
     * Revokes an observers access to view the state changes occurring within 
     * the implementing object.
     * 
     * @param observer the observer class to remove
     */
    public void removeObserver(PropertyChangeListener observer) {
        observable.removePropertyChangeListener(observer);
    }
    
    /**
     * Notifies all observers of the state changes occurring in the 
     * implementing object.
     * 
     * @param name     the name of the property to check
     * @param property the current value of the property in question
     */
    public void notifyObservers(String name, Object property) {
        observable.firePropertyChange(name, properties.get(name), property);
        properties.put(name, property);
    }
    
}