package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist extends ServoSubassembly {
    private static final double MIN_SAFE_DEGREES = -90;
    private static final double MAX_SAFE_DEGREES = 90;
    private boolean isStraight;

    public Wrist(HardwareMap hwMap) {
        super (MIN_SAFE_DEGREES, MAX_SAFE_DEGREES, hwMap.get(Servo.class, "Wrist"));
    }

    public void straight() {
        setServoToAngle(0);
        isStraight = true;
    }

    public void slant() {
        setServoToAngle(50);
        isStraight = false;
    }

    public void right90() {
        setServoToAngle(-90);
        isStraight = false;
    }
    public void horizontalAutoBaskets(){
        setServoToAngle(-32);
        isStraight = false;
    }

    public void left90() {
        setServoToAngle(90);
        isStraight = false;
    }

    public void zero() {
        setServoToAngle(0);
        isStraight = true;
    }

    public void adjustAngle(double degrees) {
        setServoToAngle(currentAngle+degrees);
        isStraight = (currentAngle > -1 && currentAngle <1);
    }

    public void toggleAngle() {
        if (isStraight) {
            right90();
        }
        else{
            straight();
        }
    }

    public void outputTelemetrySimple(Telemetry telemetry) {
        telemetry.addData("Wrist Angle", currentAngle);
    }

    public void outputTelemetry(Telemetry telemetry) {
        outputTelemetrySimple(telemetry);
        telemetry.addData("Wrist Straight", isStraight);
    }
}

