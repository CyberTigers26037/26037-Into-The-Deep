package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTestingBaskets {
    private static final double TILE_WIDTH = 23.5;
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
        double robotBasketDeliveryLocationX = 56;
        double robotBasketDeliveryLocationY = 56;
        double robotFirstSampleLocationX = 38;
        double robotSecondSampleLocationX = 48;
        double robotThirdSampleLocationX = 58;
        double observationZoneLocationX = -34;
        double observationZoneLocationY = 58;


        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                // Goes to basket and drops off first sample
                .splineTo(new Vector2d(56,56), Math.toRadians(45))
                .waitSeconds(1)
                // Picks up first sample off of the field
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotFirstSampleLocationX,robotSamplePickupLocationY,Math.toRadians(0)), 0)
                .waitSeconds(1)
                // Drops off first sample into the basket
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationX,robotBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(45))
                .waitSeconds(1)
                // Picks up second sample into the basket
                .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(robotSecondSampleLocationX,robotSamplePickupLocationY,Math.toRadians(0)), Math.toRadians(0))
                .waitSeconds(1)
                // Drops off second sample into the field
                .setTangent(Math.toRadians(150))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationX, robotBasketDeliveryLocationY, Math.toRadians(45)), Math.toRadians(45))
                .waitSeconds(1)
                // Picks up third sample into the field ( this might be wrong bc i changed it to 45 last second )
                .setTangent(Math.toRadians(215))
                .splineToLinearHeading(new Pose2d(robotThirdSampleLocationX,robotSamplePickupLocationY, Math.toRadians(0)), Math.toRadians(0))
                .waitSeconds(1)
                // Drops off third sample into the basket
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationX, robotBasketDeliveryLocationY, Math.toRadians(45)), Math.toRadians(45))
                .waitSeconds(1)
                // Parks robot into observation zone (spins for some reason must fix once comes back)
                        .setTangent(Math.toRadians(200))
                        .splineToLinearHeading(new Pose2d(observationZoneLocationX, observationZoneLocationY, Math.toRadians(0)), Math.toRadians(180))
                                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}