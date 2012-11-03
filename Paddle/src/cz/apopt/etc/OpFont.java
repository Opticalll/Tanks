package cz.apopt.etc;

import java.awt.Color;
import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * OpFont Class
 * 
 * Class for easy rendering text.
 * Features: -auto initialization
 * 			 -auto render
 * @author Adam & Optical
 */
public class OpFont 
{
	private UnicodeFont font;
	private Font bFont;
	private Color col;
	private String text;
	private float x,y;
	
	/**
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param text text
	 * @param f font
	 * @param color color of font
	 */
	@SuppressWarnings("unchecked")
	public OpFont(float x, float y, String text, Font f, Color col)
	{
		this.x = x;
		this.y = y;
		this.text = text;
		this.setbFont(f);
		this.setCol(col);
		
		this.font = new UnicodeFont(f);
		this.font.addAsciiGlyphs();
		this.font.getEffects().add(new ColorEffect(col));
		try {
			this.font.loadGlyphs();
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Render method
	 */
	public void render()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.font.drawString(this.x, this.y, this.text);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	/**
	 * 
	 * @return Width
	 */
	public float getWidth()
	{
		return (float)font.getWidth(this.text);
	}
	
	/**
	 * 
	 * @return Height
	 */
	public float getHeight()
	{
		return (float)font.getHeight(this.text);
	}
	
	/**
	 * Re-initialization method.
	 */
	@SuppressWarnings("unchecked")
	private void reInit()
	{
		this.font = new UnicodeFont(getbFont());
		this.font.addAsciiGlyphs();
		this.font.getEffects().add(new ColorEffect(getCol()));
		try {
			this.font.loadGlyphs();
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return The font.
	 */
	public UnicodeFont getFont() 
	{
		return font;
	}

	/**
	 * Sets the current font to another.
	 * 
	 * @param font New font.
	 */
	public void setFont(UnicodeFont font) 
	{
		this.font = font;
	}

	/**
	 * 
	 * @return Y coordinate.
	 */
	public float getY() 
	{
		return y;
	}

	/**
	 * Sets Y coordinate.
	 * 
	 * @param y New Y coordinate.
	 */
	public void setY(float y) 
	{
		this.y = y;
	}

	/**
	 * 
	 * @return X coordinate.
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * Sets X coordinate.
	 * 
	 * @param x New X coordinate.
	 */
	public void setX(float x) 
	{
		this.x = x;
	}
	
	/**
	 * Sets position.
	 * 
	 * @param x New X coordinate.
	 * @param y New Y coordinate.
	 */
	public void setPos(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return Text
	 */
	public String getText() 
	{
		return text;
	}

	/**
	 * Sets text.
	 * 
	 * @param text New text.
	 */
	public void setText(String text) 
	{
		this.text = text;
	}

	/**
	 * 
	 * @return Color.
	 */
	public Color getCol() 
	{
		return col;
	}

	/**
	 * Sets color.
	 * 
	 * @param col New color.
	 */
	public void setCol(Color col)
	{
		this.col = col;
		reInit();
	}

	/**
	 * 
	 * @return AWT font.
	 */
	public Font getbFont()
	{
		return bFont;
	}

	/**
	 * Sets AWT font
	 * 
	 * @param bFont New AWT font.
	 */
	public void setbFont(Font bFont) 
	{
		this.bFont = bFont;
		reInit();
	}
	
}
