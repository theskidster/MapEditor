package dev.theskidster.mapeditor.utils;

import dev.theskidster.mapeditor.main.App;

/**
 * Created: Aug 3, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public final class Timer {

    public int time;
    public int speed;
    private final int initialTime;
    private int startTick;
    
    private boolean finished;
    private boolean start;
    
    public Timer(int time, int speed) {
        this.time   = time;
        this.speed  = speed;
        initialTime = time;
    }
    
    public void start() {
        start     = true;
        startTick = App.getCurrTick();
    }
    
    public boolean started()  { return start; }
    public boolean finished() { return finished; }
    
    public void update() {
        if(start) {
            if(time != 0) {
                if(App.tick(startTick, speed)) time--;
            } else {
                finished = true;
            }
        }
    }
    
    public void restart() {
        time     = initialTime;
        finished = false;
        start();
    }
    
}