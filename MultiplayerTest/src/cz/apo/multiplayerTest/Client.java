package cz.apo.multiplayerTest;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import cz.opt.networking.SocketEvent;
import cz.opt.networking.SocketListener;
import cz.opt.networking.SocketThread;

public class Client implements Runnable, SocketListener, Network
{
	private static final char END = '\r';
	
	private Thread thread;
	
	private Socket socket;
	private SocketThread socketThread;
	private BufferedWriter out;
	
	public Client()
	{
		thread = new Thread(this);
	}
	
	public void start()
	{
		thread.start();
	}
	
	public void sendData(String data)
	{
		try
		{
			out.write(data + END);
			out.flush();
		} catch(IOException e)
		{
			System.out.println("Failed to send data [Client]");
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			socket = new Socket("192.168.11.51", Server.PORT);
			System.out.println("Host established: " + socket.getInetAddress());
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			socketThread = new SocketThread(socket);
			socketThread.addSocketListener(this);
			socketThread.start();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {}
	
	public void socketReadyRead(SocketEvent e)
	{
		System.out.println("CLIENT DATA: " + e.getData());
	}
}
