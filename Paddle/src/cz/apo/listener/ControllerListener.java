package cz.apo.listener;

import cz.apo.event.ItemChangedEvent;
import cz.apo.event.WeaponChangedEvent;

/**
 * Controller listener interface
 * 
 * @author adam
 */
public interface ControllerListener
{
	public void onWeaponChanged(WeaponChangedEvent e);
	public void onItemChanged(ItemChangedEvent e);
}
