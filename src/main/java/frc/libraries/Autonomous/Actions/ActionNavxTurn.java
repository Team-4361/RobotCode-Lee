package frc.libraries.Autonomous.Actions;

import frc.libraries.Util.*;
import frc.libraries.Chassis.*;
import frc.libraries.Controllers.TurnControl;

import edu.wpi.first.wpilibj.Timer;

public class ActionNavxTurn implements ActionBase
{
	double angle, speed;
    Chassis chassis = ActionObjects.chassis;
    Timer timer;
    TurnControl turnControl;

	public ActionNavxTurn(double angle, double speed, TurnControl turnControl)
	{
		this.angle = angle;
        this.speed = speed;

        this.timer = new Timer();

        this.turnControl = turnControl;

	}
    
    public void Init()
    {
        this.turnControl.SetSpeed(speed);
        this. turnControl.SetFromPosition(angle);
        

        timer.reset();
        timer.start();
    }

	public void Run()
	{
		double RotateRate = turnControl.GetRotateRate();
		chassis.Turn(RotateRate);
	}
	
	public boolean FinishCondition()
	{
        //return turnControl.onTarget() || timer.get() > 200 ;
        return false;
    }
    
    public void Finish()
    {
        chassis.Stop();
        
        timer.stop();
        timer.reset();
    }
}
