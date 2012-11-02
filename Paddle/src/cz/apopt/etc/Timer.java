package cz.apopt.etc;

import java.util.ArrayList;
import java.util.List;

import cz.apopt.listener.TimerListener;

public class Timer
{
	private final long DELTA;
	private long last = -1;
	private long now = 0;
	
	private List<TimerListener> listeners;;
	
	public Timer(long delta)
	{
		this.DELTA = delta;
		this.listeners = new ArrayList<TimerListener>();
	}
	
	public void addTimerListener(TimerListener l)
	{
		listeners.add(l);
	}
	
	public void removeTimerListener(TimerListener l)
	{
		listeners.remove(l);
	}
	
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
	
	private void fireTimerEvent()
	{
		for(TimerListener l : listeners)
			l.onTime();
	}
}
