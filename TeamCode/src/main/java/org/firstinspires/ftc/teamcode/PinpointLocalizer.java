package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.FlightRecorder;
import com.acmerobotics.roadrunner.ftc.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.config.RobotConfig;

@Config
public class PinpointLocalizer {
    public static class Params {
        public String pinpointDeviceName = "pinpoint";

        public double xOffset = RobotConfig.getxPinpointOffsetMM();
        public double yOffset = RobotConfig.getyPinpointOffsetMM();
        public double encoderResolution = RobotConfig.getEncoderPinpointResolution();

        public GoBildaPinpointDriver.EncoderDirection xDirection = RobotConfig.getxPinpointDirection();
        public GoBildaPinpointDriver.EncoderDirection yDirection = RobotConfig.getyPinpointDirection();
    }

    public static Params PARAMS = new Params();

    private Pose2d pose;
    public GoBildaPinpointDriver pinpoint;

    public PinpointLocalizer(HardwareMap hardwareMap, Pose2d pose) {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, PARAMS.pinpointDeviceName);

        pinpoint.setOffsets(PARAMS.xOffset, PARAMS.yOffset);

        pinpoint.setEncoderResolution(PARAMS.encoderResolution);

        pinpoint.setEncoderDirections(PARAMS.xDirection, PARAMS.yDirection);

        pinpoint.resetPosAndIMU();
        // wait for pinpoint to finish calibrating
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        pinpoint.setPosition(convert(pose));

        FlightRecorder.write("PINPOINT_PARAMS", PARAMS);

        this.pose = pose;
    }

    public void setPose(Pose2d pose) {
        this.pose = pose;
        pinpoint.setPosition(convert(pose));
    }

    public Pose2d getPose() { return pose; }

    public PoseVelocity2d update() {
        pinpoint.update();
        pose = convert(pinpoint.getPosition());

        Pose2D velocity = pinpoint.getVelocity();
        return new PoseVelocity2d(new Vector2d(velocity.getX(DistanceUnit.INCH), velocity.getY(DistanceUnit.INCH)), velocity.getHeading(AngleUnit.RADIANS));
    }

    private Pose2d convert(Pose2D pose2d) {
        return new Pose2d(pose2d.getX(DistanceUnit.INCH), pose2d.getY(DistanceUnit.INCH), pose2d.getHeading(AngleUnit.RADIANS));
    }

    private Pose2D convert(Pose2d pose2d) {
        return new Pose2D(DistanceUnit.INCH, pose2d.position.x, pose2d.position.y, AngleUnit.RADIANS, pose2d.heading.toDouble());
    }
}
