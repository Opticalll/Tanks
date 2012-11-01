package cz.apo.entity.projectile;


/**
 * Projectile interface
 * 
 * @author adam
 */
public interface Projectile
{
	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public void checkCollision();
}
