package org.firstinspires.ftc.teamcode.test;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subassembly.Wrist;

@TeleOp
public class TestWrist extends OpMode {
    private Wrist wrist;
    @Override
    public void init() {
        wrist = new Wrist(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad2.x){
            wrist.zero();
        }
        if(gamepad2.y){
            wrist.left90();
        }
        if(gamepad2.b){
            wrist.right90();
        }
        if(gamepad2.a)
            wrist.straight();
        wrist.adjustAngle(-gamepad2.left_trigger * 0.1);
        wrist.adjustAngle(gamepad2.right_trigger * 0.1);

        telemetry.addData("Wrist Angle", wrist.getCurrentAngle());
    }
}
