package cz.apopt.menu;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cz.apopt.etc.OpFont;
import cz.apopt.event.ButtonEvent;
import cz.apopt.listener.ButtonListener;

/**
 * Menu button class
 * 
 * @author Adam & Optical
 */
public class MenuButton
{
	private float x, y, w, h;
	private String name;
	private OpFont font;
	
	private List<ButtonListener> listeners;
	/**
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param w Button width
	 * @param h Button height
	 * @param name Button name
	 */
	public MenuButton(float x, float y, float w, float h, String name)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.name = name;
		this.font = new OpFont(0f, 0f, this.name, new Font("Arial", Font.BOLD, 15), java.awt.Color.YELLOW);
		this.listeners = new ArrayList<ButtonListener>();
	}
	
	/**
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param w Button width
	 * @param h Button height
	 * @param name Button name
	 * @param font Button text font
	 * @param col Text color
	 */
	public MenuButton(float x, float y, float w, float h, String name, Font font, Color col)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.name = name;
		this.font = new OpFont(0f, 0f, this.name, font, col);
		this.listeners = new ArrayList<ButtonListener>();
	}
	
	/**
	 * 
	 * @return Button name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Button render method
	 */
	public void render()
	{ 
		//this class does not have on loop...
		this.font.setPos(this.x + (this.w/2-this.font.getWidth()/2), this.y + (this.h/2-this.font.getHeight()));
		
		
		if(Mouse.isButtonDown(0))
		{
			float mx = Mouse.getX();
			float my = Display.getDisplayMode().getHeight() - Mouse.getY();
			
			if(mx >= x && mx <= x+w && my >= y && my <= y+h)
				onClick();
		}
		
		float mx = Mouse.getX(), my = Display.getDisplayMode().getHeight() - Mouse.getY();
		
		// Hover
		
		if(mx >= x && mx <= x+w && my >= y && my <= y+h)
		{
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glColor4f(0.7f, 0.7f, 0.85f, 0.4f);
				GL11.glVertex2f(x, y);
				GL11.glVertex2f(x+w, y);
				GL11.glVertex2f(x+w, y+h);
				GL11.glVertex2f(x, y+h);
				GL11.glColor4f(1, 1, 1, 1);
			GL11.glEnd();
		}
		else
		{
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(0.1f, 0.55f, 0.5f);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x+w, y);
			GL11.glVertex2f(x+w, y+h);
			GL11.glVertex2f(x, y+h);
			GL11.glColor3f(1, 1, 1);
		GL11.glEnd();
		}
		this.font.render();
	}
	
	/**
	 * Fires the onClick event to all listeners
	 */
	private void onClick()
	{
		for(ButtonListener l : listeners)
			l.onClicked(new ButtonEvent(name));
	}
	
	/**
	 * Adds new ButtonListener to this button
	 * 
	 * @param l ButtonListener
	 */
	public void addButtonListener(ButtonListener l)
	{
		this.listeners.add(l);
	}
}
