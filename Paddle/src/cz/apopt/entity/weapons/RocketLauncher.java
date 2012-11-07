package cz.apopt.entity.weapons;

import cz.apopt.entity.Tank;
import cz.apopt.entity.projectile.Projectile;
import cz.apopt.entity.projectile.RocketProjectile;
import cz.apopt.etc.OpSound;

public class RocketLauncher extends Weapon
{
	private static final String NAME = "Cannon";
	
	public RocketLauncher(Tank owner)
	{
		super(owner, NAME);
	}

	public void fire()
	{
		Projectile projectile = super.currentAmmo.getInstance();
		if(projectile instanceof RocketProjectile)
		{	
			projectile.fire();
			super.removeAmmo(projectile);
			
			OpSound.audioMap.get("SHOT").getAudio().playAsSoundEffect(1.0f, 1.0f, false);
		}
	}
}
