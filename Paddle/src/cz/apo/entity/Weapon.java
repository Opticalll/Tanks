package cz.apo.entity;

import cz.apo.entity.projectile.Cluster;
import cz.apo.entity.projectile.Missile;
import cz.apo.etc.OpSound;
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
	public static final int ROCKET = 3;

	/**
	 * 
	 * @param tank
	 *            Tank carrying this weapon
	 */
	public Weapon(Tank tank)
	{
		this.tank = tank;
	}

	/**
	 * 
	 * @param weaponType
	 *            Weapon type - use Tank class static final integers
	 */
	public void fire(int weaponType)
	{
		boolean playSound = true;
		
		switch(weaponType)
		{
			case MISSILE:
				if(tank.getMissileCount() <= 0) 
				{
					playSound = false;
					break;
				}
				tank.setMissileCount(tank.getMissileCount()-1);
				switch(tank.getFacing())
				{
					case NORTH:
						PaddleGame.entities.add(new Missile(tank.getX() + (tank.getWidth()/2 - tank.getGunWidth()/2), (tank.getY() + tank.getHeight()/2) - tank.getGunLength()/2, 2.0f, 4.0f, tank));
						break;
					case EAST:
						PaddleGame.entities.add(new Missile(tank.getX() + (tank.getWidth()/2 + tank.getGunLength()/2), (tank.getY() + tank.getHeight()/2), 2.0f, 4.0f, tank));
						break;
					case SOUTH:
						PaddleGame.entities.add(new Missile((tank.getX() + tank.getWidth()/2), tank.getY() + tank.getHeight()/2 + tank.getGunLength()/2, 2.0f, 4.0f, tank));
						break;
					case WEST:
						PaddleGame.entities.add(new Missile(tank.getX() + (tank.getWidth()/2 - tank.getGunLength()/2), (tank.getY() + tank.getHeight()/2), 2.0f, 4.0f, tank));
						break;
				}
				break;
			case CLUSTER:
				if(tank.getClusterCount() <= 0) 
				{
					playSound = false;
					break;
				}
				tank.setClusterCount(tank.getClusterCount()-1);
				switch(tank.getFacing())
				{
					case NORTH:
						PaddleGame.entities.add(new Cluster(tank.getX() + (tank.getWidth()/2 - tank.getGunWidth()/2), (tank.getY() + tank.getHeight()/2) - tank.getGunLength()/2, 2.0f, 4.0f, tank));
						break;
					case EAST:
						PaddleGame.entities.add(new Cluster(tank.getX() + (tank.getWidth()/2 + tank.getGunLength()/2), (tank.getY() + tank.getHeight()/2), 2.0f, 4.0f, tank));
						break;
					case SOUTH:
						PaddleGame.entities.add(new Cluster((tank.getX() + tank.getWidth()/2), tank.getY() + tank.getHeight()/2 + tank.getGunLength()/2, 2.0f, 4.0f, tank));
						break;
					case WEST:
						PaddleGame.entities.add(new Cluster(tank.getX() + (tank.getWidth()/2 - tank.getGunLength()/2), (tank.getY() + tank.getHeight()/2), 2.0f, 4.0f, tank));
						break;
				}
				break;
			case ROCKET:
				
				break;
		}
		
		if(playSound)
			OpSound.soundMap.get("SHOT").getSound().play(1.0f, 0.6f);
	}
}
