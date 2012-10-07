package cz.apo.event;

/**
 * LevelChangedEvent class
 * 
 * @author adam
 */
public class LevelChangedEvent
{
	private int level;
	
	/**
	 * 
	 * @param level Level
	 */
	public LevelChangedEvent(int level)
	{
		this.level = level;
	}
	
	/**
	 * 
	 * @return Level
	 */
	public int getLevel()
	{
		return level;
	}
}
