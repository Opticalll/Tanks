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
	
	public void negate()
	{
		if(type == TypeVelocity.NORMAL)
		{
			this.vx = -this.vx;
			this.vy = -this.vy;
		}
		if(type == TypeVelocity.SELECTED)
		{
			for(int i = 0; i < vxs.length; i++)
				vxs[i] = -vxs[i];
			for(int i = 0; i < vys.length; i++)
				vys[i] = -vys[i];
		}
		if(type == TypeVelocity.RANGED)
		{
			this.minVy = -this.minVy;
			this.maxVy = -this.maxVy;
			this.maxVx = -this.maxVx;
			this.minVx = -this.minVx;
		}
		if(type == TypeVelocity.SELECTED_AND_RANGED)
		{
			for(int i = 0; i < velocitiesList.size(); i++)
			{
				VVector temp = velocitiesList.get(i);
				temp.setMinVx(-temp.getMinVx());
				temp.setMinVy(-temp.getMinVy());
				temp.setMaxVx(-temp.getMaxVx());
				temp.setMaxVy(-temp.getMaxVy());
				velocitiesList.set(i, temp);
			}
		}
	}
	
	public void negateX()
	{
		if(type == TypeVelocity.NORMAL)
		{
			this.vx = -this.vx;
		}
		if(type == TypeVelocity.SELECTED)
		{
			for(int i = 0; i < vxs.length; i++)
				vxs[i] = -vxs[i];
		}
		if(type == TypeVelocity.RANGED)
		{
			this.maxVx = -this.maxVx;
			this.minVx = -this.minVx;
		}
		if(type == TypeVelocity.SELECTED_AND_RANGED)
		{
			for(int i = 0; i < velocitiesList.size(); i++)
			{
				VVector temp = velocitiesList.get(i);
				temp.setMinVx(-temp.getMinVx());
				temp.setMaxVx(-temp.getMaxVx());
				velocitiesList.set(i, temp);
			}
		}
	}
	
	public void negateY()
	{
		if(type == TypeVelocity.NORMAL)
		{
			this.vy = -this.vy;
		}
		if(type == TypeVelocity.SELECTED)
		{
			for(int i = 0; i < vys.length; i++)
				vys[i] = -vys[i];
		}
		if(type == TypeVelocity.RANGED)
		{
			this.minVy = -this.minVy;
			this.maxVy = -this.maxVy;
		}
		if(type == TypeVelocity.SELECTED_AND_RANGED)
		{
			for(int i = 0; i < velocitiesList.size(); i++)
			{
				VVector temp = velocitiesList.get(i);
				temp.setMinVx(-temp.getMinVx());
				temp.setMinVy(-temp.getMinVy());
				velocitiesList.set(i, temp);
			}
		}
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

	public float getMinVx() 
	{
		return minVx;
	}

	public void setMinVx(float minVx) 
	{
		this.minVx = minVx;
	}

	public float getMaxVx() 
	{
		return maxVx;
	}

	public void setMaxVx(float maxVx) 
	{
		this.maxVx = maxVx;
	}

	public float getMinVy() 
	{
		return minVy;
	}

	public void setMinVy(float minVy)
	{
		this.minVy = minVy;
	}

	public float getMaxVy()
	{
		return maxVy;
	}

	public void setMaxVy(float maxVy) 
	{
		this.maxVy = maxVy;
	}
	
}
