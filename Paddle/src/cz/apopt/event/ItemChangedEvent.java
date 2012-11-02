package cz.apopt.event;


public class ItemChangedEvent
{
	public static final int NEXT = 0;
	public static final int PREVIOUS = 1;
	
	private int event;
	
	public ItemChangedEvent(int event)
	{
		this.event = event;
	}
	
	public int getChangeEvent()
	{
		return event;
	}
}
