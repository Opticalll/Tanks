package cz.opt.particleEngine;

import java.util.List;
import java.util.Random;

public class ParticleRGB
{
	public enum TypeColor
	{
		RGB,
		RGB_RANDOM,
		RGB_ARRAY,
		TEXTURE
	}
	private TypeColor ColorType;
	private float r;
	private float g;
	private float b;
	private float a;
	private List<ParticleRGB> colors;
	
	public ParticleRGB(float r, float g, float b, float a)
	{
		if(r > 1.0f || g > 1.0f || b > 1.0f || a > 1.0f)
		{
			this.r = r/255.0f;
			this.g = g/255.0f;
			this.b = b/255.0f;
			this.a = a/255.0f;
		}
		else
		{
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;
		}
		ColorType = TypeColor.RGB;
	}
	
	public ParticleRGB(float r, float g, float b)
	{
		if(r > 1.0f || g > 1.0f || b > 1.0f)
		{
			this.r = r/255.0f;
			this.g = g/255.0f;
			this.b = b/255.0f;
		}
		else
		{
			this.r = r;
			this.g = g;
			this.b = b;
		}
		this.a = 1.0f;
		ColorType = TypeColor.RGB;
	}
	
	public ParticleRGB()
	{
		randomize();
		ColorType = TypeColor.RGB_RANDOM;
	}
	
	public ParticleRGB(List<ParticleRGB> PArray)
	{
		colors = PArray;
		if(colors.size() > 0)
		{
			this.r = PArray.get(0).getR();
			this.g = PArray.get(0).getG();
			this.b = PArray.get(0).getB();
			this.a = PArray.get(0).getA();
		}
		ColorType = TypeColor.RGB_ARRAY;
	}
	
	public void randomize()
	{
		Random ra = new Random();
		r = ra.nextFloat();
		g = ra.nextFloat();
		b = ra.nextFloat();
		a = ra.nextFloat();
	}
	
	public TypeColor getColorType()
	{
		return ColorType;
	}

	public void changeToProgres(float progres)
	{
		if(progres > 100.0f)
			progres = 100.0f;
		float parts = (100.0f / (float) colors.size() - 1)/100.0f;
		
		int intC1 = 0;
		int intC2 = 1;
		
		for(int i = 1; i < colors.size(); i++)
		{
			if((parts*i) >= progres)
			{
				break;
			}
			intC1++;
			intC2++;
		}
		
		if(intC2 >= colors.size())
			intC2 = intC1;
		
		ParticleRGB C1 = colors.get(intC1);
		ParticleRGB C2 = colors.get(intC2);
		
		float delR = C2.getR() -  C1.getR();
		float delG = C2.getG() -  C1.getG();
		float delB = C2.getB() -  C1.getB();
		float delA = C2.getA() - C1.getA();
		
		float proR = delR/(parts*100);
		float proG = delG/(parts*100);
		float proB = delB/(parts*100);
		float proA = delA/(parts*100);
		
		this.r = C1.getR() + (proR*(progres - parts*intC1)*100);
		this.g = C1.getG() + (proG*(progres - parts*intC1)*100);
		this.b = C1.getB() + (proB*(progres - parts*intC1)*100);
		this.a = C1.getA() + (proA*(progres - parts*intC1)*100);
		
//		System.out.print("Color Progres: " + progres + " r: " + r + " g: " + g + " b: " + b + " ProR: "+ proR + " delR: " + delR + " ProG: "+ proG + " delG: " + delG + " ProB: "+ proB + " delB: " + delB + " Parts: " + parts +"\n");
	}

	public float getA()
	{
		return a;
	}

	public void setA(float a)
	{
		this.a = a;
	}

	public List<ParticleRGB> getColors()
	{
		return colors;
	}

	public void setColors(List<ParticleRGB> colors)
	{
		this.colors = colors;
	}

	public float getR()
	{
		return r;
	}

	public void setR( float r )
	{
		this.r = r;
	}

	public float getG()
	{
		return g;
	}

	public void setG( float g )
	{
		this.g = g;
	}

	public float getB()
	{
		return b;
	}

	public void setB( float b )
	{
		this.b = b;
	}

}
