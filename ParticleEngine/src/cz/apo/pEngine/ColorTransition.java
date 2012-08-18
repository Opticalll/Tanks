package cz.apo.pEngine;

import java.util.Random;

public class ColorTransition
{
	private final Color c1, c2;
	
	public ColorTransition(Color c1, Color c2)
	{
		this.c1 = c1;
		this.c2 = c2;
	}
	
	public Color getFirst()
	{
		return c1;
	}
	
	public Color getSecond()
	{
		return c2;
	}
	
	private static float randomFloat()
	{
		Random r = new Random();
		float f = r.nextFloat();
		
		return f;
	}
	
	public static ColorTransition getRandomTransition()
	{	
		Color c1 = new Color(randomFloat(), randomFloat(), randomFloat());
		Color c2 = new Color(randomFloat(), randomFloat(), randomFloat());
		
		ColorTransition ct = new ColorTransition(c1, c2);
		
		return ct;
	}
}
