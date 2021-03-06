package cz.apopt.lightEngine;

import org.lwjgl.opengl.GL11;

import cz.apopt.etc.Color;

public class PointLight implements Light 
{
	public static final long FLASH_DURATION = 60L; // In ms
	
	float x;
	float y;
	float range;
	int bAngle = 0;
	int eAngle = 360;
	int sides = 180;
	Color centerColor = new Color(0f, 0f, 0f, 0.6f);
	Color edgeColor = new Color(0f, 0f, 0f, 0f);
	
	private long timeFlashed;
	private boolean flashed = false;
	
	public PointLight(float x, float y, float range)
	{
		this.x = x;
		this.y = y;
		this.range = range;
	}
	
	
	
	public Color getCenterColor()
	{
		return centerColor;
	}



	public void setCenterColor(Color centerColor)
	{
		this.centerColor = centerColor;
	}



	public Color getEdgeColor()
	{
		return edgeColor;
	}



	public void setEdgeColor(Color edgeColor)
	{
		this.edgeColor = edgeColor;
	}



	public float getX()
	{
		return x;
	}



	public void setX(float x) 
	{
		this.x = x;
	}



	public float getY() 
	{
		return y;
	}



	public void setY(float y) 
	{
		this.y = y;
	}



	public float getRange() 
	{
		return range;
	}



	public void setRange(float range) 
	{
		this.range = range;
	}



	public int getbAngle() 
	{
		return bAngle;
	}



	public void setbAngle(int bAngle)
	{
		this.bAngle = bAngle;
	}



	public int geteAngle() 
	{
		return eAngle;
	}



	public void seteAngle(int eAngle)
	{
		this.eAngle = eAngle;
	}



	public int getSides() 
	{
		return sides;
	}



	public void setSides(int sides) 
	{
		this.sides = sides;
	}

	public void flash()
	{
		flashed = true;
		
		this.centerColor.R = 0.8f;
		this.centerColor.G = 0.0f;
		this.centerColor.B = 0.0f;
		
		this.edgeColor.R = 0.25f;
		this.edgeColor.G = 0.25f;
		this.edgeColor.B = 0.25f;
		
		timeFlashed = System.currentTimeMillis();
	}

	@Override
	public void render()
	{
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glColor4f(centerColor.R, centerColor.G, centerColor.B, centerColor.A);
			GL11.glVertex2f(this.x, this.y);
		GL11.glColor4f(edgeColor.R, edgeColor.G, edgeColor.B, edgeColor.A);
		for(int i = bAngle; i < eAngle + 1; i ++)
		{
			GL11.glVertex2f((x + range*(float)Math.cos(0 + ((i*(2*Math.PI))/sides))), (y + range*(float)Math.sin(0 + ((i*(2*Math.PI))/sides))));
		}
		GL11.glEnd();
	}

	@Override
	public void update()
	{
		if(flashed)
		{
			if(System.currentTimeMillis() >= timeFlashed + FLASH_DURATION)
			{
				flashed = false;
				this.centerColor.R = 0.0f;
				this.centerColor.G = 0.0f;
				this.centerColor.B = 0.0f;
				
				this.edgeColor.R = 0.0f;
				this.edgeColor.G = 0.0f;
				this.edgeColor.B = 0.0f;

			}
		}
	}
}
