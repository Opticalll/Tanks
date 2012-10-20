package cz.apo.multiplayerTest;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import cz.opt.networking.SocketEvent;
import cz.opt.networking.SocketListener;
import cz.opt.networking.SocketThread;

public class Server implements Runnable, SocketListener, Network
{
	public static final int PORT = 12345;
	private static final char END = '\r';
	
	private Thread thread;
	
	private ServerSocket srvr;
	private Socket socket;
	private SocketThread socketThread;
	private BufferedWriter out;
	
	
	public Server()
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
			System.out.println("Failed to send data [Server]");
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			srvr = new ServerSocket(12345);
			System.out.println("Waiting for client...");
			socket = srvr.accept();
			System.out.println("Client established: " + socket.getInetAddress());
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
		System.out.println("SERVER DATA: " + e.getData());
	}
}
