package cz.apopt.entity.weapons;

import cz.apopt.entity.Tank;
import cz.apopt.entity.projectile.CannonProjectile;
import cz.apopt.entity.projectile.Projectile;
import cz.apopt.etc.OpSound;

public class Cannon extends Weapon
{	
	public Cannon(Tank owner)
	{
		super(owner);
	}

	public void fire(Projectile p)
	{
		if(p instanceof CannonProjectile)
		{
			((CannonProjectile) p).fire();
			OpSound.soundMap.get("SHOT").getSound().play(1.0f, 0.6f);
		}
	}
}
