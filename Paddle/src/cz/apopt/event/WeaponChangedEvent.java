package cz.apopt.event;

/**
 * WeaponChangedEvent class
 * 
 * @author Adam & Optical
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
