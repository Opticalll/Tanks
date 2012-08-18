package cz.apo.entity.projectile;

public interface Projectile
{
	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public void checkCollision();
}
