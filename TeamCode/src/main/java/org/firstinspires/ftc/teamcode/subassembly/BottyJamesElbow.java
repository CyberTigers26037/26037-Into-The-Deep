package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BottyJamesElbow extends ServoSubassembly {
    private static final double MIN_SAFE_DEGREES = 0;
    private static final double MAX_SAFE_DEGREES = 90;

    public BottyJamesElbow(HardwareMap hwMap) {
        super (MIN_SAFE_DEGREES, MAX_SAFE_DEGREES, hwMap.get(Servo.class, "Elbow"), false);
    }


    public void straight() {
        setServoToAngle(90);
    }

    public void down() {
        setServoToAngle(0);
    }

    public void zero() {
        setServoToAngle(0);
    }

    public void adjustAngle(double degrees) {
        setServoToAngle(currentAngle+degrees);
    }

    public void outputTelemetrySimple(Telemetry telemetry) {
        telemetry.addData("Elbow Angle", currentAngle);
    }

    public void outputTelemetry(Telemetry telemetry) {
        outputTelemetrySimple(telemetry);
    }
}
