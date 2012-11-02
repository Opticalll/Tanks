package cz.apopt.listener;

import cz.apopt.event.ItemChangedEvent;
import cz.apopt.event.WeaponChangedEvent;

/**
 * Controller listener interface
 * 
 * @author Adam & Optical
 */
public interface ControllerListener
{
	public void onWeaponChanged(WeaponChangedEvent e);
	public void onItemChanged(ItemChangedEvent e);
}
