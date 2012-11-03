package cz.apopt.etc;

import org.lwjgl.opengl.Display;

/**
 * FPS counter
 * 
 * @author Adam & Optical
 */
public class FpsCounter
{
	private static long last = 0L;
	private static int fps = 0;
	
	/**
	 * Tick. Call every time in main loop
	 */
	public static void tick()
	{
		long now = System.currentTimeMillis();
		
		if(now >= last + 1000L)
		{
			Display.setTitle("FPS: " + fps);
			fps = 0;
			last = now;
		}
		
		fps++;
	}
}
