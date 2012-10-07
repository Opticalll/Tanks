package cz.apo.entity;

import cz.apo.paddleGame.Controller;
import cz.apo.paddleGame.PaddleGame;

/**
 * Player class
 * 
 * @author adam
 */
public class Player
{	
	private Tank tank;
	private Controller controller;
	
	/**
	 * Constructor of Player class
	 * 
	 * @param x X position of player
	 * @param y Y position of player
	 * @param id Player ID (for Controller class)
	 */
	public Player(float x, float y, int id)
	{
		controller = new Controller(id);
		
		tank = new Tank(x, y, controller);
		PaddleGame.entities.add(tank);
	}

	/**
	 * 
	 * @return Player's tank
	 */
	public Tank getTank()
	{
		return tank;
	}
	
	/**
	 * 
	 * @return Player's controller class
	 */
	public Controller getController()
	{
		return controller;
	}
}
