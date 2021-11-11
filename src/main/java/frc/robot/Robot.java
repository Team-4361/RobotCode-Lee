package frc.robot;

/*----------------------------------------------------------------------------*/

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.libraries.Autonomous.AutonomousMethods;
import frc.libraries.Chassis.TankDrive;
import frc.libraries.Controllers.DriveSpark;
import frc.libraries.Util.Counter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
  boolean ctrlmode, arcadeMode;
  double speedDivider;

  CANSparkMax DriveSpark1;
  CANSparkMax DriveSpark2;
  CANSparkMax DriveSpark3;
  CANSparkMax DriveSpark4;
  CANSparkMax ClimberSpark1;
  CANSparkMax ClimberSpark2;
  CANSparkMax shooterSpark1;
  CANSparkMax shooterSpark2;
  CANSparkMax controlPanelSpark;
  
  CANEncoder DriveSparkEnc1;
  CANEncoder DriveSparkEnc2;
  CANEncoder DriveSparkEnc3;
  CANEncoder DriveSparkEnc4;
  CANEncoder shooterSparkEnc1;
  CANEncoder shooterSparkEnc2;
  
  DriveSpark driveTrainL;
  DriveSpark driveTrainR;
  TankDrive theTank;

  double rampRate;
  double deadzone;
  boolean leftOut, rightOut;

  Joystick lStick, rStick;
  
  XboxController cont1;
  

  AutonomousMethods autoMethods;
  Autonomous auto;

  Counter RunNum;

  Shuffleboard board;

  boolean conveyerState;
  boolean intakeState;
  boolean climberState;
  
  TalonSRX intakeTalon1;
  TalonSRX intakeTalon2;
  TalonSRX intakeTalon3;
  TalonSRX conveyerTalon1;
  TalonSRX gripperTalon;
  
  Shooter theShooter;
  Intake theIntake;
  ControlPanel theControlPanel;
  Climber theClimber;
  Gripper theGripper;
  
  ColorSensorV3 colorSens;
  I2C.Port i2cPort;
  
  Timer AutoTimer;
  Conveyer theConveyer;

  ShuffleboardLayout autoChooser;
  SendableChooser <String> autoSendable;
  
  DigitalInput intakeLim1, intakeLim2, climberBotLim, climberTopLim;

  boolean red, green, blue, yellow;

  DigitalInput conveyerBottom, conveyerStart, conveyerEnd;

  Timer intakeActuationTimer, shooterTimer;

  boolean wait;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit()
  {
    UsbCamera camera  = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(60, 40);

    //DRIVE TRAIN
    ctrlmode = false; //False = Stick Tank, True = Xbox Tank
    arcadeMode = false; //Changes Xbox to 2-Stick arcade when true
    speedDivider = 1.0;
    DriveSpark1 = new CANSparkMax(1, MotorType.kBrushless);
    DriveSpark2 = new CANSparkMax(2, MotorType.kBrushless);
    DriveSpark3 = new CANSparkMax(5, MotorType.kBrushless);
    DriveSpark4 = new CANSparkMax(6, MotorType.kBrushless);
    DriveSparkEnc1 = new CANEncoder(DriveSpark1);
    DriveSparkEnc2 = new CANEncoder(DriveSpark2);
    DriveSparkEnc3 = new CANEncoder(DriveSpark3);
    DriveSparkEnc4 = new CANEncoder(DriveSpark4);
    CANSparkMax[] lDriveMotors = {DriveSpark1,DriveSpark2};
    CANSparkMax[] rDriveMotors = {DriveSpark3,DriveSpark4};
    driveTrainL = new DriveSpark(lDriveMotors);
    driveTrainR = new DriveSpark(rDriveMotors);
    theTank = new TankDrive(driveTrainL, driveTrainR, DriveSparkEnc1, DriveSparkEnc2, 6);

    //Ramp Rates (Acceleration Curves)
    rampRate = 1.0; //This is the time, in seconds, that the Spark will take to go from 0 to full speed
    DriveSpark1.setOpenLoopRampRate(rampRate);
    DriveSpark2.setOpenLoopRampRate(rampRate);
    DriveSpark3.setOpenLoopRampRate(rampRate);
    DriveSpark4.setOpenLoopRampRate(rampRate);
    //Deadzone value
    deadzone = 0.08;    

    //CONTROL PANEL
    controlPanelSpark = new CANSparkMax(12, MotorType.kBrushless);
    controlPanelSpark.setIdleMode(IdleMode.kBrake);
    i2cPort = I2C.Port.kOnboard;
    colorSens = new ColorSensorV3(i2cPort);
    theControlPanel = new ControlPanel(controlPanelSpark, colorSens);

    //SHOOTER
    shooterSpark1 = new CANSparkMax(3, MotorType.kBrushless);
    shooterSpark2 = new CANSparkMax(7, MotorType.kBrushless);
    shooterSpark1.setIdleMode(IdleMode.kCoast);
    shooterSpark2.setIdleMode(IdleMode.kCoast);
    shooterSparkEnc1 = new CANEncoder(shooterSpark1);
    shooterSparkEnc2 = new CANEncoder(shooterSpark2);  
    theShooter = new Shooter(shooterSpark1, shooterSpark2);
    shooterTimer = new Timer();
    shooterTimer.reset();

    //CLIMBER
    ClimberSpark1 = new CANSparkMax(4, MotorType.kBrushless);
    ClimberSpark1.setIdleMode(IdleMode.kBrake);
    ClimberSpark2 = new CANSparkMax(11, MotorType.kBrushless);
    ClimberSpark2.setIdleMode(IdleMode.kBrake);
    gripperTalon = new TalonSRX(13);
    climberBotLim = new DigitalInput(5);
    climberTopLim = new DigitalInput(6);
    theClimber = new Climber(ClimberSpark1, ClimberSpark2, climberBotLim, climberTopLim);
    theGripper = new Gripper(gripperTalon);
    
    //INTAKE
    intakeTalon1 = new TalonSRX(14);
    intakeTalon2 = new TalonSRX(10);
    intakeLim1 = new DigitalInput(0);
    intakeLim2 = new DigitalInput(1);
    theIntake = new Intake(intakeTalon1, intakeTalon2, intakeLim1, intakeLim2);
    intakeActuationTimer = new Timer();
    intakeActuationTimer.reset();
    

    //CONVEYER
    conveyerTalon1 = new TalonSRX(9);
    conveyerBottom = new DigitalInput(2);
    conveyerStart = new DigitalInput(3);
    conveyerEnd = new DigitalInput(4);
    theConveyer = new Conveyer(conveyerTalon1, conveyerBottom, conveyerStart, conveyerEnd);

    //Sticks
    lStick = new Joystick(1);
    rStick = new Joystick(2);
    cont1 = new XboxController(0);

    RunNum = new Counter();
  
    auto = new Autonomous(theTank, theIntake, theShooter, theConveyer);

    //Auto options and SmartDashboard/Shuffleboard selector
    autoSendable = new SendableChooser<>();
    autoSendable.addOption("Don't Move", "Don't Move");
    autoSendable.addOption("Edge of Opposing Trench", "Start 1");
    autoSendable.addOption("Loading Zone", "Start 2");
    autoSendable.addOption("Middle of Field", "Start 3");
    autoSendable.addOption("Edge of Shield Gen", "Start 4");
    autoSendable.addOption("Middle of Power Port", "Start 5");
    autoSendable.addOption("Middle of Friendly Trench", "Start 6");
    SmartDashboard.putData("Autonomous Chooser", autoSendable);

    //Color Sensor Value Idicators
    SmartDashboard.putBoolean("Color Sensor Blue", blue);
    SmartDashboard.putBoolean("Color Sensor Red", red);
    SmartDashboard.putBoolean("Color Sensor Green", green);
    SmartDashboard.putBoolean("Color Sensor Yellow", yellow);

    //Drive control values
    SmartDashboard.putNumber("Drive Speed Modifier (default=1.0)", speedDivider);
    SmartDashboard.putBoolean("L Stick > deadzone", leftOut);
    SmartDashboard.putBoolean("R Stick > deadzone", rightOut);
    if(!ctrlmode) {
      SmartDashboard.putString("Drive Control Mode", "Stick Tank");
    }
    else {
      SmartDashboard.putString("Drive Control Mode", "Xbox Tank");
    }
    SmartDashboard.putBoolean("Intake Top Lim Green=up", intakeLim1.get());
    SmartDashboard.putBoolean("Intake Bot Lim Red=down", intakeLim2.get());

    conveyerState = false;
    intakeState = false;
    climberState = false;
    wait = false;

  }

  @Override
  public void robotPeriodic()
  {
    //Updating dynamic values to/from SmartDashboard/ShuffleBoard
    if (SmartDashboard.getNumber("Drive Speed Modifier (default=1.0)", speedDivider)>1.0) {
      speedDivider=1.0;
      SmartDashboard.putNumber("Drive Speed Modifier (default=1.0)", 1.0);
    }
    else {
      speedDivider=(double)SmartDashboard.getNumber("Drive Speed Modifier (default=1.0)", speedDivider);
      SmartDashboard.putNumber("Drive Speed Modifier (default=1.0)", speedDivider);
    }
    SmartDashboard.putBoolean("L Stick > deadzone", leftOut);
    SmartDashboard.putBoolean("R Stick > deadzone", rightOut);
    if(!ctrlmode) {
      SmartDashboard.putString("Drive Control Mode", "Stick Tank");
    }
    else {
      SmartDashboard.putString("Drive Control Mode", "Xbox Tank");
    }
    SmartDashboard.putBoolean("Intake Top Lim Green=up", intakeLim1.get());
    SmartDashboard.putBoolean("Intake Bot Lim Red=down", intakeLim2.get());
    SmartDashboard.putNumber("Left Shooter Spd.", shooterSparkEnc1.getVelocity());
    SmartDashboard.putNumber("Right Shooter Spd.", shooterSparkEnc2.getVelocity());
  }


  @Override
  public void autonomousInit()
  {
    
  }


  @Override
  public void autonomousPeriodic()
  {
    if(autoSendable.getSelected() == "Don't Move" || autoSendable.getSelected() ==  "Don't Move")
    {
      auto.runAuto("Don't Move");
    }
    else if(autoSendable.getSelected() == "Edge of Opposing Trench" || autoSendable.getSelected() ==  "Start 1")
    {
      auto.runAuto("Start 1");
    }
    else if(autoSendable.getSelected() == "Loading Zone" || autoSendable.getSelected() ==  "Start 2")
    {
      auto.runAuto("Start 2");
    }
    else if(autoSendable.getSelected() == "Middle of Field" || autoSendable.getSelected() ==  "Start 3")
    {
      auto.runAuto("Start 3");
    }
    else if(autoSendable.getSelected() == "Edge of Shield Gen" || autoSendable.getSelected() ==  "Start 4")
    {
      auto.runAuto("Start 4");
    }
    else if(autoSendable.getSelected() == "Middle of Power Port" || autoSendable.getSelected() ==  "Start 5")
    {
      auto.runAuto("Start 5");
    }
    else if(autoSendable.getSelected() == "Middle of Friendly Trench" || autoSendable.getSelected() ==  "Start 6")
    {
      auto.runAuto("Start 6");
    }
    else
    {
      auto.runAuto("");
    }
  }


  @Override
  public void teleopInit() {
  }


  @Override
  public void disabledPeriodic() {
  }


  @Override
  public void teleopPeriodic()
  {
    //Control Panel Code
    if(cont1.getAButtonPressed())
    {
      theControlPanel.SpinForRotations(1, true, 4);
    }
    if(cont1.getBButton())
    {
      theControlPanel.SpinForColor(1, true);
    }
    if(theControlPanel.matchColor() == "Blue")
    {
      red = false;
      blue = true;
      green = false;
      yellow = false;
    }
    if(theControlPanel.matchColor() == "Red")
    {
      red = true;
      blue = false;
      green = false;
      yellow = false;
    }
    if(theControlPanel.matchColor() == "Green")
    {
      red = false;
      blue = false;
      green = true;
      yellow = false;
    }
    if(theControlPanel.matchColor() == "Yellow")
    {
      red = false;
      blue = false;
      green = false;
      yellow = true;
    }

    SmartDashboard.putBoolean("Color Sensor Blue", blue);
    SmartDashboard.putBoolean("Color Sensor Red", red);
    SmartDashboard.putBoolean("Color Sensor Green", green);
    SmartDashboard.putBoolean("Color Sensor Yellow", yellow);
    
    //Shooter Code
    if(cont1.getBumper(Hand.kLeft))
    {
      if(!wait)
      {
        shooterTimer.start();
        wait = true;
      }
      
      theShooter.Shoot(1.0);
      // if(shooterSparkEnc1.getVelocity() > 1918 && shooterSparkEnc2.getVelocity() > 1918)
      // {
      //   theConveyer.runConveyer(1, false);
      // }
      // else if((shooterSparkEnc1.getVelocity() > 1726 || shooterSparkEnc2.getVelocity() > 1726) || (shooterSparkEnc1.getVelocity() < 1918 || shooterSparkEnc2.getVelocity() < 1918))
      // {
      //   theConveyer.runConveyer(.5, false);
      // }
    }
    else
    { 
      theShooter.StopShooting();
      //theConveyer.stopConveyer();
      wait = false;
    }

    //Conveyor Code
    if(cont1.getTriggerAxis(Hand.kRight)> 0.8) {
      theConveyer.runConveyer(1, false);
    }
    else{
      theConveyer.stopConveyer();
    }

    //Intake and Conveyor
    if(cont1.getTriggerAxis(Hand.kLeft)> 0.8){
      theIntake.startIntake(-0.3);
      theConveyer.runConveyer(-1, false);
    }
    else{
      theConveyer.stopConveyer();
      theIntake.stopIntake();
    }


    //Intake Code
    if(cont1.getBumper(Hand.kRight))
    {
      theIntake.startIntake(0.3);
      //theConveyer.runConveyer(1, true);
    }
    else
    {
      theIntake.stopIntake();
      // if(!cont1.getBumper(Hand.kLeft))
      // {
      //   theConveyer.stopConveyer();
      // }
    }
    //Actuator
    if(cont1.getXButtonPressed())
    {
      intakeState = !intakeState;
      intakeActuationTimer.start();
    }
    if(intakeState && intakeActuationTimer.get() < 1.2)
    {
      theIntake.intakeActuateUp();
    }
    else if(!intakeState && intakeActuationTimer.get() < 1.2)
    {
      theIntake.intakeActuateDown();
    }
    else if(intakeActuationTimer.get() > 1.2)
    {
      theIntake.stopActuation();
      intakeActuationTimer.stop();
      intakeActuationTimer.reset();
    }
    

    //Climber / Gripper Code
    /*if(cont1.getYButtonPressed())
    {
      climberState = !climberState;
    }
    if(climberState)
    {
      theClimber.climberUp(1, true);
    }
    if(!climberState)
    {
      theClimber.climberDown(1, true);
    }
    if(cont1.getPOV() == 0)
    {
      theGripper.MoveRight(1);
    }
    if(cont1.getPOV() == 180)
    {
      theGripper.MoveLeft(1);
    }*/
    if(cont1.getStickButton(Hand.kLeft))
    {
      theClimber.climberUp(1, false);
    }
    if(cont1.getStickButton(Hand.kRight))
    {
      theClimber.climberDown(1, false);
    }
    if(!cont1.getStickButton(Hand.kRight) && !cont1.getStickButton(Hand.kLeft))
    {
      theClimber.stopClimber();
    }

    //Drive Train Code
    if(cont1.getRawButtonPressed(7))
    {
      ctrlmode = !ctrlmode;
    }
    
    if(cont1.getRawButtonPressed(5) || lStick.getRawButton(4) || rStick.getRawButton(3))
    {
      if(speedDivider > .25)
      {
        speedDivider = speedDivider - .25;
      }
    }
    else if(cont1.getRawButtonPressed(6) || lStick.getRawButton(6) || rStick.getRawButton(5))
    {
      if(speedDivider < 1)
      {
        speedDivider = speedDivider + .25;
      }
    }

    double leftVal = 0;
    leftOut = false;
    double rightVal = 0;
    rightOut = false;

    if(ctrlmode)
    {
      //Deadzone if statement
      if(!arcadeMode) {
        leftVal = cont1.getY(Hand.kLeft);
        rightVal = cont1.getY(Hand.kRight);
      }
      else if(arcadeMode) {
        leftVal = cont1.getX(Hand.kRight) - cont1.getY(Hand.kLeft);
        rightVal = cont1.getX(Hand.kRight) + cont1.getY(Hand.kLeft);
      }
      deadzone=0.0;
      //System.out.println("ctrlMode Change Success to Sticks");
    }
    if(!ctrlmode)
    {
      leftVal = lStick.getY();
      rightVal = rStick.getY();
      deadzone=0.08;    
      //System.out.println("ctrlMode Change Success to Controller");
    }
    
    //Deadzones
    //leftOut/rightOut tell if the sticks are outside the deadzone - Shown on ShuffleBoard for testing and tuning purposes
    if(Math.abs(leftVal)<deadzone) {
      leftVal=0; leftOut=false;
    }
    else {
      leftOut=true;
    }
    if(Math.abs(rightVal)<deadzone) {
      rightVal=0; rightOut=false;
    }
    else {
      rightOut=true;
    }

    //Drive controls
    theTank.drive(-leftVal*speedDivider, rightVal*speedDivider);

  }
}
