package org.firstinspires.ftc.teamcode.test;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subassembly.Pincher;

@TeleOp
public class TestPincher extends OpMode {

    private Pincher pincher;

    @Override
    public void init() {

        pincher = new Pincher(hardwareMap);

    }

    @Override
    public void loop() {

        if (gamepad1.x) {

            pincher.zero();
        }

        if (gamepad1.a) {

            pincher.open();
        }

        if (gamepad1.b) {

            pincher.close();
        }

        pincher.adjustAngle(-gamepad1.left_trigger * 0.1);

        pincher.adjustAngle(gamepad1.right_trigger * 0.1);


        telemetry.addData("Pincher Servo Angle: ", pincher.getCurrentAngle());
        telemetry.update();

    }
}
