package cz.apo.entity.projectile;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import cz.apo.entity.Collidable;
import cz.apo.entity.Entity;
import cz.apo.entity.Tank;
import cz.apo.entity.TankFacing;
import cz.apo.paddleGame.PaddleGame;

public class Cluster implements Entity, Projectile
{
	public static final long SPLIT_TIME = 500L; // 500ms after launch
	
	private float x, y, width, height;
	private float dx, dy;
	private float angle = 0.0f;
	private float speed = 10.0f;
	private boolean addSpeed = false;
	private long timeFired;
	
//	private Tank tank;
	private TankFacing facing;
	
	public Cluster(float x, float y, float width, float height, Tank tank)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
//		this.tank = tank;
		this.facing = tank.getFacing();
		
		if(tank.isMoving())
			addSpeed = true;
		
		timeFired = Sys.getTime();
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
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-x, -y, 0);
		
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);			
		GL11.glEnd();
	}
	
	public void update()
	{
		float f_dx = 0f, f_dy = 0f;
		float s_dx = 0f, s_dy = 0f;
		float t_dx = 0f, t_dy = 0f;
		
		switch(facing)
		{
			case NORTH:
				angle = 0.0f;
				dx = 0;
				f_dx = 0; f_dy = -speed;
				s_dx = -speed; s_dy = 0;
				t_dx = speed; t_dy = 0;
				if(addSpeed)
					dy = -speed - Tank.SPEED;
				else
					dy = -speed;
				break;
			case SOUTH:
				f_dx = 0; f_dy = speed;
				s_dx = -speed; s_dy = 0;
				t_dx = speed; t_dy = 0;
				angle = 180.0f;
				dx = 0;
				if(addSpeed)
					dy = speed + Tank.SPEED;
				else
					dy = speed;
				break;
			case EAST:
				f_dx = speed; f_dy = 0;
				s_dx = 0; s_dy = -speed;
				t_dx = 0; t_dy = speed;
				angle = 90.0f;
				if(addSpeed)
					dx = speed + Tank.SPEED;
				else
					dx = speed;
				dy = 0;
				break;
			case WEST:
				f_dx = -speed; f_dy = 0;
				s_dx = 0; s_dy = -speed;
				t_dx = 0; t_dy = speed;
				angle = 270.0f;
				if(addSpeed)
					dx = -speed - Tank.SPEED;
				else
					dx = -speed;
				dy = 0;
				break;
		}
	
		x += dx;
		y += dy;
		
		if(Sys.getTime() >= timeFired + SPLIT_TIME)
		{
			split(f_dx, f_dy, s_dx, s_dy, t_dx, t_dy);
			PaddleGame.entities.remove(this);
		}
	}
	
	private void split(float f_dx, float f_dy, float s_dx, float s_dy, float t_dx, float t_dy)
	{	
		PaddleGame.entities.add(new Missile(x, y, 2.0f, 4.0f, f_dx, f_dy));
		PaddleGame.entities.add(new Missile(x, y, 2.0f, 4.0f, s_dx, s_dy));
		PaddleGame.entities.add(new Missile(x, y, 2.0f, 4.0f, t_dx, t_dy));
	}
	
	public void checkCollision()
	{
		for(int i = 0; i < PaddleGame.entities.size(); i++)
		{
			Entity e = PaddleGame.entities.get(i);
			
			if(e instanceof Collidable)
				((Collidable) e).intersects(this);
		}
	}
}
