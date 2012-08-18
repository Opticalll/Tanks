package cz.apo.listener;

import cz.apo.event.WeaponChangedEvent;

public interface ControllerListener
{
	public void onWeaponChanged(WeaponChangedEvent e);
}
