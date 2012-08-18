package cz.apo.pEngine;

public class Color
{
	private float R, G, B;
	
	public Color(float r, float g, float b)
	{
		this.R = r;
		this.G = g;
		this.B = b;
	}
	
	public Color(short r, short g, short b)
	{
		this.R = ((float)r/256f);
		System.out.println(r);
		System.out.println(R);
		this.G = ((float)g/256f);
		this.B = ((float)b/256f);
	}
	
	public Color copy()
	{
		return (new Color(R, G, B));
	}
	
	public float getR()
	{
		return R;
	}
	
	public void setR(float r)
	{
		this.R = r;
	}
	
	public float getG()
	{
		return G;
	}
	
	public void setG(float g)
	{
		this.G = g;
	}
	
	public float getB()
	{
		return B;
	}
	
	public void setB(float b)
	{
		this.B = b;
	}
}
