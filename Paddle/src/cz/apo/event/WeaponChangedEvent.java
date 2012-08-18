package cz.apo.event;

public class WeaponChangedEvent
{
	private int weaponType;
	
	public WeaponChangedEvent(int weaponType)
	{
		this.weaponType = weaponType;
	}
	
	public int getWeaponType()
	{
		return weaponType;
	}
}
