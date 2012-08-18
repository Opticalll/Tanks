package cz.apo.paddleGame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.opengl.Display;

import cz.apo.entity.Block;
import cz.apo.entity.Entity;
import cz.apo.entity.Wall;

public class Grid
{
	private float tileWidth, tileHeight;
	private int lines, columns;
	private int[][] layout;
	
	private List<Entity> blocks = new ArrayList<Entity>();
	
	public Grid()
	{
		blocks = new ArrayList<Entity>();
	}
	
	public void setGrid(int lvl)
	{
		layout = getMapLayout(new File("res/level_" + lvl + ".lvl"));
		
		for(int i = 0; i < lines; i++)
		{
			for(int o = 0; o < columns; o++)
			{
				if(layout[i][o] == 1)
					blocks.add(new Block(o * tileWidth + PaddleGame.WALL_WIDTH, i * tileHeight + PaddleGame.WALL_WIDTH, tileWidth, tileHeight));
				if(layout[i][o] == 2)
					blocks.add(new Wall(o * tileWidth + PaddleGame.WALL_WIDTH, i * tileHeight + PaddleGame.WALL_WIDTH, (int) tileWidth, (int) tileHeight));
			}
		}
	}
	
	private int[][] getMapLayout(File f)
	{
		Scanner s = null;
		int[][] layout = null;
		
		try
		{
			s = new Scanner(f);
				
			while(s.hasNextInt())
			{
				while(s.nextInt() != 99)
				{
					columns++;
				}
				break;
			}
			s.close();
			
			s = new Scanner(f);
			
			while(s.hasNextLine())
			{
				s.nextLine();
				lines++;
			}
			s.close();
			
			s = new Scanner(f);
			
			layout = new int[lines][columns];
			
			int line = 0;
			int col = 0;
			while(s.hasNextInt())
			{
				int num = s.nextInt();
				if(num == 99)
				{
					line++;
					col = 0;
					continue;
				}
				
				layout[line][col] = num;
				col++;
			}
			
			tileWidth = (Display.getWidth() - PaddleGame.WALL_WIDTH) / columns;
			tileHeight = (Display.getHeight() - PaddleGame.WALL_WIDTH) / lines;
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		s.close();
		return layout;
	}
	
	public List<Entity> getBlocksFromGrid()
	{	
		return blocks;
	}
}
