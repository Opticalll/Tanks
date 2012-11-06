package cz.apopt.paddleGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import cz.apopt.entity.Block;
import cz.apopt.entity.Entity;

/**
 * Grid class for loading levels from files
 * 
 * @author Adam & Optical
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
		Block[][] layout = loadMap("/levels/level_" + lvl + ".lvl");
		for(int i = 0; i < lines; i++)
			for(int z = 0; z < columns; z++)
				if(layout[i][z] != null)
				{
					blocks.add(new Block(layout[i][z]));
					layout[i][z] = null;
				}
	}

	/**
	 * 
	 * @param input String input
	 * @return List of Maps
	 */
	private List<Map<String, String>> getMapFromString(String input)
	{
		String[] stringChunks = input.split("~");
		stringChunks[0] = stringChunks[0].substring(stringChunks[0].lastIndexOf(">") + 1);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for(int i = 0; i < stringChunks.length; i++)
		{
			stringChunks[i] = stringChunks[i].substring(1);
			String[] lines = stringChunks[i].split("\n");
			Map<String, String> variableMap = new HashMap<String, String>();
			for(int z = 0; z < lines.length; z++)
			{
				if(z == 0)
				{
					lines[z] = lines[z].substring(1);
					lines[z] = lines[z].substring(0, lines[z].lastIndexOf("]"));
					variableMap.put("name/id", lines[z]); 
				}
				else
				{
					// Warning no spaces between variable and value.
					String[] record = lines[z].split("=");
					variableMap.put(record[0], record[1]);
				}
			}
			mapList.add(variableMap);
		}
		return mapList;
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


		try
		{
			mfs = new Scanner(this.getClass().getResourceAsStream(mfp));
			if(mfs.hasNextLine())
				cfp = mfs.nextLine();
			
			PaddleGame.log("Loading map from -> " + mfp);
			
			cfs = new Scanner(this.getClass().getResourceAsStream(cfp));
			
			
			String blockString = "";
			while(cfs.hasNextLine())
			{
				String in = cfs.nextLine();
				in += "\n";
				if(in.equals("</Blocks>\n"))
				{
					List<Map<String, String>> mapList = getMapFromString(blockString);
					for(int i = 0; i < mapList.size(); i++)
						blockConfig.put(Integer.parseInt(mapList.get(i).get("name/id")), new Block(mapList.get(i)));
					blockString = "";
				}
				else if(in.equals("</Placeable>\n"))
				{
						blockString = "";
				}
				else if(in.equals("</Guns>\n"))
				{
						blockString = "";
				}
				else if(in.equals("</Tanks>\n"))
				{
						blockString = "";
				}
				else
					blockString += in;
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
			mfs = new Scanner(this.getClass().getResourceAsStream(mfp));

			while(mfs.hasNextLine())
			{
				mfs.nextLine();
				lines++;
			}

			mfs.close();
			mfs = new Scanner(this.getClass().getResourceAsStream(mfp));

			layout = new Block[lines][columns];

			tileWidth = 20.0f;
			tileHeight = 20.0f;
			
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
			System.err.println("FAIL " + e.getMessage());
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
