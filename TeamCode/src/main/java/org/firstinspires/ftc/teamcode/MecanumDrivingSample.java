package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@SuppressWarnings("unused")
@TeleOp
public class MecanumDrivingSample extends LinearOpMode {

    private double expoRate = 1.0;
    private boolean previousDpadUp = false;
    private boolean previousDpadDown = false;


    @Override
    public void runOpMode() {
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
                if (gamepad1.dpad_up && !previousDpadUp) {
                    expoRate += 0.1;
                }
                if (gamepad1.dpad_down && !previousDpadDown) {
                    expoRate -= 0.1;
                }
                previousDpadUp = gamepad1.dpad_up;
                previousDpadDown = gamepad1.dpad_down;
                drive.driveRobotCentric(
                        -exponentialRate(driverOp.getLeftX(), expoRate),
                        -exponentialRate(driverOp.getLeftY(), expoRate),
                        -exponentialRate(driverOp.getRightX(), expoRate),
                        false
                );
                telemetry.addData("Expo Rate:", expoRate);
                telemetry.update();
        }
    }

    private double exponentialRate(double oldValue, double exponent) {
        return Math.signum(oldValue) * Math.pow(Math.abs(oldValue), exponent);
    }
}
