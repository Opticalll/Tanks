package cz.apopt.event;

/**
 * Button event class
 * 
 * @author Adam & Optical
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
