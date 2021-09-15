package frc.libraries.Autonomous.Actions;
import frc.libraries.Chassis.*;

public class ActionObjects
{
	public static Chassis chassis;
	
	public static boolean HasEncoders()
	{
		return chassis != null && chassis.HasEncoder();
	}
}
