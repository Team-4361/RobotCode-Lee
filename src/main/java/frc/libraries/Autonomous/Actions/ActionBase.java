package frc.libraries.Autonomous.Actions;

public interface ActionBase
{
    public void Init();

	public void Run();
	
	public boolean FinishCondition();

	public void Finish();
}
