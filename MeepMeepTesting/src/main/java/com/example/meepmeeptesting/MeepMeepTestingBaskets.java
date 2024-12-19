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
        double robotStartingPositionX = TILE_WIDTH*1.5 - 3;
        double robotSamplePickupLocationY = 40;
        double robotThirdSamplePickupLocationY = 25.25;
        double robotBasketDeliveryLocationX = 60.5;
        double robotBasketDeliveryLocationY = 54.5;
        double robotFirstSampleLocationX = 48;
        double robotSecondSampleLocationX = 58;
        double robotThirdSampleLocationX = 56;
        double observationZoneLocationX = 34;
        double observationZoneLocationY = 10;
        double robotBasketDeliveryOneLocationX = 62.5;
        double robotBasketDeliveryOneLocationY = 44;

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                // Goes to basket and drops off first sample
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryOneLocationX,robotBasketDeliveryOneLocationY,Math.toRadians(230)),Math.toRadians(45))
                .waitSeconds(1)
                // Picks up first sample off of the field
                .setTangent(Math.toRadians(230))
                .splineToLinearHeading(new Pose2d(robotFirstSampleLocationX,robotSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(270))
                .waitSeconds(1)
                // Drops off first sample into the basket
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryOneLocationX,robotBasketDeliveryOneLocationY,Math.toRadians(230)),Math.toRadians(45))
                .waitSeconds(1)
                // Picks up second sample into the basket
                .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(robotSecondSampleLocationX,robotSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(0))
                .waitSeconds(1)
                // Drops off second sample into the field
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryOneLocationX, robotBasketDeliveryOneLocationY, Math.toRadians(230)), Math.toRadians(45))
                .waitSeconds(1)
                // Picks up third sample into the field ( this might be wrong bc i changed it to 45 last second )
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotThirdSampleLocationX,robotThirdSamplePickupLocationY, Math.toRadians(0)), Math.toRadians(0))
                .waitSeconds(1)
                // Drops off third sample into the basket
                .setTangent(Math.toRadians(100))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryOneLocationX, robotBasketDeliveryOneLocationY, Math.toRadians(230)), Math.toRadians(100))
                .waitSeconds(1)
                // Parks robot into observation zone (spins for some reason must fix once comes back)
                        .setTangent(Math.toRadians(235))
                        .splineToLinearHeading(new Pose2d(observationZoneLocationX, observationZoneLocationY, Math.toRadians(180)), Math.toRadians(180))
                                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}