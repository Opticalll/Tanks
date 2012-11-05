package cz.opt.test;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cz.opt.lEngine.Segment;

public class Block 
{
	static List<Block> blockList = new ArrayList<Block>();
	public float x, y, width, height;
	public Block(float x, float y, float width)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = width;
		blockList.add(this);
	}
	
	void render()
	{
		GL11.glColor4f(1f, 0, 0, 1f);
		GL11.glBegin(GL11.GL_QUADS);		
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}

}
