package frc.robot;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;

public class Climber
{
    private CANSparkMax motor1;
    private CANSparkMax motor2;
    private DigitalInput climberBotLim;
    private DigitalInput climberTopLim;

    public Climber(CANSparkMax climberSpark1, CANSparkMax climberSpark2)
    {
        this.motor1 = climberSpark1;
        this.motor2 = climberSpark2;
    }
    
    public Climber(CANSparkMax climberSpark1, CANSparkMax climberSpark2, DigitalInput climberBotLim, DigitalInput climberTopLim)
    {
        this.motor1 = climberSpark1;
        this.motor2 = climberSpark2;
        this.climberBotLim = climberBotLim;
        this.climberTopLim = climberTopLim;
	}

    /*Moves the climber up.
    power = double value between -1.0 and 1.0 (motor speed)
    automatic = boolean, true = using sensors, false = direct control.
    */
	public void climberUp(double power, boolean automatic)
    {
        if(automatic)
        {
            if(climberTopLim.get())
            {
                stopClimber();
            }
            else
            {
                motor1.set(power);
                motor2.set(power);
            }
        }
        if(!automatic)
        {
            motor1.set(power);
            motor2.set(power);
        }
    }

    /*Moves the climber down.
    power = double value between -1.0 and 1.0 (motor speed)
    automatic = boolean, true = using sensors, false = direct control.
    */
    public void climberDown(double power, boolean automatic)
    {
        if(automatic)
        {
            if(climberBotLim.get())
            {
                stopClimber();
            }
            else
            {
                motor1.set(-power);
                motor2.set(-power);
            }
        }
        if(!automatic)
        {
            motor1.set(-power);
            motor2.set(-power);
        }
    }

    public void stopClimber()
    {
        motor1.set(0);
        motor2.set(0);
    }

}