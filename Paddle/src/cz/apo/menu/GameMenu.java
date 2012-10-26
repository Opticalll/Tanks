package cz.apo.menu;



public class GameMenu implements Runnable
{
	private boolean active = false;
	
	private Thread thread;
	
	public GameMenu()
	{
		thread = new Thread(this);
	}
	
	public void invoke()
	{
		active = true;
		thread.start();
	}
	
	public void run()
	{
		while(active)
		{
			render();
		}
	}
	
	private void render()
	{
//		System.out.println("GameMenu running");
	}
}
