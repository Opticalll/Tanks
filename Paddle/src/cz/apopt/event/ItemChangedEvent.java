package cz.apopt.event;

/**
 * Item changed event class.
 * 
 * @author adam
 *
 */
public class ItemChangedEvent
{
	public static final int NEXT = 0;
	public static final int PREVIOUS = 1;
	
	private int event;
	
	/**
	 * 
	 * @param event Event - NEXT or PREVIOUS
	 */
	public ItemChangedEvent(int event)
	{
		this.event = event;
	}
	
	/**
	 * 
	 * @return event type.
	 */
	public int getChangeEvent()
	{
		return event;
	}
}
