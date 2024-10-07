package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTestingSpecimens {
    private static final double TILE_WIDTH = -8;
    private static final double TILE_HEIGHT = 23.5;
    private static final double ROBOT_HEIGHT = 18;
    private static final double SAMPLE_HEIGHT = 3.5;
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX = TILE_WIDTH*1.5;
        double robotSamplePickupLocationY = TILE_HEIGHT+(SAMPLE_HEIGHT)/2;
        // Basket drop off
        double robotBasketDeliveryLocationX = 56;
        double robotBasketDeliveryLocationY = 56;
        // bar drop off
        double robotBarDeliveryLocationX = 0;
        double robotBarDeliveryLocationY = 32;
        // yellow samples pick up
        double robotFirstSampleLocationX = 38;
        double robotSecondSampleLocationX = 48;
        double robotThirdSampleLocationX = 58;
        // team specific samples pick up
        double robotFirstTeamSampleLocationX = -38;
        double robotSecondTeamSampleLocationX = -48;
        double robotThirdTeamSampleLocationX = -58;
        // observation zone
        double observationZoneLocationX = -57;
        double observationZoneLocationY = 59;


        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                // Goes to bar and hangs beginning sample sample
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
                // Drops off first sample on the bar
//                .setTangent(Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(robotBarDeliveryLocationX, robotBarDeliveryLocationY, Math.toRadians(270)), Math.toRadians(270))
//                .waitSeconds(1)
                // Picks up second sample into the basket
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotSecondTeamSampleLocationX,robotSamplePickupLocationY,Math.toRadians(180)), 180)
                .waitSeconds(1)
                // Drops off second sample into the observation zone
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(observationZoneLocationX, observationZoneLocationY, Math.toRadians(90)), Math.toRadians(130))
                .waitSeconds(1)
                // Drops off second sample on the bar
//                .setTangent(Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(robotBarDeliveryLocationX, robotBarDeliveryLocationY, Math.toRadians(270)), Math.toRadians(270))
//                .waitSeconds(1)
                // Picks up third sample into the basket (this might be wrong bc i changed it to 45 last second )
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotThirdTeamSampleLocationX,robotSamplePickupLocationY,Math.toRadians(180)), 180)
                .waitSeconds(1)
                // Drops off third sample into the observation zone
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(observationZoneLocationX, observationZoneLocationY, Math.toRadians(90)), Math.toRadians(130))
                .waitSeconds(1)
                // Drops off third sample on the bar
//                .setTangent(Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(robotBarDeliveryLocationX, robotBarDeliveryLocationY, Math.toRadians(270)), Math.toRadians(270))
//                .waitSeconds(1)
                // Parks robot into observation zone (spins for some reason must fix once comes back)
//                .setTangent(Math.toRadians(180))
//                .splineToLinearHeading(new Pose2d(observationZoneLocationX, observationZoneLocationY, Math.toRadians(270)), Math.toRadians(180))
//                        .waitSeconds(1)
                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}