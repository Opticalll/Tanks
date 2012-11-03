package cz.apopt.menu;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cz.apopt.event.ButtonEvent;
import cz.apopt.listener.ButtonListener;
import cz.apopt.paddleGame.PaddleGame;

/**
 * Main menu class
 * 
 * @author Adam & Optical
 */
public class MainMenu implements ButtonListener
{
	private boolean gameStart = false;
	private MenuButton newGame, exit;
	
	/**
	 * Constructor
	 */
	public MainMenu()
	{		
		newGame = new MenuButton(Display.getDisplayMode().getWidth() / 2 - 100, Display.getDisplayMode().getHeight() / 2 + 0f, 200f, 100f, "New Game");
		newGame.addButtonListener(this);
		
		exit = new MenuButton(Display.getDisplayMode().getWidth() / 2 - 100, Display.getDisplayMode().getHeight() / 2 + 150, 200f, 100f, "Exit");
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
		GL11.glClearColor(0.1f, 0.5f, 0.15f, 0.7f);
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
