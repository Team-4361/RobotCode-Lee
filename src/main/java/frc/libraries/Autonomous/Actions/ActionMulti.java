package frc.libraries.Autonomous.Actions;

public class ActionMulti implements ActionBase
{
	private ActionList list;
	private int FinishConditionState = 0;
	//0 = JUST THE FIRST in the list is Finished
	//1 = Any are Finished
	//2 = All are Finished
	public ActionMulti(ActionList list)
	{
		this.list = list;
	}
	
	public void Init()
	{
		for(ActionBase action : list)
		{
			action.Init();
		}
	}
	
	public void Run()
	{
		for(ActionBase action : list)
		{
			action.Run();
		}
	}
	
	public boolean FinishCondition()
	{
		if(list.Count() == 0)
			return true;
		
		if(FinishConditionState == 1)
		{
			for(ActionBase action : list)
			{
				if(action.FinishCondition())
				{
					return true;
				}
			}
			return false;
		}
		else if(FinishConditionState == 2)
		{
			for(ActionBase action : list)
			{
				if(!action.FinishCondition())
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return this.FinishCondition();
		}
	}

	public void Finish()
	{
		for(ActionBase action : list)
		{
			action.Finish();
		}
	}
}
