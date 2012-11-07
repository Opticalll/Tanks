package cz.apopt.entity.projectile;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import cz.apopt.entity.Collidable;
import cz.apopt.entity.Entity;
import cz.apopt.entity.Tank;
import cz.apopt.pEngine.PVector;
import cz.apopt.pEngine.Pengine;
import cz.apopt.pEngine.VVector;
import cz.apopt.paddleGame.PaddleGame;

/**
 * Cluster class
 * 
 * @author Adam & Optical
 */
public class Cluster implements Entity, CannonProjectile
{
	public static final long SPLIT_TIME = 500L; // 500ms after launch
	private static final String NAME = "Cluster";
	
	private final float WIDTH = 2.0f, HEIGHT = 4.0f;
	
	private float x, y;
	private float dx, dy;
	private float f_dx = 0f, f_dy = 0f;
	private float s_dx = 0f, s_dy = 0f;
	private float t_dx = 0f, t_dy = 0f;
	private float angle = 0.0f;
	private float speed = 10.0f;
	private long timeFired;
	
	private Tank shooter;
	
	public Cluster(Tank tank)
	{
		this.shooter = tank;
	}
	
	public Cluster(float x, float y, Tank tank)
	{
		this.x = x;
		this.y = y;
		this.shooter= tank;
		
		timeFired = Sys.getTime();
	}
	
	public Projectile getInstance()
	{
		return new Cluster(shooter);
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
	
	public void render()
	{
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-x, -y, 0);
		
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + WIDTH, y);
			GL11.glVertex2f(x + WIDTH, y + HEIGHT);
			GL11.glVertex2f(x, y + HEIGHT);			
		GL11.glEnd();
	}
	
	public void update()
	{	
		x += dx;
		y += dy;
		
		Pengine eng = new Pengine(new PVector(x + WIDTH/2, y + HEIGHT/2), 2, 90, null);
		eng.setVVector(new VVector(0.5f, 0.5f));
		eng.setTime(0.05f);
		eng.setMinFade(0.005f);
		eng.setMaxFade(0.5f);
		eng.create();
		
		if(Sys.getTime() >= timeFired + SPLIT_TIME)
			split(f_dx, f_dy, s_dx, s_dy, t_dx, t_dy);
	}
	
	public void fire()
	{	
		this.timeFired = Sys.getTime();
		
		switch(shooter.getFacing())
		{
			case NORTH:
				setX(shooter.getX() + (shooter.getWidth()/2 - shooter.getGunWidth()/2));
				setY((shooter.getY() + shooter.getHeight()/2) - shooter.getGunLength()/2);
				
				angle = 0.0f;
				dx = 0;
				f_dx = 0; f_dy = -speed;
				s_dx = -speed; s_dy = 0;
				t_dx = speed; t_dy = 0;
				dy = -speed;
				break;
			case EAST:
				setX(shooter.getX() + (shooter.getWidth()/2 + shooter.getGunLength()/2));
				setY((shooter.getY() + shooter.getHeight()/2));
				
				f_dx = speed; f_dy = 0;
				s_dx = 0; s_dy = -speed;
				t_dx = 0; t_dy = speed;
				angle = 90.0f;
				dx = speed;
				dy = 0;
				break;
			case SOUTH:
				setX((shooter.getX() + shooter.getWidth()/2));
				setY(shooter.getY() + shooter.getHeight()/2 + shooter.getGunLength()/2);
				
				f_dx = 0; f_dy = speed;
				s_dx = -speed; s_dy = 0;
				t_dx = speed; t_dy = 0;
				angle = 180.0f;
				dx = 0;
				dy = speed;
				break;
			case WEST:
				setX(shooter.getX() + (shooter.getWidth()/2 - shooter.getGunLength()/2));
				setY((shooter.getY() + shooter.getHeight()/2));
				
				f_dx = -speed; f_dy = 0;
				s_dx = 0; s_dy = -speed;
				t_dx = 0; t_dy = speed;
				angle = 270.0f;
				dx = -speed;
				dy = 0;
				break;
		}
		
		PaddleGame.entities.add(this);
	}

	
	private void split(float f_dx, float f_dy, float s_dx, float s_dy, float t_dx, float t_dy)
	{	
		PaddleGame.entities.add(new Missile(x, y, 2.0f, 4.0f, f_dx, f_dy));
		PaddleGame.entities.add(new Missile(x, y, 2.0f, 4.0f, s_dx, s_dy));
		PaddleGame.entities.add(new Missile(x, y, 2.0f, 4.0f, t_dx, t_dy));
		PaddleGame.entities.remove(this);
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
