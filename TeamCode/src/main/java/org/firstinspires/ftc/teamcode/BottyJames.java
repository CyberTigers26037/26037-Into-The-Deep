package org.firstinspires.ftc.teamcode;
import android.media.audiofx.DynamicsProcessing;
import android.text.method.Touch;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.subassembly.LimitSwitch;


@TeleOp(name= "BottyJamesDEMO")

@SuppressWarnings("unused")
public class BottyJames extends OpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor armPan = null;
    private DcMotor armSlide = null;
    private LimitSwitch maxlimitSwitch;
    private LimitSwitch minLimitSwitch;
    private boolean slideMaxed = false;
    private boolean slideMinned = false;

    @Override
    public void init(){

        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        armPan = hardwareMap.get(DcMotor.class, "arm_pan");
        armSlide = hardwareMap.get(DcMotor.class, "arm_slide");
        minLimitSwitch = new LimitSwitch(hardwareMap, "min_limit_switch");
        maxlimitSwitch = new LimitSwitch(hardwareMap, "max_limit_switch");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        armPan.setDirection(DcMotor.Direction.FORWARD);
        armSlide.setDirection(DcMotor.Direction.FORWARD);
        telemetry.addData("Status", "Initialized");


    }


    @Override
    public void start(){
    runtime.reset();

    }
    @Override
    public void loop(){


    double leftPower;
    double rightPower;

        double drive = gamepad1.left_stick_y;
        double turn  =  -gamepad1.right_stick_x;
        double armTurn = gamepad1.left_stick_x;
        double slidePower = gamepad1.right_stick_y;
        boolean maxSwitchPressed = maxlimitSwitch.isLimitSwitchPressed();
        boolean minSwitchPressed = minLimitSwitch.isLimitSwitchPressed();

        //double bucket = gamepad1.dpad_down;
        leftPower    = Range.clip(drive + turn, -1.0, 1.0);
        rightPower   = Range.clip(drive - turn, -1.0, 1.0);
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        armPan.setPower(armTurn);

        if(maxSwitchPressed && (slidePower > 0)){
            slidePower = 0;
        }
        if(minSwitchPressed && (slidePower < 0)){
            slidePower = 0;
        }
        armSlide.setPower(slidePower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Max Switch Pressed", maxlimitSwitch.isLimitSwitchPressed());
        telemetry.addData("Min Switch Pressed", minLimitSwitch.isLimitSwitchPressed());
    }



    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }


}


