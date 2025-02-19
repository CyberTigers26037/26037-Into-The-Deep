package org.firstinspires.ftc.teamcode.subassembly;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BottyJamesClaw {
    private final BottyJamesElbow elbow;
    private final BottyJamesPincher pincher;

    public BottyJamesClaw(HardwareMap hwMap) {
        elbow          = new BottyJamesElbow(hwMap);
        pincher        = new BottyJamesPincher(hwMap);
    }

    public void zero() {
        elbow.zero();
        pincher.zero();
    }



    public void togglePincher() {
        pincher.toggle();
    }

    public void adjustElbowAngle(double degrees) {
        elbow.adjustAngle(degrees);
    }

    public void outputTelemetry(Telemetry telemetry) {
        elbow.outputTelemetry(telemetry);
        pincher.outputTelemetry(telemetry);
    }

    public void openPincher() {
        pincher.open();
    }

    public void elbowStraight() {
        elbow.straight();
    }

    public void closePincher() {
        pincher.close();
    }

    public void elbowDown() {
        elbow.down();
    }

    public void adjustPincherAngle(double degrees) {
        pincher.adjustAngle(degrees);
    }
}
