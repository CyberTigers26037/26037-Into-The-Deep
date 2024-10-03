package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MecanumDrivingSample extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // constructor takes in frontLeft, frontRight, backLeft, backRight motors
        // IN THAT ORDER
        MecanumDrive drive = new MecanumDrive(
                new Motor(hardwareMap, "frontLeftMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "frontRightMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "backLeftMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "backRightMotor", Motor.GoBILDA.RPM_312)
        );


        // the extended gamepad object
        GamepadEx driverOp = new GamepadEx(gamepad1);

        waitForStart();

        while (!isStopRequested()) {

                drive.driveRobotCentric(
                        driverOp.getLeftX(),
                        driverOp.getLeftY(),
                        driverOp.getRightX(),
                        false
                );
            }
            }

        }
