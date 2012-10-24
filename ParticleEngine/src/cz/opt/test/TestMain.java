package cz.opt.test;


import java.awt.Font;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class TestMain
{	
	UnicodeFont font;

	private TestMain()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle("Test Particles");
			Display.create();

			initGL();
			initObj();
			loop();
		} catch(LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	private void loop()
	{
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			FpsCounter.tick();

			GL11.glPushMatrix();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(0.1f, 0.55f, 0.5f);
			GL11.glVertex2f(100, 100);
			GL11.glVertex2f(300, 100);
			GL11.glVertex2f(300, 300);
			GL11.glVertex2f(100, 300);
			GL11.glColor3f(1, 1, 1);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			this.font.drawString(125, 125, "Hello World");
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();	

			Display.sync(60);
			Display.update();
		}

		Display.destroy();
		System.exit(0);
	}

	private void initObj()
	{

	}

	private void initGL()
	{
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		this.font = new UnicodeFont(new Font("Times New Roman", Font.BOLD, 15));
		this.font.addAsciiGlyphs();
		this.font.addGlyphs(400, 600);
		this.font.getEffects().add(new ColorEffect(java.awt.Color.RED));
		try {
			this.font.loadGlyphs();
		} catch (SlickException e)
		{
			e.printStackTrace();
		}

	}

	public static void main(String[] args)
	{
		new TestMain();
	}
}
