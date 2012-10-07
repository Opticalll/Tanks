package cz.apo.menu;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cz.apo.event.ButtonEvent;
import cz.apo.listener.ButtonListener;
import cz.apo.paddleGame.PaddleGame;

/**
 * Main menu class
 * 
 * @author adam
 */
public class MainMenu implements ButtonListener
{
	private boolean gameStart = false;
	private MenuButton newGame, exit;
	
	public MainMenu()
	{
		GL11.glClearColor(0.1f, 0.25f, 0.15f, 0.7f);
		
		newGame = new MenuButton(Display.getWidth() / 2 - 100, Display.getHeight() / 2 + 0f, 200f, 100f, "newGame");
		newGame.addButtonListener(this);
		
		exit = new MenuButton(Display.getWidth() / 2 - 100, Display.getHeight() / 2 + 150, 200f, 100f, "exit");
		exit.addButtonListener(this);
	}
	
	/**
	 * 
	 * @return True if game is about to start
	 */
	public boolean gameStart()
	{
		return gameStart;
	}

	/**
	 * Renders all menu components
	 */
	public void render()
	{
		newGame.render();
		exit.render();
	}
	
	/**
	 * Method from ButtonListener interface
	 */
	public void onClicked(ButtonEvent e)
	{
		if(e.getName().equals(newGame.getName()))
			gameStart = true;
		else if(e.getName().equals(exit.getName()))
			PaddleGame.cleanUp();
	}
}
