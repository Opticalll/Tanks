package cz.opt.pEngine;

import java.util.List;

public class VVector 
{
	private float vx;
	private float vy;
	private float minVx;
	private float maxVx;
	private float minVy;
	private float maxVy;
	private float vxs[]; 
	private float vys[]; 
	private List<VVector> velocitiesList;

	private TypeVelocity type;

	enum TypeVelocity
	{
		NORMAL,
		SELECTED,
		RANGED,
		SELECTED_AND_RANGED,
		ERROR
	}

	public VVector(float vx, float vy)
	{
		this.vx = vx;
		this.vy = vy;
		type = TypeVelocity.NORMAL;
	}

	public VVector(float vxs[], float vys[])
	{
		this.vxs = vxs;
		this.vys = vys;
		type = TypeVelocity.SELECTED;
	}

	public VVector(List<VVector> vList)
	{
		velocitiesList = vList;
		for(int i = 0; i < vList.size(); i++)
		{
			if(vList.get(i).getType() != TypeVelocity.RANGED)
			{
				type = TypeVelocity.ERROR;
				System.out.print("In vList is non Ranged Velocity.");
				return;
			}
		}
		type = TypeVelocity.SELECTED_AND_RANGED;
	}

	public VVector(float minVx, float maxVx, float minVy, float maxVy)
	{
		this.minVx = minVx;
		this.maxVx = maxVx;
		this.minVy = minVy;
		this.maxVy = maxVy;
		type = TypeVelocity.RANGED;
	}

	public float getRandomVx()
	{
		if(type == TypeVelocity.NORMAL)
		{
			return Pengine.getRandom(-vx, vx);
		}

		if(type == TypeVelocity.SELECTED)
		{
			int r = Pengine.getRandom(0, vxs.length);
			return vxs[r];
		}

		if(type == TypeVelocity.RANGED)
		{
			return Pengine.getRandom(minVx, maxVx);
		}

		if(type == TypeVelocity.SELECTED_AND_RANGED)
		{
			int r = Pengine.getRandom(0, velocitiesList.size());
			return velocitiesList.get(r).getRandomVx();
		}
		return 0;
	}

	public float getRandomVy()
	{
		if(type == TypeVelocity.NORMAL)
		{
			return Pengine.getRandom(-vy, vy);
		}

		if(type == TypeVelocity.SELECTED)
		{
			int r = Pengine.getRandom(0, vys.length);
			return vys[r];
		}

		if(type == TypeVelocity.RANGED)
		{
			return Pengine.getRandom(minVy, maxVy);
		}

		if(type == TypeVelocity.SELECTED_AND_RANGED)
		{
			int r = Pengine.getRandom(0, velocitiesList.size());
			return velocitiesList.get(r).getRandomVy();
		}
		return 0;
	}

	public TypeVelocity getType()
	{
		return type;
	}

	public void setType(TypeVelocity type)
	{
		this.type = type;
	}

	public float getVx()
	{
		return vx;
	}

	public void setVx(float vx)
	{
		this.vx = vx;
	}

	public float getVy()
	{
		return vy;
	}

	public void setVy(float vy)
	{
		this.vy = vy;
	}
}
