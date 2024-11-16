package org.firstinspires.ftc.teamcode.test;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subassembly.Pincher;

@SuppressWarnings("unused")
@TeleOp
public class TestPincher extends OpMode {
    private Pincher pincher;

    @Override
    public void init() {
        pincher = new Pincher(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad2.x) {
            pincher.zero();
        }

        if (gamepad2.a) {
            pincher.open();
        }

        if (gamepad2.b) {
            pincher.close();
        }

        pincher.adjustAngle(-gamepad2.left_trigger * 0.1);
        pincher.adjustAngle(gamepad2.right_trigger * 0.1);

        pincher.outputTelemetry(telemetry);
        telemetry.update();
    }
}
