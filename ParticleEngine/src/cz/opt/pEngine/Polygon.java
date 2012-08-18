package cz.opt.pEngine;

import static org.lwjgl.opengl.GL11.glColor4f;

import org.lwjgl.opengl.GL11;

public class Polygon extends Particle
{
	public Polygon(float size, float vx, float vy, int sides, Pengine peng)
	{
		super(vx, vy, peng);
		this.sides = sides;
		this.size = size;
	}
	
	int sides = 0;
	float size = 0;
	
	@Override
	public void render()
	{
		glColor4f(col.getR(), col.getG(), col.getB(), alpha-=fadeFactor);
		
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-x, -y, 0);
		GL11.glBegin(GL11.GL_POLYGON);
		for(int i = 1; i < sides + 1; i++)
		{
			GL11.glVertex2f((x + size*(float)Math.cos(0 + ((i*(2*Math.PI))/sides))), (y + size*(float)Math.sin(0 + ((i*(2*Math.PI))/sides))));
		}
		GL11.glEnd();
	}
}
