package org.firstinspires.ftc.teamcode.subassembly;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {

    private Elbow elbow;
    private Pincher pincher;
    private Wrist wrist;

    public Claw(HardwareMap hwMap) {
        elbow = new Elbow(hwMap);
        wrist = new Wrist(hwMap);
        pincher = new Pincher(hwMap);

    }

    public void zero() {
        elbow.zero();
        wrist.zero();
        pincher.zero();
    }

    public void prepareToPickupVerticalSample() {
        elbow.prepareToPickupVerticalSample();
        wrist.straight();
        pincher.open();

    }
    public void prepareToPickupHorizontalSample() {
        elbow.down();
        wrist.right90();
        pincher.open();

    }

    public void prepareToDropSampleLowBasket() {
        elbow.prepareToDropSampleLowBasket();
        wrist.straight();
    }

    public void prepareToDropSampleHighBasket() {
        elbow.prepareToDropSampleHighBasket();
        wrist.straight();
    }

    public void prepareToHangLowSpecimen() {
        elbow.straight();
        wrist.straight();
    }

    public void prepareToHangHighSpecimen() {
        elbow.prepareToHangHighSpecimen();
        wrist.straight();
    }

    public void togglePincher() {
        pincher.toggle();
    }

    public void dropSample() {
        pincher.open();
    }

    public void pickupSample() {
        pincher.close();
    }

    public void adjustWristAngle(double degrees){
        wrist.adjustAngle(degrees);
    }
    public void adjustElbowAngle(double degrees){
        elbow.adjustAngle(degrees);
    }
    public void toggleWristAngle(){
        wrist.toggleAngle();
    }
    public void outputTelemetry(Telemetry telemetry) {
        elbow.outputTelemetry(telemetry);
        wrist.outputTelemetry(telemetry);
        pincher.outputTelemetry(telemetry);
    }
}
