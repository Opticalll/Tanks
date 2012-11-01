package cz.apo.entity.items;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import cz.apo.entity.Tank;
import cz.apo.paddleGame.PaddleGame;

public class Teleporter extends Item
{
	private static final Texture texture = loadTexture("res/textures/items/Teleport.png");
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
		}
	}
	
}
