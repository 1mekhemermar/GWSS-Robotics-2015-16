package org.usfirst.frc.team5409.robot;

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
public class MainDriveCode201415 extends IterativeRobot {

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    Joystick driver;
    Joystick driver2;
    RobotDrive motors;
    Jaguar lift;
    Encoder liftRotation;
    String turn = "None";
    DoubleSolenoid mySolenoid = new DoubleSolenoid(0, 1);
    int currentGear = 0;
    DigitalInput proxSensor;
    DigitalInput proxSensor1;
    DigitalInput proxSensor2;
    DigitalInput proxSensor3;
    DigitalInput limitSwitchTop;
    DigitalInput limitSwitchGround;
    boolean isOpen = false;
    boolean allowGearUp = true, allowGearDown = true;
    double currentLevel;
    final float[] GEAR = {0.5f, 0.8f, 1};
    double currentLiftSpeed = 0;
    boolean canLift = true;
    boolean canLower = true;
    int actionsExecuted = 0;

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

    public void autonomousInit() {
    	//config.Transition = 1;
        //autonomousPeriodic();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        //if(config.Transition == 1){
        liftBox();

        // Back up
        double seconds = 3.0;

        double currentTime = System.currentTimeMillis() / 1000.0;
        double futureTime = (double) (currentTime + seconds);
        do {
            currentTime = System.currentTimeMillis() / 1000.0;
            motors.tankDrive(0.6, 0.64);
        } while (currentTime < futureTime);

        motors.tankDrive(0, 0);

        currentTime = System.currentTimeMillis() / 1000.0;
        futureTime = (double) (currentTime + (15 - seconds - 5));
        do {
            currentTime = System.currentTimeMillis() / 1000.0;
            motors.tankDrive(0, 0);
            //Timer.delay(6.5);
        } while (currentTime < futureTime);
    } /*else {
     motors.tankDrive(0.3, 0.3);
     }*/
    	//}
    // Start with arms on tote
    // Lift tote
//    	    liftBox();
//    		
//    		// Back up
//    		double seconds = 3.0;
//    		
//    		double currentTime = System.currentTimeMillis() / 1000.0;
//    		double futureTime = (double)(currentTime + seconds);
//    		do {
//    		currentTime = System.currentTimeMillis() / 1000.0;
//    		motors.tankDrive(0.6,0.64);
//    		} while (currentTime < futureTime);
//    		
//    		motors.tankDrive(0,0);
//    		
//    		 currentTime = System.currentTimeMillis() / 1000.0;
//    		 futureTime = (double)(currentTime + (15 - seconds - 5));
//    		do {
//    			currentTime = System.currentTimeMillis() / 1000.0;
//    			motors.tankDrive(0, 0);
//    		} while (currentTime < futureTime);
//    	}
//    		public void driveRobot(double leftSpeed, double rightSpeed, double seconds, boolean running) {
//    		double currentTime = System.currentTimeMillis() / 1000.0;
//    		double futureTime = (double)(currentTime + seconds);
//    		if (!running) {
//    		do {
//    		currentTime = System.currentTimeMillis() / 1000.0;
//    		motors.tankDrive(leftSpeed,rightSpeed);
//    		} while (currentTime < futureTime);
//    		} else {
//    		motors.tankDrive(0, 0);
//    		}
//    		System.out.println(currentTime);
//    		System.out.println(futureTime);
//    		actionsExecuted++;
//    		executeNextCommand();
//    		}
//    		
//    		public void executeNextCommand() {
//    		switch (actionsExecuted) {
//    		case 0:
//    		//70 degree turn into box
//    		driveRobot(0.7,0.5, 2, false);
//    		break;
//    		case 1:
//    		//go forward into box
//    		driveRobot(0.33,0.3,0.7, false);
//    		break;
//    		case 2:
//    		//backwards
//    		driveRobot(0.8, 0.65, 2, false);
//    		break;
//    		case 3:
//    		//Into box
//    		driveRobot(0.6,0.7,1.1, false);
//    		break;
//    		 /* case 2:
//    		 driveRobot(0.3, 0.6, 1.1);
//    		 break;
//    		 case 3:
//    		 driveRobot(0.33,0.3,1.3);
//    		 break;
//    		 case 4:
//    		 driveRobot(0.2,0.2,1);
//    		 break;
//    		 */
//    		case 4:
//    		//lift box
//    		liftBox();
//    		break;
//    		case 5:
//    		//move back and turn up field
//    		driveRobot(0.4, 0.3, 0.32, false);
//    		break;
//    		case 6:
//    		//move forward
//    		driveRobot(0.6, 0.3, 4, false);
//    		break;
//    		case 7:
//    		//drop box
//    		dropBox();
//    		break;
//    		case 8:
//    		//move back
//    		driveRobot(0.6, 0.64, 2, false);
//    		break;
//    		/*In case we want to go for second */
//    		case 9:
//    		//move to side to get can
//    		driveRobot(0.5, 0.3, 1.2, false);
//    		break;
//    		case 10:
//    		liftBox();
//    		break;
//    		case 11:
//    		//turn to left to pick up robot
//    		driveRobot(0.5, 0.3, 1.2, false);
//    		break;
//    		case 12:
//    		//drive toward auto zone
//    		driveRobot(0.64, 0.6, 2, false);
//    		break;
//    		case 13:
//    		dropBox();
//    		break;
//    		}
//    		}


    public void liftBox() {
        //Close claw
        mySolenoid.set(DoubleSolenoid.Value.kForward);
        isOpen = false;

        double current = System.currentTimeMillis() / 1000.0;
        double future = current + 6;

        do {
            current = System.currentTimeMillis() / 1000.0;
            lift.set(0.6);
        } while (current < future);

        lift.set(0);

    }

    public void dropBox() {
        //Open claw
        mySolenoid.set(DoubleSolenoid.Value.kReverse);
        isOpen = true;
    }
//    		/**
//    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        // Left-Right Input XBOX CONTROLLER
        float leftRight = 0;
        leftRight = (float) driver.getRawAxis(0);
        float rightTrigger = (float) driver.getRawAxis(3) * -1;
        float leftTrigger = (float) driver.getRawAxis(2);
//    	boolean rightCloseButton = driver.getRawButton(2);
//    	boolean leftOpenButton = driver.getRawButton(1);
        boolean increaseGear = driver.getRawButton(6);
        boolean lowerGear = driver.getRawButton(5);
//    	boolean level1=driver.getRawButton(3);
//    	boolean level2=driver.getRawButton(4);

        // LOGITECH CONTROLLER    	
        float direction = (float) driver2.getRawAxis(1);
        boolean closeClawButton = driver2.getRawButton(3);
        boolean openClawButton = driver2.getRawButton(4);
//    	boolean level1Button = driver2.getRawButton(7);
//    	boolean level2Button = driver2.getRawButton(8);
//    	boolean level3Button = driver2.getRawButton(9);
//    	boolean level4Button = driver2.getRawButton(10);
//    	boolean levelUpButton = driver2.getRawButton(11);
//    	boolean levelDownButton = driver2.getRawButton(12);
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
//    	LOGITECH CONTROLELR:
//    	if (level1Button&&proxSensor.get()) {
//    		if(currentLevel>LEVEL_ONE){
//        			while(proxSensor.get()){
//        				lift.set(-1);
//        			}
//        			currentLevel=LEVEL_ONE;
//    	}
//    		canLift=true;
//    		canLower=false;
//    	}
//    	if(level2Button&&proxSensor1.get()){
//    		if(currentLevel!=LEVEL_TWO){
//        		if(currentLevel>LEVEL_TWO){
//        			while(proxSensor1.get()&&limitSwitchGround.get()){
//        				lift.set(-1);
//        			}
//        			currentLevel=LEVEL_TWO;
//        		if(currentLevel<LEVEL_TWO){
//        			while(proxSensor1.get()&&limitSwitchTop.get()){
//        				lift.set(1);
//        			}
//        			currentLevel=LEVEL_TWO;
//        			}
//        		}
//        	}
//    		canLift=true;
//    		canLower=true;
//    	}
//    	if(level3Button&&proxSensor2.get()){
//    		if(currentLevel!=LEVEL_THREE){
//    		if(currentLevel>LEVEL_THREE){
//    			while(proxSensor2.get()&&limitSwitchGround.get()){
//    				lift.set(-1);
//    			}
//    			currentLevel=LEVEL_THREE;
//    		if(currentLevel<LEVEL_THREE){
//    			while(proxSensor2.get()&&limitSwitchTop.get()){
//    				lift.set(1);
//    			}
//    			currentLevel=LEVEL_THREE;
//   		}
//    	}
//    	}
//    		canLift=true;
//    		canLower=true;
//    	}
//    	if (level4Button&&proxSensor3.get()) {
//    		if(currentLevel<LEVEL_FOUR){
//        			while(proxSensor3.get()&&limitSwitchTop.get()){
//        				lift.set(1);
//        			}
//        			currentLevel=LEVEL_FOUR;
//    	} 	allowGearUp=true;
//    	}
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
            turn = "Right";
        } else if (leftRight > 0) { // Turning Left
            turn = "Left";
        }

        // Value to negate from final speed
        float turnSpeed = Math.abs(leftRight) * currentSpeed;

        // Negate speed
        switch (turn) {
            case "Right":
                finalRightSpeed -= turnSpeed * GEAR[currentGear];
                break;
            case "Left":
                finalLeftSpeed -= turnSpeed * GEAR[currentGear];
                break;
            case "None":
                break;
        }

        if (rightTrigger != 0) {
            finalLeftSpeed = finalLeftSpeed * ((float) 0.947);
        } else if (leftTrigger != 0) {
            finalRightSpeed = finalRightSpeed * ((float) 0.947);
        }

        /* +/positive = up
         //    	XBOX CONTROLLER:
         if(rightStick >0 && proxSensor.get()){
         if((!proxSensor1.get())||(proxSensor2.get())){
         currentLevel=currentLevel+0.5;
         }
         lift.set(rightStick * 1);
    		
         }
         else if(rightStick <0 && proxSensor3.get()){
         if((!proxSensor1.get())||(proxSensor2.get())){
         currentLevel=currentLevel-0.5;
         }
         lift.set(rightStick * 1);
    		
         }
 	
         */
        if (currentSpeed != 0) {
            motors.tankDrive(finalLeftSpeed, finalRightSpeed);
        } else { // Stationary turn
            motors.tankDrive(leftRight * GEAR[currentGear], -1 * leftRight * GEAR[currentGear]);
        }

        /*    	XBOX CONTROLLER:
         if (rightCloseButton&&!isOpen) {
         mySolenoid.set(DoubleSolenoid.Value.kForward);
         isOpen=true;
         }
         else if (leftOpenButton&&isOpen) {
         mySolenoid.set(DoubleSolenoid.Value.kReverse);
         isOpen=false;
         }
         else{
         mySolenoid.set(DoubleSolenoid.Value.kOff);
         }
         */
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

//    	else if(direction <0 && proxSensor.get()&&canLower){
//    		currentLiftSpeed=direction;
//    		canLift=true;    		
//    		if((!proxSensor.get())&&((currentLevel%(int)currentLevel)==0)){
//    			currentLevel=currentLevel-0.5;
//    		}
//    		if(!proxSensor.get()){
//    			lift.set(0);
//    			canLower=false;
//    		}
//    	}
//    	if(!proxSensor.get()){
//			lift.set(0);
//			canLower=false;
//		}
//    	else if(!proxSensor3.get()){
//			lift.set(0);
//			canLift=false;
//		}
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

//    	if (level1Button && proxSensor2.get()&&canLift){
//    		lift.set(1);
//    		currentLiftSpeed = direction;
//    		canLower = true;
//    		if(((proxSensor2.get())&&((currentLevel%(int)currentLevel)==0))){
//    			currentLevel=currentLevel+0.5;
//    		}
//    		if (!proxSensor2.get()) {
//    			lift.set(0);
//    			canLift = false;
//    		}
//    	}
//    	
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
