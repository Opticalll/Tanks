package cz.apopt.entity.projectile;


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
	public void checkCollision();
}
