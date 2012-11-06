package cz.apopt.ui;

import java.awt.Font;

import org.lwjgl.opengl.GL11;

import cz.apopt.etc.Color;
import cz.apopt.etc.OpFont;

public class UiBar
{
	float x;
	float y;
	float width;
	float height;
	float innerOffset;
	float maxHealth;
	float curHealth;
	OpFont healthText;
	Color backGroundBoxColor = new Color(0f, 0f, 0f, 125.5f);
	Color innerBoxColor = new Color(0f, 1f, 0f, 200f);;
	java.awt.Color textColor = java.awt.Color.red;
	
	 public UiBar(float x, float y, float width, float height, float innerOffset, float maxHealth)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.innerOffset = innerOffset;
		this.maxHealth = maxHealth;
		this.curHealth = maxHealth;
		this.healthText = new OpFont(0, 0, curHealth + "/" + this.maxHealth, new Font("Arial", Font.BOLD, (int)(this.height - 2)), textColor);
		this.healthText.setPos(x + (width)/2 - this.healthText.getWidth()/2, y + (height - innerOffset)/2 - this.healthText.getHeight()/2);
	}
	
	public void render()
	{
		this.healthText.setText(curHealth + "/" + maxHealth);
		GL11.glColor4f(backGroundBoxColor.R, backGroundBoxColor.G, backGroundBoxColor.B, backGroundBoxColor.A);
		GL11.glBegin(GL11.GL_QUADS);		
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		GL11.glColor4f(innerBoxColor.R, innerBoxColor.G, innerBoxColor.B, innerBoxColor.A);
		GL11.glBegin(GL11.GL_QUADS);		
			GL11.glVertex2f(x + innerOffset, y + innerOffset);
			GL11.glVertex2f((x + (width - innerOffset)*(curHealth/maxHealth)), y + innerOffset);
			GL11.glVertex2f((x + (width - innerOffset)*(curHealth/maxHealth)), y + height - innerOffset);
			GL11.glVertex2f((x + innerOffset), y + height - innerOffset);
		GL11.glEnd();
		healthText.render();
	}
	
	public Color getBackGroundBoxColor()
	{
		return backGroundBoxColor;
	}

	public void setBackGroundBoxColor(Color backGroundBoxColor)
	{
		this.backGroundBoxColor = backGroundBoxColor;
	}

	public Color getInnerBoxColor()
	{
		return innerBoxColor;
	}

	public void setInnerBoxColor(Color innerBoxColor)
	{
		this.innerBoxColor = innerBoxColor;
	}

	public java.awt.Color getTextColor()
	{
		return textColor;
	}

	public void setTextColor(java.awt.Color textColor)
	{
		this.textColor = textColor;
		this.healthText.setCol(textColor);
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

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	public float getInnerOffset()
	{
		return innerOffset;
	}

	public void setInnerOffset(float innerOffset)
	{
		this.innerOffset = innerOffset;
	}

	public float getMaxHealth()
	{
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth)
	{
		this.maxHealth = maxHealth;
	}

	public float getCurHealth()
	{
		return curHealth;
	}

	public void setCurHealth(float curHealth)
	{
		this.curHealth = curHealth;
	}

	public OpFont getHealthText()
	{
		return healthText;
	}

	public void setHealthText(OpFont healthText)
	{
		this.healthText = healthText;
	}
	
	
}
