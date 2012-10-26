package cz.apo.paddleGame;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import cz.apo.entity.Entity;
import cz.apo.entity.Player;
import cz.apo.entity.Wall;
import cz.apo.entity.projectile.Projectile;
import cz.apo.enums.BlockType;
import cz.apo.etc.FpsCounter;
import cz.apo.event.LevelChangedEvent;
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
	public static final int WALL_WIDTH = 10;
	public static final int FPS = 25;
	
	private static int level = 1;
	
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
		
		while(!Display.isCloseRequested())
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			Controller.static_checkInput(this);
			FpsCounter.tick();
			
			Pengine.update();
			
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
			
			Display.sync(25);
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
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setFullscreen(false);
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
		
		// balls
//		entities.add(new Ball(157.0f, 650.0f, 10));
//		entities.add(new Ball(200.0f, 300.0f, 10));
		
		new Player(120.0f, 300.0f, 1);
		new Player(400.0f, 300.0f, 2);				
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
}
