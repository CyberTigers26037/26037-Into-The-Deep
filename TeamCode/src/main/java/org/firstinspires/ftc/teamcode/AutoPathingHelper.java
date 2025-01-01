package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.RobotConfig;
import org.firstinspires.ftc.teamcode.subassembly.Claw;
import org.firstinspires.ftc.teamcode.subassembly.ViperSlideArm;

@SuppressWarnings("unused")
@TeleOp(name="Autonomous Pathing Helper")
public class AutoPathingHelper extends OpMode {
    private static final double TILE_WIDTH = 23.5;
    private static final double TILE_HEIGHT = 23.5;
    private static final double ROBOT_HEIGHT = 18;

    private MecanumDrive drive;
    private Claw claw;
    private ViperSlideArm viperSlideArm;

    private GamepadEx driverOp;
    private GamepadEx subDriverOp;

    private Localizer localizer;
    private Pose2d currentPose;

    double robotStartingPositionY = 3 * TILE_HEIGHT - ROBOT_HEIGHT / 2;
    double robotStartingPositionX = -TILE_WIDTH * 0.5;
    Pose2d beginningPose = new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270));

    @Override
    public void init() {
        drive = new MecanumDrive(
                new Motor(hardwareMap, "frontLeftMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "frontRightMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "backLeftMotor", Motor.GoBILDA.RPM_312),
                new Motor(hardwareMap, "backRightMotor", Motor.GoBILDA.RPM_312)
        );
        driverOp = new GamepadEx(gamepad1);
        subDriverOp = new GamepadEx(gamepad2);
        localizer = new ThreeDeadWheelLocalizer(hardwareMap, RobotConfig.getInPerTick());
        currentPose = beginningPose;

        claw = new Claw(hardwareMap);
        viperSlideArm = new ViperSlideArm(hardwareMap);
        viperSlideArm.disableArmCompensation();
        claw.pickupSample();
    }

    @Override
    public void loop() {
        driverOp.readButtons();
        subDriverOp.readButtons();

        drive.driveRobotCentric(
                -driverOp.getLeftX(),
                -driverOp.getLeftY(),
                -driverOp.getRightX(),
                true
        );

        armSlideAndClawControl();

        updatePoseEstimate();

        telemetry.addData("Robot Heading (deg)", Math.toDegrees(currentPose.heading.toDouble()));
        telemetry.addData("Robot X", currentPose.position.x);
        telemetry.addData("Robot Y", currentPose.position.y);

        claw.checkIfSampleDetected();
        viperSlideArm.outputTelemetrySimple(telemetry);
        claw.outputTelemetrySimple(telemetry);
        telemetry.update();
    }

    private void armSlideAndClawControl() {
        if (gamepad2.dpad_right && !gamepad2.back) {
            claw.prepareToHangHighSpecimenBackwards();
            viperSlideArm.prepareToHangHighSpecimenBackwards();
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
    }

    private void updatePoseEstimate() {
        Twist2dDual<Time> twist = localizer.update();
        currentPose = currentPose.plus(twist.value());
    }
}
