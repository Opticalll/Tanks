package cz.apo.entity;

import org.lwjgl.util.vector.Vector2f;

import cz.apo.paddleGame.Controller;
import cz.apo.paddleGame.PaddleGame;

/**
 * Player class
 * 
 * @author adam
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
			PaddleGame.log("Respawn. Lives: " + lives);
		}
		
		if(lives <= 0)
		{
			PaddleGame.log("Player lose");
			return;
		}
		
		Vector2f spawnPoint = PaddleGame.getRandomSpawnPoint();
		PaddleGame.log("SpawnX: " + spawnPoint.x + " | SpawnY: " + spawnPoint.y);
		tank = new Tank(spawnPoint.x, spawnPoint.y, controller, this);
		PaddleGame.entities.add(tank);
	}
}
