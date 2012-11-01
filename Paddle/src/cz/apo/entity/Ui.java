package cz.apo.entity;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import cz.apo.etc.Color;
import cz.apo.etc.OpFont;

public class Ui implements Entity {

	private Player uiPlayer;
	private float x,y,width,height,playerPartX,playerPartY,tankPartX,tankPartY;
	private Texture back_Texture;
	private Font font;
	private OpFont lives, weapon, ammo;
	
	public Ui(Player player)
	{
		this.uiPlayer = player;
		font = new Font("Arial", Font.BOLD, 12);
		weapon = new OpFont(tankPartX, tankPartY, "Weapon: ", font, java.awt.Color.WHITE);
		ammo = new OpFont(tankPartX, tankPartY + 12, "Ammo: ", font, java.awt.Color.WHITE);
		lives = new OpFont(playerPartX, playerPartY, "Lives: " + uiPlayer.lives, font, java.awt.Color.WHITE);
	}
	
	private void render_PlayerPart()
	{
		//Lives
		playerPartX = x;
		playerPartY = y;
		lives.setPos(playerPartX, playerPartY);
		lives.setText("Lives: " + uiPlayer.lives);
		lives.render();
	}
	
	private void render_TankPart()
	{
		//Ammo + Weapon
		tankPartX = x;
		tankPartY = y + 17;
		weapon.setPos(tankPartX, tankPartY);
		weapon.setText("Weapon: " + uiPlayer.getTank().getClusterCount());
		ammo.setText("Ammo: " + uiPlayer.getTank().getMissileCount());
		ammo.setPos(tankPartX, tankPartY + 12);
		weapon.render();
		ammo.render();
	}
	
	@Override
	public void render() 
	{
		// TODO Auto-generated method stub
		Color col = new Color(0,125,0);
		GL11.glColor4f(col.R, col.G, col.B, 0.2f);
		GL11.glBegin(GL11.GL_QUADS);		
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		render_PlayerPart();
		render_TankPart();
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
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

	public Texture getBack_Texture()
	{
		return back_Texture;
	}

	public void setBack_Texture(Texture back_Texture) 
	{
		this.back_Texture = back_Texture;
	}

	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font) 
	{
		this.font = font;
	}

	
	
}
