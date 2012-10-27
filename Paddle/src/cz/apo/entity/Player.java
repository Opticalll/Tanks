package cz.apo.entity;

import java.util.ArrayList;
import java.util.List;

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
	public Player(float x, float y, int id)
	{
		controller = new Controller(id);
		respawn(true);
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
		
		Vector2f spawnPoint = getRandomSpawnPoint();
		PaddleGame.log("SpawnX: " + spawnPoint.x + " | SpawnY: " + spawnPoint.y);
		tank = new Tank(spawnPoint.x, spawnPoint.y, controller, this);
		PaddleGame.entities.add(tank);
	}
	
	private Vector2f getRandomSpawnPoint()
	{
		Vector2f spawnPoint = null;
		List<Block> potSpawns = new ArrayList<Block>();

		for (int i = 0; i < PaddleGame.entities.size(); i++)
		{
			Entity e = PaddleGame.entities.get(i);

			if (e instanceof Block)
			{
				Block b = (Block) e;
				if (!b.isSolid())
					potSpawns.add(b);
			}
		}

		
		if(potSpawns.size() > 1)
		{
			int rand = PaddleGame.getRandom(0, potSpawns.size() - 1);
			Block spawn = potSpawns.get(rand);
			spawnPoint = new Vector2f(spawn.getX(), spawn.getY());
		} else if(potSpawns.size() == 1)
			spawnPoint = new Vector2f(potSpawns.get(0).getX(), potSpawns.get(0).getY());
		else
			spawnPoint = new Vector2f(PaddleGame.getRandom(50f, 750f), PaddleGame.getRandom(50f, 550f));
		return spawnPoint;
	}
}
