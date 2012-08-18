package cz.opt.particleEngine.Particles;

import java.util.ArrayList;
import java.util.List;

import cz.opt.particleEngine.ParticleEngine;
import cz.opt.particleEngine.ParticleRGB;

public abstract class Particle
{
	static public List<Particle> ParticleList = new ArrayList<Particle>();
	static protected float DISTANCEFRIC = 2.0f;
	static protected float TIMEFRIC = 10.0f;
	static protected float ANGLESPEED = 10.0f;

	public static float getRandom(float min, float max)
	{
		return (min + (float)(Math.random()* ((max - min) + 1)));
	}

	static public enum TypeParticle
	{	
		SQUARE,
		TRIANGLE,
		POLYGON_5,
		POLYGON_6,
		POLYGON_7,
		POLYGON_8,
		POLYGON_9,
		POLYGON_10
	}

	static public enum TypeSpread
	{
		CIRCLE,
		SQUARE,
		TRIANGLE
	}

	static public enum TypePar
	{
		STATIC,
		DYNAMIC,
		COMBINED
	}

	protected float x = 0;
	protected float y = 0;
	protected float vx = 0;
	protected float vy = 0;
	protected float size = 0;
	protected float radius = 0;
	protected float defX = 0;
	protected float defY = 0;
	
	protected int sides = 0;

	protected boolean rotate = false; 

	protected ParticleRGB color;

	protected float angleAcc = 0;
	protected float angle = 0;

	protected float SecFinal;
	protected float SecProgres = 0;
	protected float miliSec;

	protected float progres = 0;

	protected TypeSpread Spread = TypeSpread.CIRCLE;
	protected TypePar ParticleType = TypePar.STATIC;

	Particle(float x, float y, float size, float radius, ParticleRGB color, TypeSpread TS, TypePar TP, float vx, float vy, int miliSec, boolean rotate, int sides)
	{
		this.sides = sides;
		this.x = x;
		this.y = y;
		defX = x;
		defY = y;
		this.size = size;
		this.radius = radius;
		this.radius += getRandom(-(radius/DISTANCEFRIC), (radius/DISTANCEFRIC));
		this.Spread = TS;
		this.ParticleType = TP;
		this.color = color;
		this.angleAcc = getRandom(-ANGLESPEED, ANGLESPEED);
		if(angleAcc < 1.0f)
			angleAcc = 1.0f;
		this.angle = getRandom(0.0f, 360.0f);
		this.rotate = rotate;
		miliSec += getRandom(-(miliSec/TIMEFRIC), (miliSec/TIMEFRIC));
		this.miliSec = (float)miliSec;
		if(this.color.getColorType() == ParticleRGB.TypeColor.RGB_RANDOM)
		{
			this.color.randomize();
		}

		if( TP == TypePar.STATIC )
		{
			this.vx = 0;
			this.vy = 0;
		} 
		else
		{
			this.vx = vx;
			this.vy = vy;
		}
		setUpTimer();
		Particle.ParticleList.add(this);
	}
	
	Particle(float x, float y, float size, float radius, ParticleRGB color, TypeSpread TS, TypePar TP, float vx, float vy, int miliSec, boolean rotate)
	{
		this.x = x;
		this.y = y;
		defX = x;
		defY = y;
		this.size = size;
		this.radius = radius;
		this.radius += getRandom(-(radius/DISTANCEFRIC), (radius/DISTANCEFRIC));
		this.Spread = TS;
		this.ParticleType = TP;
		this.color = color;
		this.angleAcc = getRandom(-ANGLESPEED, ANGLESPEED);
		if(angleAcc < 1.0f)
			angleAcc = 1.0f;
		this.angle = getRandom(0.0f, 360.0f);
		this.rotate = rotate;
		miliSec += getRandom(-(miliSec/TIMEFRIC), (miliSec/TIMEFRIC));
		this.miliSec = (float)miliSec;
		if(this.color.getColorType() == ParticleRGB.TypeColor.RGB_RANDOM)
		{
			this.color.randomize();
		}

		if( TP == TypePar.STATIC )
		{
			this.vx = 0;
			this.vy = 0;
		} 
		else
		{
			this.vx = vx;
			this.vy = vy;
		}
		setUpTimer();
		Particle.ParticleList.add(this);
	}
	
	protected void setUpTimer()
	{
		SecFinal = (float)(miliSec/1000.0f)*ParticleEngine.getFPS();
	}

	public void onLoop()
	{
		x += vx;
		y += vy;

		progres = (float) ((Math.pow((x - defX), 2) + Math.pow((y - defY), 2))/(Math.pow(radius, 2)));
		if(progres > 1.0f)
			progres = 1.0f;
		if(color.getColorType() == ParticleRGB.TypeColor.RGB_ARRAY)
			color.changeToProgres(progres);

		if(rotate)
		{
			angle += angleAcc;
		}
		if(angle >= 360.0f)
		{
			angle = 0.0f;
		}

		if(ParticleType == TypePar.COMBINED || ParticleType == TypePar.STATIC)
		{
			if(SecProgres >= SecFinal)
				deleteParticle();
			SecProgres++;
		}

		if(Spread == TypeSpread.SQUARE)
		{
			if((defX+radius) < x || (defX-radius) > x || (defY+radius) < y || (defY-radius) > y)
			{
				deleteParticle();	
			}
		}
		else if(Spread == TypeSpread.CIRCLE)
		{
			if(Math.pow(radius, 2) < ((Math.pow((x - defX), 2) + Math.pow((y - defY), 2))))
			{
				deleteParticle();
			}
		}
		else if(Spread == TypeSpread.TRIANGLE)
		{
		}
	}

	public abstract void onRender();

	public static float getDISTANCEFRIC()
	{
		return DISTANCEFRIC;
	}

	public static void setDISTANCEFRIC(float dISTANCEFRIC)
	{
		DISTANCEFRIC = dISTANCEFRIC;
	}

	public static float getTIMEFRIC()
	{
		return TIMEFRIC;
	}

	public static void setTIMEFRIC(float tIMEFRIC)
	{
		TIMEFRIC = tIMEFRIC;
	}
	
	private void deleteParticle()
	{
		color = new ParticleRGB(0, 0, 0, 0);
		Particle.ParticleList.remove(this);
	}
}
