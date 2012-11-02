package cz.apopt.pEngine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import cz.apopt.pEngine.enums.EnumUtils;
import cz.apopt.pEngine.enums.ParType;
import cz.apopt.pEngine.enums.SpreadType;

public class Pengine
{
	public static final List<Particle> particles = new ArrayList<Particle>();	
	
	public PVector pVector;
	public float count;
	public float range;
	public boolean transition;
	public float time;
	public float maxFade;
	public float minFade;
	
	public ColorTransition ct;
	public SpreadType s_type = SpreadType.ROUND;
	public VVector velocity;
	public Vector2f rotVec;
	
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
	public Pengine(PVector vec, float c, float range, ColorTransition ct)
	{
		this.pVector = vec;
		this.count = c;
		this.range = range;
		this.ct = ct;
		this.p_type = ParType.RANDOM;
		finallConstruct();
	}
	
	public Pengine(PVector vec, float c, float range, ParType type, SpreadType s_type, ColorTransition ct)
	{
		this.pVector = vec;
		this.count = c;
		this.range = range;
		this.p_type = type;
		this.sides = EnumUtils.getInt(p_type);
		this.s_type = s_type;
		this.ct = ct;
		finallConstruct();
	}
	
	public Pengine(PVector vec, float c, float range, int sides, SpreadType s_type, ColorTransition ct)
	{
		this.pVector = vec;
		this.count = c;
		this.range = range;
		this.p_type = null;
		this.s_type = s_type;
		this.sides = sides;
		this.ct = ct;
		finallConstruct();
	}
	
	
	
	public void setMaxFade(float maxFade) {
		this.maxFade = maxFade;
	}

	public void setMinFade(float minFade) {
		this.minFade = minFade;
	}

	public void setTime(float time) 
	{
		this.time = time;
	}

	private void finallConstruct()
	{
		rotVec = new Vector2f(0f, 0f);
		velocity = new VVector(-2f, 2f);
		time = 1.0f;
		minFade = 0.05f;
		maxFade = 0.1f;
	}
	
	public void setPVector(PVector vec)
	{
		this.pVector = vec;
	}
	
	public void setVVector(VVector vec)
	{
		this.velocity = vec;
	}
	
	public void setRotation(float min, float max)
	{
		rotVec.x = min;
		rotVec.y = max;
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
				particles.add(new Polygon(1f, velocity.getRandomVx(), velocity.getRandomVy(), getRandom(3, 8), rotVec, this));
		} 
		else
		{
			for(int i = 0; i < count; i++)
				particles.add(new Polygon(1f, velocity.getRandomVx(), velocity.getRandomVx(), sides, rotVec, this));
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
