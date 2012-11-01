package cz.apo.entity.items;

public class ItemStack
{
	private int count;
	private Item item;
	
	public ItemStack(Item item)
	{
		this.item = item;
		this.count = 1;
	}
	
	public ItemStack(Item item, int count)
	{
		this.item = item;
		this.count = count;
	}
	
	public void addItem()
	{
		count++;
	}
	
	public void addItem(int count)
	{
		this.count += count;
	}
	
	public void removeItem()
	{
		count--;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
		
	public Class<? extends Item> getItemType()
	{
		return item.getClass();
	}
	
	public Item getItem()
	{
		return item;
	}
}
