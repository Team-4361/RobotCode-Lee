package frc.libraries.Controllers;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

public class DriveSpark implements Drive
{
	CANSparkMax[] sparks;
	
	static CANSparkMax[] FULLsparks;

	public DriveSpark(CANSparkMax[] CAN)
	{
		this.sparks = CAN;
	}

	public DriveSpark(int[] nums)
	{
		sparks = new CANSparkMax[nums.length];
		for (int i = 0; i < nums.length; i++)
		{
			sparks[i] = FULLsparks[nums[i]];
		}
	}
	

	public void drive(double val)
	{
		for(CANSparkMax spark : sparks)
		{
			spark.set(val);
		}
	}

	public double GetSpeed()
	{
		if(sparks != null && sparks[0] != null)
			return sparks[0].get();
		return 0;
	}

	public static void SetFullSparks(CANSparkMax[] sparks)
	{
		FULLsparks = sparks;
	}
	public CANSparkMax[] GetSparks()
	{
		return sparks;
	}
}