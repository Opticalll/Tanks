package cz.apo.entity.items;

import org.newdawn.slick.opengl.Texture;

import cz.apo.entity.Tank;


public class AmmoPack extends Item
{
	private static final Texture texture = loadTexture("res/textures/items/AmmoPack.png");

	public AmmoPack(float x, float y)
	{
		super();
		
		this.x = x;
		this.y = y;
		super.texture = AmmoPack.texture;
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
		tank.setFullAmmo();
	}
	
	public void use()
	{
		
	}
}
