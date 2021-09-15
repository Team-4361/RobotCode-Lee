package frc.libraries.Autonomous.Actions;

import java.util.*;

public class ActionList implements Iterable<ActionBase>
{
	private List<ActionBase> list;
	
	public ActionList()
	{
		list = new ArrayList<ActionBase>();
	}
	
	public void Add(ActionBase action)
	{
		list.add(action);
	}
	
	public void Add(ActionList list)
	{
		for(ActionBase action : list)
		{
			this.list.add(action);
		}
	}
	
	public ActionBase Get(int index)
	{
		return this.list.get(index);
	}
	
	public int Count()
	{
		return list.size();
	}
	
	public Iterator<ActionBase> iterator()
	{
		return null;
	}
	
	
}
