package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.Range;

public class ServoSubassembly {
    private static final boolean IS_AXON = true;
    private static final double MIN_PWM = IS_AXON ? 525 : 500;
    private static final double MAX_PWM = IS_AXON ? 2475 : 2500;
    private static final double SERVO_DEGREES = IS_AXON ? 180 : 300;
    protected final double minSafeDegrees;
    protected final double maxSafeDegrees;
    protected final Servo servo;
    protected double currentAngle;

    protected ServoSubassembly(double minSafeDegrees, double maxSafeDegrees, Servo servo) {
        this.minSafeDegrees = minSafeDegrees;
        this.maxSafeDegrees = maxSafeDegrees;
        this.servo = servo;

        setServoRange();
        if (IS_AXON){
            servo.setDirection(Servo.Direction.REVERSE);
        }
    }

    private void setServoRange() {
        if(servo.getController() instanceof ServoControllerEx) {
            ServoControllerEx controller = (ServoControllerEx) servo.getController();
            controller.setServoPwmRange(
                    servo.getPortNumber(),
                    new PwmControl.PwmRange(MIN_PWM, MAX_PWM)
            );
        }
    }

    protected void setServoToAngle(double degrees) {
        degrees = Range.clip(degrees, minSafeDegrees, maxSafeDegrees);
        currentAngle = degrees;
        servo.setPosition(Range.scale(degrees, -SERVO_DEGREES / 2, SERVO_DEGREES / 2, 0, 1));
    }
}
