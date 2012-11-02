package cz.apo.etc;

import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;

public class OpSound
{
	// ok its very easy if we are not playing sound in 3d space :D
	static public Map<String, OpSound> soundMap;
	private Music sound;
	
	public OpSound(String name, String path, int volume)
	{
		try
		{
			soundMap.put(name, this);
			sound = new Music(path);
			sound.setVolume(volume/100);
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		sound.setVolume(volume/100);
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
