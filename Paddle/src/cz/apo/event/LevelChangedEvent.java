package cz.apo.event;

public class LevelChangedEvent
{
	private int level;
	
	public LevelChangedEvent(int level)
	{
		this.level = level;
	}
	
	public int getLevel()
	{
		return level;
	}
}
