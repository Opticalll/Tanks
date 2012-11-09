package cz.apopt.entity.items;

import org.newdawn.slick.opengl.Texture;

import cz.apopt.entity.Tank;
import cz.apopt.paddleGame.PaddleGame;

public class RepairKit extends Item
{
	private static Texture texture = loadTexture("/textures/items/RepairKit.png");
	private static final String NAME = "RepairKit";
	
	private boolean fullRepair = false;
	
	private Tank owner = null;
	
	public RepairKit(float x, float y)
	{
		super();
		
		this.x = x;
		this.y = y;
		super.texture = RepairKit.texture;
		init();
	}
	
	public RepairKit(Tank tank)
	{
		super();
		super.texture = RepairKit.texture;
		this.owner = tank;
		init();
	}
	
	private void init()
	{
		int chance = PaddleGame.getRandom(0, 100);	
		if(chance < 25)
			fullRepair = true;
	}
	
	@Override
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
		
		this.owner = tank;
	}

	@Override
	public void use()
	{
		if(owner != null)
		{
			if(owner.getHealth() == owner.getMaxHealth())
				return;
			
			if(fullRepair)
			{
				owner.setHealth(owner.getMaxHealth());
				return;
			}
			
			float randomPercent = PaddleGame.getRandom(0.3f, 1.0f);
			float healthToFull = owner.getMaxHealth() - owner.getHealth();
			float healthToSet = owner.getHealth() + healthToFull*randomPercent;
			
			owner.setHealth(healthToSet);
		}
		
		
	}
}
