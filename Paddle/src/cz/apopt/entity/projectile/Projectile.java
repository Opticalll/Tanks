package cz.apopt.entity.projectile;

import cz.apopt.entity.Tank;


/**
 * Projectile interface
 * 
 * @author Adam & Optical
 */
public interface Projectile
{
	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public float getDamage();
	public void fire();
	public void checkCollision();
	public Tank getShooter();
	public String getName();
	public Projectile getInstance();
}
