package cz.apo.event;

/**
 * Button event class
 * 
 * @author adam
 */
public class ButtonEvent
{
	private String buttonName;
	
	/**
	 * Constructor
	 * 
	 * @param buttonName Button name
	 */
	public ButtonEvent(String buttonName)
	{
		this.buttonName = buttonName;
	}
	
	/**
	 * 
	 * @return Button name
	 */
	public String getName()
	{
		return buttonName;
	}
}
