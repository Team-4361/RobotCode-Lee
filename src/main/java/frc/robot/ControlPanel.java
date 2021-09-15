package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatchResult;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;

public class ControlPanel
{
    private CANSparkMax spinner;
    private ColorSensorV3 colorSensor;
    private final ColorMatch colorMatcher;
    private final Color kBlueTarget;
    private final Color kGreenTarget;
    private final Color kRedTarget;
    private final Color kYellowTarget;
    private String gameData;
    int timesRun;
    CANEncoder theEnc;


    public ControlPanel(CANSparkMax controlPanelSpark, ColorSensorV3 colorSens)
    {
        this.spinner = controlPanelSpark;
        this.colorSensor = colorSens;
        colorMatcher = new ColorMatch();
        kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
        kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
        kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
        kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        theEnc = new CANEncoder(spinner);
        theEnc.setPosition(0);
    }

    public ControlPanel(CANSparkMax controlPanelSpark)
    {
        this.spinner = controlPanelSpark;
        colorMatcher = new ColorMatch();
        kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
        kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
        kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
        kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
        gameData = DriverStation.getInstance().getGameSpecificMessage();
    }
    
    //Collects the color that the Color Sensor sees. Returns value as a single character string.
	public String matchColor()
    {
        ColorMatchResult match = colorMatcher.matchClosestColor(colorSensor.getColor());
        if (match.color == kBlueTarget)
        {
            return "B";
        }
        else if (match.color == kRedTarget)
        {
            return "R";
        }
        else if (match.color == kGreenTarget)
        {
            return "G";
        }
        else if (match.color == kYellowTarget)
        {
            return "Y";
        }
        else
        {
            return "";
        }
    }

    /*Spins the control panel manipulator to land on a specific color.
    spinSpeed = double between -1.0 and 1.0 (motor speed)
    automatic = boolean, true = with sensors, false = direct control.
    */
    public void SpinForColor(double spinSpeed, boolean automatic)
    {
        if(automatic)
        {
            if(gameData.length() > 0)
            {
                if(Character.toString(gameData.charAt(0)) == "R" && matchColor() == "B")
                {
                    //GAME NEEDS RED
                    StopSpin();
                }
                if(Character.toString(gameData.charAt(0)) == "Y" && matchColor() == "G")
                {
                    //GAME NEEDS YELLOW
                    StopSpin();
                }
                if(Character.toString(gameData.charAt(0)) == "B" && matchColor() == "R")
                {
                    //GAME NEEDS BLUE
                    StopSpin();
                }
                if(Character.toString(gameData.charAt(0)) == "G" && matchColor() == "Y")
                {
                    //GAME NEEDS GREEN
                    StopSpin();
                }
                else
                {
                    spinner.set(spinSpeed);
                }
            }
        }
        if(!automatic)
        {
            spinner.set(spinSpeed);
        }
    }

    /*Spins the control panel manipulator for number of rotations.
    spinSpeed = double between -1.0 and 1.0 (motor speed)
    automatic = boolean, true = with sensors, false = direct control.
    rotations = number between 3-5 for the number of required rotations.
    */
    public void SpinForRotations(double spinSpeed, boolean automatic, int rotations)
    {
        if(automatic)
        {
            if((32*Math.PI)/((4*Math.PI)*rotations) > theEnc.getPosition())
            {
                spinner.set(spinSpeed);
            }
            else if((32*Math.PI)/((4*Math.PI)*rotations) > theEnc.getPosition())
            {
                StopSpin();
            }
        }
        if(!automatic)
        {
            spinner.set(spinSpeed);
        }
    }
    
    //Stops the spinner.
    public void StopSpin()
    {
        spinner.set(0);
    }
}