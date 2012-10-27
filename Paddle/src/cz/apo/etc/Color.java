package cz.apo.etc;

import java.util.Random;

/**
 * Color class
 * 
 * @author adam
 */
public class Color
{
	public float R, G, B;
	
	public Color(float r, float g, float b)
	{
		this.R = r;
		this.G = g;
		this.B = b;
	}
	
	public Color(int r, int g, int b)
	{
		this.R = r/256f;
		this.G = g/256f;
		this.B = b/256f;
	}
	
	public static Color getRandomColorF()
	{
		Random r = new Random();
		
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	}
	
	public static Color getRandomColorRgb()
	{
		Random r = new Random();
		
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
}
