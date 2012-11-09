package cz.apopt.entity.items;

import java.awt.Rectangle;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import cz.apopt.entity.Collidable;
import cz.apopt.entity.Entity;
import cz.apopt.entity.Tank;
import cz.apopt.etc.OpSound;
import cz.apopt.paddleGame.PaddleGame;

public abstract class Item implements Entity, Collidable
{
	public static final int AMMO_PACK = 0;
	public static final int SPEED_BOOST = 1;
	public static final int TELEPORT = 2;
	public static final int REPAIR_KIT = 3;
	
	protected float x = 100, y = 100;
	protected float width = 13f;
	protected float height = 13f;
	protected float angle;
	
	protected Texture texture;
	
	public abstract void onPick(Tank tank);
	public abstract void use();
	public abstract String getName();
	
	Item()
	{
		angle = PaddleGame.getRandom(0.0f, 360.0f);
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
	
	public void drop()
	{
		PaddleGame.entities.add(this);
	}
	
	public void render()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		org.newdawn.slick.Color.white.bind();
		texture.bind();
		
		GL11.glTranslatef(x + width/2, y + height/2, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-x - width/2, -y - height/2, 0);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(x + width, y);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(x + width, y + height);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public void update()
	{
		angle+=1.0f;
		if(angle >= 360.0f)
			angle = 0.0f;
	}
	
	public static void spawnRandomItem()
	{
		Vector2f spawn = PaddleGame.getRandomSpawnPoint();
		Item toSpawn = null;
		
		int rand = PaddleGame.getRandom(0, 4);
		
		switch(rand)
		{
			case AMMO_PACK:
				toSpawn = new AmmoPack(spawn.x, spawn.y);
				break;
			case SPEED_BOOST:
				toSpawn = new SpeedBoost(spawn.x, spawn.y);
				break;
			case TELEPORT:
				toSpawn = new Teleporter(spawn.x, spawn.y);
				break;
			case REPAIR_KIT:
				toSpawn = new RepairKit(spawn.x, spawn.y);
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
			tex = TextureLoader.getTexture("PNG", Item.class.getResourceAsStream(path));
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
				OpSound.audioMap.get("PICK").getAudio().playAsSoundEffect(1.0f, 1.0f, false);
				PaddleGame.entities.remove(this);
				return true;
			}
		}
		
		return false;
	}
}
