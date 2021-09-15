package frc.libraries.Chassis;

public interface Chassis
{
	public void Forward(double value);
	public void Straight(double value);
	public void Turn(double value);
	public void drive(double lVal, double rVal);
	
	public double GetDistance();
	
	public void Stop();
	
	public boolean HasEncoder();
	public void ResetEncoders();
}
