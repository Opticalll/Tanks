package cz.apo.entity;

import java.awt.Rectangle;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cz.apo.entity.projectile.Projectile;
import cz.apo.paddleGame.PaddleGame;
import cz.opt.particleEngine.ParticleEngine;
import cz.opt.particleEngine.ParticleRGB;
import cz.opt.particleEngine.ParticleVelocityVector;
import cz.opt.particleEngine.Particles.Particle.TypePar;
import cz.opt.particleEngine.Particles.Particle.TypeSpread;

public class Block implements Entity, Collidable
{	
	private float x, y;
	private float blockWidth, blockHeight;
	float[] rgb = new float[3];
	
	public Block(float x, float y, float blockWidth, float blockHeight)
	{
		this.x = x;
		this.y = y;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		rgb = rColor();
		
		
	}
	
	public void render()
	{
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(rgb[0], rgb[1], rgb[2]);
			GL11.glVertex2f(x, y);
			GL11.glColor3f(rgb[0], rgb[1], rgb[2]);
			GL11.glVertex2f(x + blockWidth, y);
			GL11.glColor3f(rgb[0], rgb[1], rgb[2]);
			GL11.glVertex2f(x + blockWidth, y + blockHeight);
			GL11.glColor3f(rgb[0], rgb[1], rgb[2]);
			GL11.glVertex2f(x, y + blockHeight);
		GL11.glEnd();
	}

	public void update()
	{
		
	}

	public float getX()
	{	
		return x;
	}

	public float getY()
	{
		return y;
	}
	
	public float getWidth()
	{
		return blockWidth;
	}
	
	public float getHeight()
	{
		return blockHeight;
	}
	
	private float[] rColor()
	{
		float[] rgb = new float[3];
		Random r = new Random();
		
		rgb[0] = r.nextFloat();
		rgb[1] = r.nextFloat();
		rgb[2] = r.nextFloat();
		
		return rgb;
	}
	
	public boolean intersects(Entity e)
	{
		if(e instanceof Ball)
		{
			Ball b = (Ball) e;
			int bRadius = (int) b.getWidth();
			
			Rectangle block = new Rectangle((int) x, (int) y, (int) blockWidth, (int) blockHeight);
			Rectangle ball = new Rectangle((int) b.getX(), (int) b.getY(), bRadius, bRadius);			
			
			if(ball.intersects(block))
			{
				// step back
				float pX = b.getX() - b.getDX();
				float pY = b.getY() - b.getDY();
								
				boolean left = false;
				if(pX + bRadius <= x)
					left = true;
				
				boolean right = false;
				if(pX >= x + blockWidth)
					right = true;
				
				boolean top = false;
				if(pY + bRadius <= y)
					top = true;
				
				boolean bottom = false;
				if(pY >= y + blockHeight)
					bottom = true;
				
				if(left || right)
				{
					b.setDX(-(b.getDX()));
				}
				else if(top || bottom)
				{
					b.setDY(-b.getDY());
				}
				
				
				ParticleRGB color = new ParticleRGB(rgb[0], rgb[1], rgb[2]);
				ParticleVelocityVector vector = new ParticleVelocityVector(1.5f, 1.5f);
				new ParticleEngine(x + blockWidth/2, y + blockHeight/2, 2, 4, 50, color, TypeSpread.CIRCLE, TypePar.COMBINED, vector, 3000, true, 400);
				PaddleGame.entities.remove(this);
				return true;
			}
		} else if(e instanceof Projectile)
		{
			Projectile p = (Projectile) e;
			int mWidth = (int) p.getWidth();
			int mHeight = (int) p.getHeight();
			
			Rectangle block = new Rectangle((int) x, (int) y, (int) blockWidth, (int) blockHeight);
			Rectangle missile = new Rectangle((int) p.getX(), (int) p.getY(), mWidth, mHeight);
			
			if(missile.intersects(block))
			{
				PaddleGame.entities.remove(p);
				ParticleRGB color = new ParticleRGB(rgb[0], rgb[1], rgb[2]);
				ParticleVelocityVector vector = new ParticleVelocityVector(1.5f, 1.5f);
				new ParticleEngine(x + blockWidth/2, y + blockHeight/2, 2, 4, 50, color, TypeSpread.CIRCLE, TypePar.COMBINED, vector, 3000, true, 400);
				PaddleGame.log("removing");
				PaddleGame.entities.remove(this);
			}
		}
		
		return false;
	}
}
