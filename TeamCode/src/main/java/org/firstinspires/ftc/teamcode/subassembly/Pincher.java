package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.RobotConfig;

public class Pincher extends ServoSubassembly {
    private static final double MIN_SAFE_DEGREES = RobotConfig.getPincherMinSafeDegrees();
    private static final double MAX_SAFE_DEGREES = RobotConfig.getPincherMaxSafeDegrees();
    private boolean isOpen;

    public Pincher (HardwareMap hwMap) {
        super (MIN_SAFE_DEGREES, MAX_SAFE_DEGREES, hwMap.get(Servo.class, "Pincher"));
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

    public void outputTelemetrySimple(Telemetry telemetry) {
        telemetry.addData("Pincher Angle", currentAngle);
    }

    public void outputTelemetry(Telemetry telemetry) {
        outputTelemetrySimple(telemetry);
        telemetry.addData("Pincher Open", isOpen);
    }
}
