package cz.opt.pEngine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import cz.opt.pEngine.enums.EnumUtils;
import cz.opt.pEngine.enums.ParType;
import cz.opt.pEngine.enums.SpreadType;

public class Pengine
{
	public static final List<Particle> particles = new ArrayList<Particle>();	
	
	public float x, y;
	public float count;
	public float range;
	public boolean transition;
	
	public ColorTransition ct;
	public SpreadType s_type = SpreadType.ROUND;
	public Vector2f vec;
	
	public int sides;
	
	private ParType p_type = null;
	
	/**
	 * 
	 * @param x spread position x
	 * @param y spread position y
	 * @param c count
	 * @param range range of spread (radius)
	 * @param ct Color transition, pass null for no transition
	 * @param velVec Velocity vector
	 */
	public Pengine(float x, float y, float c, float range, ColorTransition ct, Vector2f velVec)
	{
		this.x = x;
		this.y = y;
		this.count = c;
		this.range = range;
		this.ct = ct;
		this.vec = velVec;
		this.p_type = ParType.RANDOM;
	}
	
	public Pengine(float x, float y, float c, float range, ParType type, SpreadType s_type, ColorTransition ct, Vector2f velVec)
	{
		this.x = x;
		this.y = y;
		this.count = c;
		this.range = range;
		this.p_type = type;
		this.sides = EnumUtils.getInt(p_type);
		this.s_type = s_type;
		this.ct = ct;
		this.vec = velVec;
	}
	
	public Pengine(float x, float y, float c, float range, int sides, SpreadType s_type, ColorTransition ct, Vector2f velVec)
	{
		this.x = x;
		this.y = y;
		this.count = c;
		this.range = range;
		this.p_type = null;
		this.s_type = s_type;
		this.sides = sides;
		this.ct = ct;
		this.vec = velVec;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public void create()
	{
		if(ct == null)
			transition = false;
		else
			transition = true;
		
		if(p_type == ParType.RANDOM) // random all types
		{
			for(int i = 0; i < count; i++)
				particles.add(new Polygon(3.5f, getRandom(-2f,  2f), getRandom(-2f,  2f), getRandom(3, 15), this));
		} 
		else
		{
			for(int i = 0; i < count; i++)
				particles.add(new Polygon(3.5f, getRandom(-2f,  2f), getRandom(-2f,  2f), sides, this));
		}
	}
	
	public static void update()
	{	
		for(int i = 0; i < particles.size(); i++)
		{
			Particle p = particles.get(i);
			
			GL11.glPushMatrix();
				p.update();
				p.render();
			GL11.glPopMatrix();
		}
	}
	
	public static float getRandom(float min, float max)
	{
		return (min + (float)(Math.random() * ((max - min))));
	}
	
	public static int getRandom(int min, int max)
	{
		return (min + (int)(Math.random() * ((max - min))));
	}
	
	public static Vector2f getRandomVector()
	{
		return (new Vector2f(getRandom(-3f,  3f), getRandom(-3f, 3f)));
	}
}
