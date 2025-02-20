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
        double robotObservationHangSpecimenY = 32;


        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-10, robotStartingPositionY, Math.toRadians(-90)))
                .lineToY(robotObservationHangSpecimenY)
        // Goes to push first field sample in
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-46,38, Math.toRadians(270)), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-46,18), Math.toRadians(270)) // line
                .splineToSplineHeading(new Pose2d(-62,18, Math.toRadians(90)), Math.toRadians(90))
               // .splineToLinearHeading(new Pose2d(-62,18, Math.toRadians(270)), Math.toRadians(90))  // Curve to the left
        // Pushes first field sample in then backs up
                .splineToSplineHeading(new Pose2d(-62,48, Math.toRadians(90)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-62,18, Math.toRadians(90)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-62,18, Math.toRadians(90)), Math.toRadians(90))
              // .setTangent(Math.toRadians(270))
                //.splineToConstantHeading(new Vector2d(-62,48), Math.toRadians(270))
        // Pushes second field sample in then backs up
                /*.splineToLinearHeading(new Pose2d(-52,50, Math.toRadians(90)), Math.toRadians(90))  // Curve to the left

                // somethin
                /*.setTangent(Math.toRadians(-180))  // #5spec Turns right (clockwise) before pushing the third sample in
                .splineToLinearHeading(new Pose2d(-60,20, Math.toRadians(0)), Math.toRadians(-180)) // Turns clockwise to face right
                // Face upwards to push the third sample in
                .splineToLinearHeading(new Pose2d(-60, 20, Math.toRadians(90)), Math.toRadians(90)) // Turn upwards
                .splineToConstantHeading(new Vector2d(-60,20), Math.toRadians(-90))
                // Line
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-60,48), Math.toRadians(-90))


                //.splineToConstantHeading(new Vector2d(-60, 20), Math.toRadians(-90))
                //.splineToLinearHeading(new Pose2d(-60,20, Math.toRadians(90)), Math.toRadians(-180))
                //.splineToConstantHeading(new Vector2d(-60, 20), Math.toRadians(90))

                //.setTangent(Math.toRadians(270))
                //.splineToConstantHeading(new Vector2d(-60, 53), Math.toRadians(270))
                //.splineToLinearHeading(new Pose2d(-65,43, Math.toRadians(270)), Math.toRadians(90))

                // Goes to drop off human player specimen
                /*.setTangent(Math.toRadians(-270))
                .splineToLinearHeading(new Pose2d(-10, 30, Math.toRadians(-90)), Math.toRadians(-90))*/


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