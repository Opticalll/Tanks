package cz.apo.entity;

/**
 * Collidable interface
 * 
 * @author adam
 */
public interface Collidable
{
	public boolean isCollidable();
	public boolean intersects(Entity e);
}
