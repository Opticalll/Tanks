package cz.apo.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cz.apo.entity.items.Item;
import cz.apo.entity.projectile.Projectile;
import cz.apo.etc.Color;
import cz.apo.event.ItemChangedEvent;
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
	public static final float DEF_SPEED = 2.0f;
	public static final int MAX_MISSILES = 15, MAX_CLUSTERS = 7;
	
	private float x, y, width, height;
	private float gunWidth, gunLength;
	private float dx = 0.0f, dy = 0.0f;
	public float speed = DEF_SPEED;
	private float angle = 0.0f;
	private long timeBoosted = 0;
	private long boostDuration = 0;
	
	private int currentWeapon;
	private Item currentItem = null;
	
	// Ammo
	private int missiles = MAX_MISSILES;
	private int clusters = MAX_CLUSTERS;
	private int rockets = 3;
	
	private boolean left = false, right = false, up = false, down = false;
	private boolean moving = false;
	private boolean boosted = false;
	private boolean solid = true;
	private boolean destroyable = true;
	
	private List<Item> items;
	
	private Player player;
	private Weapon weapon;
	private TankFacing facing;
	private Color color;
	private final Controller controller;
	
	/**
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param controller controller
	 */
	public Tank(float x, float y, Controller controller, Player player)
	{
		this.x = x;
		this.y = y;
		this.controller = controller;
		this.player = player;
		this.color = Color.getRandomColorF();
		this.items = new ArrayList<Item>();
		
		facing = TankFacing.NORTH;
		width = 15.0f;
		height = 15.0f;
		gunWidth = 2.0f;
		gunLength = 15.0f;
		
		weapon = new Weapon(this);
		currentWeapon = Controller.DEFAULT_WEAPON;
		if(!items.isEmpty())
			currentItem = items.get(0);
		
		controller.addControllerListener(this);
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
	
	public int getMissileCount()
	{
		return missiles;
	}
	
	public void setMissileCount(int missiles)
	{
		this.missiles = missiles;
	}
	
	public int getClusterCount()
	{
		return clusters;
	}
	
	public void setClusterCount(int clusters)
	{
		this.clusters = clusters;
	}
	
	public int getRocketCount()
	{
		return rockets;
	}
	
	public void setRocketCount(int rockets)
	{
		this.rockets = rockets;
	}
	
	public void setCurrentItem(Item i)
	{
		this.currentItem = i;
	}
	
	public void setFullAmmo()
	{
		this.missiles = MAX_MISSILES;
		this.clusters = MAX_CLUSTERS;
		this.rockets = 3;
		
		PaddleGame.log("Player " + player.getId() + " refilled ammo!");
	}
	
	public void setSpeedBoost(int dur)
	{
		boostDuration = dur;
		timeBoosted = System.currentTimeMillis();
		boosted = true;
		speed = DEF_SPEED * 2;
		updateSpeed();
	}
	
	public List<Item> getItems()
	{
		return items;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void addItem(Item item)
	{
		this.items.add(item);
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
	
	public boolean isSolid()
	{
		return solid;
	}
	
	public boolean isDestroyable()
	{
		return destroyable;
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
		GL11.glColor3f(color.R, color.G, color.B);
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
	
	private void updateSpeed()
	{
		if(up)
			dy = -speed;
		else if(down)
			dy = speed;
		else if(left)
			dx = -speed;
		else if(right)
			dx = speed;
	}
	
	/**
	 * Tank update method
	 */
	public void update()
	{
		if(boosted)
		{
			if(System.currentTimeMillis() >= timeBoosted + boostDuration)
			{
				boosted = false;
				speed = DEF_SPEED;
			}
		}
		
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
				dy = -speed;
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
				dy = speed;
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
				dx = -speed;
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
				dx = speed;
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
		
		if(controller.useItem)
		{
			if(currentItem != null)
			{
				currentItem.use();
				Class<? extends Item> type = currentItem.getClass();
				items.remove(currentItem);
				currentItem = getNextSameTypeItem(type);
			}
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
				PaddleGame.entities.remove(e);
				
				if(destroyable)
				{
					Pengine eng = new Pengine(new PVector(x + width/2, y + height/2), 100, 100, ColorTransition.getRandomTransition());
					eng.create();
					PaddleGame.entities.remove(this);
					controller.removeControllerListener(this);
					
					this.player.respawn(false);
				}
				return true;
			}
		} else if((e instanceof Tank))
		{
			Tank p = (Tank) e;
			if(p.isSolid())
			{
				int pWidth = (int) p.getWidth();
				int pHeight = (int) p.getHeight();
				
				Rectangle wall = new Rectangle((int) x, (int) y, (int) pWidth, (int) pHeight);
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
			}
		}
		return false;
	}	
	
	/**
	 * 
	 * @param next If true, search for next type of item in itemList. If false, search for previous.
	 * @return
	 */
	private Item getItem(boolean next)
	{
		Item item = currentItem;
		
		if(items.isEmpty())
		{
			PaddleGame.log("You don't have any items");
			return item;
		}
		
		Item first = items.get(0);
		boolean same = true;
		for(Item itm : items)
		{
			if(!first.getClass().equals(itm.getClass()))
			{
				same = false;
				break;
			}
		}
		
		if(same)
			return item;
		
		int startIndex = 0;
		for(int i = 0; i < items.size(); i++)
		{
			if(items.get(i).equals(currentItem))
			{
				startIndex = i;
				break;
			}
		}
		
		if(next)
		{
			for(int i = startIndex; i < items.size(); i++)
			{
				Item it = items.get(i);
				
				if(it.getClass().equals(currentItem.getClass()))
				{
					if(i == items.size() - 1)
						i = 0;
					continue;
				} else
				{
					item = it;
					break;
				}
			}
		} else
		{
			for(int i = startIndex; i >= 0; i--)
			{
				Item prevItem = items.get(i);
				if(prevItem.getClass().equals(currentItem.getClass()))
				{
					if(i == 0)
						i = items.size() - 1;
					continue;
				} else
				{
					item = prevItem;
					break;
				}
			}
		}
		
		return item;
	}
	
	private Item getNextSameTypeItem(Class<? extends Item> type)
	{
		Item item = null;
		
		if(items.isEmpty())
		{
			PaddleGame.log("No more items");
			return item;
		}
		
		for(Item i : items)
		{
			if(i.getClass().equals(type.getClass()))
			{
				item = i;
				return item;
			}
		}
		
		item = items.get(0);
		return item;
	}
	
	@Override
	public void onWeaponChanged(WeaponChangedEvent e)
	{
		currentWeapon = e.getWeaponType();
		PaddleGame.log("Weapon changed! " + e.getWeaponType());
	}
	
	@Override
	public void onItemChanged(ItemChangedEvent e)
	{
		if(e.getChangeEvent() == ItemChangedEvent.NEXT)
		{
			currentItem = getItem(true);
			
		} else if(e.getChangeEvent() == ItemChangedEvent.PREVIOUS)
		{
			currentItem = getItem(false);
		}
		
		if(currentItem != null)
			PaddleGame.log("Current item is: " + currentItem.getClass().getCanonicalName());
	}
}
