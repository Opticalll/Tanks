package cz.apo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.apo.etc.Color;

public class FileUtils
{
	private static BufferedReader in;
	
	private static void openStreams(String path)
	{
		try
		{
			in = new BufferedReader(new FileReader(new File(path)));
		} catch(IOException e)
		{
			System.err.println("Cannot open the streams.");
			e.printStackTrace();
		}
	}
	
	public static Color readColor(String path, int i)
	{
		openStreams(path);
		List<String> file = new ArrayList<String>();
		
		try
		{
			String line = "";
			while((line = in.readLine()) != null)
				file.add(line);
		} catch(IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				in.close();
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		String color = "";
		for(String str : file)
		{
			if(str.startsWith(String.valueOf(i)))
			{
				String[] line = str.split(" ");
				color = line[1];
			}
		}
		
		if(color.equals(""))
			return null;
		
		String[] s_color = color.split("-");
		Color col = new Color(Integer.valueOf(s_color[0]), Integer.valueOf(s_color[1]), Integer.valueOf(s_color[2]));
		return col;
	}
}
