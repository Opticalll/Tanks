package cz.apopt.entity.projectile;

import java.awt.geom.Rectangle2D;

import org.lwjgl.opengl.GL11;

import cz.apopt.entity.Block;
import cz.apopt.entity.Collidable;
import cz.apopt.entity.Entity;
import cz.apopt.entity.Tank;
import cz.apopt.pEngine.PVector;
import cz.apopt.pEngine.Pengine;
import cz.apopt.pEngine.VVector;
import cz.apopt.paddleGame.PaddleGame;

public class GuidedMissile implements Entity, RocketProjectile
{

	float x,y,vx = 0f,vy = 0f,angle,speed = 8f, width = 5.0f, height = 5.0f, lockOnRange, vangle, targetangle;
	Tank target = null;
	Tank shooter;
	
	public GuidedMissile(Tank tank)
	{
		this.shooter = tank;
	}
	
	public GuidedMissile(float x, float y, Tank tank)
	{
		this.x = x;
		this.y = y;
		this.shooter = tank;
		this.angle = 0;
	}
	
	public Projectile getInstance()
	{
		return new GuidedMissile(shooter);
	}
	
	@Override
	public void render()
	{
		GL11.glTranslatef(x+width/2, y+height/2, 0);
			GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-(x+width/2), -(y+height/2), 0);
		
		PaddleGame.log("" + angle);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(x, y+height);
			GL11.glVertex2f(x+width, y+height);
			GL11.glVertex2f(x+width/2, y);
		GL11.glEnd();
	}
	
	private void lockOn()
	{
		for(Entity e : PaddleGame.entities)
		{
			if(e instanceof Tank)
			{
				Tank t = (Tank) e;
				if(t != shooter)
				{
					if(vx > 0 && (t.getX() + width/2) > x && ((t.getY() + t.getHeight()/2) <= y + lockOnRange && t.getY() >= y - lockOnRange))
						target = t;
					else if(vx < 0 && (t.getX() + width/2) < x && ((t.getY() + t.getHeight()/2) <= y + lockOnRange && t.getY() >= y - lockOnRange))
						target = t;
					else if(vy > 0 && (t.getY() + height/2) > y && ((t.getX() + t.getWidth()/2) <= x + lockOnRange && t.getX() >= x - lockOnRange))
						target = t;
					else if(vy < 0 && (t.getY() + height/2) < y && ((t.getX() + t.getHeight()/2) <= x + lockOnRange && t.getX() >= x - lockOnRange))
						target = t;
					else
						target = null;
				}
				else
					target = null;
				if(target != null)
				{
					PaddleGame.logT("Target Locked on X: " + target.getX() + " Y: " + target.getY() + "\n Missile Cord X: " + x + " Y: " + y);
					break;
				}	
			}
		}
	}

	private float findAngle(float px1, float py1, float px2, float py2)
	{
		return (float) (Math.atan2((py2 - py1), (px2 - px1)) * 180/Math.PI);
	}
	
	private boolean checkPathToTarget()
	{
		for(Block b : PaddleGame.blocks.blockList)
		{
			Rectangle2D.Float block = new Rectangle2D.Float(b.getX(), b.getY(), b.getWidth(), b.getHeight());
			if(block.intersectsLine(x, y, target.getX()+target.getWidth()/2, target.getY()+target.getHeight()/2))
				return false;	
		}
		return true;
	}
	
	@Override
	public void update()
	{
		if(target == null)
			lockOn();
		else
			if(checkPathToTarget())
				targetangle = findAngle(x, y, target.getX(), target.getY());
		if(target != null)
		{
			if(targetangle > angle)
				angle += vangle;
			else
				angle -=vangle;
		}
//		angle++;
		vx = (float) Math.sin(Math.toRadians(angle)) * speed;
		vy = (float) Math.cos(Math.toRadians(angle)) * speed;
		
		PaddleGame.log("sin: " + Math.sin(angle) +"vx :" + vx + " cos:  " +  Math.cos(angle) +"  vy: " + vy);
		
		x += vx;
		y += vy;
		
		Pengine eng = new Pengine(new PVector(x + width/2, y + height/2), 2, 90, null);
		eng.setVVector(new VVector(0.5f, 0.5f));
		eng.setTime(0.05f);
		eng.setMinFade(0.005f);
		eng.setMaxFade(0.5f);
		eng.create();
	}

	@Override
	public float getX()
	{
		return x;
	}

	@Override
	public float getY()
	{
		return y;
	}

	@Override
	public float getWidth()
	{
		return width;
	}

	@Override
	public float getHeight()
	{
		return height;
	}

	@Override
	public void checkCollision()
	{
		for(int i = 0; i < PaddleGame.entities.size(); i++)
		{
			Entity e = PaddleGame.entities.get(i);
			
			if(e instanceof Collidable)
			{
				Collidable obj = (Collidable) e;
				if(obj.equals(shooter))
					return;
				if(obj.isSolid() || obj.isDestroyable())
					obj.intersects(this);
			}
		}
	}

	@Override
	public Tank getShooter()
	{
		return shooter;
	}

	@Override
	public String getName()
	{
		return "Guided";
	}

	@Override
	public void fire()
	{
		angle = shooter.getAngleFromFacing() + 180;
		this.x = shooter.getX();
		this.y = shooter.getY();
		PaddleGame.entities.add(this);
	}

}
