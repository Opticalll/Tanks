package cz.apopt.etc;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;

public class OpSound
{
	static public Map<String, OpSound> soundMap = new HashMap<String, OpSound>();
	private Music sound;

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

	public static void addNewSound(String name, String path, int volume)
	{
		OpSound newSound = new OpSound(path, volume);
		soundMap.put(name, newSound);
	}
	
	public Music getSound()
	{
		return sound;
	}
	
	public void addMusicListener(MusicListener lis)
	{
		sound.addListener(lis);
	}

	public void removeMusicListener(MusicListener lis)
	{
		sound.removeListener(lis);
	}

	public void setVolume(int volume)
	{
		sound.setVolume((float) volume/100.0f);
	}

	public void loop()
	{
		sound.loop();
	}

	public void play()
	{
		sound.play();
	}

	public void pause()
	{
		sound.pause();
	}

	public void stop()
	{
		sound.stop();
	}

	public void resume()
	{
		sound.resume();
	}

	public boolean isPlaying()
	{
		return sound.playing();
	}

	public void fadeSound(float fadeDuration, int endVolume, boolean stopAfterFade)
	{
		sound.fade((int)fadeDuration*1000, endVolume/100, stopAfterFade);
	}
}
