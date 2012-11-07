package cz.apopt.etc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.apopt.entity.Block;


public class BlockList
{
	boolean changed = false;
	public List<Block> blockList = new ArrayList<Block>();
	
	public void update()
	{
		if(changed)
		{
			Block[] blocks = (Block[])blockList.toArray();
			sort(blocks);
			blockList = Arrays.asList(blocks);
		}
	}
	
	public void addBlock(Block block)
	{
		blockList.add(block);
		changed = true;
	}
	
	public void removeBlock(Block block)
	{
		blockList.remove(block);
		changed = false;
	}
	
	public Block getBlockOn(float x, float y)
	{
		if(x <= blockList.get(blockList.size()/2).getX())
		{
			for(Block b : blockList)
				if((x <= b.getX() + b.getBlockWidth() && x >= b.getX()) && (y <= b.getY() + b.getBlockHeight() && y >= b.getY()))
					return b;
		}
		else
		{
			for(int i = blockList.size() - 1; i > -1; i--)
				if((x <= blockList.get(i).getX() + blockList.get(i).getBlockWidth() && x >= blockList.get(i).getX()) && (y <= blockList.get(i).getY() + blockList.get(i).getBlockHeight() && y >= blockList.get(i).getY()))
					return blockList.get(i);
		}
		return null;
	}

	private Block[] points;
	private int number;
	
	private void sort(Block[] values)
	{
		// Check for empty or null array
		if (values ==null || values.length==0)
			return;
		points = values;
		number = values.length;
		quicksort(0, number - 1);
	}

	private void quicksort(int low, int high) 
	{
		int i = low, j = high;
		float pivot = points[low + (high-low)/2].getX();

		while (i <= j) {
			while (points[i].getX() < pivot)
				i++;
			while (points[j].getX() > pivot)
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

	private void exchange(int i, int j)
	{
		Block temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}

}
