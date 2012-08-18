package cz.opt.test;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import cz.opt.pEngine.ColorTransition;
import cz.opt.pEngine.PVector;
import cz.opt.pEngine.Pengine;
import cz.opt.pEngine.VVector;

public class TestMain
{	
	Rocket rocket;
	Pengine eng;
//	static final ColorTransition rocketFlameTrans = new ColorTransition(new Color((short)226, (short)34, (short)44), new Color((short)226, (short)88, (short)34));
//	static final Pengine rocketFlame = new Pengine(0, 0, 10, 20, ParType.TRIANGLE, SpreadType.ROUND, rocketFlameTrans, Pengine.getRandomVector());
	
	private TestMain()
	{
		try
		{
			Display.setFullscreen(false);
			Display.setResizable(false);
			Display.setDisplayMode(new DisplayMode(800,	600));
			Display.setTitle("Test Particles");
			Display.setVSyncEnabled(true);
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
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			Pengine.update();						
//			rocket.update();
//			rocket.render();
			FpsCounter.tick();
			
			if(Mouse.isButtonDown(0))
			{
				float mX = Mouse.getX();
				float mY = Display.getHeight() - Mouse.getY();
				
				eng.setPVector(new PVector(mX, mY));
				eng.create();
			}
			
			Display.sync(60);
			Display.update();
		}
		
		Display.destroy();
		System.exit(0);
	}
	
	private void initObj()
	{
		eng = new Pengine(new PVector(0, 0), 10, 90, ColorTransition.getRandomTransition());
		VVector vec = new VVector(4f, 4f, 0.5f, -0.5f);
		VVector vec2 = new VVector(-4f, -4f, 0.5f, -0.5f);
		List<VVector> arr = new ArrayList<VVector>();
		arr.add(vec);
		arr.add(vec2);
		eng.setVVector(new VVector(arr));
	}
	
	private void initGL()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void main(String[] args)
	{
		new TestMain();
	}
}
