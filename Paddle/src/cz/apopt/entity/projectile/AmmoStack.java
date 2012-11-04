package cz.apopt.entity.projectile;


public class AmmoStack
{
	private int count;
	private Projectile projectile;
	
	public AmmoStack(Projectile projectile)
	{
		this.projectile = projectile;
		this.count = 1;
	}
	
	public AmmoStack(Projectile projectile, int count)
	{
		this.projectile = projectile;
		this.count = count;
	}
	
	public AmmoStack(AmmoStack another)
	{
		this.count = another.getCount();
		this.projectile = another.getAmmo();
	}

	public AmmoStack clone()
	{
		try
		{
			AmmoStack obj = (AmmoStack) super.clone();
			return obj;
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void addItem()
	{
		count++;
	}
	
	public void addAmmo(int count)
	{
		this.count += count;
	}
	
	public void removeAmmo()
	{
		count--;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
		
	public Class<? extends Projectile> getAmmoType()
	{
		return projectile.getClass();
	}
	
	public Projectile getAmmo()
	{
		return projectile;
	}
}
