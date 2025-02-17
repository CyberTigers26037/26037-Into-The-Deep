package org.firstinspires.ftc.teamcode.test;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subassembly.BottyJamesClaw;

@SuppressWarnings("unused")
@TeleOp
public class TestBottyJamesClaw extends OpMode {
    private BottyJamesClaw claw;

    public void init() {
        claw = new BottyJamesClaw(hardwareMap);
        claw.zero();
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            claw.openPincher();
        }
        if (gamepad1.b) {
            claw.closePincher();
        }
        if (gamepad1.x) {
            claw.elbowStraight();
        }
        if (gamepad1.y) {
            claw.elbowDown();
        }
        claw.adjustElbowAngle(-gamepad1.right_stick_y);
        claw.adjustPincherAngle(gamepad1.right_stick_x);

        claw.outputTelemetry(telemetry);
        telemetry.update();
    }
}
