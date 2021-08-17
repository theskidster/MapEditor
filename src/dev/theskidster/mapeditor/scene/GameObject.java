package dev.theskidster.mapeditor.scene;

/*
 * Created: Aug 11, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class GameObject {

    private static int indexLimit;
    public final int index;
    
    protected boolean hide;
    
    protected String name;
    
    public GameObject(String name) {
        index     = indexLimit++;
        this.name = name;
    }
    
    public boolean getHidden() {
        return hide;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setHidden(boolean hide) {
        this.hide = hide;
    }
    
}
