package cz.apo.pEngine;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Quad extends Particle
{	
	private float width, height;
	
	public Quad(float width, float height, float dx, float dy, Pengine eng)
	{
		super(dx, dy, eng);
		
		this.width = width;
		this.height = height;
	}
	
	public void render()
	{
		glColor4f(col.getR(), col.getG(), col.getB(), alpha-=fadeFactor);
		
		glTranslatef(x, y, 0);
		glRotatef(angle, 0, 0, 1);
		glTranslatef(-x, -y, 0);
		
		glBegin(GL_QUADS);
			glVertex2f(x - width/2, y + height/2);
			glVertex2f(x + width/2, y + height/2);
			glVertex2f(x + width/2, y - height/2);
			glVertex2f(x - width/2, y - height/2);
		glEnd();
		
		glColor4f(0f, 0f, 0f, 1f);
	}
}
