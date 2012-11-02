package cz.apopt.listener;

import cz.apopt.event.LevelChangedEvent;

/**
 * World listener interface
 * 
 * @author Adam & Optical
 */
public interface WorldListener
{
	public void onLevelChanged(LevelChangedEvent e);
}
