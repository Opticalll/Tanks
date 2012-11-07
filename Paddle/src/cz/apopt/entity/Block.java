package cz.apopt.entity;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import cz.apopt.entity.projectile.Projectile;
import cz.apopt.etc.Color;
import cz.apopt.etc.OpSound;
import cz.apopt.pEngine.ColorTransition;
import cz.apopt.pEngine.PVector;
import cz.apopt.pEngine.Pengine;
import cz.apopt.paddleGame.PaddleGame;

/**
 * Main block class. Can be collidable or not
 * 
 * @author Adam & Optical
 */
public class Block implements Entity, Collidable
{	
	private float x, y;
	private float blockWidth, blockHeight;
		
	// Properties
	private boolean solid;
	private boolean destroyable;
	private boolean slow_boost;
	private float slow_boostFactor;
	private boolean damageDealing;
	private float dps;
	private boolean deadly;
	
	private boolean isTextured;
	
	private Color col;
	private String tPath;
	private Texture texture = null;
	
	/**
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param blockWidth width
	 * @param blockHeight height
	 * @param textPath path of texture
	 * @param properties array of properties of Block (0 - Solid; 1 - Destroyable; 2 - AmmoResupply)
	 */
	public Block(float x, float y, float blockWidth, float blockHeight, String textPath)
	{
		this.x = x;
		this.y = y;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.tPath = textPath;
		this.isTextured = true;
		this.texture = loadTexture(textPath, "PNG");
		PaddleGame.blocks.addBlock(this);
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
	public Block(float x, float y, float blockWidth, float blockHeight, Color col)
	{
		this.x = x;
		this.y = y;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.col = col;
		this.isTextured = false;
		PaddleGame.blocks.addBlock(this);
	}
	
	public Block(Block another)
	{
		this.x = another.getX();
		this.y = another.getY();
		this.blockHeight = another.getHeight();
		this.blockWidth = another.getWidth();
		this.col = another.getCol();
		this.isTextured = another.isTexture();
		this.tPath = another.gettPath();
		this.solid = another.isSolid();
		this.destroyable = another.isDestroyable();
		this.texture = another.texture;
		this.slow_boost = another.isSlow_boost();
		this.slow_boostFactor = another.getSlow_boostFactor();
		PaddleGame.blocks.addBlock(this);
	}
	
	public Block(Map<String, String> conf)
	{
		String[] rgb = conf.get("color").split(" ");
		this.col = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
		this.isTextured = Boolean.parseBoolean(conf.get("isTexture"));
		this.destroyable = Boolean.parseBoolean(conf.get("destroyable"));
		this.tPath = conf.get("texture");
		this.solid = Boolean.parseBoolean(conf.get("solid"));
		this.slow_boost = Boolean.parseBoolean(conf.get("slow_boost"));
		this.slow_boostFactor = Float.parseFloat(conf.get("slow_boostFactor"));
		this.damageDealing = Boolean.parseBoolean(conf.get("damageDealing"));
		this.dps = Float.parseFloat(conf.get("dps"));
		this.deadly = Boolean.parseBoolean(conf.get("deadly"));
		if(this.isTextured)
			this.texture = loadTexture(this.tPath, "PNG");
	}
	
	public void setBlock(Block another)
	{
		this.x = another.getX();
		this.y = another.getY();
		this.blockHeight = another.getBlockHeight();
		this.blockWidth = another.getBlockWidth();
		this.col = another.getCol();
		this.isTextured = another.isTexture();
		this.tPath = another.gettPath();
		this.solid = another.isSolid();
		this.destroyable = another.isDestroyable();
		this.deadly = another.isDeadly();
		this.damageDealing = another.isDamageDealing();
		this.dps = another.getDps();
	}
	
	public void delete()
	{
		try
		{
			this.finalize();
		} catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isDamageDealing() {
		return damageDealing;
	}

	public void setDamageDealing(boolean damageDealing) {
		this.damageDealing = damageDealing;
	}

	public float getDamagePerFrame()
	{
		return dps/PaddleGame.FPS;
	}
	
	public float getDps() {
		return dps;
	}

	public void setDps(float dps) {
		this.dps = dps;
	}

	public boolean isDeadly() {
		return deadly;
	}

	public void setDeadly(boolean deadly) {
		this.deadly = deadly;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public boolean isSlow_boost() {
		return slow_boost;
	}

	public void setSlow_boost(boolean slow_boost) {
		this.slow_boost = slow_boost;
	}

	public float getSlow_boostFactor() {
		return slow_boostFactor;
	}

	public void setSlow_boostFactor(float slow_boostFactor) {
		this.slow_boostFactor = slow_boostFactor;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isDestroyable()
	{
		return destroyable;
	}
	
	public void setDestroyable(boolean destroyable)
	{
		this.destroyable = destroyable;
	}
	
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public Texture getTexture()
	{
		return texture;
	}
	
	public boolean isTexture() {
		return isTextured;
	}

	public void setTexture(boolean texture) {
		this.isTextured = texture;
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
	
	private Texture loadTexture(String texturePath, String format)
	{
		Texture tex = null;
		try
		{
			tex = TextureLoader.getTexture(format, this.getClass().getResourceAsStream(texturePath));
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		return tex;
	}
	
	/**
	 * Block render method
	 */
	public void render()
	{
		//if texture
		if(!isTextured)
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
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			org.newdawn.slick.Color.white.bind();
			texture.bind();
			
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(x, y);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(x + blockWidth, y);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(x + blockWidth, y + blockHeight);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(x, y + blockHeight);
			GL11.glEnd();
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
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
		if(e instanceof Projectile)
		{
			Projectile p = (Projectile) e;
			
			int mWidth = (int) p.getWidth();
			int mHeight = (int) p.getHeight();
			
			Rectangle block = new Rectangle((int) x, (int) y, (int) blockWidth, (int) blockHeight);
			Rectangle missile = new Rectangle((int) p.getX(), (int) p.getY(), mWidth, mHeight);
			
			if(missile.intersects(block))
			{
				PaddleGame.entities.remove(p);
				if(this.destroyable)
				{
					Pengine eng = new Pengine(new PVector(x, y, blockWidth, blockHeight), 50, 50, ColorTransition.getRandomTransition());
					eng.create();
					OpSound.audioMap.get("EXPLOS").getAudio().playAsSoundEffect(1.0f, 1.0f, false);
//					PaddleGame.entities.remove(this);

					this.settPath("/textures/blocks/grass.png");
					this.setSolid(false);
					this.setDestroyable(false);
					this.texture = loadTexture(tPath, "PNG");
				}
			}
		} else if((e instanceof Tank))
		{
			Tank p = (Tank) e;
			int pWidth = (int) p.getWidth();
			int pHeight = (int) p.getHeight();
			
			Rectangle player = new Rectangle((int) p.getX(), (int) p.getY(), pWidth, pHeight);			
			
			if(player.intersects(new Rectangle((int) x, (int) y, (int) blockWidth, (int) blockHeight)))
			{
				if(solid)
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
				else if(slow_boost)
				{
					//set speed on MaxSpeed*slow_boostFactor
				}
				else if(damageDealing)
				{
					
				}
				else if(deadly)
				{
					
				}
			}
		} 		
		return false;
	}
}
