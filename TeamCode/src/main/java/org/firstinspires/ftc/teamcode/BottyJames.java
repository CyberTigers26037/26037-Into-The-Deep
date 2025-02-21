package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.subassembly.BottyJamesClaw;
import org.firstinspires.ftc.teamcode.subassembly.BottyJamesLinearActuator;
import org.firstinspires.ftc.teamcode.subassembly.BottyJamesWormDriveArm;


@TeleOp(name= "BottyJamesDEMO")

@SuppressWarnings("unused")
public class BottyJames extends LinearOpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private BottyJamesClaw claw;
    private BottyJamesLinearActuator armActuator;
    private BottyJamesWormDriveArm armWormDrive;
    private boolean previousRightStickButton;
    private boolean previousButtonA;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        claw.elbowStraight();
        claw.openPincher();
        resetArmActuator();
        setArmToMinimumExtension();
        resetArmWormDrive();
        zeroArm();
        while(opModeIsActive()){
            driveRobot();
        }
    }

    private void setArmToMinimumExtension() {
        armActuator.setMotorPower(0.5);
        while(!isStopRequested() && (armActuator.getCurrentExtensionMm() < 30)){

            armActuator.execute();
        }
        armActuator.setMotorPower(0);
        armActuator.execute();
        armActuator.setMinimumExtensionMm(30);
    }


    public void initialize(){

        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        claw = new BottyJamesClaw(hardwareMap);
        armActuator = new BottyJamesLinearActuator(hardwareMap);
        armWormDrive = new BottyJamesWormDriveArm(hardwareMap);
        telemetry.addData("Status", "Initialized");

    }


    public void resetArmActuator(){
        armActuator.setMotorPower(-0.2);
        while(!isStopRequested() && !armActuator.isFullyRetracted()){
            armActuator.execute();
        }
        armActuator.reset();
    }
    public void resetArmWormDrive(){
        armWormDrive.setMotorPower(-0.2);
        while(!isStopRequested() && !armWormDrive.isAtLimitSwitchPosition()){
            armWormDrive.execute();
        }
        armWormDrive.reset();
    }
    public void zeroArm(){
        armWormDrive.setMotorPower(0.5);
        while(!isStopRequested() && !armWormDrive.isAtZeroPosition()){
            armWormDrive.execute();
        }
    }

    public void driveRobot(){


    double leftPower;
    double rightPower;

        double drive = gamepad1.left_stick_y * 0.5;
        double turn  =  -gamepad1.left_stick_x * 0.5;
        double armTurn = gamepad1.right_stick_x;
        double slidePower = -gamepad1.right_stick_y;

        leftPower    = Range.clip(drive + turn, -0.5, 0.5);
        rightPower   = Range.clip(drive - turn, -0.5, 0.5);
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        armWormDrive.setMotorPower(armTurn);
        armActuator.setMotorPower(slidePower);
        armActuator.execute();
        armWormDrive.execute();

        if (gamepad1.a && !previousButtonA) {
            claw.togglePincher();
        }
        if (gamepad1.dpad_up) {
            claw.elbowStraight();
        }
        if (gamepad1.dpad_down) {
            claw.elbowDown();
        }
        if (gamepad1.right_stick_button && !previousRightStickButton) {
            claw.togglePincher();
        }
        previousRightStickButton = gamepad1.right_stick_button;
        previousButtonA = gamepad1.a;


        // Show the elapsed game time and wheel power.
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Limit Switch Pressed", armActuator.isFullyRetracted());
        telemetry.addData("Arm Extension",armActuator.getCurrentExtensionMm());
        telemetry.addData("Arm Slide Power",slidePower);
        telemetry.addData("Worm Drive Limit Switch Pressed", armWormDrive.isAtLimitSwitchPosition());
        telemetry.addData("Arm Angle",armWormDrive.getCurrentArmAngleDegrees());
        telemetry.addData("Arm Worm Drive Power",armTurn);
        telemetry.update();
    }



}


