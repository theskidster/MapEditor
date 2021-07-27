package dev.theskidster.mapeditor.main;

import dev.theskidster.jlogger.JLogger;
import dev.theskidster.mapeditor.graphics.Color;
import dev.theskidster.mapeditor.graphics.Graphics;
import dev.theskidster.shadercore.GLProgram;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import static org.lwjgl.stb.STBTruetype.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/**
 * Created: Jul 27, 2021
 */

/**
 * @author J Hoffman
 * @since  
 */
public class Font {

    private final int texHandle;
    
    private final float SCALE = 1.5f;
    
    private Graphics g;
    
    private final Map<Character, Glyph> glyphs = new HashMap<>();
    
    private final class Glyph {
        
    }
    
    Font(String filename, int size) {
        texHandle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texHandle);
        
        int bitmapWidth;
        int bitmapHeight;
        
        try(InputStream file = Font.class.getResourceAsStream(App.ASSETS_PATH + filename)) {
            try(MemoryStack stack = MemoryStack.stackPush()) {
                byte[] data = file.readAllBytes();
                
                ByteBuffer fontBuf = MemoryUtil.memAlloc(data.length).put(data).flip();
                STBTTFontinfo info = STBTTFontinfo.mallocStack(stack);
                
                if(!stbtt_InitFont(info, fontBuf)) {
                    throw new IllegalStateException("Failed to parse font information.");
                }
                
                String charset = " !\"#$%&\'()*+,-./" +
                                 "0123456789:;<=>?"   +
                                 "@ABCDEFGHIJKLMNO"   +
                                 "PQRSTUVWXYZ[\\]^_"  + 
                                 "`abcdefghijklmno"   +
                                 "pqrstuvwxyz{|}~";
                
                int bitmapSizeInPixels = 128;
                int exitStatus         = -1;
                int extraCells         = -1;
                bitmapWidth        = 0;
                bitmapHeight       = 0;
                
                ByteBuffer imageBuf = MemoryUtil.memAlloc(bitmapWidth * bitmapHeight);
                STBTTBakedChar.Buffer bakedCharBuf = STBTTBakedChar.malloc(charset.length());

                /*
                Continuously generate a bitmap image until its large enough to
                contain every glyph in the font. 
                */
                while(exitStatus <= 0) {
                    bitmapWidth  = Math.round(bitmapSizeInPixels * SCALE);
                    bitmapHeight = Math.round(bitmapSizeInPixels * SCALE);
                    imageBuf     = MemoryUtil.memAlloc(bitmapWidth * bitmapHeight);

                    bakedCharBuf = STBTTBakedChar.malloc(charset.length());

                    extraCells = stbtt_BakeFontBitmap(fontBuf, size * SCALE, imageBuf, bitmapWidth, bitmapHeight, 32, bakedCharBuf);
                    exitStatus = Math.abs(extraCells) - charset.length();

                    if(extraCells > 0) break;

                    MemoryUtil.memFree(bakedCharBuf);
                    MemoryUtil.memFree(imageBuf);

                    bitmapSizeInPixels += 16;
                }
                
                for(int i = 0; i < charset.length(); i++) {
                    STBTTAlignedQuad quad = STBTTAlignedQuad.callocStack(stack);
                    
                    FloatBuffer xPosBuf = MemoryUtil.memAllocFloat(1);
                    FloatBuffer yPosBuf = MemoryUtil.memAllocFloat(1);
                    
                    stbtt_GetBakedQuad(bakedCharBuf, bitmapWidth, bitmapHeight, i, xPosBuf, yPosBuf, quad, true);
                    
                    System.out.println(charset.charAt(i));
                    System.out.println("s0: " + quad.s0());
                    System.out.println("s1: " + quad.s1());
                    System.out.println("t0: " + quad.t0());
                    System.out.println("t1: " + quad.t1());
                    System.out.println("x0: " + quad.x0());
                    System.out.println("x1: " + quad.x1());
                    System.out.println("y0: " + quad.y0());
                    System.out.println("y1: " + quad.y1());
                    System.out.println();
                    
                    MemoryUtil.memFree(xPosBuf);
                    MemoryUtil.memFree(yPosBuf);
                }
                
                MemoryUtil.memFree(bakedCharBuf);
                
                glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, bitmapWidth, bitmapHeight, 0, GL_ALPHA, GL_UNSIGNED_BYTE, imageBuf);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                
                MemoryUtil.memFree(imageBuf);
                MemoryUtil.memFree(fontBuf);
            }
            
            //TODO: temp for drawing font bitmap
            {
                g = new Graphics();

                try(MemoryStack stack = MemoryStack.stackPush()) {
                    g.vertices = stack.mallocFloat(16);
                    g.indices  = stack.mallocInt(6);

                    //(vec3 position), (vec2 tex coords)
                    g.vertices.put(0)          .put(bitmapHeight)   .put(0).put(0);
                    g.vertices.put(bitmapWidth).put(bitmapHeight)   .put(1).put(0);
                    g.vertices.put(bitmapWidth).put(0)              .put(1).put(1);
                    g.vertices.put(0)          .put(0)              .put(0).put(1);

                    g.indices.put(0).put(1).put(2);
                    g.indices.put(2).put(3).put(0);

                    g.vertices.flip();
                    g.indices.flip();
                }

                g.bindBuffers();
                
                glVertexAttribPointer(0, 3, GL_FLOAT, false, (4 * Float.BYTES), 0);
                glVertexAttribPointer(1, 2, GL_FLOAT, false, (4 * Float.BYTES), (2 * Float.BYTES));
                
                glEnableVertexAttribArray(0);
                glEnableVertexAttribArray(1);
            }
            
        } catch(Exception e) {
            JLogger.setModule("core");
            JLogger.logSevere("Failed to load font \"" + filename + "\"", e);
        }
        
    }
    
    public void drawString(String text, int xPos, int yPos, Color color, GLProgram uiProgram) {
        glBindTexture(GL_TEXTURE_2D, texHandle);
        glBindVertexArray(g.vao);
        
        uiProgram.setUniform("uType", 3);
        
        glDrawElements(GL_TRIANGLES, g.indices.capacity(), GL_UNSIGNED_INT, 0);
        App.checkGLError();
    }
    
}