package cz.apo.etc;

import org.lwjgl.opengl.Display;

public class FpsCounter
{
	private static long last = 0L;
	private static int fps = 0;;
	
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
