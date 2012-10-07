package cz.apo.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cz.apo.event.ButtonEvent;
import cz.apo.listener.ButtonListener;

/**
 * Menu button class
 * 
 * @author adam
 */
public class MenuButton
{
	private float x, y, w, h;
	private String name;
	
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
		// TODO: Add text on button, add method setText(String str) ... 
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(0.1f, 0.55f, 0.5f);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x+w, y);
			GL11.glVertex2f(x+w, y+h);
			GL11.glVertex2f(x, y+h);
			GL11.glColor3f(1, 1, 1);
		GL11.glEnd();
		
		if(Mouse.isButtonDown(0))
		{
			float mx = Mouse.getX();
			float my = Display.getHeight() - Mouse.getY();
			
			if(mx >= x && mx <= x+w && my >= y && my <= y+h)
				onClick();
		}
		
		float mx = Mouse.getX(), my = Display.getHeight() - Mouse.getY();
		
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
