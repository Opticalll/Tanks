package cz.apo.entity;

import cz.apo.paddleGame.Controller;
import cz.apo.paddleGame.PaddleGame;

public class Player
{	
	private Tank tank;
	private Controller controller;
	
	public Player(float x, float y, int id)
	{
		controller = new Controller(id);
		
		tank = new Tank(x, y, controller);
		PaddleGame.entities.add(tank);
	}

	public Tank getTank()
	{
		return tank;
	}
	
	public Controller getController()
	{
		return controller;
	}
}
