package cz.apo.entity;

import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import cz.apo.entity.projectile.Projectile;
import cz.apo.etc.Color;
import cz.apo.paddleGame.PaddleGame;
import cz.opt.pEngine.ColorTransition;
import cz.opt.pEngine.PVector;
import cz.opt.pEngine.Pengine;

/**
 * Main block class. Can be collidable or not
 * 
 * @author adam
 */
public class Block implements Entity, Collidable
{	
	private float x, y;
	private float blockWidth, blockHeight;
	
	private boolean colidable;
	private boolean texture;

	private Color col;
	private String tPath;
	
	/**
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param blockWidth width
	 * @param blockHeight height
	 * @param textPath path of texture
	 * @param properties array of properties of Block (0 - Collidable)
	 */
	public Block(float x, float y, float blockWidth, float blockHeight, String textPath, boolean[] properties)
	{
		this.x = x;
		this.y = y;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.tPath = textPath;
		this.texture = true;
		propertiesInit(properties);
	}

	/**
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param blockWidth width
	 * @param blockHeight height
	 * @param col color
	 * @param properties array of properties of Block (0 - Collidable)
	 */
	public Block(float x, float y, float blockWidth, float blockHeight, Color col, boolean[] properties)
	{
		this.x = x;
		this.y = y;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.col = col;
		this.texture = false;
		propertiesInit(properties);
	}
	
	public boolean isColidable() {
		return colidable;
	}

	public void setColidable(boolean colidable) {
		this.colidable = colidable;
	}

	public boolean isTexture() {
		return texture;
	}

	public void setTexture(boolean texture) {
		this.texture = texture;
	}

	public Color getCol() {
		return col;
	}

	public void setCol(Color col) {
		this.col = col;
	}

	public String gettPath() {
		return tPath;
	}

	public void settPath(String tPath) {
		this.tPath = tPath;
	}

	public Block(Block another)
	{
	    this.x = another.getX();
	    this.y = another.getY();
	    this.blockHeight = another.getHeight();
	    this.blockWidth = another.getWidth();
	    this.col = another.getCol();
	    this.texture = another.isTexture();
	    this.tPath = another.gettPath();
	    this.colidable = another.isColidable();
	}
	
	private void propertiesInit(boolean[] properties)
	{
		this.colidable = properties[0];
	}
	
	/**
	 * Block render method
	 */
	public void render()
	{
		//if texture
		if(!texture)
		{
			GL11.glColor3f(col.R, col.G, col.B);
			GL11.glBegin(GL11.GL_QUADS);		
				GL11.glVertex2f(x, y);
				GL11.glVertex2f(x + blockWidth, y);
				GL11.glVertex2f(x + blockWidth, y + blockHeight);
				GL11.glVertex2f(x, y + blockHeight);
			GL11.glEnd();
		}
		else
		{
			
		}
	}
	
	/**
	 * Block update method
	 */
	public void update()
	{
		
	}

	/**
	 * 
	 * @return X coordinate of the block
	 */
	public float getX()
	{	
		return x;
	}

	/**
	 * 
	 * @return Y coordinate of the block
	 */
	public float getY()
	{
		return y;
	}
	
	/**
	 * 
	 * @return Block width
	 */
	public float getWidth()
	{
		return blockWidth;
	}
	
	/**
	 * 
	 * @return Block height
	 */
	public float getHeight()
	{
		return blockHeight;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getBlockWidth() {
		return blockWidth;
	}

	public void setBlockWidth(float blockWidth) {
		this.blockWidth = blockWidth;
	}

	public float getBlockHeight() {
		return blockHeight;
	}

	public void setBlockHeight(float blockHeight) {
		this.blockHeight = blockHeight;
	}

	/**
	 * Method for collision check
	 * 
	 * @param e Entity to check collision with the block
	 */
	public boolean intersects(Entity e)
	{
		if(e instanceof Ball)
		{
			Ball b = (Ball) e;
			int bRadius = (int) b.getWidth();
			
			Rectangle block = new Rectangle((int) x, (int) y, (int) blockWidth, (int) blockHeight);
			Rectangle ball = new Rectangle((int) b.getX(), (int) b.getY(), bRadius, bRadius);			
			
			if(ball.intersects(block))
			{
				// step back
				float pX = b.getX() - b.getDX();
				float pY = b.getY() - b.getDY();
								
				boolean left = false;
				if(pX + bRadius <= x)
					left = true;
				
				boolean right = false;
				if(pX >= x + blockWidth)
					right = true;
				
				boolean top = false;
				if(pY + bRadius <= y)
					top = true;
				
				boolean bottom = false;
				if(pY >= y + blockHeight)
					bottom = true;
				
				if(left || right)
				{
					b.setDX(-(b.getDX()));
				}
				else if(top || bottom)
				{
					b.setDY(-b.getDY());
				}
				
				Pengine eng = new Pengine(new PVector(x, y, blockWidth, blockHeight), 50, 50, ColorTransition.getRandomTransition());
				eng.create();
				PaddleGame.entities.remove(this);
				return true;
			}
		} else if(e instanceof Projectile)
		{
			Projectile p = (Projectile) e;
			int mWidth = (int) p.getWidth();
			int mHeight = (int) p.getHeight();
			
			Rectangle block = new Rectangle((int) x, (int) y, (int) blockWidth, (int) blockHeight);
			Rectangle missile = new Rectangle((int) p.getX(), (int) p.getY(), mWidth, mHeight);
			
			if(missile.intersects(block))
			{
				PaddleGame.entities.remove(p);
				Pengine eng = new Pengine(new PVector(x, y, blockWidth, blockHeight), 50, 50, ColorTransition.getRandomTransition());
				eng.create();
				PaddleGame.log("removing");
				PaddleGame.entities.remove(this);
			}
		} else if((e instanceof Tank) && colidable)
		{
			Tank p = (Tank) e;
			int pWidth = (int) p.getWidth();
			int pHeight = (int) p.getHeight();
			
			Rectangle wall = new Rectangle((int) x, (int) y, (int) blockWidth, (int) blockHeight);
			Rectangle player = new Rectangle((int) p.getX(), (int) p.getY(), pWidth, pHeight);			
			
			if(player.intersects(wall))
			{
				// step back
				float pX = p.getX() - p.getDX();
				float pY = p.getY() - p.getDY();
								
				boolean left = false;
				if(pX + pWidth <= x)
					left = true;
				
				boolean right = false;
				if(pX >= x + blockWidth)
					right = true;
				
				boolean top = false;
				if(pY + pHeight <= y)
					top = true;
				
				boolean bottom = false;
				if(pY >= y + blockHeight)
					bottom = true;
				
				if(left || right)
				{
					p.setDX(0);
					p.setX(pX);
				}
				else if(top || bottom)
				{
					p.setDY(0);
					p.setY(pY);
				}
				return true;

			}
		} 		
		return false;
	}
}
