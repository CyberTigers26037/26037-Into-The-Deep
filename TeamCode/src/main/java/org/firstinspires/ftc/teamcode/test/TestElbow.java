package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subassembly.Elbow;

@SuppressWarnings("unused")
@TeleOp
public class TestElbow extends OpMode {
    private Elbow elbow;

    @Override
    public void init() {
        elbow = new Elbow(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad2.x) {
            elbow.zero();
        }
        if (gamepad2.y) {
            elbow.straight();
        }
        if (gamepad2.a) {
            elbow.down();
        }
        elbow.adjustAngle(-gamepad2.left_trigger * 0.1);
        elbow.adjustAngle(gamepad2.right_trigger * 0.1);

        elbow.outputTelemetry(telemetry);
        telemetry.update();
    }
}
