package frc.libraries.Autonomous.Actions;

import frc.libraries.Chassis.*;

public class ActionEncoderDistance implements ActionBase
{
	double distance, speed;
	Chassis chassis = ActionObjects.chassis;
	public ActionEncoderDistance(double distance, double speed)
	{
		this.distance = distance;
		this.speed = speed;
	}

	public void Init()
	{
		chassis.ResetEncoders();
	}
	
	public void Run()
	{
		chassis.Straight(speed);
	}
	
	public boolean FinishCondition()
	{
		return chassis.GetDistance() > distance;
	}
	public void Finish()
	{
		chassis.Stop();
	}
}