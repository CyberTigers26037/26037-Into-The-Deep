package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elbow extends ServoSubassembly {
    private static final double MIN_SAFE_DEGREES = -30;
    private static final double MAX_SAFE_DEGREES = 90;

    public Elbow(HardwareMap hwMap) {
        super (MIN_SAFE_DEGREES, MAX_SAFE_DEGREES, hwMap.get(Servo.class, "Elbow"));
    }

    public void straight() {
        setServoToAngle(0);
    }

    public void down() {
        setServoToAngle(90);
    }

    public void nathansDown() {
        setServoToAngle(78);
    }

    public void prepareToDropSampleLowBasket() {
        setServoToAngle(43);
    }

    public void prepareToDropSampleHighBasketBackwards(){setServoToAngle(-30); }

    public void prepareToDropSampleHighBasket() {
        setServoToAngle(57);
    }

    public void prepareToPickupVerticalSample() {
        setServoToAngle(73);
    }

    public void prepareToHangHighSpecimen() {
        setServoToAngle(53);
    }
    public void prepareToHangHighSpecimenBackwards(){
        setServoToAngle(-30);

    }

    public void fieldPickUpElbow() {
        setServoToAngle(38);
    }



    public void zero() {
        setServoToAngle(0);
    }
    public void horizontalPickup(){
        setServoToAngle(60.5);
    }
    public void horizontalPickupAutoBaskets(){
        setServoToAngle(88.74);

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
