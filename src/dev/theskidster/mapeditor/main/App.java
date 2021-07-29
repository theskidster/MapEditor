package dev.theskidster.mapeditor.main;

import dev.theskidster.jlogger.JLogger;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.shadercore.BufferType;
import dev.theskidster.shadercore.GLProgram;
import dev.theskidster.shadercore.Shader;
import dev.theskidster.shadercore.ShaderCore;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.LinkedList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.joml.Matrix4f;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL20.*;

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

    private int tickCount = 0;
    
    private boolean vSync = true;
    
    public static final String ASSETS_PATH = "/dev/theskidster/mapeditor/assets/";
    public static final String VERSION     = "0.0.0";
    
    private final Monitor monitor;
    private final Window window;
    private final GLProgram sceneProgram;
    private final GLProgram uiProgram;
    private final Camera camera;
    private final UI ui;
    
    /**
     * Initializes the applications dependencies.
     */
    App() {
        glfwInit();
        
        int windowWidth     = 1200;
        int windowHeight    = 800;
        String fontFilename = "fnt_karla_regular.ttf";
        int fontSize        = 14;
        
        //Import user preferences.
        try {
            InputStream stream = new FileInputStream(Path.of("").toAbsolutePath() + "/usrpref.cfg");
            XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
            
            while(xmlReader.hasNext()) {
                switch(xmlReader.next()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        if(xmlReader.getName().getLocalPart().equals("config")) {
                            //TODO: uncomment these during final build, add font size and filename.
                            //windowWidth  = Integer.parseInt(xmlReader.getAttributeValue(null, "windowWidth"));
                            //windowHeight = Integer.parseInt(xmlReader.getAttributeValue(null, "windowHeight"));
                            vSync        = Boolean.parseBoolean(xmlReader.getAttributeValue(null, "vSync"));
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
        
        //Log system info.
        JLogger.newHorizontalLine();
        JLogger.logInfo("OS NAME:\t\t" + System.getProperty("os.name"));
        JLogger.logInfo("JAVA VERSION:\t" + System.getProperty("java.version"));
        JLogger.logInfo("GLFW VERSION:\t" + glfwGetVersionString());
        JLogger.logInfo("OPENGL VERSION:\t" + glGetString(GL_VERSION));
        JLogger.logInfo("APP VERSION:\t" + VERSION);
        JLogger.newHorizontalLine();
        JLogger.newLine();
        
        ShaderCore.setFilepath("/dev/theskidster/mapeditor/shaders/");
        
        //Establish scene shaders.
        {
            var shaderSourceFiles = new LinkedList<Shader>() {{
                add(new Shader("sceneVertex.glsl", GL_VERTEX_SHADER));
                add(new Shader("sceneFragment.glsl", GL_FRAGMENT_SHADER));
            }};
            
            sceneProgram = new GLProgram(shaderSourceFiles, "scene");
            sceneProgram.use();
            
            sceneProgram.addUniform(BufferType.INT,  "uType");
            sceneProgram.addUniform(BufferType.MAT4, "uModel");
            sceneProgram.addUniform(BufferType.MAT4, "uView");
            sceneProgram.addUniform(BufferType.MAT4, "uProjection");
        }
        
        //Establish UI shaders.
        {
            var shaderSourceFiles = new LinkedList<Shader>() {{
                add(new Shader("uiVertex.glsl", GL_VERTEX_SHADER));
                add(new Shader("uiFragment.glsl", GL_FRAGMENT_SHADER));
            }};
            
            uiProgram = new GLProgram(shaderSourceFiles, "ui");
            uiProgram.use();
            
            uiProgram.addUniform(BufferType.INT,  "uType");
            uiProgram.addUniform(BufferType.VEC3, "uColor");
            uiProgram.addUniform(BufferType.MAT4, "uProjection");
        }
        
        camera = new Camera();
        ui     = new UI(window, fontFilename, fontSize);
    }
    
    /**
     * Exposes the window and begins running the application.
     */
    void start() {
        window.show(monitor, vSync, camera);
        
        final double TARGET_DELTA = 1 / 60.0;
        double prevTime = glfwGetTime();
        double currTime;
        double delta = 0;
        boolean ticked;
        
        setClearColor(Color.WHITE);
        
        //TODO: move this projection matrix to somewhere more appropriate.
        Matrix4f projMatrix = new Matrix4f();
        projMatrix.setOrtho(0, window.getWidth(), 0, window.getHeight(), 0, Integer.MAX_VALUE);
        Font font = new Font("fnt_karla_regular.ttf", 14);
        Triangle triangle = new Triangle(0, 0, -5, 1);
        
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
                
                camera.update(window.getWidth(), window.getHeight());
                triangle.update();
            }
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            if(!window.getMinimized()) {
                sceneProgram.use();
                glViewport(0, 0, window.getWidth(), window.getHeight());
                camera.render(sceneProgram);
                triangle.render(sceneProgram);
                
                uiProgram.use();
                uiProgram.setUniform("uProjection", false, projMatrix);
                font.drawString("The quick brown fox jumped over the lazy dog.", 40, 100, Color.RED, uiProgram);
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
                      .append(vSync + "\" ")
                      .append("fontFilename=\"")
                      .append(ui.getFontFilename() + "\" ")
                      .append("fontSize=\"")
                      .append(ui.getFontSize() + "\">")
                      .append("</config>");
            }
        } catch(IOException e) {
            JLogger.setModule("core");
            JLogger.logWarning("Failed to export user preferences.", e);
            JLogger.setModule(null);
        }
        
        GL.destroy();
        glfwTerminate();
    }
    
    public static void checkGLError() {
        int glError = glGetError();
        
        if(glError != GL_NO_ERROR) {
            String desc = "";
            
            switch(glError) {
                case GL_INVALID_ENUM      -> desc = "invalid enum";
                case GL_INVALID_VALUE     -> desc = "invalid value";
                case GL_INVALID_OPERATION -> desc = "invalid operation";
                case GL_STACK_OVERFLOW    -> desc = "stack overflow";
                case GL_STACK_UNDERFLOW   -> desc = "stack underflow";
                case GL_OUT_OF_MEMORY     -> desc = "out of memory";
            }
            
            JLogger.logSevere("OpenGL Error: (" + glError + ") " + desc, null);
        }
    }
    
    public static void setClearColor(Color color) {
        glClearColor(color.r, color.g, color.b, 0);
    }
    
}