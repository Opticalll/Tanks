package cz.apopt.entity.projectile;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cz.apopt.entity.Collidable;
import cz.apopt.entity.Entity;
import cz.apopt.entity.Tank;
import cz.apopt.pEngine.PVector;
import cz.apopt.pEngine.Pengine;
import cz.apopt.pEngine.VVector;
import cz.apopt.paddleGame.PaddleGame;

/**
 * Missile class
 * 
 * @author Adam & Optical
 */
public class Missile implements Entity, CannonProjectile
{
	private static final String NAME = "Missile";
	
	private final float WIDTH = 2.0f;
	private final float HEIGHT = 4.0f;
	private float x, y;
	private float speed = 5.0f;
	private float dx, dy;
	private float angle = 0.0f;

	private float lockOnRange = 50;
	
	private Tank shooter;
	private Tank target = null;
	
	public Missile(Tank tank)
	{
		this.shooter = tank;
	}
	
//	public Missile(float x, float y, Tank tank)
//	{
//		this.x = x;
//		this.y = y;
//		this.shooter = tank;
//	}
//	
	public Missile(float x, float y, float width, float height, float dx, float dy)
	{
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	public float getX()
	{
		return x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setY(float y)
	{
		this.y = y;
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
	
	public float getWidth()
	{
		return WIDTH;
	}
	
	public float getHeight()
	{
		return HEIGHT;
	}
	
	public void setAngle(float angle)
	{
		this.angle = angle;
	}
	
	public Tank getShooter()
	{
		return shooter;
	}
	
	public String getName()
	{
		return NAME;
	}
	
	private void lockOn()
	{
		for(Entity e : PaddleGame.entities)
		{
			if(e instanceof Tank)
			{
				
				Tank t = (Tank) e;
				if(t != shooter)
				{
					if(dx > 0 && t.getX() > x && (t.getY() <= y + lockOnRange && t.getY() >= y - lockOnRange))
						target = t;
					else if(dx < 0 && t.getX() < x && (t.getY() <= y + lockOnRange && t.getY() >= y - lockOnRange))
						target = t;
					else if(dy > 0 && t.getY() > y && (t.getX() <= x + lockOnRange && t.getX() >= x - lockOnRange))
						target = t;
					else if(dy < 0 && t.getY() > y && (t.getX() <= x + lockOnRange && t.getX() >= x - lockOnRange))
						target = t;
					else
						target = null;
				}
				else
					target = null;
				if(target != null)
				{
					PaddleGame.logT("Target Locked on X: " + target.getX() + " Y: " + target.getY() + "\n Missile Cord X: " + x + " Y: " + y);
					break;
				}
				
			}
		}
	}
	
	public void render()
	{
		GL11.glTranslatef(x + WIDTH/2, y + HEIGHT/2, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-(x + WIDTH/2), -(y + HEIGHT/2), 0);
		
		GL11.glColor3f(0.5f, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + WIDTH, y);
			GL11.glVertex2f(x + WIDTH, y + HEIGHT);
			GL11.glVertex2f(x, y + HEIGHT);			
		GL11.glEnd();
	}
	
	public void update()
	{
		
		if(x > Display.getDisplayMode().getWidth() || x < 0 || y > Display.getDisplayMode().getHeight() || y < 0)
			PaddleGame.entities.remove(this);
		//Guided missile engine you can transfer it some where else if you will revork missile
		
		switch(shooter.getFacing())
		{
			case NORTH:
				setDX(0.0f);
				setDY(-speed);
				break;
			case EAST:
				setDX(speed);
				setDY(0.0f);
				break;
			case SOUTH:
				setDX(0.0f);
				setDY(speed);
				break;
			case WEST:
				setDX(-speed);
				setDY(0.0f);
				break;
		}
		
		if(target == null)
			lockOn();
		else
		{
			if(target.getX() > x)
				dx += speed/10;
			if(target.getX() < x)
				dx -= speed/10;
			if(target.getY() < y)
				dy -= speed/10;
			if(target.getY() > y)
				dy += speed/10;
			if(dx > speed)
				dx = speed;
			if(dy > speed)
				dy = speed;
			if(dx < -speed)
				dx = -speed;
			if(dy < -speed)
				dy = -speed;
		}
		
		x += dx;
		y += dy;
		
		Pengine eng = new Pengine(new PVector(x + WIDTH/2, y + HEIGHT/2), 2, 90, null);
		eng.setVVector(new VVector(0.5f, 0.5f));
		eng.setTime(0.05f);
		eng.setMinFade(0.005f);
		eng.setMaxFade(0.5f);
		eng.create();
	}
	
	public void fire()
	{	
		switch(shooter.getFacing())
		{
			case NORTH:
				setX(shooter.getX() + (shooter.getWidth()/2 - shooter.getGunWidth()/2));
				setY((shooter.getY() + shooter.getHeight()/2) - shooter.getGunLength()/2);
				setDX(0.0f);
				setDY(-speed);
				setAngle(0.0f);
				break;
			case EAST:
				setX(shooter.getX() + (shooter.getWidth()/2 + shooter.getGunLength()/2));
				setY((shooter.getY() + shooter.getHeight()/2));
				setDX(speed);
				setDY(0.0f);
				setAngle(90.0f);
				break;
			case SOUTH:
				setX((shooter.getX() + shooter.getWidth()/2));
				setY(shooter.getY() + shooter.getHeight()/2 + shooter.getGunLength()/2);
				setDX(0.0f);
				setDY(speed);
				setAngle(180.0f);
				break;
			case WEST:
				setX(shooter.getX() + (shooter.getWidth()/2 - shooter.getGunLength()/2));
				setY((shooter.getY() + shooter.getHeight()/2));
				setDX(-speed);
				setDY(0.0f);
				setAngle(270.0f);
				break;
		}
		this.target = null;
		PaddleGame.entities.add(this);
	}
	
	public void checkCollision()
	{
		for(int i = 0; i < PaddleGame.entities.size(); i++)
		{
			Entity e = PaddleGame.entities.get(i);
			
			if(e instanceof Collidable)
			{
				Collidable obj = (Collidable) e;
				
				if(obj.isSolid() || obj.isDestroyable())
					obj.intersects(this);
			}
		}
	}
}
