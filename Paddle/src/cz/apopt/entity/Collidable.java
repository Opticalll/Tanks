package cz.apopt.entity;

/**
 * Collidable interface
 * 
 * @author Adam & Optical
 */
public interface Collidable
{
	public boolean isSolid();
	public boolean isDestroyable();
	public boolean intersects(Entity e);
}
