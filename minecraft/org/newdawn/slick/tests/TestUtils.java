package org.newdawn.slick.tests;

import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;

/**
 * A simple utility test to use the internal slick API without 
 * the slick framework.
 * 
 * @author kevin
 */
public class TestUtils {
	/** The texture that's been loaded */
	private Texture texture;
	/** The font to draw to the screen */
	private Font font;
	
	/**
	 * Start the test 
	 */
	public void start() {
		initGL(800,600);
		init();
		
		while (true) {
			update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			render();
			
			Display.update();
			Display.sync(100);

			if (Display.isCloseRequested()) {
				System.exit(0);
			}
		}
	}
	
	/**
	 * Initialise the GL display
	 * 
	 * @param width The width of the display
	 * @param height The height of the display
	 */
	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);        
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);                    
        
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
        GL11.glClearDepth(1);                                       
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glViewport(0,0,width,height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	/**
	 * Initialise resources
	 */
	public void init() {
		// turn off all but errors
		Log.setVerbose(false);

		java.awt.Font awtFont = new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 16);
		font = new TrueTypeFont(awtFont, false);
		
		// texture load, the second argument is a name assigned to the texture to
		// allow for caching in the texture loader. The 3rd argument indicates whether
		// the image should be flipped on loading
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("testdata/rocks.png"));
		
			System.out.println("Texture loaded: "+texture);
			System.out.println(">> Image width: "+texture.getImageWidth());
			System.out.println(">> Image height: "+texture.getImageWidth());
			System.out.println(">> Texture width: "+texture.getTextureWidth());
			System.out.println(">> Texture height: "+texture.getTextureHeight());
			System.out.println(">> Texture ID: "+texture.getTextureID());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Game loop update
	 */
	public void update() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				
			}
		}
		
		// polling is required to allow streaming to get a chance to
		// queue buffers.
	}

	/**
	 * Game loop render
	 */
	public void render() {
		Color.white.bind();
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(100,100);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(100+texture.getTextureWidth(),100);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(100+texture.getTextureWidth(),100+texture.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(100,100+texture.getTextureHeight());
		GL11.glEnd();
		
		font.drawString(150, 300, "HELLO LWJGL WORLD", Color.yellow);
	}
	
	/**
	 * Entry point to the tests
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		TestUtils utils = new TestUtils();
		utils.start();
	}
}
