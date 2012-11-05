package cz.apopt.lightEngine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

public class LEngine
{
	float screenWidth;
	float screenHeight;
	List<Light> lightList = new ArrayList<Light>();
	
	public LEngine(float scWidth, float scHeight)
	{
		this.screenHeight = scHeight;
		this.screenWidth = scWidth;
	}
	
	public void addLight(Light light)
	{
		lightList.add(light);
	}
	
	public void removeLight(Light light)
	{
		lightList.remove(light);
	}
	
	private void clearAlphaChannel()
	{
		GL11.glClearColor(0f, 0f, 0f, 0f);
		GL11.glColorMask(false, false, false, true);
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	    GL11.glColorMask(true, true, true, true);
	    GL11.glClearColor(0f, 0f, 0f, 0f);
	}
	
	public void render()
	{
		clearAlphaChannel();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		for(Light l : lightList)
			l.render();
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_DST_ALPHA);
		GL11.glColor4f(0f, 0f, 0f, 1f);
		GL11.glBegin(GL11.GL_QUADS);		
			GL11.glVertex2f(0, 0);
			GL11.glVertex2f(0 + screenWidth, 0);
			GL11.glVertex2f(0 + screenWidth, 0 + screenHeight);
			GL11.glVertex2f(0, 0 + screenHeight);
		GL11.glEnd();
	}
}
