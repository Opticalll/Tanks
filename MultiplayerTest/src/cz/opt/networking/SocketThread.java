package cz.opt.networking;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.event.EventListenerList;

public class SocketThread extends Thread
{

	private Socket socket = null;
	private BufferedInputStream input = null;
	private InputStreamReader inRead = null;
	private EventListenerList socketListeners = new EventListenerList();
	private boolean running = false;


	public void addSocketListener(SocketListener listener) 
	{
		socketListeners.add(SocketListener.class, listener);
	}

	public void removeSocketListener(SocketListener listener) 
	{
		socketListeners.remove(SocketListener.class, listener);
	}

	private void fireEvent(SocketEvent event)
	{
		Object[] listenerArray = socketListeners.getListenerList();

		for(int i = 0; i < listenerArray.length; i++)
			if (listenerArray[i] == SocketListener.class) 
				((SocketListener)listenerArray[i+1]).socketReadyRead(event);   
	}

	public SocketThread(Socket socket)
	{
		running = true;
		try {
			this.socket = socket;
			input = new BufferedInputStream(this.socket.getInputStream());
			inRead = new InputStreamReader(input);
		} catch (IOException e) {
			System.err.println("" + e.toString());
			running = false;
		}
	}

	public void run()
	{
		while(running)
		{
			try
			{
				StringBuffer buf = new StringBuffer();
				int charac = 0;
				while((charac = inRead.read()) != 13)
					buf.append((char) charac);
				fireEvent(new SocketEvent((Object)socket, buf.toString()));
			} catch (IOException e)
			{
				System.err.println("" + e);
				running = false;
			}
		}
	}
	
	public void stopLoop()
	{
		this.running = false;
	}

}
	