package cz.opt.lEngine;

public class EndingPoint 
{
	float x, y;
	float angle;
	Segment segment;
	boolean begin;
	boolean visualize;
	
	public EndingPoint(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setAnglebyOrigin(float oX, float oY)
	{
		float deltaX, deltaY;
		deltaX = x - oX;
		deltaY = y - oY;
		angle = (float) (java.lang.Math.atan2(deltaX, deltaY) * 180 / java.lang.Math.PI);
	}
}