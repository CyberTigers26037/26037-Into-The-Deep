package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Wrist {
    private Servo servo;
    private static final double SERVO_DEGREES = 270;
    private double currentAngle;
    private static final double MIN_SAFE_DEGREES = -90;
    private static final double MAX_SAFE_DEGREES = 90;
    public Wrist(HardwareMap hwMap) {
        servo = hwMap.get(Servo.class, "Wrist");
    }

    public void straight() {
        setServoToAngle(0);
    }
    public void right90() {
        setServoToAngle(-90);
    }
    public void left90() {
        setServoToAngle(90);
    }
    public void zero() {
        setServoToAngle(0);
    }
    public void adjustAngle(double degrees){
        setServoToAngle(currentAngle+degrees);
    }
    private void setServoToAngle(double degrees) {
        degrees=Range.clip(degrees,MIN_SAFE_DEGREES,MAX_SAFE_DEGREES);
        currentAngle = degrees;
        servo.setPosition(Range.scale(degrees, -SERVO_DEGREES / 2, SERVO_DEGREES / 2, 0, 1));

    }

    public double getCurrentAngle() {

        return currentAngle;

    }
}
