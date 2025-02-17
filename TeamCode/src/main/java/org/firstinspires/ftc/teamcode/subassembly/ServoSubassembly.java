package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.Range;

public class ServoSubassembly {
    private final double MIN_PWM;
    private final double MAX_PWM;
    private final double SERVO_DEGREES;
    protected final double minSafeDegrees;
    protected final double maxSafeDegrees;
    protected final Servo servo;
    protected double currentAngle;

    protected ServoSubassembly(double minSafeDegrees, double maxSafeDegrees, Servo servo, boolean isAxon) {
        this.minSafeDegrees = minSafeDegrees;
        this.maxSafeDegrees = maxSafeDegrees;
        this.servo = servo;
        MIN_PWM = isAxon ? 525 : 500;
        MAX_PWM = isAxon ? 2475 : 2500;
        SERVO_DEGREES = isAxon ? 180 : 300;

        setServoRange();
        if (isAxon){
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
