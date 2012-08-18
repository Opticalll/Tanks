package cz.opt.pEngine.enums;

public class EnumUtils 
{
	public static int getInt(ParType p)
	{
		if(p == ParType.QUAD)
			return 4;
		else if(p == ParType.TRIANGLE)
			return 3;
		else 
			return 0;
	}
}
