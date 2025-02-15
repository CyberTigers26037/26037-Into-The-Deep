package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.subassembly.BottyJamesLinearActuator;


@TeleOp(name= "BottyJamesDEMO")

@SuppressWarnings("unused")
public class BottyJames extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor armPan = null;
    private BottyJamesLinearActuator armActuator;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        resetArmActuator();
        while(opModeIsActive()){
            driveRobot();
        }
    }


    public void initialize(){

        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        armPan = hardwareMap.get(DcMotor.class, "arm_pan");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        armPan.setDirection(DcMotor.Direction.FORWARD);
        armActuator = new BottyJamesLinearActuator(hardwareMap);
        telemetry.addData("Status", "Initialized");

    }


    public void resetArmActuator(){
        runtime.reset();
        armActuator.setMotorPower(-0.2);
        while(!isStopRequested() && !armActuator.isFullyRetracted()){
            armActuator.execute();
        }
        armActuator.reset();
    }
    public void driveRobot(){


    double leftPower;
    double rightPower;

        double drive = gamepad1.left_stick_y;
        double turn  =  -gamepad1.left_stick_x;
        double armTurn = gamepad1.right_stick_x;
        double slidePower = -gamepad1.right_stick_y;

        //double bucket = gamepad1.dpad_down;
        leftPower    = Range.clip(drive + turn, -1.0, 1.0);
        rightPower   = Range.clip(drive - turn, -1.0, 1.0);
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        armPan.setPower(armTurn);
        armActuator.setMotorPower(slidePower);
        armActuator.execute();


        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Limit Switch Pressed", armActuator.isFullyRetracted());
        telemetry.addData("Arm Extension",armActuator.getCurrentExtensionMm());
        telemetry.addData("Arm Power",slidePower);
        telemetry.update();
    }



}


