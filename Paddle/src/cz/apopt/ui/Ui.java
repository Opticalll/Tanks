package cz.apopt.ui;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import cz.apopt.entity.Player;
import cz.apopt.etc.Color;
import cz.apopt.etc.OpFont;


public class Ui 
{
	private Player uiPlayer;
	private float x,y,width,height,playerPartX,playerPartY,tankPartX,tankPartY;
	private Texture back_Texture;
	private Font font;
	private OpFont lives, weapon, ammo, currentItem, count;
	private UiBar healthBar;
	private Color col = new Color(0, 40, 100, 150);
	
	public Ui(Player player)
	{
		this.uiPlayer = player;
		font = new Font("Arial", Font.BOLD, 12);
		weapon = new OpFont(0, 0, "Weapon: ", font, java.awt.Color.WHITE);
		ammo = new OpFont(0, 0, "Ammo: ", font, java.awt.Color.WHITE);
		currentItem = new OpFont(0, 0 , "Current item: ", font, java.awt.Color.WHITE);
		count = new OpFont(0, 0, "Count: ", font, java.awt.Color.WHITE);
		lives = new OpFont(0, 0, "Lives: " + uiPlayer.getLives(), font, java.awt.Color.WHITE);
	}
	
	private void render_PlayerPart()
	{
		lives.render();
	}
	
	private void pos_init()
	{
		playerPartX = x;
		playerPartY = y;
		tankPartX = x;
		tankPartY = y + 16;
		

		lives.setPos(playerPartX, playerPartY);
		
		tankPartY += 15;
		weapon.setPos(tankPartX, tankPartY);
		
		tankPartY += 12;
		ammo.setPos(tankPartX, tankPartY);
		tankPartY += 12;
		currentItem.setPos(tankPartX, tankPartY);
		tankPartY += 12;
		count.setPos(tankPartX, tankPartY);
		tankPartY = y + 16;
		healthBar = new UiBar(tankPartX + 5, tankPartY, width - 10, 14, 3, uiPlayer.getTank().getMaxHealth());
	}
	
	private void render_TankPart()
	{
		healthBar.render();
		weapon.render();
		ammo.render();
		currentItem.render();
		count.render();
	}
	
	public void render() 
	{	
		GL11.glColor4f(col.R, col.G, col.B, col.A);
		GL11.glBegin(GL11.GL_QUADS);		
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		render_PlayerPart();
		render_TankPart();
	}

	public void update() 
	{
		lives.setText("Lives: " + uiPlayer.getLives());
		count.setText("Count: " + (uiPlayer.getTank().getCurrentItem() == null ? "-" : uiPlayer.getTank().getCurrentItemCount()));
		currentItem.setText("Current item: " + (uiPlayer.getTank().getCurrentItem() == null ? "NONE" : uiPlayer.getTank().getCurrentItem().getName()));
		weapon.setText("Weapon: " + uiPlayer.getTank().getWeapon().getCurrentProjectileName());
		ammo.setText("Ammo: " + ((uiPlayer.getTank().getWeapon().getCurrentAmmoCount() == 0) ? "-" : uiPlayer.getTank().getWeapon().getCurrentAmmoCount()));
		healthBar.setCurHealth(uiPlayer.getTank().getHealth());
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		pos_init();
	}

	public float getY() 
	{
		return y;
	}

	public void setY(float y) 
	{
		this.y = y;
		pos_init();
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
