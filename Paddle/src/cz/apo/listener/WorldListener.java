package cz.apo.listener;

import cz.apo.event.LevelChangedEvent;

public interface WorldListener
{
	public void onLevelChanged(LevelChangedEvent e);
}
