package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Pincher {

    private static final double SERVO_DEGREES = 270;
    private static final double MIN_SAFE_DEGREES = -90;
    private static final double MAX_SAFE_DEGREES = -30;
    private double currentAngle;
    private boolean isOpen;
    private Servo servo;
    public Pincher (HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, "Pincher Servo");

    }
    public void toggle() {
        if (isOpen) {
            close();
        }
        else{
            open();
        }

    }
    public void open() {

        setServoToAngle(MAX_SAFE_DEGREES);
        isOpen = true;
    }

    public void close() {

        setServoToAngle(MIN_SAFE_DEGREES);

        isOpen = false;
    }

    public void adjustAngle(double degrees) {

        setServoToAngle(currentAngle + degrees);

    }

    public void zero() {

        setServoToAngle(0);
        isOpen = true;
    }
    private void setServoToAngle(double degrees) {

        degrees = Range.clip(degrees, MIN_SAFE_DEGREES, MAX_SAFE_DEGREES);
        currentAngle = degrees;
        servo.setPosition(Range.scale(degrees, -SERVO_DEGREES/2, SERVO_DEGREES/2, 0, 1));

    }
    public double getCurrentAngle() {
        return currentAngle;
    }
    public void outputTelemetry(Telemetry telemetry) {
        telemetry.addData("Pincher Angle", currentAngle);
        telemetry.addData("Pincher Open", isOpen);
    }
}
