package cz.apopt.paddleGame;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import cz.apopt.entity.Entity;
import cz.apopt.entity.Tank;
import cz.apopt.event.ItemChangedEvent;
import cz.apopt.event.LevelChangedEvent;
import cz.apopt.event.WeaponChangedEvent;
import cz.apopt.listener.ControllerListener;
import cz.apopt.listener.WorldListener;

/**
 * Controller class
 * 
 * @author Adam & Optical
 */
public class Controller
{	
	public static final int DEFAULT_LEVEL = 1;
	private final int UP, DOWN, RIGHT, LEFT, FIRE, NEXT_W, PREV_W, BUILD, PREV_I, NEXT_I, USE_I;
	
	private static final int MAX_LEVEL = 1;
	private static final long FIRE_DELAY = 500 * 1000000;
	private static int level = DEFAULT_LEVEL;
	private static boolean homeDown = false, endDown = false;
	private long lastTime = 0;
	
	public boolean left = false;
	public boolean right = false;
	public boolean up = false;
	public boolean down = false;
	public boolean build = false;
	public boolean useItem = false;
	public volatile boolean fire = false;
	
	private boolean next_p = false;
	private boolean prev_p = false;
	private boolean next_i = false;
	private boolean prev_i = false;
	private boolean use_down = false;
	
	private final List<ControllerListener> controllerListeners;
	private static final List<WorldListener> worldListeners = new ArrayList<WorldListener>();
	
	/**
	 * 
	 * @param id Controller ID
	 */
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
			BUILD = Keyboard.KEY_NUMPAD0;
			PREV_I = Keyboard.KEY_NUMPAD1;
			NEXT_I = Keyboard.KEY_NUMPAD3;
			USE_I = Keyboard.KEY_NUMPAD5;
		} else if(id == 2)
		{
			UP = Keyboard.KEY_W;
			DOWN = Keyboard.KEY_S;
			LEFT = Keyboard.KEY_A;
			RIGHT = Keyboard.KEY_D;
			FIRE = Keyboard.KEY_SPACE;
			NEXT_W = Keyboard.KEY_E;
			PREV_W = Keyboard.KEY_Q;
			BUILD = Keyboard.KEY_B;
			PREV_I = Keyboard.KEY_LCONTROL;
			NEXT_I = Keyboard.KEY_LMENU;
			USE_I = Keyboard.KEY_X;
		} else
		{
			UP = DOWN = LEFT = RIGHT = FIRE = NEXT_W = PREV_W = BUILD = PREV_I = NEXT_I = USE_I = 0;
		}
		
		controllerListeners = new ArrayList<ControllerListener>();
	}
	
	/**
	 * Adds new ControllerListener to this class
	 * 
	 * @param l ControllerListener
	 */
	public void addControllerListener(ControllerListener l)
	{
		controllerListeners.add(l);
	}
	
	/**
	 * Adds new WorldListener to this class
	 * 
	 * @param l WorldListener
	 */
	public static void addWorldListener(WorldListener l)
	{
		worldListeners.add(l);
	}
	
	/**
	 * Removes ControllerListener if exists
	 * 
	 * @param l ControllerListener to remove
	 */
	public void removeControllerListener(ControllerListener l)
	{
		controllerListeners.remove(l);
	}
	
	/**
	 * Removes WorldListener if exists
	 * 
	 * @param l WorldListener to remove
	 */
	public static void removeWorldListener(WorldListener l)
	{
		worldListeners.remove(l);
	}
	
	/**
	 * Main check input method
	 */
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
		if(Keyboard.isKeyDown(BUILD))
			build = true;
		else
			build = false;
		if(Keyboard.isKeyDown(FIRE))
		{
			if(System.nanoTime() > lastTime + FIRE_DELAY)
			{
				lastTime = System.nanoTime();
				fire = true;		
			}
		}
		
		Keyboard.enableRepeatEvents(false);
		// Weapon change
		if(Keyboard.isKeyDown(NEXT_W))
		{
			if(!next_p)
			{
				next_p = true;
				
				weaponChanged(WeaponChangedEvent.NEXT);
			}
		} else if(!Keyboard.isKeyDown(NEXT_W))
			next_p = false;
		
		if(Keyboard.isKeyDown(PREV_W))
		{
			if(!prev_p)
			{
				prev_p = true;
				
				weaponChanged(WeaponChangedEvent.PREVIOUS);
			}
		} else if(!Keyboard.isKeyDown(PREV_W))
			prev_p = false;
		
		// Item change
		if(Keyboard.isKeyDown(NEXT_I))
		{
			if(!next_i)
			{
				next_i = true;
				itemChanged(ItemChangedEvent.NEXT);
			}
		} else if(!Keyboard.isKeyDown(NEXT_I))
			next_i = false;
		
		if(Keyboard.isKeyDown(PREV_I))
		{
			if(!prev_i)
			{
				prev_i = true;
				itemChanged(ItemChangedEvent.PREVIOUS);
			}
		} else if(!Keyboard.isKeyDown(PREV_I))
			prev_i = false;
		
		// Use item
		if(Keyboard.isKeyDown(USE_I))
		{
			if(!use_down)
			{
				use_down = true;
				useItem = true;
			} else
				useItem = false;
		} else if(!Keyboard.isKeyDown(USE_I))
			use_down = false;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			PaddleGame.cleanUp();
		
//		Keyboard.enableRepeatEvents(true);
	}
	
	/**
	 * Static check input method. Reads keys like HOME and END to change levels
	 */
	public static void static_checkInput(PaddleGame game)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_HOME) && !homeDown)
		{
			homeDown = true;
			if(level < MAX_LEVEL)
			{
				level++;
				levelChanged();
			}
		} else if(Keyboard.isKeyDown(Keyboard.KEY_END) && !endDown)
		{
			endDown = true;
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
		} else if(Keyboard.isKeyDown(Keyboard.KEY_P))
		{
			game.invokeGameMenu();
		}
		
		if(!Keyboard.isKeyDown(Keyboard.KEY_HOME))
			homeDown = false;
		if(!Keyboard.isKeyDown(Keyboard.KEY_END))
			endDown = false;
	}
	
//	private static int getMaxLevel()
//	{
//		int count = 0;
//		try
//		{
//			for(String str : dir.list())
//			{
//				if(str.endsWith(".lvl"))
//					count++;
//			}
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//		return count - 1;
//	}
	
	// =============== EVENTS ============== //
	
	/**
	 * Fires WeaponChangedEvent
	 */
	private void weaponChanged(int event)
	{
		WeaponChangedEvent e = new WeaponChangedEvent(event);
		
		for(ControllerListener l : controllerListeners)
			l.onWeaponChanged(e);
	}
	
	/**
	 * Fires ItemChangedEvent
	 * 
	 * @param event Event - Use ItemChangedEvent.NEXT or ItemChangedEvent.PREVIOUS
	 */
	private void itemChanged(int event)
	{
		ItemChangedEvent e = new ItemChangedEvent(event);
		
		for(ControllerListener l : controllerListeners)
			l.onItemChanged(e);
	}
	
	/**
	 * Fires LevelChangedEvent
	 */
	private static void levelChanged()
	{
		LevelChangedEvent e = new LevelChangedEvent(level);

		for(WorldListener l : worldListeners)
			l.onLevelChanged(e);
	}
}
