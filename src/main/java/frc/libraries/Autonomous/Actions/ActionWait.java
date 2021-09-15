package frc.libraries.Autonomous.Actions;

import edu.wpi.first.wpilibj.Timer;
import frc.libraries.Chassis.*;

public class ActionWait implements ActionBase
{
    Timer timer;
    double time;
	public ActionWait(double time)
	{
        this.time = time;
	}

	public void Init()
	{
        timer.start();
	}
	
	public void Run()
	{ }
	
	public boolean FinishCondition()
	{
        return timer.get() >= time;
	}
	public void Finish()
	{
        timer.stop();
        timer.reset();
    }
    
    
}
