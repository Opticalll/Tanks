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
	
	public void render()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.font.drawString(this.x, this.y, this.text);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public float getWidth()
	{
		return (float)font.getWidth(this.text);
	}
	
	public float getHeight()
	{
		return (float)font.getHeight(this.text);
	}
	
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

	public UnicodeFont getFont() 
	{
		return font;
	}

	public void setFont(UnicodeFont font) 
	{
		this.font = font;
	}

	public float getY() 
	{
		return y;
	}

	public void setY(float y) 
	{
		this.y = y;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x) 
	{
		this.x = x;
	}
	
	public void setPos(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public String getText() 
	{
		return text;
	}

	public void setText(String text) 
	{
		this.text = text;
	}

	public Color getCol() 
	{
		return col;
	}

	public void setCol(Color col)
	{
		this.col = col;
		reInit();
	}

	public Font getbFont()
	{
		return bFont;
	}

	public void setbFont(Font bFont) 
	{
		this.bFont = bFont;
		reInit();
	}
	
}
