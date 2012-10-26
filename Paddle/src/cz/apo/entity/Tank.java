package cz.apo.entity;

import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import cz.apo.entity.projectile.Projectile;
import cz.apo.event.WeaponChangedEvent;
import cz.apo.listener.ControllerListener;
import cz.apo.paddleGame.Controller;
import cz.apo.paddleGame.PaddleGame;
import cz.opt.pEngine.ColorTransition;
import cz.opt.pEngine.PVector;
import cz.opt.pEngine.Pengine;

/**
 * Tank class
 * 
 * @author adam
 */
public class Tank implements Entity, Collidable, ControllerListener
{
	public static final float SPEED = 3.0f;
	private float x, y, width, height;
	private float gunWidth, gunLength;
	private float dx = 0.0f, dy = 0.0f;
	private float angle = 0.0f;
	
	private int currentWeapon;
	
	private boolean left = false, right = false, up = false, down = false;
	private boolean moving = false;
	private boolean collidable = true;
	
	private Weapon weapon;
	private TankFacing facing;
	private final Controller controller;
	
	/**
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param controller controller
	 */
	public Tank(float x, float y, Controller controller)
	{
		this.x = x;
		this.y = y;
		this.controller = controller;
		facing = TankFacing.NORTH;
		width = 20.0f;
		height = 20.0f;
		gunWidth = 2.0f;
		gunLength = 20.0f;
		
		weapon = new Weapon(this);
		currentWeapon = Controller.DEFAULT_WEAPON;
		
		controller.addControllerListener(this);
		PaddleGame.log("Listener added");
	}
	
	/**
	 * 
	 * @return X coordinate
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * 	
	 * @param x X coordinate
	 */
	public void setX(float x)
	{
		this.x = x;
	}
	
	/**
	 * 
	 * @return X direction
	 */
	public float getDX()
	{
		return dx;
	}
	
	/**
	 * 
	 * @param dx X direction
	 */
	public void setDX(float dx)
	{
		this.dx = dx;
	}
	
	/**
	 * 
	 * @return Y coordinate
	 */
	public float getY()
	{
		return y;
	}
	
	/**
	 * 
	 * @param y Y coordinate
	 */
	public void setY(float y)
	{
		this.y = y;
	}
	
	/**
	 * 
	 * @return Y direction
	 */
	public float getDY()
	{
		return dy;
	}
	
	/**
	 * 
	 * @param dy Y direction
	 */
	public void setDY(float dy)
	{
		this.dy = dy;
	}
	
	/**
	 * 
	 * @return Tank width
	 */
	public float getWidth()
	{
		return width;
	}
	
	/**
	 * 
	 * @return Gun width
	 */
	public float getGunWidth()
	{
		return gunWidth;
	}
	
	/**
	 * 
	 * @return Gun length
	 */
	public float getGunLength()
	{
		return gunLength;
	}
	
	/**
	 * 
	 * @return Tank height
	 */
	public float getHeight()
	{
		return height;
	}
	
	/**
	 * 
	 * @return Tank's facing
	 */
	public TankFacing getFacing()
	{
		return facing;
	}
	
	/**
	 * 
	 * @return True if the tank is moving, false otherwise
	 */
	public boolean isMoving()
	{
		return moving;
	}
	
	public boolean isCollidable()
	{
		return collidable;
	}
	
	/**
	 * Tank render method
	 */
	public void render()
	{
		GL11.glTranslatef(x + width/2, y + height/2, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-(x + width/2), -(y + height/2), 0);
		
		// tank
		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	
		// gun
		GL11.glColor3f(0, 0, 1);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + (width/2 - gunWidth/2), (y + height/2) - gunLength);
			GL11.glVertex2f(x + (width/2 + gunWidth/2), (y + height/2) - gunLength);
			GL11.glVertex2f(x + (width/2 + gunWidth/2), (y + height/2));
			GL11.glVertex2f(x + (width/2 - gunWidth/2), (y + height/2));
		GL11.glEnd();
	}
	
	/**
	 * Tank update method
	 */
	public void update()
	{
		controller.checkInput();
		
		if(controller.up && !controller.down && !controller.left && !controller.right)
		{
			if(!up)
			{
				up = true;
				down = false;
				right = false;
				left = false;
				moving = true;
				facing = TankFacing.NORTH;
				dy = -SPEED;
			}
		} else if(!controller.up && !controller.down)
		{
			up = false;
			dy = 0;
		}
		
		if(controller.down && !controller.up && !controller.left && !controller.right)
		{
			if(!down)
			{
				down = true;
				up = false;
				right = false;
				left = false;
				moving = true;
				facing = TankFacing.SOUTH;
				dy = SPEED;
			}
		} else if(!controller.down && !controller.up)
		{
			down = false;
			dy = 0;
		}
		
		if(controller.left && !controller.right && !controller.up && !controller.down)
		{
			if(!left)
			{
				left = true;
				down = false;
				up = false;
				right = false;
				moving = true;
				facing = TankFacing.WEST;
				dx = -SPEED;
			}
		} else if(!controller.left && !controller.right)
		{
			left = false;
			dx = 0;
		}
		
		if(controller.right && !controller.left && !controller.up && !controller.down)
		{
			if(!right)
			{
				right = true;
				left = false;
				down = false;
				up = false;
				moving = true;
				facing = TankFacing.EAST;
				dx = SPEED;
			}
		} else if(!controller.right && !controller.left)
		{
			right = false;
			dx = 0;
		}
		
		if(!controller.up && !controller.down &&  !controller.left && !controller.right)
			moving = false;
		
		if(controller.fire)
		{
			controller.fire = false;
			weapon.fire(currentWeapon);
		}
		
		switch(facing)
		{
			case NORTH:
				angle = 0.0f;
				break;
			case EAST:
				angle = 90.0f;
				break;
			case SOUTH:
				angle = 180.0f;
				break;
			case WEST:
				angle = 270.0f;
				break;
		}
		
		for(int i = 0; i < PaddleGame.entities.size(); i++)
		{
			Entity e = PaddleGame.entities.get(i);
			
			if(e instanceof Collidable)
			{
				((Collidable) e).intersects(this);
			}
		}
		
		x += dx;
		y += dy;
	}
	
	/**
	 * Method for collision check
	 * 
	 * @param e Entity to check collision with
	 */
	public boolean intersects(Entity e)
	{
		if(e instanceof Projectile)
		{
			Projectile m = (Projectile) e;
			
			Rectangle missile = new Rectangle((int) m.getX(), (int) m.getY(), (int) m.getWidth(), (int) m.getHeight());
			Rectangle player = new Rectangle((int) x, (int) y, (int) width, (int) height);
			
			if(missile.intersects(player))
			{
				Pengine eng = new Pengine(new PVector(x + width/2, y + height/2), 100, 100, ColorTransition.getRandomTransition());
				eng.create();
				PaddleGame.entities.remove(e);
				PaddleGame.entities.remove(this);
				controller.removeControllerListener(this);
				
				return true;
			}
		} else if(e instanceof Ball)
		{
			Ball b = (Ball) e;
			
			Rectangle ball = new Rectangle((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
			Rectangle player = new Rectangle((int) x, (int) y, (int) width, (int) height);
			
			if(ball.intersects(player))
			{
				Pengine eng = new Pengine(new PVector(x + width/2, y + height/2), 100, 100, ColorTransition.getRandomTransition());
				eng.create();
				PaddleGame.entities.remove(this);
				controller.removeControllerListener(this);
				
				return true;
			}
		}
		
		return false;
	}
	
	
	@Override
	public void onWeaponChanged(WeaponChangedEvent e)
	{
		currentWeapon = e.getWeaponType();
		PaddleGame.log("Weapon changed! " + e.getWeaponType());
	}
}
