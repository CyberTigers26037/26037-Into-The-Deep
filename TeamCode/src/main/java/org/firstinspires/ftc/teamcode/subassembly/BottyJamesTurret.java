package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

public class BottyJamesTurret {
    private static final double MIN_PWM = 1000;
    private static final double MAX_PWM = 2000;
    private final Servo leftFireMotorServo;
    private final Servo rightFireMotorServo;
    private final Servo firingPinServo;
    private boolean currentlyFiring;
    private static final double FIRE_SPEED = 0.25;
    private final double FIRE_ZERO_POSITION = 0.5;
    private static final double FIRE_FIRE_POSITION = 0.3;
    private long firingStartTime;
    private static final long FIRE_TIMELINE_START_MOTORS = 0;
    private static final long FIRE_TIMELINE_EXTEND_FIRING_PIN1 = 250;
    private static final long FIRE_TIMELINE_RETRACT_FIRING_PIN1 = 500;
    private static final long FIRE_TIMELINE_EXTEND_FIRING_PIN2 = 750;
    private static final long FIRE_TIMELINE_RETRACT_FIRING_PIN2 = 1000;
    private static final long FIRE_TIMELINE_EXTEND_FIRING_PIN3 = 1250;
    private static final long FIRE_TIMELINE_RETRACT_FIRING_PIN3 = 1500;
    private static final long FIRE_TIMELINE_STOP_MOTORS = 1750;
    private double currentFiringSpeed;
    private static final double FIRE_SLOW_DOWN_DECREMENT = 0.02;
    public BottyJamesTurret(HardwareMap hardwareMap){
        leftFireMotorServo = hardwareMap.get(Servo.class, "LeftFireMotor");
        rightFireMotorServo = hardwareMap.get(Servo.class, "RightFireMotor");
        firingPinServo = hardwareMap.get(Servo.class, "FiringPinServo");
        setServoRange(leftFireMotorServo);
        setServoRange(rightFireMotorServo);
        firingPinServo.setPosition(FIRE_ZERO_POSITION);
    }

    public void execute() {
        if(currentlyFiring){
            if(System.currentTimeMillis() > firingStartTime + FIRE_TIMELINE_STOP_MOTORS) {
                currentFiringSpeed -= FIRE_SLOW_DOWN_DECREMENT;
                if (currentFiringSpeed >= 0) {
                    rightFireMotorServo.setPosition(currentFiringSpeed);
                    leftFireMotorServo.setPosition(currentFiringSpeed);
                } else {
                    currentlyFiring = false;
                }
            }
            else if (System.currentTimeMillis() > firingStartTime + FIRE_TIMELINE_RETRACT_FIRING_PIN3) {
                firingPinServo.setPosition(FIRE_ZERO_POSITION);
            }
            else if (System.currentTimeMillis() > firingStartTime + FIRE_TIMELINE_EXTEND_FIRING_PIN3) {
                firingPinServo.setPosition(FIRE_FIRE_POSITION);
            }
            else if (System.currentTimeMillis() > firingStartTime + FIRE_TIMELINE_RETRACT_FIRING_PIN2) {
                firingPinServo.setPosition(FIRE_ZERO_POSITION);
            }
            else if (System.currentTimeMillis() > firingStartTime + FIRE_TIMELINE_EXTEND_FIRING_PIN2) {
                firingPinServo.setPosition(FIRE_FIRE_POSITION);
            }
            else if (System.currentTimeMillis() > firingStartTime + FIRE_TIMELINE_RETRACT_FIRING_PIN1) {
                firingPinServo.setPosition(FIRE_ZERO_POSITION);
            }
            else if (System.currentTimeMillis() > firingStartTime + FIRE_TIMELINE_EXTEND_FIRING_PIN1) {
                firingPinServo.setPosition(FIRE_FIRE_POSITION);
            }
        }
    }

    public void startFiring() {
        if(currentlyFiring) return;
        currentFiringSpeed = FIRE_SPEED;
        leftFireMotorServo.setPosition(FIRE_SPEED);
        rightFireMotorServo.setPosition(FIRE_SPEED);
        firingStartTime = System.currentTimeMillis();
        currentlyFiring = true;
    }

    private void setServoRange(Servo servo) {
        if(servo.getController() instanceof ServoControllerEx) {
            ServoControllerEx controller = (ServoControllerEx) servo.getController();
            controller.setServoPwmRange(
                    servo.getPortNumber(),
                    new PwmControl.PwmRange(MIN_PWM, MAX_PWM)
            );
        }
    }
}
