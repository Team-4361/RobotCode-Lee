package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Gripper
{
    private TalonSRX motor1;

    public Gripper(TalonSRX talon1)
    {
        motor1 = talon1;
    }

    /*Gripper spins left.
    amount = value between 0 and 1.0 (motor speed)
    */
    public void MoveLeft(double amount)
    {
        motor1.set(ControlMode.PercentOutput, Math.abs(amount));
    }

    /*Gripper spins right.
    amount = value between 0 and 1.0 (motor speed)
    */
    public void MoveRight(double amount)
    {
        motor1.set(ControlMode.PercentOutput, -Math.abs(amount));
    }

    //Stops gripper.
    public void stopGripper()
    {
        motor1.set(ControlMode.PercentOutput, 0);
    }
}