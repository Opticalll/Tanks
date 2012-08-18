package cz.opt.particleEngine;

import org.lwjgl.opengl.GL11;

import cz.opt.particleEngine.Particles.Particle.TypePar;
import cz.opt.particleEngine.Particles.Particle.TypeParticle;
import cz.opt.particleEngine.Particles.Particle.TypeSpread;
import cz.opt.particleEngine.Particles.Particle;
import cz.opt.particleEngine.Particles.Polygon;
import cz.opt.particleEngine.Particles.Square;
import cz.opt.particleEngine.Particles.Triangle;

public class ParticleEngine
{	
	private static int FPS = 0;
	
	public static float getRandom(float min, float max)
	{
		return (min + (float)(Math.random() * ((max - min))));
	}
	
	public static int getRandom(int min, int max)
	{
		return (min + (int)(Math.random() * ((max - min))));
	}
	
	private static int PARTICLECOUNT = 1;
	
	public ParticleEngine(float x, float y, float minSize, float maxSize, float radius, ParticleRGB color, TypeParticle PT, TypeSpread TS, TypePar TD, ParticleVelocityVector velocity, int miliSec, boolean rotate, int partCount)
	{
		for(int i = 0; i < partCount; i++)
		{
			float fvx = velocity.getRandomVx();
			float fvy = velocity.getRandomVy();
			if(PT == TypeParticle.SQUARE)
			{
				new Square(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate);
			}
			if(PT == TypeParticle.TRIANGLE)
			{
				new Triangle(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate);
			}
			if(PT == TypeParticle.POLYGON_5)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 5);
			}
			if(PT == TypeParticle.POLYGON_6)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 6);
			}
			if(PT == TypeParticle.POLYGON_7)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 7);
			}
			if(PT == TypeParticle.POLYGON_8)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 8);
			}
			if(PT == TypeParticle.POLYGON_9)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 9);
			}
			if(PT == TypeParticle.POLYGON_10)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 10);
			}
		}
	}
	
	public ParticleEngine(float x, float y, float minSize, float maxSize, float radius, ParticleRGB color, TypeSpread TS, TypePar TD, ParticleVelocityVector velocity, int miliSec, boolean rotate, int partCount)
	{
		for(int i = 0; i < partCount; i++)
		{
			int r = getRandom(1, 7);
			float fvx = velocity.getRandomVx();
			float fvy = velocity.getRandomVy();
			if(r == 1)
			{
				new Square(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate);
			}
			if(r == 2)
			{
				new Triangle(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate);
			}
			if(r == 3)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 5);
			}
			if(r == 4)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 6);
			}
			if(r == 5)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 7);
			}
			if(r == 6)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 8);
			}
			if(r == 7)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 9);
			}
			if(r == 8)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 10);
			}
		}
	}
	
	public ParticleEngine(float x, float y, float minSize, float maxSize, float radius, ParticleRGB color, TypeParticle PT, TypeSpread TS, TypePar TD, ParticleVelocityVector velocity, int miliSec, boolean rotate)
	{
		for(int i = 0; i < PARTICLECOUNT; i++)
		{
			float fvx = velocity.getRandomVx();
			float fvy = velocity.getRandomVy();
			if(PT == TypeParticle.SQUARE)
			{
				new Square(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate);
			}
			if(PT == TypeParticle.TRIANGLE)
			{
				new Triangle(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate);
			}
			if(PT == TypeParticle.POLYGON_5)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 5);
			}
			if(PT == TypeParticle.POLYGON_6)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 6);
			}
			if(PT == TypeParticle.POLYGON_7)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 7);
			}
			if(PT == TypeParticle.POLYGON_8)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 8);
			}
			if(PT == TypeParticle.POLYGON_9)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 9);
			}
			if(PT == TypeParticle.POLYGON_10)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 10);
			}
		}
	}
	
	public ParticleEngine(float x, float y, float minSize, float maxSize, float radius, ParticleRGB color, TypeSpread TS, TypePar TD, ParticleVelocityVector velocity, int miliSec, boolean rotate)
	{
		for(int i = 0; i < PARTICLECOUNT; i++)
		{
			int r = getRandom(1, 7);
			float fvx = velocity.getRandomVx();
			float fvy = velocity.getRandomVy();
			if(r == 1)
			{
				new Square(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate);
			}
			if(r == 2)
			{
				new Triangle(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate);
			}
			if(r == 3)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 5);
			}
			if(r == 4)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 6);
			}
			if(r == 5)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 7);
			}
			if(r == 6)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 8);
			}
			if(r == 7)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 9);
			}
			if(r == 8)
			{
				new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, 10);
			}
		}
	}
	public ParticleEngine(float x, float y, float minSize, float maxSize, float radius, ParticleRGB color, int sides, TypeSpread TS, TypePar TD, ParticleVelocityVector velocity, int miliSec, boolean rotate, int partCount)
	{
		for(int i = 0; i < partCount; i++)
		{
			float fvx = velocity.getRandomVx();
			float fvy = velocity.getRandomVy();
			new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, sides);
		}
	}
	public ParticleEngine(float x, float y, float minSize, float maxSize, float radius, ParticleRGB color, int minsides, int maxsides, TypeSpread TS, TypePar TD, ParticleVelocityVector velocity, int miliSec, boolean rotate, int partCount)
	{
		for(int i = 0; i < partCount; i++)
		{
			float fvx = velocity.getRandomVx();
			float fvy = velocity.getRandomVy();
			new Polygon(x, y, getRandom(minSize, maxSize), radius, color, TS, TD, fvx, fvy, miliSec, rotate, getRandom(minsides, maxsides));
		}
	}
	
	static public void Init(int FPS)
	{
		ParticleEngine.FPS = FPS;
	}
	
	static public void loopParticles()
	{
		for(int i = 0; i < Particle.ParticleList.size(); i++)
		{
			GL11.glPushMatrix();
			Particle.ParticleList.get(i).onRender();
			Particle.ParticleList.get(i).onLoop();	
			GL11.glPopMatrix();
		}
	}
	
	public static int getFPS()
	{
		return FPS;
	}

	public static void setPARTICLECOUNT(int pARTICLECOUNT)
	{
		PARTICLECOUNT = pARTICLECOUNT;
	}

}
