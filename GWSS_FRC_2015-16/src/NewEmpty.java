/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    Joystick driver, driver2;
    RobotDrive motors;
    Jaguar lift;
    Encoder liftRotation;
    int turn = 0;
    DoubleSolenoid mySolenoid = new DoubleSolenoid(0, 1);
    int currentGear = 0;
    DigitalInput proxSensor, proxSensor1, proxSensor2, proxSensor3, limitSwitchTop, limitSwitchGround;
    boolean isOpen = false, allowGearUp = true, allowGearDown = true, canLift = true, canLower = true;
    double currentLevel, currentLiftSpeed = 0;
    final float[] GEAR = {0.5f, 0.8f, 1};
    double initialTime=0,finalTime=0;
    
    public void robotInit() {
        proxSensor = new DigitalInput(2);
        proxSensor1 = new DigitalInput(3);
        proxSensor2 = new DigitalInput(4);
        proxSensor3 = new DigitalInput(5);
        limitSwitchTop = new DigitalInput(8);
        limitSwitchGround = new DigitalInput(9);
        motors = new RobotDrive(0, 1, 2, 3);
        lift = new Jaguar(4);
        driver = new Joystick(0);
        driver2 = new Joystick(1);
    }
    public void firstLift(){
        
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        //could do without initial time
        //could include lift and drive in same loop so that it moves both at the same time
        //+3 and drive values are temporary and require testing
        //need to include emergency sswitch codes
    mySolenoid.set(DoubleSolenoid.Value.kReverse);
    if(/*proxsensor stuff to move it down to initial position*/){
        
    }
    initialTime = System.currentTimeMillis() / 1000.0;
    finalTime = initialTime+2;
    do{
        
    }while(initialTime<finalTime);
    mySolenoid.set(DoubleSolenoid.Value.kForward);
    initialTime = System.currentTimeMillis() / 1000.0;
    finalTime = initialTime+3;
    do{
        lift.set(0.9);
    }while(initialTime<finalTime);
    lift.set(0);
    initialTime = System.currentTimeMillis() / 1000.0;
    finalTime = initialTime+3;
    do{
        motors.tankDrive(0,0);
    }while(initialTime<finalTime);
    motors.tankDrive(0,0);
    mySolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {}
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}