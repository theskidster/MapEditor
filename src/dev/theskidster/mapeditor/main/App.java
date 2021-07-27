package dev.theskidster.mapeditor.main;

import dev.theskidster.jlogger.JLogger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created: Jul 26, 2021
 */

/**
 * Manages the state of the application in the broadest sense.
 * 
 * @author J Hoffman
 * @since  0.0.0
 */
public final class App {

    private static int tickCount = 0;
    
    private static boolean vSync = true;
    
    public static final Path   PWD     = Path.of("").toAbsolutePath();
    public static final String DOMAIN  = "mapeditor";
    public static final String VERSION = "0.0.0";
    
    private final Monitor monitor;
    private final Window window;
    
    /**
     * Initializes the applications dependencies.
     */
    App() {
        glfwInit();
        
        int windowWidth  = 1200;
        int windowHeight = 800;
        
        //Import user preferences.
        try {
            InputStream stream = new FileInputStream(PWD + "/usrpref.cfg");
            XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
            
            while(xmlReader.hasNext()) {
                switch(xmlReader.next()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        if(xmlReader.getName().getLocalPart().equals("config")) {
                            vSync        = Boolean.parseBoolean(xmlReader.getAttributeValue(null, "vSync"));
                            windowWidth  = Integer.parseInt(xmlReader.getAttributeValue(null, "windowWidth"));
                            windowHeight = Integer.parseInt(xmlReader.getAttributeValue(null, "windowHeight"));
                        }
                    }
                    
                    case XMLStreamConstants.END_ELEMENT -> {
                        if(xmlReader.getName().getLocalPart().equals("config")) {
                            xmlReader.close();
                        }
                    }
                }
            }
        } catch(FileNotFoundException | NumberFormatException | XMLStreamException e) {
            JLogger.setModule("core");
            JLogger.logWarning("Failed to load user preferences, using default configuration.", e);
            JLogger.setModule(null);
        }
        
        monitor = new Monitor();
        window  = new Window("XJGE Map Editor v" + VERSION, monitor, windowWidth, windowHeight);
        
        glfwMakeContextCurrent(window.handle);
        GL.createCapabilities();
        
    }
    
    /**
     * Exposes the window and begins running the application.
     */
    void start() {
        window.show(monitor, vSync);
        
        //Log system info.
        JLogger.newHorizontalLine();
        JLogger.logInfo("OS NAME:\t\t" + System.getProperty("os.name"));
        JLogger.logInfo("JAVA VERSION:\t" + System.getProperty("java.version"));
        JLogger.logInfo("GLFW VERSION:\t" + glfwGetVersionString());
        JLogger.logInfo("OPENGL VERSION:\t" + glGetString(GL_VERSION));
        JLogger.logInfo("APP VERSION:\t" + VERSION);
        JLogger.newHorizontalLine();
        
        final double TARGET_DELTA = 1 / 60.0;
        double prevTime = glfwGetTime();
        double currTime;
        double delta = 0;
        boolean ticked;
        
        while(!glfwWindowShouldClose(window.handle)) {
            currTime = glfwGetTime();
            
            delta += currTime - prevTime;
            if(delta < TARGET_DELTA && vSync) delta = TARGET_DELTA;
            
            prevTime = currTime;
            ticked   = false;
            
            while(delta >= TARGET_DELTA) {
                delta -= TARGET_DELTA;
                ticked = true;
                tickCount = (tickCount == Integer.MAX_VALUE) ? 0 : tickCount + 1;
                
                glfwPollEvents();
            }
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            if(!window.getMinimized()) {
                
            }
            
            glfwSwapBuffers(window.handle);
            
            if(ticked) {
                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {}
            }
        }
        
        //Export user preferences.
        try {
            FileWriter file = new FileWriter("usrpref.cfg");
            
            try(PrintWriter output = new PrintWriter(file)) {
                output.append("<config windowWidth=\"")
                      .append(window.getWidth() + "\" ")
                      .append("windowHeight=\"")
                      .append(window.getHeight() + "\" ")
                      .append("vSync=\"")
                      .append(vSync + "\">")
                      .append("</config>");
            }
        } catch(IOException e) {
            JLogger.setModule("core");
            JLogger.logWarning("Failed to export user preferences.", e);
            JLogger.setModule(null);
        }
        
    }
    
}