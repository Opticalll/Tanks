package cz.opt.lEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL41;

public class PointLight 
{
	float x,y,range;
	int sides = 360;
	
	
	public PointLight(float x, float y, float range)
	{
		this.x = x;
		this.y = y;
		this.range = range;
	}
	
	public void render()
	{
		GL11.glClearColor(0f, 0f, 0f, 0f);
		GL11.glColorMask(false, false, false, true);
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	    GL11.glColorMask(true, true, true, true);
	    GL11.glClearColor(0f, 0f, 0f, 0f);
	    
	    
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glColor4f(0f, 0f, 0f, 1f);
			GL11.glVertex2f(this.x, this.y);
		GL11.glColor4f(0f, 0f, 0f, 0f);
		for(int i = 0; i < sides + 1; i ++)
		{
			GL11.glVertex2f((x + range*(float)Math.cos(0 + ((i*(2*Math.PI))/sides))), (y + range*(float)Math.sin(0 + ((i*(2*Math.PI))/sides))));
		}
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glColor4f(0f, 0f, 0f, 1f);
			GL11.glVertex2f(this.x, this.y);
		GL11.glColor4f(0f, 0f, 0f, 0f);
		for(int i = 0; i < sides + 1; i ++)
		{
			GL11.glVertex2f((x + range*(float)Math.cos(0 + ((i*(2*Math.PI))/sides))), (y + range*(float)Math.sin(0 + ((i*(2*Math.PI))/sides))));
		}
		GL11.glEnd();
		
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_DST_ALPHA);
		GL11.glColor4f(0f, 0f, 0f, 1f);
		GL11.glBegin(GL11.GL_QUADS);		
			GL11.glVertex2f(0, 0);
			GL11.glVertex2f(0 + 800, 0);
			GL11.glVertex2f(0 + 800, 0 + 800);
			GL11.glVertex2f(0, 0 + 800);
		GL11.glEnd();
	}
	
}
