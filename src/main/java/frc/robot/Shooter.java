package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

public class Shooter
{
    private CANSparkMax motor1;
    private CANSparkMax motor2;

    public Shooter(CANSparkMax forwardMotor, CANSparkMax backwardMotor)
    {
        this.motor1 = forwardMotor;
        this.motor2 = backwardMotor;
    }

    /*Runs the shooter.
    power = double between 0 and 1.0 (motor speed)
    */
	public void Shoot(double power)
    {
        motor1.set(power);
        motor2.set(-power);
    }
    public void StopShooting()
    {
        motor1.set(0);
        motor2.set(0);
    }

}
