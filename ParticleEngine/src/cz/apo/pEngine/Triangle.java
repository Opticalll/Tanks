package cz.apo.pEngine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Triangle extends Particle
{
	private float s;
	private float height;
	
	public Triangle(float s, float dx, float dy, Pengine eng)
	{
		super(dx, dy, eng);
		
		this.s = s;
		
		this.height = (float) (Math.sqrt(3) / 2) * s;
	}
	
	public void render()
	{
		glColor4f(col.getR(), col.getG(), col.getB(), alpha-=fadeFactor);
		
		glTranslatef(x, y, 0);
		glRotatef(angle, 0, 0, 1);
		glTranslatef(-x, -y, 0);
		
		glBegin(GL_TRIANGLES);
			glVertex2f(x, y + ( (2f/3f)*height ));
			glVertex2f(x - s/2f, y - ( (1f/3f)*height ));
			glVertex2f(x + s/2f, y - ( (1f/3f)*height ));
		glEnd();
		
		glColor4f(0f, 0f, 0f, 1f);
	}
}
