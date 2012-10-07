package cz.apo.event;

/**
 * WeaponChangedEvent class
 * 
 * @author adam
 */
public class WeaponChangedEvent
{
	private int weaponType;
	
	/**
	 * 
	 * @param weaponType Weapon type
	 */
	public WeaponChangedEvent(int weaponType)
	{
		this.weaponType = weaponType;
	}
	
	/**
	 * 
	 * @return Weapon type
	 */
	public int getWeaponType()
	{
		return weaponType;
	}
}
