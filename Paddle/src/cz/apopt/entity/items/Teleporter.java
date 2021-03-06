package cz.apopt.entity.items;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import cz.apopt.entity.Tank;
import cz.apopt.etc.OpSound;
import cz.apopt.paddleGame.PaddleGame;

public class Teleporter extends Item
{
	private static final Texture texture = loadTexture("/textures/items/Teleport.png");
	private static final String NAME = "Teleporter";
	private Tank owner = null;
	
	public Teleporter(float x, float y)
	{
		super();
		this.x = x;
		this.y = y;
		super.texture = Teleporter.texture;
	}

	public Teleporter(Tank tank)
	{
		super();
		super.texture = Teleporter.texture;
		this.owner = tank;
	}
	
	public String getName()
	{
		return NAME;
	}
	
	@Override
	public boolean isSolid()
	{
		return false;
	}

	@Override
	public boolean isDestroyable()
	{
		return false;
	}

	@Override
	public void onPick(Tank tank)
	{
		if(tank.getItems().isEmpty())
		{
			tank.addItem(this);
			tank.setCurrentItem(tank.getItems().get(0).getItem());
		} else
		{
			tank.addItem(this);
		}
		
		PaddleGame.log(tank.getPlayer().getName() + " picked up teleport");
		this.owner = tank;
	}

	@Override
	public void use()
	{
		if(owner != null)
		{
			Vector2f teleportLoc = PaddleGame.getRandomSpawnPoint();
			owner.teleport(teleportLoc);
			OpSound.audioMap.get("PORT").getAudio().playAsSoundEffect(1.0f, 1.0f, false);
		}
	}
	
}
