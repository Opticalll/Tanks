package cz.apo.listener;

import cz.apo.event.LevelChangedEvent;

/**
 * World listener interface
 * 
 * @author adam
 */
public interface WorldListener
{
	public void onLevelChanged(LevelChangedEvent e);
}
