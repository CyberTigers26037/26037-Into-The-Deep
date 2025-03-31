package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subassembly.BottyJamesTurret;

@SuppressWarnings("unused")
@TeleOp
public class TestBottyJamesTurret extends OpMode {
    private BottyJamesTurret bottyJamesTurret;

    @Override
    public void init() {
        bottyJamesTurret = new BottyJamesTurret(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0.5){
            bottyJamesTurret.startFiring();
        }
        bottyJamesTurret.execute();
    }
}
