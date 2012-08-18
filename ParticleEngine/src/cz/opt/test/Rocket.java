package cz.opt.test;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Rocket
{
	private float x, y;
	private final float w = 3f, h = 3f;
	private final Vector2f vec = new Vector2f(6f, 6f);
	
	public Rocket(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public void render()
	{
		GL11.glColor4f(1f, 1f, 0f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x - w/2, y + h/2);
			GL11.glVertex2f(x + w/2, y + h/2);
			GL11.glVertex2f(x + w/2, y - h/2);
			GL11.glVertex2f(x - w/2, y - h/2);
		GL11.glEnd();
	}
	
	public void update()
	{
		x += vec.getX();
		y += vec.getY();
		
//		TestMain.rocketFlame.setX(x);
//		TestMain.rocketFlame.setY(y);
//		TestMain.rocketFlame.create();
	}
}
