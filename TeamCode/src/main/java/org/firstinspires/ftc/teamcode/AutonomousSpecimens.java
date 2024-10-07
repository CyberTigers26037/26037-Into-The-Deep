package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AutonomousSpecimens {

    private static final double TILE_WIDTH = 23.5;
    private static final double TILE_HEIGHT = 23.5;
    private static final double ROBOT_HEIGHT = 18;
    private static final double SAMPLE_HEIGHT = 3.5;
    private MecanumDrive drive;
    private double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
    private double robotStartingPositionX = -TILE_WIDTH*0.5;

    public AutonomousSpecimens(HardwareMap hardwareMap) {
        Pose2d beginningPose = new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270));
        drive = new MecanumDrive(hardwareMap,beginningPose);
    }
    public void runAutonomous() {

        double robotSamplePickupLocationY = TILE_HEIGHT+(SAMPLE_HEIGHT)/2;
        double robotFirstTeamSampleLocationX = -38;
        double robotSecondTeamSampleLocationX = -48;
        double robotThirdTeamSampleLocationX = -58;
        double observationZoneLocationX = -57;
        double observationZoneLocationY = 57;


        Actions.runBlocking(drive.actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                // Goes to bar and hangs beginning sample
                .splineTo(new Vector2d(0,32), Math.toRadians(270))
                .waitSeconds(1)
                // Picks up first team sample off of the field
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(robotFirstTeamSampleLocationX,robotSamplePickupLocationY,Math.toRadians(180)), 80)
                .waitSeconds(1)
                // Drops off first sample into the observation zone
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(observationZoneLocationX, observationZoneLocationY, Math.toRadians(90)), Math.toRadians(130))
                .waitSeconds(1)
                // Picks up second sample into the basket
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotSecondTeamSampleLocationX,robotSamplePickupLocationY,Math.toRadians(180)), 180)
                .waitSeconds(1)
                // Drops off second sample into the observation zone
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(observationZoneLocationX, observationZoneLocationY, Math.toRadians(90)), Math.toRadians(130))
                .waitSeconds(1)
                // Picks up third sample into the basket (this might be wrong bc i changed it to 45 last second )
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotThirdTeamSampleLocationX,robotSamplePickupLocationY,Math.toRadians(180)), 180)
                .waitSeconds(1)
                // Drops off third sample into the observation zone
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(observationZoneLocationX, observationZoneLocationY, Math.toRadians(90)), Math.toRadians(130))
                .waitSeconds(1)
                .build());

    }
}