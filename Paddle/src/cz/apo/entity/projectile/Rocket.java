package cz.apo.entity.projectile;

import cz.apo.entity.Entity;

// TODO: Rocket

/**
 * Rocket class
 * 
 * @author adam
 */
public class Rocket implements Entity, Projectile
{
	private float x, y;
	private float width, height;
	
	public Rocket(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public float getX()
	{
		return x;
	}

	@Override
	public float getY()
	{
		return y;
	}

	@Override
	public float getWidth()
	{
		return width;
	}

	@Override
	public float getHeight()
	{
		return height;
	}

	@Override
	public void render()
	{

		
	}

	@Override
	public void update()
	{

		
	}

	@Override
	public void checkCollision()
	{

		
	}

}
