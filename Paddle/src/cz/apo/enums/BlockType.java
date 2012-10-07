package cz.apo.enums;

import cz.apo.etc.Color;
import cz.apo.utils.FileUtils;

/**
 * Block type enumeration
 * 
 * @author adam
 */
public enum BlockType
{
	COVER(1),
	WALL(2),
	BLOCK(3);
	
	private Color col;
	private int index;
	
	BlockType(int i)
	{
		col = FileUtils.readColor("config/blocks.conf", i);
		index = i;
	}
	
	public Color getColor()
	{
		return col;
	}
	
	public int getIndex()
	{
		return index;
	}
}
