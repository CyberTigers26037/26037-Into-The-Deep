/*   MIT License
 *   Copyright (c) [2024] [Base 10 Assets, LLC]
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:

 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.

 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subassembly.Claw;
import org.firstinspires.ftc.teamcode.subassembly.SampleDetector;
import org.firstinspires.ftc.teamcode.subassembly.ViperSlideArm;
import org.firstinspires.ftc.teamcode.subassembly.WheelieBar;

@SuppressWarnings("unused")
@TeleOp(name="Cyber Tigers Into the Deep TeleOp")
public class CyberTigersIntoTheDeepTeleOp extends LinearOpMode {
    /*
    Control hub
        Motors
            Port 0 : frontLeftMotor
            Port 1 : backLeftMotor
            Port 2 : backRightMotor
            Port 3 : frontRightMotor
        Servos
            Port 0 : right_wheelie_servo
     Expansion hub
        Servos
            Port 1 : Pincher
            Port 2 : Wrist
            Port 3 : Elbow
            Port 5 : left_wheelie_servo
        I2C
            Port 0 : sensor_color_distance
        Motors
            Port 0 : armMotor
            Port 1 : viperSlideMotor
     */
    private com.arcrobotics.ftclib.drivebase.MecanumDrive drive;
    private GamepadEx driverOp;
    private boolean autoCloseClaw = false;
    private long autoClosePincherEnableTime;
    private boolean autoClosePincherTimerEnabled = false;

    @Override
    public void runOpMode() {
        /* Define and Initialize Motors */
        drive = new MecanumDrive(
                new Motor(hardwareMap, "frontLeftMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "frontRightMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "backLeftMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "backRightMotor", Motor.GoBILDA.RPM_312)
        );
        driverOp = new GamepadEx(gamepad1);
        GamepadEx subDriverOp = new GamepadEx(gamepad2);

        allowDriverToFixArmAndSlide();

        /* Wait for the game driver to press play */
        waitForStart();

        Claw claw = new Claw(hardwareMap);
        ViperSlideArm viperSlideArm = new ViperSlideArm(hardwareMap);
        claw.zero();

        WheelieBar wheelieBar = new WheelieBar(hardwareMap);

        /* Run until the driver presses stop */
        while (opModeIsActive()) {
            drive.driveRobotCentric(
                    -driverOp.getLeftX(),
                    -driverOp.getLeftY(),
                    -driverOp.getRightX(),
                    true
            );
            subDriverOp.readButtons();

            viperSlideArm.setArmPositionFudgeFactor(gamepad2.right_trigger + (-gamepad2.left_trigger) + gamepad1.right_trigger + (-gamepad1.left_trigger));

            if (gamepad2.a) {
                viperSlideArm.prepareToDropSampleLowBasket();
                claw.prepareToDropSampleLowBasket();
                wheelieBar.close();
                disableAutoClose();
            } else if (gamepad2.b) {
                viperSlideArm.setArmClearBarrier();
                claw.prepareToPickupVerticalSample();
                wheelieBar.close();
                enableAutoClose();
            } else if (gamepad2.x) {
                viperSlideArm.prepareToPickupVerticalSample();
                claw.prepareToPickupVerticalSample();
                wheelieBar.close();
                enableAutoClose();
            } else if (gamepad2.y && !gamepad2.back) {
                viperSlideArm.prepareToDropSampleHighBasket();
                wheelieBar.open();
                claw.prepareToDropSampleHighBasket();
                disableAutoClose();
            } else if (gamepad2.y && gamepad2.back) {
                viperSlideArm.prepareToDropSampleHighBasketBackwards();
                wheelieBar.open();
                claw.prepareToDropSampleHighBasketBackwards();
                disableAutoClose();
            } else if (gamepad2.dpad_left) {
                viperSlideArm.prepareToHangLowSpecimen();
                claw.prepareToHangLowSpecimen();
                wheelieBar.close();
                disableAutoClose();
            } else if (gamepad1.dpad_left) {
                viperSlideArm.park();
                claw.zero();
                wheelieBar.close();
                disableAutoClose();
            } else if (gamepad2.dpad_right && !gamepad2.back) {
                claw.prepareToHangHighSpecimenBackwards();
                viperSlideArm.prepareToHangHighSpecimenBackwards();
                wheelieBar.close();
                disableAutoClose();
            } else if (gamepad2.dpad_right && gamepad2.back) {
                viperSlideArm.prepareToHangHighSpecimen();
                claw.prepareToHangHighSpecimen();
                wheelieBar.close();
                disableAutoClose();
            } else if (gamepad2.dpad_up) {
                viperSlideArm.prepareToPickUpWallSpecimen();
                claw.prepareToPickUpWallSpecimen();
                wheelieBar.close();
                enableAutoClose();
            } else if (gamepad2.dpad_down) {
                viperSlideArm.prepareToPickUpFieldSpecimen();
                claw.prepareToPickUpFieldSpecimen();
                wheelieBar.close();
                enableAutoClose();
            } else if (gamepad1.dpad_up) {
                viperSlideArm.prepareToHang();
                claw.zero();
                wheelieBar.close();
                disableAutoClose();
            } else if (gamepad1.dpad_down) {
                viperSlideArm.setArmWinch();
                wheelieBar.close();
                disableAutoClose();
            } else if (subDriverOp.wasJustPressed(GamepadKeys.Button.RIGHT_STICK_BUTTON)) {
                claw.togglePincher();
                disableAutoClose();
                reEnableAutoCloseAfter(500);
            } else if (subDriverOp.wasJustPressed(GamepadKeys.Button.LEFT_STICK_BUTTON)) {
                claw.toggleWristAngle();
            }
            claw.adjustWristAngle(-gamepad2.left_stick_x);
            claw.adjustElbowAngle(gamepad2.left_stick_y);

            viperSlideArm.adjustArm(-gamepad2.right_stick_y * 3);

            if (gamepad2.right_bumper) {
                viperSlideArm.adjustViperSlidePosition(10);
            } else if (gamepad2.left_bumper) {
                viperSlideArm.adjustViperSlidePosition(-10);
            }
            if (isAutoCloseEnabled()) {
                if (claw.closeIfSampleDetected()) {
                    viperSlideArm.armClearBarrierIfBelow();
                }
            }

            viperSlideArm.execute();

            viperSlideArm.outputTelemetry(telemetry);
            wheelieBar.outputTelemetry(telemetry);
            claw.outputTelemetry(telemetry);
            telemetry.update();
        }


    }

    //FailSafe jajajajaja done by me?
    private void reEnableAutoCloseAfter(long millis) {
        autoClosePincherEnableTime = System.currentTimeMillis() + millis;
        autoClosePincherTimerEnabled = true;
    }

    private boolean isAutoCloseEnabled() {

        if (autoClosePincherTimerEnabled && (System.currentTimeMillis() > autoClosePincherEnableTime)) {
            autoCloseClaw = true;
            autoClosePincherTimerEnabled = false;
        }
        return autoCloseClaw;
    }

    private void enableAutoClose() {
        autoCloseClaw = true;
        autoClosePincherTimerEnabled = false;

    }

    private void disableAutoClose() {
        autoCloseClaw = false;
        autoClosePincherTimerEnabled = false;

    }

    private void allowDriverToFixArmAndSlide() {
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        DcMotor slideMotor = hardwareMap.get(DcMotor.class, "viperSlideMotor");
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        while (!opModeIsActive() && !isStopRequested()) {
            armMotor.setPower(-gamepad2.right_stick_y);
            slideMotor.setPower(gamepad2.left_stick_y * 0.5);
            drive.driveRobotCentric(
                    -driverOp.getLeftX(),
                    -driverOp.getLeftY(),
                    -driverOp.getRightX(),
                    true
            );
        }
        armMotor.setPower(0);
        slideMotor.setPower(0);
    }
}