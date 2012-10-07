package cz.apo.entity;

import cz.apo.entity.projectile.Cluster;
import cz.apo.entity.projectile.Missile;
import cz.apo.paddleGame.PaddleGame;

/**
 * Weapon class
 * 
 * @author adam
 */
public class Weapon
{
	private Tank tank;
	
	public static final int MISSILE = 1;
	public static final int CLUSTER = 2;
	
	/**
	 * 
	 * @param tank Tank carrying this weapon
	 */
	public Weapon(Tank tank)
	{
		this.tank = tank;
	}
	
	/**
	 * 
	 * @param weaponType Weapon type - use Tank class static final integers
	 */
	public void fire(int weaponType)
	{
		switch(weaponType)
		{
			case MISSILE:
				switch(tank.getFacing())
				{
					case NORTH:
						PaddleGame.entities.add(new Missile(tank.getX() + (tank.getWidth()/2 - tank.getGunWidth()/2), (tank.getY() + tank.getHeight()/2) - tank.getGunLength(), 2.0f, 4.0f, tank));
						break;
					case EAST:
						PaddleGame.entities.add(new Missile(tank.getX() + (tank.getWidth()/2 + tank.getGunLength()), (tank.getY() + tank.getHeight()/2), 2.0f, 4.0f, tank));
						break;
					case SOUTH:
						PaddleGame.entities.add(new Missile((tank.getX() + tank.getWidth()/2), tank.getY() + tank.getHeight()/2 + tank.getGunLength(), 2.0f, 4.0f, tank));
						break;
					case WEST:
						PaddleGame.entities.add(new Missile(tank.getX() + (tank.getWidth()/2 - tank.getGunLength()), (tank.getY() + tank.getHeight()/2), 2.0f, 4.0f, tank));
						break;
				}
				break;
			case CLUSTER:
				switch(tank.getFacing())
				{
					case NORTH:
						PaddleGame.entities.add(new Cluster(tank.getX() + (tank.getWidth()/2 - tank.getGunWidth()/2), (tank.getY() + tank.getHeight()/2) - tank.getGunLength(), 2.0f, 4.0f, tank));
						break;
					case EAST:
						PaddleGame.entities.add(new Cluster(tank.getX() + (tank.getWidth()/2 + tank.getGunLength()), (tank.getY() + tank.getHeight()/2), 2.0f, 4.0f, tank));
						break;
					case SOUTH:
						PaddleGame.entities.add(new Cluster((tank.getX() + tank.getWidth()/2), tank.getY() + tank.getHeight()/2 + tank.getGunLength(), 2.0f, 4.0f, tank));
						break;
					case WEST:
						PaddleGame.entities.add(new Cluster(tank.getX() + (tank.getWidth()/2 - tank.getGunLength()), (tank.getY() + tank.getHeight()/2), 2.0f, 4.0f, tank));
						break;
				}
				break;
		}
	}
}
