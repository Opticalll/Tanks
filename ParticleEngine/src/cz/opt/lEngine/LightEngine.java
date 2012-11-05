package cz.opt.lEngine;

import java.util.Arrays;
import java.util.List;

import cz.opt.test.Block;

public class LightEngine 
{
	List<EndingPoint> endPoints;
	List<Segment> segments;
	float x, y, range;
	
	public static boolean isInsideCircle(float pX, float pY, float cX, float cY, float cRange)
	{
		return (pX - cX)*(pX - cX) + (pY - cY)*(pY - cY) <= cRange*cRange;
	}
	
	public static boolean areLinesCollding(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		float s1, s2, c1, c2, dx, dy;
		dx = x2 - x1;
		dy = y2 - y1;
		
		s1 = dy / dx;
		c1 = y1 - s1 * x1;
		
		dx = x4 - x3;
		dy = y4 - y3;
		
		s2 = dy/dx;
		c2 = y4 - s2 * x4;
		
		return (c1 - c2) != 0;
	}
	
	public void listInit(List<Block> blockList)
	{
		for(Block e : blockList)
		{
			//init block segments
			float x = e.x;
			float y = e.y;
			float r = e.width;
			addSegment(x-r, y-r, x-r, y+r);
            addSegment(x-r, y+r, x+r, y+r);
            addSegment(x+r, y+r, x+r, y-r);
            addSegment(x+r, y-r, x-r, y-r);
		}
	}
	
	private void addSegment(float x, float y, float x1, float y1)
	{
		EndingPoint p1 = new EndingPoint(x, y);
		EndingPoint p2 = new EndingPoint(x1, y1);
		Segment seg = new Segment(p1, p2, 0);
		
		segments.add(seg);
		endPoints.add(p1);
		endPoints.add(p2);
	}
	
	public void setLightLocation(float x, float y)
	{
		
	}
	
	private boolean isInside(float x, float y)
	{
		return isInsideCircle(x, y, this.x, this.y, this.range);
	}
	
	public static void angleSort(float oX, float oY, EndingPoint[] arrayPoints)
	{
		for(EndingPoint e : arrayPoints)
			e.setAnglebyOrigin(oX, oY);
		sort(arrayPoints);
	}
	
	private static EndingPoint[] points;
	private static int number;
	
	private static void sort(EndingPoint[] values)
	{
		// Check for empty or null array
		if (values ==null || values.length==0)
			return;
		points = values;
		number = values.length;
		quicksort(0, number - 1);
	}

	private static void quicksort(int low, int high) 
	{
		int i = low, j = high;
		float pivot = points[low + (high-low)/2].angle;

		while (i <= j) {
			while (points[i].angle < pivot)
				i++;
			while (points[j].angle > pivot)
				j--;
			if (i <= j) 
			{
				exchange(i, j);
				i++;
				j--;
			}
		}
		if (low < j)
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}

	private static void exchange(int i, int j)
	{
		EndingPoint temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}

	private void sweep()
	{
		EndingPoint[] arr = (EndingPoint[]) endPoints.toArray();
		sort(arr);
		endPoints = Arrays.asList(arr);
		
		
	}
	
	public void render()
	{
		sweep();
	}
}
