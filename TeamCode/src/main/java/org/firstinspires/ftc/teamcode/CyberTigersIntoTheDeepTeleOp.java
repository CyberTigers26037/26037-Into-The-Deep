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
import org.firstinspires.ftc.teamcode.subassembly.Claw;
import org.firstinspires.ftc.teamcode.subassembly.ViperSlideArm;

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
     Expansion hub
        Servos
            Port 1 : Pincher
            Port 2 : Wrist
            Port 3 : Elbow
        Motors
            Port 0 : armMotor
            Port 1 : viperSlideMotor
     */
    @Override
    public void runOpMode() {
        /* Define and Initialize Motors */
        com.arcrobotics.ftclib.drivebase.MecanumDrive drive = new MecanumDrive(
                new Motor(hardwareMap, "frontLeftMotor",  Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "frontRightMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "backLeftMotor",   Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "backRightMotor",  Motor.GoBILDA.RPM_312)
        );
        GamepadEx driverOp = new GamepadEx(gamepad1);
        GamepadEx subDriverOp = new GamepadEx(gamepad2);

        Claw claw = new Claw(hardwareMap);
        ViperSlideArm viperSlideArm = new ViperSlideArm(hardwareMap);
        claw.zero();
        /* Send telemetry message to signify robot waiting */
        telemetry.addLine("Robot Ready.");
        telemetry.update();

        /* Wait for the game driver to press play */
        waitForStart();

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
            } else if (gamepad2.b) {
                viperSlideArm.setArmClearBarrier();
                claw.prepareToPickupVerticalSample();
            } else if (gamepad2.x) {
                viperSlideArm.prepareToPickupVerticalSample();
                claw.prepareToPickupVerticalSample();
            } else if (gamepad2.y) {
                viperSlideArm.prepareToDropSampleHighBasket();
                claw.prepareToDropSampleHighBasket();
            } else if (gamepad2.dpad_left) {
                viperSlideArm.prepareToHangLowSpecimen();
                claw.prepareToHangLowSpecimen();
            } else if (gamepad1.dpad_left) {
                viperSlideArm.park();
                claw.zero();
            } else if (gamepad2.dpad_right) {
                viperSlideArm.prepareToHangHighSpecimen();
                claw.prepareToHangHighSpecimen();
            } else if (gamepad2.dpad_up) {
                viperSlideArm.prepareToPickUpWallSpecimen();
                claw.prepareToPickUpWallSpecimen();
            } else if (gamepad2.dpad_down) {
                viperSlideArm.prepareToPickUpFieldSpecimen();
                claw.prepareToPickUpFieldSpecimen();
            } else if (gamepad1.dpad_up) {
                viperSlideArm.prepareToHang();
                claw.zero();
            } else if (gamepad1.dpad_down) {
                viperSlideArm.setArmWinch();
            } else if (subDriverOp.wasJustPressed(GamepadKeys.Button.RIGHT_STICK_BUTTON)) {
                claw.togglePincher();
            } else if (subDriverOp.wasJustPressed(GamepadKeys.Button.LEFT_STICK_BUTTON)) {
                claw.toggleWristAngle();
            }
            claw.adjustWristAngle(-gamepad2.left_stick_x);
            claw.adjustElbowAngle(gamepad2.left_stick_y);

            viperSlideArm.adjustArm(-gamepad2.right_stick_y * 0.1);

            if (gamepad2.right_bumper) {
                viperSlideArm.adjustViperSlidePosition(10);
            } else if (gamepad2.left_bumper) {
                viperSlideArm.adjustViperSlidePosition(-10);
            }

            viperSlideArm.execute();

            viperSlideArm.outputTelemetry(telemetry);
            claw.outputTelemetry(telemetry);
            telemetry.update();
        }
    }
}