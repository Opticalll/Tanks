package cz.apo.etc;

import org.lwjgl.opengl.Display;

/**
 * Fps counter
 * 
 * @author adam
 */
public class FpsCounter
{
	private static long last = 0L;
	private static int fps = 0;
	
	/**
	 * Tick. Call everytime in main loop
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
