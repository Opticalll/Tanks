package cz.apopt.entity.weapons;

import cz.apopt.entity.Tank;
import cz.apopt.entity.projectile.CannonProjectile;
import cz.apopt.entity.projectile.Projectile;
import cz.apopt.etc.OpSound;

public class Cannon extends Weapon
{	
	private static final String NAME = "Cannon";
	
	public Cannon(Tank owner)
	{
		super(owner, NAME);
	}

	public void fire()
	{
		Projectile projectile = super.currentAmmo;
		if(projectile instanceof CannonProjectile)
		{	
			projectile.fire();
			super.removeAmmo(projectile);
			
			OpSound.audioMap.get("SHOT").getAudio().playAsSoundEffect(1.0f, 1.0f, false);
		}
	}
}
