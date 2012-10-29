package cz.apo.entity.items;

import java.awt.Rectangle;
import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import cz.apo.entity.Collidable;
import cz.apo.entity.Entity;
import cz.apo.entity.Tank;
import cz.apo.paddleGame.PaddleGame;

public abstract class Item implements Entity, Collidable
{
	public static final int AMMO_PACK = 0;
	public static final int SPEED_BOOST = 1;
	
	protected float x = 100, y = 100;
	protected float width = 13f;
	protected float height = 13f;
	
	public abstract void onPick(Tank tank);
	public abstract void use();
	
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
	
	public void drop()
	{
		PaddleGame.entities.add(this);
	}
	
	public static void spawnRandomItem()
	{
		Vector2f spawn = PaddleGame.getRandomSpawnPoint();
		Item toSpawn = null;
		
		int rand = PaddleGame.getRandom(0, 2);
		
		switch(rand)
		{
			case AMMO_PACK:
				toSpawn = new AmmoPack(spawn.x, spawn.y);
				break;
			case SPEED_BOOST:
				toSpawn = new SpeedBoost(spawn.x, spawn.y);
				break;
		}
		
		if(toSpawn != null)
			PaddleGame.entities.add(toSpawn);
	}
	
	protected static Texture loadTexture(String path)
	{
		Texture tex = null;
		
		try
		{
			tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return tex;
	}
	
	public boolean intersects(Entity e)
	{
		if(e instanceof Tank)
		{
			Tank t = (Tank) e;
			
			Rectangle item = new Rectangle((int) x, (int) y, (int) width, (int) height);
			Rectangle tank = new Rectangle((int) t.getX(), (int) t.getY(), (int) t.getWidth(), (int) t.getHeight());
			
			if(item.intersects(tank))
			{
				onPick(t);	
				PaddleGame.entities.remove(this);
				return true;
			}
		}
		
		return false;
	}
}
