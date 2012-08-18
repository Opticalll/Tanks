package cz.opt.particleEngine.Particles;

import org.lwjgl.opengl.GL11;

import cz.opt.particleEngine.ParticleRGB;

public class Square extends Particle
{
	public Square( float x, float y, float size, float radius, ParticleRGB color, TypeSpread TS, TypePar TP, float vx, float vy, int miliSec, boolean rotate)
	{
		super(x, y, size, radius, color, TS, TP, vx, vy, miliSec, rotate);
	}

	@Override
	public void onRender()
	{
		if(rotate)
		{
			GL11.glTranslatef((x + size/2), (y + size/2), 0);
			GL11.glRotatef(angle, 0, 0, 1);
			GL11.glTranslatef(-(x + size/2), -(y + size/2), 0);
		}
		GL11.glColor4f(color.getR(), color.getG(), color.getB(), color.getA());
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + size, y);
		GL11.glVertex2f(x + size, y + size);
		GL11.glVertex2f(x, y + size);
		GL11.glEnd();

	}

}
