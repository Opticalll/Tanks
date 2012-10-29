package cz.apo.entity.items;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import cz.apo.entity.Tank;
import cz.apo.paddleGame.PaddleGame;


public class SpeedBoost extends Item
{
	private static final Texture texture = loadTexture("res/textures/items/SpeedBooster.png");
	
	private float angle;
	
	public SpeedBoost(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.angle = PaddleGame.getRandom(0f, 360f);
	}
	
	public boolean isSolid()
	{
		return false;
	}
	
	public boolean isDestroyable()
	{
		return false;
	}
	
	public void onPick(Tank tank)
	{
		tank.setSpeedBoost(5000);
	}
	
	public void use()
	{
		
	}
	
	public void render()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		org.newdawn.slick.Color.white.bind();
		texture.bind();
		
		GL11.glTranslatef(x + width/2, y + height/2, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-x - width/2, -y - height/2, 0);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(x + width, y);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(x + width, y + height);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public void update()
	{
		angle += 1f;
		if(angle >= 360f)
			angle = 0f;
	}
}
