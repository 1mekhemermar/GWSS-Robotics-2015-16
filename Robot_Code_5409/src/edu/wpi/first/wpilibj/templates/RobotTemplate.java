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
        double seconds = 3.0;
        //moves it forwards
        double currentTime = System.currentTimeMillis() / 1000.0;
        double futureTime = (double)(currentTime + seconds);
       do{
          motors.tankDrive(-0.6,-0.64);
       }while (futureTime > currentTime);
       lift.set(1);
        do{
          motors.tankDrive(0.6,0.64);
       }while (futureTime > currentTime);
       lift.set(1);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        // Left-Right Input XBOX CONTROLLER
        float leftRight = 0;
        leftRight = (float) driver.getRawAxis(0);
        float rightTrigger = (float) driver.getRawAxis(3) * -1;
        float leftTrigger = (float) driver.getRawAxis(2);
        boolean increaseGear = driver.getRawButton(6);
        boolean lowerGear = driver.getRawButton(5);

        // LOGITECH CONTROLLER    	
        float direction = (float) driver2.getRawAxis(1);
        boolean closeClawButton = driver2.getRawButton(3);
        boolean openClawButton = driver2.getRawButton(4);

        currentLiftSpeed = 0;
        currentLevel = 0;
        final int LEVEL_ONE = 0, LEVEL_TWO = 1, LEVEL_THREE = 2, LEVEL_FOUR = 3;
        if (!(proxSensor.get())) {
            currentLevel = LEVEL_ONE;
        } else if (!(proxSensor1.get())) {
            currentLevel = LEVEL_TWO;
        } else if (!(proxSensor2.get())) {
            currentLevel = LEVEL_THREE;
        } else if (!(proxSensor3.get())) {
            currentLevel = LEVEL_FOUR;
        }

        if (!lowerGear) {
            allowGearDown = true;
        }
        if (increaseGear && currentGear <= 1 && allowGearUp) {
            currentGear++;
            allowGearUp = false;
            allowGearDown = false;
        } else if (lowerGear && currentGear > 0 && allowGearDown) {
            currentGear--;
            allowGearDown = false;
            allowGearUp = true;
        }

        // Trigger Input
        float currentSpeed = (rightTrigger + leftTrigger) * GEAR[currentGear];

        float finalRightSpeed = currentSpeed;
        float finalLeftSpeed = currentSpeed;

        // Final Speed Outputs
        if (leftRight < 0) { // Turning Right
            turn = -1;
        } else if (leftRight > 0) { // Turning Left
            turn = 1;
        }

        // Value to negate from final speed
        float turnSpeed = Math.abs(leftRight) * currentSpeed;

        // Negate speed
        switch (turn) {
            case -1:
                finalRightSpeed -= turnSpeed * GEAR[currentGear];
                break;
            case 1:
                finalLeftSpeed -= turnSpeed * GEAR[currentGear];
                break;
            case 0:
                break;
        }

        if (rightTrigger != 0) {
            finalLeftSpeed = finalLeftSpeed * ((float) 0.947);
        } else if (leftTrigger != 0) {
            finalRightSpeed = finalRightSpeed * ((float) 0.947);
        }

        if (currentSpeed != 0) {
            motors.tankDrive(finalLeftSpeed, finalRightSpeed);
        } else { // Stationary turn
            motors.tankDrive(leftRight * GEAR[currentGear], -1 * leftRight * GEAR[currentGear]);
        }

        // LOGITECH CONTROLLER
        if (closeClawButton && !isOpen) {
            mySolenoid.set(DoubleSolenoid.Value.kForward);
            isOpen = true;
        } else if (openClawButton && isOpen) {
            mySolenoid.set(DoubleSolenoid.Value.kReverse);
            isOpen = false;
        } else {
            mySolenoid.set(DoubleSolenoid.Value.kOff);
        }

//    	LOGITEH CONTROLLER
        if (direction > 0 && proxSensor3.get() && canLift) {
            currentLiftSpeed = direction;
            canLower = true;
            if (((proxSensor3.get()) && ((currentLevel % (int) currentLevel) == 0))) {
                currentLevel = currentLevel + 0.5;
            }
            if (!proxSensor3.get()) {
                lift.set(0);
                canLift = false;
            }
        }

        double seconds = 0.1;

        double currentTime = (double) System.currentTimeMillis() / 1000.0;
        double futureTime = (double) (currentTime + seconds);

        if (direction < 0 && proxSensor.get() && canLower) {
            canLift = true;
        }

        if (!proxSensor.get() && canLift) {
            currentTime = (double) System.currentTimeMillis() / 1000.0;
            futureTime = (double) (currentTime + seconds);
            do {
                currentTime = (double) System.currentTimeMillis() / 1000.0;
                lift.set(1);
            } while (currentTime < futureTime);
            canLower = false;
        }

        if (direction < 0 && proxSensor1.get() && canLower) {
            currentLiftSpeed = direction;
            canLift = true;
            if ((!proxSensor1.get()) && ((currentLevel % (int) currentLevel) == 0)) {
                currentLevel = currentLevel - 0.5;
            }
            if (!proxSensor1.get()) {
                lift.set((0.3) * -1);
            }
        }

        if ((!proxSensor.get() && currentLiftSpeed < 0) || (!proxSensor3.get() && currentLiftSpeed > 0)) {
            currentLiftSpeed = 0;
        }
        lift.set(currentLiftSpeed);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
