package cz.apopt.entity;

import org.lwjgl.util.vector.Vector2f;

import cz.apopt.paddleGame.Controller;
import cz.apopt.paddleGame.PaddleGame;

/**
 * Player class
 * 
 * @author Adam & Optical
 */
public class Player
{
	private int id;
	
	private Tank tank;
	private Controller controller;

	int lives = 5;

	/**
	 * Constructor of Player class
	 * 
	 * @param x
	 *            X position of player
	 * @param y
	 *            Y position of player
	 * @param id
	 *            Player ID (for Controller class)
	 */
	public Player(int id)
	{
		this.id = id;
		controller = new Controller(id);
		respawn(true);
	}

	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return new String("Player " + id);
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

	public void respawn(boolean firstSpawn)
	{
		if(!firstSpawn) 
		{
			lives--;
			PaddleGame.log(getName() + " respawns. Lives: " + lives);
		}
		
		if(lives <= 0)
		{
			PaddleGame.log("Player lose");
			return;
		}
		
		Vector2f spawnPoint = PaddleGame.getRandomSpawnPoint();
		tank = new Tank(spawnPoint.x, spawnPoint.y, controller, this);
		PaddleGame.entities.add(tank);
	}
}
