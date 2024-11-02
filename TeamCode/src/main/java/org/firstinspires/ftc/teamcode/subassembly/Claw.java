package org.firstinspires.ftc.teamcode.subassembly;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
        elbow.down();
        wrist.straight();
        pincher.open();

    }
    public void prepareToPickupHorizontalSample() {
        elbow.down();
        wrist.right90();
        pincher.open();

    }

    public void prepareToDropSampleBasket() {

        elbow.straight();
        wrist.straight();

    }

    public void prepareToHangSpecimen() {
        elbow.straight();
        wrist.right90();
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

}
