package cz.apopt.entity.weapons;

import java.util.ArrayList;
import java.util.List;

import cz.apopt.entity.Tank;
import cz.apopt.entity.projectile.AmmoStack;
import cz.apopt.entity.projectile.Cluster;
import cz.apopt.entity.projectile.GuidedMissile;
import cz.apopt.entity.projectile.Missile;
import cz.apopt.entity.projectile.Projectile;

public abstract class Weapon
{
	private final String NAME;
	
	protected List<AmmoStack> ammo;
	protected Projectile currentAmmo;
	protected Tank owner;
	private List<AmmoStack> def_ammo;
	
	protected Weapon(Tank owner, String name)
	{
		this.NAME = name;
		this.owner = owner;
		this.ammo = new ArrayList<AmmoStack>();				
		this.ammo.add(new AmmoStack(new Missile(owner), 10));
		this.ammo.add(new AmmoStack(new Cluster(owner), 10));
		this.ammo.add(new AmmoStack(new GuidedMissile(owner), 10));

		def_ammo = new ArrayList<AmmoStack>();
		for(AmmoStack stack : ammo)
			def_ammo.add(new AmmoStack(stack));
		
		if(!ammo.isEmpty())
			currentAmmo = ammo.get(0).getAmmo();
	}
	
	public Tank getOwner()
	{
		return owner;
	}
	
	public boolean isNoAmmo()
	{
		if(ammo.isEmpty())
			return true;
		return false;
	}
	
	public String getName()
	{
		return NAME;
	}
	
	public void setFullAmmo()
	{
		ammo.clear();
		for(AmmoStack stack : def_ammo)
			ammo.add(new AmmoStack(stack));
	}
	
	public String getCurrentProjectileName()
	{
		if(currentAmmo == null)
			return new String("NONE");
		
		return currentAmmo.getName();
	}
	
	public int getCurrentAmmoCount()
	{
		if(currentAmmo != null)
		{
			for(AmmoStack ammoStack : ammo)
			{
				if(ammoStack.getAmmoType().equals(currentAmmo.getClass()))
					return ammoStack.getCount();
			}
		}
		
		return 0;
	}
	
	public boolean canFire()
	{
		if(currentAmmo == null)
			return false;
		
		for(AmmoStack ammoStack : ammo)
		{
			if(ammoStack.getAmmoType().equals(currentAmmo.getClass()))
				return true;
		}
		
		return false;
	}
	
	protected void removeAmmo(Projectile p)
	{
		for(int i = 0; i < ammo.size(); i++)
		{
			AmmoStack ammoStack = ammo.get(i);
			
			if(ammoStack.getAmmoType().equals(currentAmmo.getClass()))
			{
				ammoStack.removeAmmo();
				if(ammoStack.getCount() == 0)
				{
					if(ammo.size() == 1)
					{
						ammo.remove(ammoStack);
						currentAmmo = null;
						break;
					} else
					{
						if(i == ammo.size() - 1)
						{
							currentAmmo = ammo.get(0).getAmmo();
							ammo.remove(ammoStack);
						} else
						{
							currentAmmo = ammo.get(i+1).getAmmo();
							ammo.remove(ammoStack);
						}
					}
				} else
				{
					currentAmmo = ammoStack.getAmmo();
				}
			}
		}
	}
	
	public void setAmmo(boolean next)
	{
		if(ammo.isEmpty())
			return;
		else if(ammo.size() == 1)
		{
			currentAmmo = ammo.get(0).getAmmo();
			return;
		}
		
		if(currentAmmo == null)
		{
			if(next)
				currentAmmo = ammo.get(0).getAmmo();
			else
				currentAmmo = ammo.get(ammo.size()-1).getAmmo();
		}
		
		for(int i = 0; i < ammo.size(); i++)
		{
			AmmoStack ammoStack = ammo.get(i);
			if(ammoStack.getAmmoType().equals(currentAmmo.getClass()))
			{
				if(next)
				{
					if(i == ammo.size() - 1)
					{
						currentAmmo = ammo.get(0).getAmmo();
						return;
					} else
					{
						currentAmmo = ammo.get(i+1).getAmmo();
						return;
					}
				} else
				{
					if(i == 0)
					{
						currentAmmo = ammo.get(ammo.size()-1).getAmmo();
						return;
					} else
					{
						currentAmmo = ammo.get(i-1).getAmmo();
						return;
					}
				}
			}
		}
	}	
	
	public abstract void fire();
	
}
