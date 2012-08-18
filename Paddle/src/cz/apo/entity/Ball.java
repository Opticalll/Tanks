package cz.apo.entity;

import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import cz.apo.entity.projectile.Projectile;
import cz.apo.paddleGame.PaddleGame;
import cz.opt.particleEngine.ParticleEngine;
import cz.opt.particleEngine.ParticleRGB;
import cz.opt.particleEngine.ParticleVelocityVector;
import cz.opt.particleEngine.Particles.Particle.TypePar;
import cz.opt.particleEngine.Particles.Particle.TypeSpread;

public class Ball implements Entity, Collidable
{
	private float x, y;
	private float radius;
	private float dx, dy;

	public Ball(float x, float y, float radius)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;

		dx = 3f;
		dy = -3f;
	}

	public float getX()
	{
		return x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getDX()
	{
		return dx;
	}
	
	public void setDX(float dx)
	{
		this.dx = dx;
	}
	
	public float getDY()
	{
		return dy;
	}
	
	public void setDY(float dy)
	{
		this.dy = dy;
	}

	public float getY()
	{
		return y;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public float getWidth()
	{
		return radius;
	}
	
	public float getHeight()
	{
		return radius;
	}

	public void render()
	{
		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + radius, y);
			GL11.glVertex2f(x + radius, y + radius);
			GL11.glVertex2f(x, y + radius);
		GL11.glEnd();
	}

	public void update()
	{
		this.x += dx;
		this.y += dy;

		for(int i = 0; i < PaddleGame.entities.size(); i++)
		{
			Entity e = PaddleGame.entities.get(i);
			
			if(e instanceof Collidable)
			{
				((Collidable) e).intersects(this);
			}
		}
	}
	
	public boolean intersects(Entity e)
	{
		if(e instanceof Projectile)
		{
			Projectile m = (Projectile) e;
			
			Rectangle ball = new Rectangle((int) x, (int) y, (int) radius, (int) radius);
			Rectangle missile = new Rectangle((int) m.getX(), (int) m.getY(), (int) m.getWidth(), (int) m.getHeight());
			
			if(missile.intersects(ball))
			{
				PaddleGame.entities.remove(e);
				
				ParticleVelocityVector vector = new ParticleVelocityVector(3.0f, 3.0f, 3.0f, 3.0f);
				ParticleRGB color = new ParticleRGB();
				new ParticleEngine(m.getX(), m.getY(), 1, 3, 300, color, TypeSpread.CIRCLE, TypePar.DYNAMIC, vector, 3300, true, 1000);
				PaddleGame.entities.remove(this);
				
				return true;
			}
		}
		
		return false;
	}
}
