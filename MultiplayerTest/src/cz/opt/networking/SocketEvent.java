package cz.opt.networking;

import java.util.EventObject;

public class SocketEvent extends EventObject
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String data;
	
	public SocketEvent(Object source, String data)
	{
		super(source);
		this.data = data;
	}

	public String getData()
	{
		return data;
	}
}
