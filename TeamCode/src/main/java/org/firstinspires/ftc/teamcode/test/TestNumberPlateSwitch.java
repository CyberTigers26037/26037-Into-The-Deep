package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subassembly.NumberPlateSwitch;

@SuppressWarnings("unused")
@TeleOp
public class TestNumberPlateSwitch extends OpMode {
    private static final double BLUE = 0.611;
    private static final double RED = 0.279;
    private NumberPlateSwitch numberPlateSwitch;
    private Servo rgbLight;


    @Override
    public void init() {
        numberPlateSwitch = new NumberPlateSwitch(hardwareMap);
        rgbLight = hardwareMap.get(Servo.class, "rgbLight");
    }

    @Override
    public void loop() {
        telemetry.addData("Number Plate Color", numberPlateSwitch.isNumberPlateBlue() ? "Blue" : "Red");
        if (numberPlateSwitch.isNumberPlateBlue()) {
            rgbLight.setPosition(BLUE);
        }
        else {
            rgbLight.setPosition(RED);
        }
    }
}
