package cz.apo.multiplayerTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

enum Status
{
	HOST, CLIENT;
}

public class Main
{
	public static volatile List<Entity> entities;
	private Status stat;
	
	// Network
	Network network;
	
	public Main()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setResizable(false);
			Display.create();
		} catch(LWJGLException e)
		{
			e.printStackTrace();
		}
		
		init();
		initNetwork();
		initGL();
		initObj();
		loop();
	}
	
	private void loop()
	{
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			if(Mouse.isButtonDown(0))
				network.sendData("Hello");

			Display.sync(30);
			Display.update();
		}
		
		Display.destroy();
		System.exit(0);
	}
	
	private void initObj()
	{
		entities = new ArrayList<Entity>();
	}
	
	private void initGL()
	{
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 600, 0, -1, 1);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	private void initNetwork()
	{
		if(stat == Status.HOST)
		{
			network = new Server();
			network.start();
		} else
		{
			network = new Client();
			network.start();
		}
	}
	
	private void init()
	{
		while(true)
		{
			Scanner s = new Scanner(System.in);
			String line = s.nextLine();
			
			if(line.equalsIgnoreCase("h"))
			{
				stat = Status.HOST;
				Display.setTitle("HOST");
				s.close();
				return;
			} else if(line.equalsIgnoreCase("c"))
			{
				stat = Status.CLIENT;
				Display.setTitle("CLIENT");
				s.close();
				return;
			}
			
			s.close();
		}
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}
