package cz.apopt.paddleGame;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import cz.apopt.entity.Block;
import cz.apopt.entity.Entity;
import cz.apopt.entity.Player;
import cz.apopt.entity.items.Item;
import cz.apopt.entity.projectile.Projectile;
import cz.apopt.etc.BlockList;
import cz.apopt.etc.FpsCounter;
import cz.apopt.etc.OpSound;
import cz.apopt.etc.Timer;
import cz.apopt.event.LevelChangedEvent;
import cz.apopt.lightEngine.LEngine;
import cz.apopt.listener.TimerListener;
import cz.apopt.listener.WorldListener;
import cz.apopt.pEngine.Pengine;
import cz.apopt.ui.Ui;
import cz.apopt.ui.menu.GameMenu;
import cz.apopt.ui.menu.MainMenu;

/**
 * 
 * @author Apo(Game) + Optical(Particle engine + loading maps to objects + some of game logic system designs...)
 * 
 * This is the main class of Tanks game.
 *
 */
public class PaddleGame implements Runnable
{
	public static List<Entity> entities = new ArrayList<Entity>();
	public static List<Ui> ui = new ArrayList<Ui>();
	public static BlockList blocks = new BlockList();
	public static LEngine lights;
	public static MainMenu menu;
	
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	public static final int FPS = 40;
	
	private static int level = Controller.DEFAULT_LEVEL;
	
	private final Thread gameThread;
	
	/**
	 * Main game constructor
	 */
	public PaddleGame()
	{	
		gameThread = new Thread(this);
	}
	
	/**
	 * Starts the game thread
	 */
	public synchronized void start()
	{
		gameThread.start();
	}
	
	/**
	 * All the main function are here, including initialization functions, menu loop, and game loop
	 */
	private void startGame()
	{
		logT("Initializing display...");
		initDisplay();
		logT("DONE!\n");
		
		logT("Initializing OpenGL...");
		initGL();
		logT("DONE!\n");
		
		logT("Initializing objects...");
		initObj();
		logT("DONE!\n");
		
		logT("Loading sounds...");
		loadSounds();
		logT("DONE!\n");
		
		logT("Setting up listeners...");
		initListeners();
		logT("DONE!\n");
		
		logT("Entering menu loop");
		menuLoop();
		
		logT("Entering game loop");
		gameLoop();
	}
	
	/**
	 * Thread's run method
	 */
	public void run()
	{
		startGame();
	}
	
	/**
	 * Main game loop
	 */
	private void gameLoop()
	{
		GL11.glClearColor(0f, 0f, 0f, 1f);
		
		// Timer for random item spawn
		final Random r = new Random();
		Timer itemSpawnTimer = new Timer(2000);
		itemSpawnTimer.addTimerListener(new TimerListener()
		{
			public void onTime()
			{
				int rand = r.nextInt(100);
				if(rand <= 100)
					Item.spawnRandomItem();
			}
		});

		while(!Display.isCloseRequested())
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			Controller.static_checkInput(this);
			FpsCounter.tick();
			
			for(int i = 0; i < entities.size(); i++)
			{		
				Entity e = entities.get(i);
				
				GL11.glPushMatrix();
					e.update();
					e.render();
				GL11.glPopMatrix();				
				
				if(e instanceof Projectile)
					((Projectile) e).checkCollision();
			}
			
			
			Pengine.update();
//			lights.render();
			itemSpawnTimer.update();
			
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			for(int i = 0; i < ui.size(); i++)
			{
				ui.get(i).update();
				ui.get(i).render();
			}
			
			
			Display.sync(FPS);
			Display.update();
		}
		
		cleanUp();
	}
	
	/**
	 * Invokes in-game menu
	 */
	public void invokeGameMenu()
	{
		new GameMenu().invoke();
	}
	
	/**
	 * Loop for main menu
	 */
	public void menuLoop()
	{
		boolean rand = getRandomBoolean();
		
		if(rand)
			OpSound.audioMap.get("MENU").getAudio().playAsMusic(1.0f, 0.5f, true);
		else
			OpSound.audioMap.get("MENU2").getAudio().playAsMusic(1.0f, 0.5f, true);
		
		while(!menu.gameStart())
		{
			if(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				cleanUp();
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			menu.render();
			
			Display.sync(FPS);
			Display.update();
		}
		
		if(rand)
			OpSound.audioMap.get("MENU").getAudio().stop();
		else
			OpSound.audioMap.get("MENU2").getAudio().stop();
	}
	
	/**
	 * Destroy/clean method
	 */
	public static void cleanUp()
	{
		Display.destroy();
		AL.destroy();
		System.exit(0);
	}
	
	/**
	 * Clear level
	 */
	private static void levelCleanUp()
	{
		entities.clear();
		//vole a pak se divis ze je na tom ui neco divnyho
		// - ja jsem vul :D
		PaddleGame.ui.clear();
	}
	
	/**
	 * Initializes display
	 */
	private void initDisplay()
	{
		try
		{   
			DisplayMode displayMode = null;
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			
			for (int i = 0; i < modes.length; i++)
			{
				if (modes[i].getWidth() == WINDOW_WIDTH
						&& modes[i].getHeight() == WINDOW_HEIGHT
						&& modes[i].isFullscreenCapable())
				{
					displayMode = modes[i];
				}
			}
			   
			Display.setDisplayMode(displayMode);
			Display.setTitle("Tanks");
			Display.setVSyncEnabled(false);
			Display.setFullscreen(false);
			Display.create();
		} catch(LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializes objects
	 */
	private static void initObj()
	{		
		menu = new MainMenu();
		lights = new LEngine(PaddleGame.WINDOW_WIDTH, PaddleGame.WINDOW_HEIGHT);
		
		Grid g = new Grid();
		g.setGrid(level);
		List<Entity> blocks = g.getBlocksFromGrid();
		
		for(Entity e : blocks)
			entities.add(e);

		Player pl = new Player(2);
		Ui ui = new Ui(pl);
		ui.setX(0);
		ui.setWidth(250);
		ui.setHeight(90);
		ui.setY(WINDOW_HEIGHT - ui.getHeight());
		PaddleGame.ui.add(ui);
		
		Player ppl = new Player(1);
		Ui uii = new Ui(ppl);
		uii.setWidth(250);
		uii.setHeight(90);
		uii.setX(WINDOW_WIDTH - uii.getWidth());
		uii.setY(WINDOW_HEIGHT - uii.getHeight());
		PaddleGame.ui.add(uii);
	}
	
	/**
	 * OpenGL initialization
	 */
	private void initGL()
	{
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * Sound loading
	 */
	private void loadSounds()
	{
		OpSound.addNewAudio("MENU", "/sounds/MenuSong.ogg");
		OpSound.addNewAudio("MENU2", "/sounds/MenuSong2.ogg");
		OpSound.addNewAudio("SHOT", "/sounds/Shot.ogg");
		OpSound.addNewAudio("PORT", "/sounds/Teleport.ogg");
		OpSound.addNewAudio("PICK", "/sounds/PickUp.ogg");
		OpSound.addNewAudio("EXPLOS", "/sounds/Explosion.ogg");
		OpSound.addNewAudio("KILL", "/sounds/TankKilled.ogg");
	}
	
	/**
	 * Game/World listener initialization
	 */
	private void initListeners()
	{
		Controller.addWorldListener(new WorldListener()
		{
			@Override
			public void onLevelChanged(LevelChangedEvent e)
			{
				PaddleGame.level = e.getLevel();
				levelCleanUp();
				initObj();
				gameLoop();		
			}
		});
	}
	
	/**
	 * Simple debug/log method
	 * 
	 * @param s Debug/log message
	 */
	public static void log(String s)
	{
		System.out.println(s);
	}
	
	public static void logT(String s)
	{
		System.out.println("[" + getTime() + "] - " + s);
	}
	
	/**
	 * Main method
	 * 
	 * @param args Program arguments
	 */
	public static void main(String[] args)
	{
		PaddleGame game = new PaddleGame();
		game.start();
	}
	
	/**
	 * Get a random float number.
	 * 
	 * @param min Bottom number of range.
	 * @param max Top number of range.
	 * @return Random float number
	 */
	public static float getRandom(float min, float max)
	{
		return (min + (float)(Math.random() * ((max - min))));
	}
	
	/**
	 * Get a random integer number.
	 * 
	 * @param min Bottom number of range.
	 * @param max Top number of range.
	 * @return Random integer number
	 */
	public static int getRandom(int min, int max)
	{
		return (min + (int)(Math.random() * ((max - min))));
	}
	
	public static boolean getRandomBoolean()
	{
		Random r = new Random();
		return r.nextBoolean();
	}
	
	public static String getTime()
	{
		Calendar c = Calendar.getInstance();
		String time = "" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + ":" + c.get(Calendar.MILLISECOND);
		
		return time;
	}
	
	/**
	 * Method for getting random spawn point.
	 * 
	 * @return Random spawn point.
	 */
	public static Vector2f getRandomSpawnPoint()
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
