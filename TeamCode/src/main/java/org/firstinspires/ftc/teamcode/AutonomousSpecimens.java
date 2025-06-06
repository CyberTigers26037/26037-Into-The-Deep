package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subassembly.Claw;
import org.firstinspires.ftc.teamcode.subassembly.ViperSlideArm;

public class AutonomousSpecimens extends AutonomousOpMode {
    private static final double TILE_WIDTH   = 23.5;
    private static final double TILE_HEIGHT  = 23.5;
    private static final double ROBOT_HEIGHT = 18;
    private static final double ROBOT_WIDTH  = 18;
    private final PinpointDrive drive;
    private final ViperSlideArm viperSlideArm;
    private final Claw claw;

    public AutonomousSpecimens(HardwareMap hardwareMap) {
        double robotStartingPositionY = 3 * TILE_HEIGHT - ROBOT_HEIGHT / 2;
        double robotStartingPositionX = -TILE_WIDTH * 0.5;
        Pose2d beginningPose = new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270));
        drive = new PinpointDrive(hardwareMap,beginningPose);
        viperSlideArm = new ViperSlideArm(hardwareMap);
        viperSlideArm.disableArmCompensation();
        claw = new Claw(hardwareMap);
    }

    private void prepareToPickUpWallLilBitHigher() {
        viperSlideArm.prepareToPickUpWallSpecimenLilBitHigher();
        claw.prepareToPickUpWallSpecimen();
        viperSlideArm.execute();
    }

    private void retractViperSlide() {
        viperSlideArm.retractViperSlide();
        viperSlideArm.execute();
    }

    private void raisedArm() {
        viperSlideArm.raiseArm();
        viperSlideArm.execute();
        claw.wristStraight();
    }

    private void parkRobot() {
        viperSlideArm.park();
        viperSlideArm.execute();
        claw.zero();
    }

    @SuppressWarnings("SameParameterValue")
    private void pickUpFirstSpecimen(double armSpeed, double slideSpeed) {
    viperSlideArm.pickUpFirstSpecimen();
    viperSlideArm.execute(armSpeed,slideSpeed);
    claw.pickUpFirstSampleAuto();
    }

    private void prepareToHangHighSpecimenBackwards() {
        viperSlideArm.prepareToHangHighSpecimenBackwards();
        viperSlideArm.execute();
        claw.prepareToHangHighSpecimenBackwards();
    }

    private void prepareToHangThirdSpecimen(){
        viperSlideArm.PrepareToHangThirdSpecimen();
        viperSlideArm.execute();
        claw.prepareToHangHighSpecimenBackwards();
    }

    private void retractViperSlideNathan() {
        viperSlideArm.retractViperSlideNathan();
        viperSlideArm.execute();
        claw.pickupSample();
    }

    private void prepareToHangHighSpecimenBackwardsForAuto() {
        viperSlideArm.prepareToHangHighSpecimenBackwardsForAuto();
        viperSlideArm.execute();
        claw.prepareToHangHighSpecimenBackwards();
    }

    private void sweepingArm() {
        viperSlideArm.sweepingArm();
        viperSlideArm.execute();
        claw.sweep();
    }

    private void sweepArmSecond() {
        viperSlideArm.sweepSecondThingy();
        viperSlideArm.execute();
        claw.sweep();
    }
    private void waitForViperSlideArmToBeWithinRange(double slideMm, double armDegrees){
        while(true){
            if (viperSlideArm.isSlideAndArmWithinRange(slideMm, armDegrees)) break;
        }
    }

    private void waitForViperSlideNotBusy(){
        while(true){
            if (!viperSlideArm.isBusy()) break;
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public void runAutonomous() {
        double robotStartingPositionY = 3 * TILE_HEIGHT - ROBOT_HEIGHT / 2;
        double robotStartingPositionX = -ROBOT_WIDTH / 2;
        double robotObservationHangSpecimenY = 33;
        double robotPivotPickupY = 41;
        double robotPivotPickupX = -34;
        double sigmaPickUpX = -49;
        double sigmaPickUpY = 54;
        double observationZoneX = -51;
        double observationZoneY = 58;
        double fourthDropOffX   = -43;
        double fourthDropOffY   = 58;
        double positioningHelperY = 50;
        double positioningHelperX = 8;
        double thirdSpecimenDropOffX = 12;
        double thirdSpecimenDropOffY = 50;
        double fourthSpecimenDropOffX = 1;
        double fourthSpecimenDroppOffY = 50;
        double sigmaSecondPickUpX = -49;
        double sigmaSecondPickUpY = 51;
        double robotSweepPositionY = 40;
        double robotSweepPositionX = -37;



        // #Otylia Drops off pre-loaded specimen
        claw.pickupSample();
        prepareToHangHighSpecimenBackwards();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(robotObservationHangSpecimenY)
                .build());
        claw.dropSample();
        retractViperSlide();
        drive.setExtraCorrectionTime(0);
        // Goes to push first field sample in
        Actions.runBlocking(drive.actionBuilder(drive.pose)
               .setTangent(Math.toRadians(90))
               .splineToLinearHeading(new Pose2d(-46,38, Math.toRadians(270)), Math.toRadians(270))
               .setTangent(Math.toRadians(270))
               .splineToConstantHeading(new Vector2d(-46,18), Math.toRadians(270)) // line
               .setTangent(Math.toRadians(270))
               .splineToLinearHeading(new Pose2d(-62,18, Math.toRadians(270)), Math.toRadians(90))  // curve
                .build());
        drive.setExtraCorrectionTime(0);
        // Pushes first field sample in then backs up
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .lineToY(48)
              //.splineToConstantHeading(new Vector2d(-62,48), Math.toRadians(270)) // line
            .lineToY(16)
              //.splineToConstantHeading(new Vector2d(-62,16), Math.toRadians(-90)) // line
              .build());
        drive.setExtraCorrectionTime(0);
        // Pushes second field sample in then backs up
      Actions.runBlocking(drive.actionBuilder(drive.pose)
              .setTangent(Math.toRadians(270))
              .splineToLinearHeading(new Pose2d(-62,17, Math.toRadians(270)), Math.toRadians(90)) // line
              //.setTangent(Math.toRadians(90))
              .splineToConstantHeading(new Vector2d(-62,48), Math.toRadians(270)) // line
              .splineToConstantHeading(new Vector2d(-62,20), Math.toRadians(270))
                       .build());
        // Pushes third field sample in then turns around to grab a human player specimen
       Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(-180))  // Turns right (clockwise) before pushing the third sample in
                .splineToLinearHeading(new Pose2d(-60,20, Math.toRadians(0)), Math.toRadians(-180)) // Turns clockwise to face right
              // Face upwards to push the third sample in
                .splineToLinearHeading(new Pose2d(-60, 20, Math.toRadians(90)), Math.toRadians(90)) // Turn upwards
                .splineToConstantHeading(new Vector2d(-60,20), Math.toRadians(270))
              // Line
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-60,48), Math.toRadians(270)) // line
                        .build());


                // #5-spec... fail....Pushes third field sample in then turns around to grab a human player specimen
       /*Actions.runBlocking(drive.actionBuilder(drive.pose)
               .splineToLinearHeading(new Pose2d(-65,13, Math.toRadians(90)), Math.toRadians(270))
               .splineToConstantHeading(new Vector2d(-65, 53), Math.toRadians(270))
               /* .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-65,13, Math.toRadians(90)), Math.toRadians(270))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-65,53, Math.toRadians(270)), Math.toRadians(90))*/
                //.build());
        // Goes to drop off human player specimen
        /*Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(-270))
                .splineToLinearHeading(new Pose2d(-10, 30, Math.toRadians(-90)), Math.toRadians(-90))
                .build());*/




        // Nathan Code
        /*claw.pickupSample();
        prepareToHangHighSpecimenBackwards();
        drive.setExtraCorrectionTime(0);
        // Goes to bar and hangs preloaded sample
        Actions.runBlocking(drive.actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
            .lineToY(robotObservationHangSpecimenY)
            .build());
        drive.setExtraCorrectionTimeDefault();
        claw.dropSample();
        retractViperSlide();
        waitForViperSlideArmToBeWithinRange(20,2);
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(robotSweepPositionX, robotSweepPositionY, Math.toRadians(230)), Math.toRadians(180))
                        .build());
        drive.setExtraCorrectionTimeDefault();
        sweepingArm();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .turn(Math.toRadians(-160))
                        .build());
        raisedArm();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(160))
                .build());
        sweepArmSecond();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .strafeTo(new Vector2d(-44, 41))
                        .build());
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(-160))
                .build());
        //pick up second specimen
        prepareToPickUpWallLilBitHigher();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(sigmaPickUpX,sigmaPickUpY,Math.toRadians(90)),Math.toRadians(90))
                .build());
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(57)
                .build());
        sleep(200);
        claw.pickupSample();
        //goes to hang second specimen
        prepareToHangHighSpecimenBackwards();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(315))
                .splineToLinearHeading(new Pose2d(positioningHelperX,positioningHelperY,Math.toRadians(270)),Math.toRadians(0))
                .build());
        drive.setExtraCorrectionTimeDefault();
        sleep(100);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(31)
                .build());
        drive.setExtraCorrectionTimeDefault();
        claw.dropSample();
        retractViperSlide();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(48)
                .build());
        drive.setExtraCorrectionTimeDefault();
        //going to get the third specimen
        waitForViperSlideArmToBeWithinRange(10,5);
        prepareToPickUpWallLilBitHigher();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .turn(Math.toRadians(-20))
            .build());
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(sigmaSecondPickUpX,sigmaSecondPickUpY,Math.toRadians(90)),Math.toRadians(90))
                .build());
        drive.setExtraCorrectionTimeDefault();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(57)
                .build());
        drive.setExtraCorrectionTimeDefault();
        sleep(250);
        claw.pickupSample();
        //goes to hang third specimen
        sleep(200);
        raisedArm();
        prepareToHangThirdSpecimen();
        sleep(200);
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(315))
                .splineToLinearHeading(new Pose2d(thirdSpecimenDropOffX,thirdSpecimenDropOffY,Math.toRadians(270)),Math.toRadians(315))
                .build());
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(30)
                .build());
        claw.dropSample();
        //pick up fourth specimen
        waitForViperSlideArmToBeWithinRange(10,5);
        prepareToPickUpWallLilBitHigher();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(-20))
                .build());
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(sigmaSecondPickUpX,sigmaSecondPickUpY,Math.toRadians(90)),Math.toRadians(90))
                .build());
        drive.setExtraCorrectionTimeDefault();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(57)
                .build());
        drive.setExtraCorrectionTimeDefault();
        sleep(250);
        claw.pickupSample();
        //hang fourth
        sleep(200);
        raisedArm();
        prepareToHangThirdSpecimen();
        sleep(200);
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(315))
                .splineToLinearHeading(new Pose2d(fourthSpecimenDropOffX,fourthDropOffY,Math.toRadians(270)),Math.toRadians(315))
                .build());
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(30)
                .build());
        claw.dropSample();
        //park robot
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(50)
                .build());
        parkRobot();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(135)
                .splineToLinearHeading(new Pose2d(observationZoneX,observationZoneY,Math.toRadians(270)),Math.toRadians(135))
                .build());
        drive.setExtraCorrectionTimeDefault();*/


        //Picks up first sample
       /* Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(Math.toRadians(90))
            .splineToLinearHeading(new Pose2d(robotPivotPickupX,robotPivotPickupY,Math.toRadians(214)),Math.toRadians(220))
            .build());
        pickUpFirstSpecimen(0.5, 3);
        waitForViperSlideArmToBeWithinRange(20,3.5);
        claw.pickupSample();
        sleep(150);
        //waitForViperSlideArmToBeWithinRange(10,5);
        //drop off first sample in observation zone
        retractViperSlideNathan();
        drive.setExtraCorrectionTime(0)  ;
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(Math.toRadians(90))
            .splineToLinearHeading(new Pose2d(sigmaPickUpX,sigmaPickUpY,Math.toRadians(90)),Math.toRadians(90))
            .build());
        drive.setExtraCorrectionTimeDefault();
        sleep(100);
        claw.dropSample();
        // picks up second specimen
        prepareToPickUpWallLilBitHigher();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .lineToY(57)
            .build());
        drive.setExtraCorrectionTimeDefault();
        sleep(250);
        claw.pickupSample();
        sleep(100);
        raisedArm();
        waitForViperSlideArmToBeWithinRange(10,5);
        //goes to hang the second specimen
        prepareToHangHighSpecimenBackwardsForAuto();
        sleep(200);
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(Math.toRadians(315))
            .splineToLinearHeading(new Pose2d(positioningHelperX,positioningHelperY,Math.toRadians(270)),Math.toRadians(0))
            .build());
        drive.setExtraCorrectionTimeDefault();
        sleep(100);
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .lineToY(32.5)
            .build());
        drive.setExtraCorrectionTimeDefault();
        claw.dropSample();
        retractViperSlide();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .lineToY(48)
            .build());
        drive.setExtraCorrectionTimeDefault();
        //going to get the third specimen
        waitForViperSlideArmToBeWithinRange(10,5);
        prepareToPickUpWallLilBitHigher();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(Math.toRadians(90))
            .splineToLinearHeading(new Pose2d(sigmaSecondPickUpX,sigmaSecondPickUpY,Math.toRadians(90)),Math.toRadians(90))
            .build());
        drive.setExtraCorrectionTimeDefault();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .lineToY(57)
            .build());
        drive.setExtraCorrectionTimeDefault();
        sleep(250);
        claw.pickupSample();
        //goes to hang third specimen
        sleep(200);
        raisedArm();
        prepareToHangThirdSpecimen();
        sleep(200);
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(Math.toRadians(315))
            .splineToLinearHeading(new Pose2d(thirdSpecimenDropOffX,thirdSpecimenDropOffY,Math.toRadians(270)),Math.toRadians(315))
            .build());
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .lineToY(32.5)
            .build());
        drive.setExtraCorrectionTimeDefault();
        sleep(100);
        claw.dropSample();
        //park robot
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .lineToY(50)
            .build());
        parkRobot();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(135)
            .splineToLinearHeading(new Pose2d(observationZoneX,observationZoneY,Math.toRadians(270)),Math.toRadians(135))
            .build());
        drive.setExtraCorrectionTimeDefault();
*/

    }
}