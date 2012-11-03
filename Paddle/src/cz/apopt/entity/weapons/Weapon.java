package cz.apopt.entity.weapons;

import java.util.ArrayList;
import java.util.List;

import cz.apopt.entity.Tank;
import cz.apopt.entity.projectile.AmmoStack;
import cz.apopt.entity.projectile.Projectile;

public abstract class Weapon
{
	protected List<AmmoStack> ammo;
	protected Projectile currentAmmo;
	protected Tank owner;
	
	protected Weapon(Tank owner)
	{
		this.owner = owner;
		this.ammo = new ArrayList<AmmoStack>();
	}
	
	public Tank getOwner()
	{
		return owner;
	}
	
	public void setNextAmmo()
	{
		
	}	
	
	public void setPreviousAmmo()
	{
		
	}
	
	public abstract void fire(Projectile p);
	
}
