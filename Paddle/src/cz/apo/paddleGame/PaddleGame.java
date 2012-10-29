package cz.apo.paddleGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import cz.apo.entity.Block;
import cz.apo.entity.Entity;
import cz.apo.entity.Player;
import cz.apo.entity.Wall;
import cz.apo.entity.items.Item;
import cz.apo.entity.projectile.Projectile;
import cz.apo.enums.BlockType;
import cz.apo.etc.FpsCounter;
import cz.apo.etc.Timer;
import cz.apo.event.LevelChangedEvent;
import cz.apo.listener.TimerListener;
import cz.apo.listener.WorldListener;
import cz.apo.menu.GameMenu;
import cz.apo.menu.MainMenu;
import cz.opt.pEngine.Pengine;

/**
 * 
 * @author Apo(Game) + Optical(Particle engine + loading maps to objects...)
 * 
 * This is the main class of Tanks game.
 *
 */
public class PaddleGame implements Runnable
{
	public static final List<Entity> entities = new ArrayList<Entity>();
	public static MainMenu menu;
	
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	public static final int WALL_WIDTH = 10;
	public static final int FPS = 35;
	
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
		initDisplay();
		initGL();
		initObj();
		initListeners();
		menuLoop();
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
		Timer itemSpawnTimer = new Timer(1); // TODO: EDIT :D
		itemSpawnTimer.addTimerListener(new TimerListener()
		{
			public void onTime()
			{
				int rand = r.nextInt(100);
				if(rand <= 100)
					Item.spawnRandomItem();
			}
		});
		
		// TODO: REMOVE :D
		Timer logger = new Timer(500);
		logger.addTimerListener(new TimerListener()
		{
			public void onTime()
			{
				log("SIZE: " + entities.size());
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
			itemSpawnTimer.update();
			logger.update();
			
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
		while(!menu.gameStart())
		{
			if(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				cleanUp();
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			menu.render();
			
			Display.sync(60);
			Display.update();
		}
	}
	
	/**
	 * Destroy/clean method
	 */
	public static void cleanUp()
	{
		Display.destroy();
		System.exit(0);
	}
	
	/**
	 * Clear level
	 */
	private static void levelCleanUp()
	{
		entities.clear();
	}
	
	/**
	 * Initializes display
	 */
	private void initDisplay()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
			Display.setTitle("PaddleGame");
			Display.setVSyncEnabled(true);
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
		
//		Wall northWall = new Wall(0, 0, Display.getDisplayMode().getWidth(), WALL_WIDTH, BlockType.WALL.getColor());
//		Wall southWall = new Wall(0, Display.getDisplayMode().getHeight() - WALL_WIDTH, Display.getDisplayMode().getWidth(), WALL_WIDTH, BlockType.WALL.getColor());
//		Wall eastWall = new Wall(Display.getDisplayMode().getWidth() - WALL_WIDTH, 0, WALL_WIDTH, Display.getDisplayMode().getHeight(), BlockType.WALL.getColor());
//		Wall westWall = new Wall(0, 0, WALL_WIDTH, Display.getDisplayMode().getHeight(), BlockType.WALL.getColor());

		Wall northWall = new Wall(0, 0, Display.getDisplayMode().getWidth(), WALL_WIDTH, BlockType.WALL.getColor());
		Wall southWall = new Wall(0, Display.getDisplayMode().getHeight() - WALL_WIDTH, Display.getDisplayMode().getWidth(), WALL_WIDTH, BlockType.WALL.getColor());
		Wall eastWall = new Wall(Display.getDisplayMode().getWidth() - WALL_WIDTH, 0, WALL_WIDTH, Display.getDisplayMode().getHeight(), BlockType.WALL.getColor());
		Wall westWall = new Wall(0, 0, WALL_WIDTH, Display.getDisplayMode().getHeight(), BlockType.WALL.getColor());
		
		Grid g = new Grid();
		g.setGrid(level);
		List<Entity> blocks = g.getBlocksFromGrid();
		
		for(Entity e : blocks)
			entities.add(e);
		// north
		entities.add(northWall);
		// east
		entities.add(eastWall);
		// south
		entities.add(southWall);
		// west
		entities.add(westWall);
				
		new Player(1);
		new Player(2);
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
	
	public static float getRandom(float min, float max)
	{
		return (min + (float)(Math.random() * ((max - min))));
	}
	
	public static int getRandom(int min, int max)
	{
		return (min + (int)(Math.random() * ((max - min))));
	}
	
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
