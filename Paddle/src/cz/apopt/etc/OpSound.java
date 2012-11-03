package cz.apopt.etc;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;

/**
 * Class for storing loaded sound effect.
 * Efficient loading and playing sounds.
 * 
 * @author Optical & Adam(some changes)
 *
 */
public class OpSound
{
	static public Map<String, OpSound> soundMap = new HashMap<String, OpSound>();
	private Music sound;

	/**
	 * Private constructor.
	 * Use method addNewSound(arguments) to actually add the sound.
	 * 
	 * @param path Path to the sound.
	 * @param volume Initial volume
	 */
	private OpSound(String path, int volume)
	{
		try
		{
			sound = new Music(this.getClass().getResource(path));
			sound.setVolume((float) volume/100.0f);
		} catch (SlickException e)
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
	public static void addNewSound(String name, String path, int volume)
	{
		OpSound newSound = new OpSound(path, volume);
		soundMap.put(name, newSound);
	}
	
	/**
	 * 
	 * @return Instance of Music class - the actual sound.
	 */
	public Music getSound()
	{
		return sound;
	}
	
	/**
	 * Adds music listener to particular sound.
	 * 
	 * @param lis The music listener.
	 */
	public void addMusicListener(MusicListener lis)
	{
		sound.addListener(lis);
	}

	/**
	 * Removes music listener from particular sound.
	 * 
	 * @param lis The music listener to remove.
	 */
	public void removeMusicListener(MusicListener lis)
	{
		sound.removeListener(lis);
	}

	/**
	 * Sets volume.
	 * 
	 * @param volume New volume.
	 */
	public void setVolume(int volume)
	{
		sound.setVolume((float) volume/100.0f);
	}

	/**
	 * Call this method, if the sound should loop.
	 */
	public void loop()
	{
		sound.loop();
	}

	/**
	 * Plays the sound.
	 */
	public void play()
	{
		sound.play();
	}

	/**
	 * Pauses the sound.
	 */
	public void pause()
	{
		sound.pause();
	}

	/**
	 * Stops the sound.
	 */
	public void stop()
	{
		sound.stop();
	}

	/**
	 * Resumes the sound.
	 */
	public void resume()
	{
		sound.resume();
	}

	/**
	 * 
	 * @return True if sound is playing, false otherwise.
	 */
	public boolean isPlaying()
	{
		return sound.playing();
	}

	/**
	 * Fades the sound.
	 * 
	 * @param fadeDuration Duration of fading.
	 * @param endVolume Ending volume.
	 * @param stopAfterFade Stop the sound after fading.
	 */
	public void fadeSound(float fadeDuration, int endVolume, boolean stopAfterFade)
	{
		sound.fade((int)fadeDuration*1000, endVolume/100, stopAfterFade);
	}
}
