package cz.apo.entity;

import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import cz.apo.entity.projectile.Projectile;
import cz.apo.paddleGame.PaddleGame;

public class Wall implements Entity, Collidable
{
	private float x, y;
	private float width, height;
	
	public Wall(float x, float y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
		return width;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public void render()
	{
		// top
		GL11.glColor3f(0, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}
	
	public void update()
	{
		
	}
	
	public boolean intersects(Entity e)
	{
		if(e instanceof Ball)
		{
			Ball b = (Ball) e;
			int oWidth = (int) b.getWidth();
			int oHeight = (int) b.getHeight();
			
			Rectangle wall = new Rectangle((int) x, (int) y, (int) width, (int) height);
			Rectangle ball = new Rectangle((int) b.getX(), (int) b.getY(), oWidth, oHeight);			
			
			if(ball.intersects(wall))
			{
				// step back
				float pX = b.getX() - b.getDX();
				float pY = b.getY() - b.getDY();
								
				boolean left = false;
				if(pX + oWidth <= x)
					left = true;
				
				boolean right = false;
				if(pX >= x + width)
					right = true;
				
				boolean top = false;
				if(pY + oHeight <= y)
					top = true;
				
				boolean bottom = false;
				if(pY >= y + height)
					bottom = true;
				
				if(left || right)
				{
					b.setDX(-(b.getDX()));
				}
				else if(top || bottom)
				{
					b.setDY(-b.getDY());
				}
				return true;
			}
		} else if(e instanceof Tank)
		{
			Tank p = (Tank) e;
			int pWidth = (int) p.getWidth();
			int pHeight = (int) p.getHeight();
			
			Rectangle wall = new Rectangle((int) x, (int) y, (int) width, (int) height);
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
				if(pX >= x + width)
					right = true;
				
				boolean top = false;
				if(pY + pHeight <= y)
					top = true;
				
				boolean bottom = false;
				if(pY >= y + height)
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
		} else if(e instanceof Projectile)
		{
			Projectile m = (Projectile) e;
			int mWidth = (int) m.getWidth();
			int mHeight = (int) m.getHeight();
			
			Rectangle wall = new Rectangle((int) x, (int) y, (int) width, (int) height);
			Rectangle missile = new Rectangle((int) m.getX(), (int) m.getY(), mWidth, mHeight);
			
			if(missile.intersects(wall))
			{
				PaddleGame.entities.remove(m);
			}
		}
		return false;
	}
}
