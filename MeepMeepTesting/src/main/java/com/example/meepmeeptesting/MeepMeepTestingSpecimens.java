package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepTestingSpecimens {
    private static final double TILE_WIDTH = 23.5;
    private static final double TILE_HEIGHT = 23.5;
    private static final double ROBOT_HEIGHT = 18;

    // I know I should use it but i don't know how to lol
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotObservationZoneX  = -TILE_WIDTH*0.5 + 2;
        double robotObservationY      = 34.5;
        double endHeading = Math.toRadians(270);


                myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-10, robotStartingPositionY, Math.toRadians(-90)))
                // Goes to bar and hangs
                        .lineToY(33)
                        .setTangent(90)
                        .splineToLinearHeading(new Pose2d(-34,37, Math.toRadians(220)), Math.toRadians(180))
                                        .turn(Math.toRadians(-90))
                // Goes to push first field sample off of the field
                        //.setTangent(Math.toRadians(90))
                        //.splineToLinearHeading(new Pose2d(-47,38, Math.toRadians(270)), Math.toRadians(270))
                        //.setTangent(Math.toRadians(270))
                        //.splineToConstantHeading(new Vector2d(-47,18), Math.toRadians(-90)) // line
                        //.setTangent(Math.toRadians(270))
                        //.splineToLinearHeading(new Pose2d(-60,18, Math.toRadians(270)), Math.toRadians(90))
                        // Pushes first field sample in then backs up
                        /*.setTangent(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-60,48), Math.toRadians(270))
                        .setTangent(Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(-60,16), Math.toRadians(270))
                        // Pushes second field sample in then backs up
                        /*.setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(-63,14, Math.toRadians(270)), Math.toRadians(90))
                        //                                .lineToY(45)
                        .setTangent(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-57,48), Math.toRadians(-90))
                        //                                .lineToY(16)
                        .splineToConstantHeading(new Vector2d(-63,16), Math.toRadians(-90))*/
                        // Pushes third field sample in then turns around to grab human player specimen
                      /*  .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(-62,17, Math.toRadians(90)), Math.toRadians(270))
                        .lineToY(53)
                        // Goes to drop off human player specimen
                        .setTangent(Math.toRadians(-270))
                        .splineToLinearHeading(new Pose2d(-10, 30, Math.toRadians(-90)), Math.toRadians(-90))
*/

                      /*old code
                        //.setTangent(Math.toRadians(90))
                        //.splineTo(new Vector2d(-50,56), Math.toRadians(-90)) // pick up speci p1
                        //.splineToConstantHeading(new Vector2d(-50,60), Math.toRadians(-90)) // straight line p2, pick up speci p2
                        //.setTangent(Math.toRadians(90)) // tangent drop off speci
                       //  .splineTo(new Vector2d(-10,30), Math.toRadians(-90)) // speci drop off
                        // .splineTo(new Vector2d())


                        //.splineToLinearHeading(new Pose2d(-62,51,Math.toRadians(90)), Math.toRadians(90))
                        //.splineTo(new Vector2d(-62,65), Math.toRadians(90)) // straight line p2, should grab hp speci
                        //.splineToConstantHeading(new Vector2d(-62,21), Math.toRadians(-270)) // curve
                        //.splineToLinearHeading(new Pose2d(-62,21,Math.toRadians(-270)), Math.toRadians(90)) // curve
*/

                        .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}