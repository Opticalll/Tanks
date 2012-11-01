package cz.apo.entity.items;

import org.newdawn.slick.opengl.Texture;

import cz.apo.entity.Tank;


public class SpeedBoost extends Item
{
	private static final Texture texture = loadTexture("res/textures/items/SpeedBooster.png");
	private static final String NAME = "SpeedBoost";
	private static final int BOOST_DURATION = 6000;
	
	public SpeedBoost(float x, float y)
	{
		super();
		
		this.x = x;
		this.y = y;
		super.texture = SpeedBoost.texture;
	}
	
	public String getName()
	{
		return NAME;
	}
	
	public boolean isSolid()
	{
		return false;
	}
	
	public boolean isDestroyable()
	{
		return false;
	}
	
	public void onPick(Tank tank)
	{
		tank.setSpeedBoost(BOOST_DURATION);
	}
	
	public void use()
	{
		
	}
}
