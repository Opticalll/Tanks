package cz.apopt.etc;

import java.util.Random;

/**
 * Color class
 * 
 * @author Adam & Optical
 */
public class Color
{
	public float R, G, B;
	
	/**
	 * Constructor. Arguments in range between 0.0f and 1.0f
	 * 
	 * @param r R
	 * @param g G
	 * @param b B
	 */
	public Color(float r, float g, float b)
	{
		this.R = r;
		this.G = g;
		this.B = b;
	}
	
	/**
	 * Constructor. Arguments in range between 0 and 256
	 * 
	 * @param r R
	 * @param g G
	 * @param b B
	 */
	public Color(int r, int g, int b)
	{
		this.R = r/256f;
		this.G = g/256f;
		this.B = b/256f;
	}
	
	/**
	 * Method for random color with arguments in range between 0.0f - 1.0f
	 * 
	 * @return Random color
	 */
	public static Color getRandomColorF()
	{
		Random r = new Random();
		
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	}
	
	/**
	 * Method for random color with arguments in range between 0 - 256
	 * 
	 * @return Random color
	 */
	public static Color getRandomColorRgb()
	{
		Random r = new Random();
		
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
}
