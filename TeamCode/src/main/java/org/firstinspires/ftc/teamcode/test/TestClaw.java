package org.firstinspires.ftc.teamcode.test;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subassembly.Claw;

@SuppressWarnings("unused")
@TeleOp
public class TestClaw extends OpMode {
    private Claw claw;

    public void init() {
        claw = new Claw(hardwareMap);
        claw.zero();
    }

    @Override
    public void loop() {
        if (gamepad2.b) {
            claw.dropSample();
        }
        if (gamepad2.x) {
            claw.pickupSample();
        }
        if (gamepad2.a) {
            claw.prepareToPickupVerticalSample();
        }
        if (gamepad2.y) {
            claw.prepareToPickupHorizontalSample();
        }

        claw.outputTelemetry(telemetry);
        telemetry.update();
    }
}
