package cz.opt.particleEngine.Particles;

import org.lwjgl.opengl.GL11;

import cz.opt.particleEngine.ParticleRGB;

public class Polygon extends Particle
{
	public Polygon(float x, float y, float size, float radius, ParticleRGB color, TypeSpread TS, TypePar TP, float vx, float vy, int miliSec, boolean rotate, int sides)
	{
		super(x, y, size, radius, color, TS, TP, vx, vy, miliSec, rotate, sides);
	}
	@Override
	public void onRender()
	{
		if(rotate)
		{
			GL11.glTranslatef(x, y, 0);
			GL11.glRotatef(angle, 0, 0, 1);
			GL11.glTranslatef(-x, -y, 0);
		}
		GL11.glColor4f(color.getR(), color.getG(), color.getB(), color.getA());
		GL11.glBegin(GL11.GL_POLYGON);
		for(int i = 1; i < sides + 1; i++)
		{
			GL11.glVertex2f((x + size*(float)Math.cos(0 + ((i*(2*Math.PI))/sides))), (y + size*(float)Math.sin(0 + ((i*(2*Math.PI))/sides))));
		}
		GL11.glEnd();
	}
}
