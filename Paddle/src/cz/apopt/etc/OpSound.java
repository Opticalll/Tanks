package cz.apopt.etc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

/**
 * Class for storing loaded sound effect.
 * Efficient loading and playing sounds.
 * 
 * @author Optical & Adam(some changes)
 *
 */
public class OpSound
{
	public static Map<String, OpSound> audioMap = new HashMap<String, OpSound>();
	private Audio audio;

	/**
	 * Private constructor.
	 * Use method addNewSound(arguments) to actually add the sound.
	 * 
	 * @param path Path to the sound.
	 * @param volume Initial volume
	 */
	private OpSound(String path, boolean isMusic)
	{
		try
		{
			audio = AudioLoader.getAudio("OGG", this.getClass().getResourceAsStream(path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Adds new sound effect to the static Map.
	 * 
	 * @param name Name of key, used to index sounds in the Map.
	 * @param path Path to the sound effect.
	 * @param volume Initial volume.
	 */
	public static void addNewAudio(String name, String path)
	{
		OpSound newSound = new OpSound(path, false);
		audioMap.put(name, newSound);
	}
	
	/**
	 * 
	 * @return Instance of Audio class - the actual sound.
	 */	
	public Audio getAudio()
	{
		return audio;
	}
}
