package cz.apo.paddleGame;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.opengl.Display;

import cz.apo.entity.Block;
import cz.apo.entity.Entity;
import cz.apo.etc.Color;

/**
 * Grid class for loading levels from files
 * 
 * @author adam
 */
public class Grid
{
	private float tileWidth, tileHeight;
	private int lines, columns;
	private List<Entity> blocks = new ArrayList<Entity>();

	public Grid()
	{
		blocks = new ArrayList<Entity>();
	}

	/**
	 * 
	 * @param lvl Level number
	 */
	public void setGrid(int lvl)
	{
		Block[][] layout = loadMap("res/level_" + lvl + ".lvl");
		for(int i = 0; i < lines; i++)
			for(int z = 0; z < columns; z++)
				if(layout[i][z] != null)
					blocks.add(layout[i][z]);
	}

	/**
	 * 
	 * @param f Path to config File
	 * @return Two dimensional array describing the layout
	 */
	private Block[][] loadMap(String mfp)
	{
		Scanner mfs = null;
		Scanner cfs = null;
		Hashtable<Integer, Block> blockConfig = new Hashtable<Integer, Block>(); 
		Block[][] layout = null;
		String cfp= "";		

		// Config File Format:  "0-BlockId~1-Texture (boolean)~2-Texture Path or Color (RRR|GGG|BBB)~3-Properties(First|Second|Third|...)\n"

		try
		{
			mfs = new Scanner(new File(mfp));
			if(mfs.hasNextLine())
				cfp = mfs.nextLine();
			
			PaddleGame.log("Loading map from -> " + mfp);
			
			cfs = new Scanner(new File(cfp));
			while(cfs.hasNextLine())
			{
				Block newBlock = null;
				String cline = cfs.nextLine();
				String[] clineparts = cline.split("~");
				String[] sproperties = clineparts[3].split("¨");
				boolean[] bproperties = new boolean[2];
				
				for(int i = 0; i < sproperties.length; i++)
					bproperties[i] = Boolean.parseBoolean(sproperties[i]);
				
				if(Boolean.parseBoolean(clineparts[1])) // if texture
					newBlock = new Block(0, 0, tileWidth, tileHeight, clineparts[2], bproperties);
				else
				{
					String[] rgb = clineparts[2].split("¨");
					Color col = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
					newBlock = new Block(0, 0, tileWidth, tileHeight, col, bproperties);
				}
				blockConfig.put(Integer.parseInt(clineparts[0]), newBlock);
			}


			while(mfs.hasNextInt())
			{
				while(mfs.nextInt() != -1)
				{
					columns++;
				}
				break;
			}
			
			mfs.close();
			mfs = new Scanner(new File(mfp));

			while(mfs.hasNextLine())
			{
				mfs.nextLine();
				lines++;
			}

			mfs.close();
			mfs = new Scanner(new File(mfp));

			layout = new Block[lines][columns];

			tileWidth = (Display.getDisplayMode().getWidth() - PaddleGame.WALL_WIDTH) / columns;
			tileHeight = (Display.getDisplayMode().getHeight() - PaddleGame.WALL_WIDTH) / lines;

			int line = 0;
			int col = 0;
			mfs.nextLine();
			while(mfs.hasNextInt())
			{
				int num = mfs.nextInt();
				if(num == -1)
				{
					line++;
					col = 0;
					continue;
				}
				
				Block tempBlock = null;
				if(num > 0)
				{
					tempBlock = new Block(blockConfig.get(num));
					tempBlock.setX(col * tileWidth);
					tempBlock.setY(line * tileHeight);

					tempBlock.setBlockHeight(tileHeight);
					tempBlock.setBlockWidth(tileWidth);
				}
				layout[line][col] = tempBlock;
				col++;
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		cfs.close();
		mfs.close();
		return layout;
	}

	/**
	 * 
	 * @return All blocks from grid
	 */
	public List<Entity> getBlocksFromGrid()
	{	
		return blocks;
	}
}
