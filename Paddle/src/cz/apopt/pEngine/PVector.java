package cz.apopt.pEngine;

import org.lwjgl.util.vector.Vector2f;

public class PVector 
{
	private enum Type
	{
		SINGLE,
		AREA
	}
	
	Type type;
	float x;
	float y;
	float width;
	float height;
	
	public PVector(float x, float y)
	{
		this.x = x;
		this.y = y;
		type = Type.SINGLE;
	}
	
	public PVector(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		type = Type.AREA;
	}
	
	public Vector2f getPos()
	{
		if(type == Type.SINGLE)
			return new Vector2f(x, y);
		else if(type == Type.AREA)
			return new Vector2f(Pengine.getRandom(x, x+width), Pengine.getRandom(y,	y+height));
		else
			return new Vector2f(0, 0);
	}
}
