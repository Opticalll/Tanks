package cz.apopt.event;

/**
 * WeaponChangedEvent class
 * 
 * @author Adam & Optical
 */
public class WeaponChangedEvent
{
	public static final int NEXT = 0, PREVIOUS = 1;
	
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
