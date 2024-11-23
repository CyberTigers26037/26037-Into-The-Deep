package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elbow extends ServoSubassembly {
    private static final double MIN_SAFE_DEGREES = -90;
    private static final double MAX_SAFE_DEGREES = 0;

    public Elbow(HardwareMap hwMap) {
        super (MIN_SAFE_DEGREES, MAX_SAFE_DEGREES, hwMap.get(Servo.class, "Elbow"));
    }

    public void straight() {
        setServoToAngle(-90);
    }

    public void down() {
        setServoToAngle(0);
    }

    public void prepareToDropSampleLowBasket() {
        setServoToAngle(-47);
    }

    public void prepareToDropSampleHighBasket() {
        setServoToAngle(-33);
    }

    public void prepareToPickupVerticalSample() {
        setServoToAngle(-17);
    }

    public void prepareToHangHighSpecimen() {
        setServoToAngle(-37);
    }

    public void fieldPickUpElbow() {
        setServoToAngle(-52);
    }



    public void zero() {
        setServoToAngle(0);
    }

    public void adjustAngle(double degrees) {
        setServoToAngle(currentAngle+degrees);
    }

    public void outputTelemetry(Telemetry telemetry) {
        telemetry.addData("Elbow Angle", currentAngle);
    }
}
