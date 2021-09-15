package frc.robot;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.libraries.Autonomous.AutonomousMethods;
import frc.libraries.Controllers.Drive;
import frc.libraries.Controllers.TurnControl;
import frc.libraries.Chassis.TankDrive;
import frc.libraries.Util.*;

public class Autonomous
{
    Counter RunNum;

    TankDrive chassis;
    Intake intake;
    Shooter shooter;
    Conveyer conveyer;

    AutonomousMethods methods;
    Timer timer;

    double wheelCirc;
    TurnControl turnControl;

    //PART 1
    public static final int left2ToTrench = 0;
    public static final int left1ToTrench = 0;
    public static final int midToTrench = 0;
    public static final int right1ToTrench = 0;
    public static final int right2ToTrench = 0;
    public static final double toBackTrench = 170.6;
    public static final double fromPortToTrench = 66.9;
    public static final double start1 = 173.09;
    public static final double start2 = 127.9;
    public static final double start4 = 23.2;
    
    
    



    public Autonomous(TankDrive chassis, Intake intake, Shooter shooter, Conveyer conveyer)
    {
        this.chassis = chassis;
        this.intake = intake;
        this.shooter = shooter;
        wheelCirc = 6 * Math.PI;
        RunNum = new Counter();
        this.methods = new AutonomousMethods(RunNum, wheelCirc,  true, chassis);
        turnControl = new TurnControl();
    }

    /*
    Runs Autonomous
    fromPos = Position that the robot is starting from, found on Shuffleboard.
    */
    public void runAuto(String fromPos)
    {
        turnControl.ResetNavx();
        //Edge of Opposing Trench
        if (fromPos == "Start 1")
        {
            RunNum.Reset();
            if(RunNum.Get() == 0)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 1)
            {
                methods.goDistance(start1, 1);
            }
            else if(RunNum.Get() == 2)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 3)
            {
                shooter.Shoot(1);
                methods.wait(2.0);
            }
            else if(RunNum.Get() == 4)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 5)
            {
                methods.goDistance(fromPortToTrench, 1);;
            }
            else if(RunNum.Get() == 6)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 7)
            {
                intake.intakeActuateDown();
                intake.startIntake(1);
                conveyer.runConveyer(1, true);
                methods.goDistance(-toBackTrench, -1);
            }
            else if(RunNum.Get() == 8)
            {
                methods.turnNavx(180, .5);
            }
            else if(RunNum.Get() == 9)
            {
                intake.intakeActuateUp();
                intake.stopIntake();
                conveyer.stopConveyer();
                methods.goDistance(toBackTrench, 1);
            }
            else if(RunNum.Get() == 10)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 11)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 12)
            {
                methods.turnNavx(90, .5);
            }
        }

        //Loading Zone
        else if (fromPos == "Start 2")
        {
            RunNum.Reset();
            if(RunNum.Get() == 0)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 1)
            {
                methods.goDistance(start2, 1);
            }
            else if(RunNum.Get() == 2)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 3)
            {
                shooter.Shoot(1);
                methods.wait(2.0);
            }
            else if(RunNum.Get() == 4)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 5)
            {
                methods.goDistance(fromPortToTrench, 1);;
            }
            else if(RunNum.Get() == 6)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 7)
            {
                intake.intakeActuateDown();
                intake.startIntake(1);
                conveyer.runConveyer(1, true);
                methods.goDistance(-toBackTrench, -1);
            }
            else if(RunNum.Get() == 8)
            {
                methods.turnNavx(180, .5);
            }
            else if(RunNum.Get() == 9)
            {
                intake.intakeActuateUp();
                intake.stopIntake();
                conveyer.stopConveyer();
                methods.goDistance(toBackTrench, 1);
            }
            else if(RunNum.Get() == 10)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 11)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 12)
            {
                methods.turnNavx(90, .5);
            }
        }

        //Middle of Field
        else if (fromPos == "Start 3")
        {
            RunNum.Reset();
            if(RunNum.Get() == 0)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 1)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 2)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 3)
            {
                shooter.Shoot(1);
                methods.wait(2.0);
            }
            else if(RunNum.Get() == 4)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 5)
            {
                methods.goDistance(fromPortToTrench, 1);;
            }
            else if(RunNum.Get() == 6)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 7)
            {
                intake.intakeActuateDown();
                intake.startIntake(1);
                conveyer.runConveyer(1, true);
                methods.goDistance(-toBackTrench, -1);
            }
            else if(RunNum.Get() == 8)
            {
                methods.turnNavx(180, .5);
            }
            else if(RunNum.Get() == 9)
            {
                intake.intakeActuateUp();
                intake.stopIntake();
                conveyer.stopConveyer();
                methods.goDistance(toBackTrench, 1);
            }
            else if(RunNum.Get() == 10)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 11)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 12)
            {
                methods.turnNavx(90, .5);
            }
        }
        //Edge of Shield Gen
        else if (fromPos == "Start 4")
        {
            RunNum.Reset();
            if(RunNum.Get() == 0)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 1)
            {
                methods.goDistance(start4, 1);
            }
            else if(RunNum.Get() == 2)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 3)
            {
                shooter.Shoot(1);
                methods.wait(2.0);
            }
            else if(RunNum.Get() == 4)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 5)
            {
                methods.goDistance(fromPortToTrench, 1);;
            }
            else if(RunNum.Get() == 6)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 7)
            {
                intake.intakeActuateDown();
                intake.startIntake(1);
                conveyer.runConveyer(1, true);
                methods.goDistance(-toBackTrench, -1);
            }
            else if(RunNum.Get() == 8)
            {
                methods.turnNavx(180, .5);
            }
            else if(RunNum.Get() == 9)
            {
                intake.intakeActuateUp();
                intake.stopIntake();
                conveyer.stopConveyer();
                methods.goDistance(toBackTrench, 1);
            }
            else if(RunNum.Get() == 10)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 11)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 12)
            {
                methods.turnNavx(90, .5);
            }
        }

        //Middle of Power Port
        else if (fromPos == "Start 5")
        {
            RunNum.Reset();
            if(RunNum.Get() == 0)
            {
                shooter.Shoot(1);
                methods.wait(2.0);
            }
            else if(RunNum.Get() == 1)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 2)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 3)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 4)
            {
                intake.intakeActuateDown();
                intake.startIntake(1);
                conveyer.runConveyer(1, true);
                methods.goDistance(-toBackTrench, -1);
            }
            else if(RunNum.Get() == 5)
            {
                methods.turnNavx(180, .5);
            }
            else if(RunNum.Get() == 6)
            {
                intake.intakeActuateUp();
                intake.stopIntake();
                conveyer.stopConveyer();
                methods.goDistance(toBackTrench, 1);
            }
            else if(RunNum.Get() == 7)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 8)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 9)
            {
                methods.turnNavx(90, .5);
            }
        }

        //Middle of Friendly Trench
        else if (fromPos == "Start 6")
        {
            RunNum.Reset();
            if(RunNum.Get() == 0)
            {
                methods.turn(-90, .5);
            }
            else if(RunNum.Get() == 1)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 2)
            {
                methods.turn(90, .5);
            }
            else if(RunNum.Get() == 3)
            {
                shooter.Shoot(1);
                methods.wait(2.0);
            }
            else if(RunNum.Get() == 4)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 5)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 6)
            {
                methods.turnNavx(90, .5);
            }
            else if(RunNum.Get() == 7)
            {
                intake.intakeActuateDown();
                intake.startIntake(1);
                conveyer.runConveyer(1, true);
                methods.goDistance(-toBackTrench, -1);
            }
            else if(RunNum.Get() == 8)
            {
                methods.turnNavx(180, .5);
            }
            else if(RunNum.Get() == 9)
            {
                intake.intakeActuateUp();
                intake.stopIntake();
                conveyer.stopConveyer();
                methods.goDistance(toBackTrench, 1);
            }
            else if(RunNum.Get() == 10)
            {
                methods.turnNavx(-90, .5);
            }
            else if(RunNum.Get() == 11)
            {
                methods.goDistance(fromPortToTrench, 1);
            }
            else if(RunNum.Get() == 12)
            {
                methods.turnNavx(90, .5);
            }
        }
        //Don't Move. Don't do it.
        else if(fromPos == "Don't Move")
        {
            methods.goDistance(0, 1);
        }
        //Move
        else
        {
            methods.goDistance(100, 1);
        }
    }
    
}