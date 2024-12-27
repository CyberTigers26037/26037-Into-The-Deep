package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subassembly.WheelieBar;

@SuppressWarnings("unused")
@TeleOp
public class TestWheelieBar extends OpMode {
    private WheelieBar wheelieBar;

    @Override
    public void init() {
        wheelieBar = new WheelieBar(hardwareMap);
    }

    @Override
    public void loop() {

        if (gamepad2.x) {
            wheelieBar.open();
        }
        if (gamepad2.y) {
            wheelieBar.close();
        }

        wheelieBar.adjustAngle(-gamepad2.left_stick_x);

        wheelieBar.outputTelemetry(telemetry);
        telemetry.update();

    }
}
