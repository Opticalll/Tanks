package cz.apopt.etc;

import java.util.ArrayList;
import java.util.List;

import cz.apopt.listener.TimerListener;

/**
 * Timer utility class.
 * 
 * @author adam
 *
 */
public class Timer
{
	private final long DELTA;
	private long last = -1;
	private long now = 0;
	
	private List<TimerListener> listeners;;
	
	/**
	 * Constructor.
	 * 
	 * @param delta Delta between updates.
	 */
	public Timer(long delta)
	{
		this.DELTA = delta;
		this.listeners = new ArrayList<TimerListener>();
	}
	
	/**
	 * Adds timer listener.
	 * 
	 * @param l The timer listener.
	 */
	public void addTimerListener(TimerListener l)
	{
		listeners.add(l);
	}
	
	/**
	 * Removes timer listener.
	 * 
	 * @param l Timer listener to remove.
	 */
	public void removeTimerListener(TimerListener l)
	{
		listeners.remove(l);
	}
	
	/**
	 * Update method.
	 */
	public void update()
	{
		if(last == -1) // First time
			last = System.currentTimeMillis();
		
		if((now = System.currentTimeMillis()) >= last + DELTA)
		{
			fireTimerEvent();
			last = now;
		}
	}
	
	/**
	 * Fires the timer event.
	 */
	private void fireTimerEvent()
	{
		for(TimerListener l : listeners)
			l.onTime();
	}
}
