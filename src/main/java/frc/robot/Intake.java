package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Intake
{
    private TalonSRX motor1;
    private TalonSRX actuationMotor1;
    private DigitalInput topLim;
    private DigitalInput botLim;

    public Intake(TalonSRX intakeMotor, TalonSRX actuationMotor1, DigitalInput topLim,
            DigitalInput botLim)
    {
        this.motor1 = intakeMotor;
        this.actuationMotor1 = actuationMotor1;
        this.topLim = topLim;
        this.botLim = botLim;
    }
    public Intake(TalonSRX intakeMotor, TalonSRX actuationMotor1)
    {
        this.motor1 = intakeMotor;
        this.actuationMotor1 = actuationMotor1;
    }

    /*Starts intake
    power = double -1.0 to 1.0 (motor speed)
    */
	public void startIntake(double power)
    {
        motor1.set(ControlMode.PercentOutput, power);
    }

    //Stops intake.
    public void stopIntake()
    {
        motor1.set(ControlMode.PercentOutput, 0);
    }

    //Actuates intake. (Moves intake into robot)
    public void intakeActuateUp()
    {
        if(topLim != null && botLim != null)
        {
            if(!topLim.get())
            {
                actuationMotor1.set(ControlMode.PercentOutput, 1);
            }
            else if(topLim.get())
            {
                actuationMotor1.set(ControlMode.PercentOutput, 0);
            }
        }
        else
        {
            actuationMotor1.set(ControlMode.PercentOutput, 1);
        }
    }

    //Actuates Intake (Moves intake out of robot)
    public void intakeActuateDown()
    {
        if(topLim != null && botLim != null)
        {
            if(botLim.get())
            {
                actuationMotor1.set(ControlMode.PercentOutput, -1);
            }
            else if(!botLim.get())
            {
                actuationMotor1.set(ControlMode.PercentOutput, 0);
            }
        }
        else
        {
            actuationMotor1.set(ControlMode.PercentOutput, -1);
        }
    }
    
    //Stops the actuation of the robot.
    public void stopActuation()
    {
        actuationMotor1.set(ControlMode.PercentOutput, 0);
    }
}
