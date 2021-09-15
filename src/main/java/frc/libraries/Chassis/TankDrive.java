package frc.libraries.Chassis;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.Encoder;
import frc.libraries.Controllers.*;
import frc.libraries.Util.Constant;

public class TankDrive implements Chassis
{
	Drive Left, Right;
	Encoder lEnc, rEnc;
	CANEncoder lFrontEnc, rFrontEnc;
	int WheelDiameter;

	public TankDrive(Drive Left, Drive Right) {
		this.Left = Left;
		this.Right = Right;
	}

	public TankDrive(Drive Left, Drive Right, Encoder lEnc, Encoder rEnc, int WheelDiameter) {
		this(Left, Right);

		this.lEnc = lEnc;
		this.rEnc = rEnc;
		this.WheelDiameter = WheelDiameter;
	}

	public TankDrive(Drive Left, Drive Right, CANEncoder lFrontEnc, CANEncoder rFrontEnc, int WheelDiameter)
	{
		this(Left, Right);
		this.lFrontEnc = lFrontEnc;
		this.rFrontEnc = rFrontEnc;
		this.WheelDiameter = WheelDiameter;
		
	}
	
	public void Forward(double value)
	{
		drive(value, -value);
	}
	
	public void Straight(double value)
	{
		if(lEnc != null && rEnc != null)
		{
			double SpeedChange = .1 * value;
			
			if(Math.abs(lEnc.getDistance())> Math.abs(rEnc.getDistance()))
				drive(value - SpeedChange, -value);
			else if(Math.abs(lEnc.getDistance()) < Math.abs(rEnc.getDistance()))
				drive(value, -(value - SpeedChange));
			else
				Forward(value);
		}
		else if(lFrontEnc != null && rFrontEnc != null)
		{
			double SpeedChange = .1 * value;
			
			if(Math.abs(lFrontEnc.getPosition()) > Math.abs(rFrontEnc.getPosition()))
				drive(value - SpeedChange, -value);
			else if(Math.abs(lFrontEnc.getPosition()) < Math.abs(rFrontEnc.getPosition()))
				drive(value, -(value - SpeedChange));
			else
				Forward(value);
		}
		else
		{
			Forward(value);
		}
	}
	
	public void Turn(double value)
	{
		Left.drive(value);
		Right.drive(-value);
	}
	
	public double GetDistance()
	{
		if(lEnc != null || rEnc != null)
		{
			return (Math.max(Math.abs(lEnc.getDistance()), Math.abs(rEnc.getDistance())) / 256) * WheelDiameter*Math.PI;
		}
		else if(lFrontEnc != null || rFrontEnc != null)
		{
			return (Math.max(Math.abs(lFrontEnc.getPosition()), Math.abs(rFrontEnc.getPosition())) / 256) * WheelDiameter*Math.PI;
		}
		return 0.0;
		
	}
	
	public void Stop()
	{
		Left.drive(0);
		Right.drive(0);
	}
	
	public void drive(double lVal, double rVal)
	{
		Left.drive(lVal);
		Right.drive(rVal);
	}
	
	public boolean HasEncoder()
	{
		return (lEnc != null && rEnc != null) || (lFrontEnc != null && rFrontEnc != null);
	}
	
	public void ResetEncoders()
	{
		if(lEnc != null || rEnc != null)
		{
			lEnc.reset();
			rEnc.reset();
		}
		else if(lFrontEnc != null || rFrontEnc != null)
		{
			lFrontEnc.setPosition(0.0);
			rFrontEnc.setPosition(0.0);
		}
	}
}
