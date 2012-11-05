package cz.opt.test;


import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;

import cz.opt.lEngine.PointLight;

public class TestMain
{	
	UnicodeFont font;
	PointLight po;
	Block bl;

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

			bl.render();
			po.render();
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
		bl = new Block(200f, 200f, 400f);
		po = new PointLight(300f, 300f, 100f);

	}

	public static void main(String[] args)
	{
		new TestMain();
	}
}
