package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
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
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                // Goes to basket and drops off first sample
                .splineTo(new Vector2d(56,56), Math.toRadians(45))
                .waitSeconds(1)
                // Picks up first sample off of the field
                .setTangent(Math.toRadians(215))
                .splineToLinearHeading(new Pose2d(38,robotSamplePickupLocationY,Math.toRadians(0)), 0)
                .waitSeconds(1)
                // Drops off first sample into the basket
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationX,robotBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(45))
                .waitSeconds(1)
                // Picks up second sample off of the field
                .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationX,robotBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(45))
                // Drops off second sample into the basket

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}