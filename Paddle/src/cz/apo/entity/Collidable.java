package cz.apo.entity;

/**
 * Collidable interface
 * 
 * @author adam
 */
public interface Collidable
{
	public boolean isSolid();
	public boolean isDestroyable();
	public boolean intersects(Entity e);
}
