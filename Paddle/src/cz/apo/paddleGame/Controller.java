package cz.apo.paddleGame;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import cz.apo.entity.Entity;
import cz.apo.entity.Tank;
import cz.apo.event.LevelChangedEvent;
import cz.apo.event.WeaponChangedEvent;
import cz.apo.listener.ControllerListener;
import cz.apo.listener.WorldListener;

public class Controller
{	
	public static final int DEFAULT_LEVEL = 0, DEFAULT_WEAPON = 1;
	private final int UP, DOWN, RIGHT, LEFT, FIRE, NEXT_W, PREV_W;
	
	private static int level = DEFAULT_LEVEL;
	private int weapon = DEFAULT_WEAPON;
	private long lastTime = 0;
	
	public boolean left = false;
	public boolean right = false;
	public boolean up = false;
	public boolean down = false;
	public volatile boolean fire = false;
	
	private boolean next_p = false;
	private boolean prev_p = false;
	
	private final List<ControllerListener> controllerListeners;
	private static final List<WorldListener> worldListeners = new ArrayList<WorldListener>();
	
	public Controller(int id)
	{		
		if(id == 1)
		{
			UP = Keyboard.KEY_UP;
			DOWN = Keyboard.KEY_DOWN;
			LEFT = Keyboard.KEY_LEFT;
			RIGHT = Keyboard.KEY_RIGHT;
			FIRE = Keyboard.KEY_RETURN;
			NEXT_W = Keyboard.KEY_NUMPAD9;
			PREV_W = Keyboard.KEY_NUMPAD7;
		} else if(id == 2)
		{
			UP = Keyboard.KEY_W;
			DOWN = Keyboard.KEY_S;
			LEFT = Keyboard.KEY_A;
			RIGHT = Keyboard.KEY_D;
			FIRE = Keyboard.KEY_SPACE;
			NEXT_W = Keyboard.KEY_E;
			PREV_W = Keyboard.KEY_Q;

		} else
		{
			UP = DOWN = LEFT = RIGHT = FIRE = NEXT_W = PREV_W = 0;
		}
		
		controllerListeners = new ArrayList<ControllerListener>();
	}
	
	public void addControllerListener(ControllerListener l)
	{
		controllerListeners.add(l);
	}
	
	public static void addWorldListener(WorldListener l)
	{
		worldListeners.add(l);
	}
	
	public void removeControllerListener(ControllerListener l)
	{
		controllerListeners.remove(l);
	}
	
	public static void removeWorldListener(WorldListener l)
	{
		worldListeners.remove(l);
	}
	
	public void checkInput()
	{
		if(Keyboard.isKeyDown(UP))
			up = true;
		else
			up = false;	
		if(Keyboard.isKeyDown(DOWN))
			down = true;
		else
			down = false;
		if(Keyboard.isKeyDown(LEFT))
			left = true;
		else
			left = false;
		if(Keyboard.isKeyDown(RIGHT))
			right = true;
		else
			right = false;

		if(Keyboard.isKeyDown(FIRE))
		{
			if(System.nanoTime() > lastTime + 500000000L)
			{
				lastTime = System.nanoTime();
				fire = true;		
			}
		}
		
		if(Keyboard.isKeyDown(NEXT_W))
		{
			if(!next_p)
			{
				next_p = true;
				if(weapon < 6)
				{
					weapon++;
					weaponChanged();
				}
			}
		} else if(!Keyboard.isKeyDown(NEXT_W))
			next_p = false;
		
		if(Keyboard.isKeyDown(PREV_W))
		{
			if(!prev_p)
			{
				prev_p = true;
				if(weapon > 1)
				{
					weapon--;
					weaponChanged();
				}
			}
		} else if(!Keyboard.isKeyDown(PREV_W))
			prev_p = false;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			PaddleGame.cleanUp();
	}
	
	public static void static_checkInput()
	{
		Keyboard.enableRepeatEvents(false);
//		while(Keyboard.next())
//		{
			if(Keyboard.isKeyDown(Keyboard.KEY_HOME))
			{
				if(level < 1)
				{
					level++;
					levelChanged();
				}
			} else if(Keyboard.isKeyDown(Keyboard.KEY_END))
			{
				if(level > 0)
				{
					level--;
					levelChanged();
				}
			} else if(Keyboard.isKeyDown(Keyboard.KEY_INSERT))
			{
				for(Entity e : PaddleGame.entities)
				{
					if(e instanceof Tank)
						PaddleGame.log("tank");
				}
			}
//		}
		Keyboard.enableRepeatEvents(true);
	}
	
	// =============== EVENTS ============== //
	
	private void weaponChanged()
	{
		WeaponChangedEvent e = new WeaponChangedEvent(weapon);
		
		for(ControllerListener l : controllerListeners)
			l.onWeaponChanged(e);
	}
	
	private static void levelChanged()
	{
		LevelChangedEvent e = new LevelChangedEvent(level);
		
		PaddleGame.log("should change " + worldListeners.size());
		for(WorldListener l : worldListeners)
		{
			PaddleGame.log("change it");
			l.onLevelChanged(e);
		}
	}
}
