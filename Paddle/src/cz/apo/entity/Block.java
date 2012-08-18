package cz.apo.entity;

import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import cz.apo.entity.projectile.Projectile;
import cz.apo.etc.Color;
import cz.apo.paddleGame.PaddleGame;
import cz.opt.pEngine.ColorTransition;
import cz.opt.pEngine.PVector;
import cz.opt.pEngine.Pengine;

public class Block implements Entity, Collidable
{	
	private float x, y;
	private float blockWidth, blockHeight;
	
	private boolean colidable;

	private Color col;
	
	public Block(float x, float y, float blockWidth, float blockHeight, Color col, boolean colidable)
	{
		this.x = x;
		this.y = y;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.col = col;
		this.colidable = colidable;
	}
	
	public void render()
	{
		GL11.glColor3f(col.R, col.G, col.B);
		GL11.glBegin(GL11.GL_QUADS);		
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + blockWidth, y);
			GL11.glVertex2f(x + blockWidth, y + blockHeight);
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
				
				Pengine eng = new Pengine(new PVector(x, y, blockWidth, blockHeight), 50, 50, ColorTransition.getRandomTransition());
				eng.create();
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
				Pengine eng = new Pengine(new PVector(x, y, blockWidth, blockHeight), 50, 50, ColorTransition.getRandomTransition());
				eng.create();
				PaddleGame.log("removing");
				PaddleGame.entities.remove(this);
			}
		} else if((e instanceof Tank) && colidable)
		{
			Tank p = (Tank) e;
			int pWidth = (int) p.getWidth();
			int pHeight = (int) p.getHeight();
			
			Rectangle wall = new Rectangle((int) x, (int) y, (int) blockWidth, (int) blockHeight);
			Rectangle player = new Rectangle((int) p.getX(), (int) p.getY(), pWidth, pHeight);			
			
			if(player.intersects(wall))
			{
				// step back
				float pX = p.getX() - p.getDX();
				float pY = p.getY() - p.getDY();
								
				boolean left = false;
				if(pX + pWidth <= x)
					left = true;
				
				boolean right = false;
				if(pX >= x + blockWidth)
					right = true;
				
				boolean top = false;
				if(pY + pHeight <= y)
					top = true;
				
				boolean bottom = false;
				if(pY >= y + blockHeight)
					bottom = true;
				
				if(left || right)
				{
					p.setDX(0);
					p.setX(pX);
				}
				else if(top || bottom)
				{
					p.setDY(0);
					p.setY(pY);
				}
				return true;

			}
		} 		
		return false;
	}
}
