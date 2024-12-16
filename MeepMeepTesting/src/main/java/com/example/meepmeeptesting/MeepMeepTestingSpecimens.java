package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTestingSpecimens {
    private static final double TILE_WIDTH = 23.5;
    private static final double TILE_HEIGHT = 23.5;
    private static final double ROBOT_HEIGHT = 18;
    private static final double ROBOT_WIDTH = 18;
    private static final double SAMPLE_HEIGHT = 3.5;
    // I know I should use it but i don't know how to lol
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX = -ROBOT_WIDTH/2;
        double robotSamplePickupLocationY = 40;
        double robotFirstTeamSampleLocationX = -50;
        double robotSecondTeamSampleLocationX = -60;
        double robotThirdTeamSampleLocationX = -54;
        double robotSpecimenDropX = -57;
        double robotSpecimenDropY = 50;
        double robotThirdSampleLocationY = 26;
        double robotObservationZoneX = -TILE_WIDTH*0.5 + 2;
        double robotObservationY = 34.5;


        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                // Goes to bar and hangs beginning sample sample
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotObservationZoneX,robotObservationY,Math.toRadians(270)),Math.toRadians(270))
                .waitSeconds(1)
                // Picks up first team sample off of the field
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotFirstTeamSampleLocationX,robotSamplePickupLocationY,Math.toRadians(270)),Math.toRadians(220))
                .waitSeconds(1)
                // Drops off first sample into the observation zone
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(robotSpecimenDropX, robotSpecimenDropY, Math.toRadians(90)), Math.toRadians(130))
                .waitSeconds(1)
                // Picks up second sample
                .turn(Math.toRadians(-90))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotSecondTeamSampleLocationX,robotSamplePickupLocationY,Math.toRadians(270)),Math.toRadians(270))
                .waitSeconds(1)
                // Drops off second sample into the observation zone4
                .turn(Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(robotSpecimenDropX, robotSpecimenDropY, Math.toRadians(90)), Math.toRadians(130))
                .waitSeconds(1)
                // Picks up third sample into the basket (this might be wrong bc i changed it to 45 last second )
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotThirdTeamSampleLocationX,robotThirdSampleLocationY,Math.toRadians(180)), 180)
                .waitSeconds(1)
                // Drops off third sample into the observation zone
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(robotSpecimenDropX, robotSpecimenDropY, Math.toRadians(90)), Math.toRadians(130))
                .waitSeconds(1)

                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}