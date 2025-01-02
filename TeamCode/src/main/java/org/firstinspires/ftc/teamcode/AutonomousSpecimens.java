package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subassembly.Claw;
import org.firstinspires.ftc.teamcode.subassembly.ViperSlideArm;

public class AutonomousSpecimens {

    private static final double TILE_WIDTH = 23.5;
    private static final double TILE_HEIGHT = 23.5;
    private static final double ROBOT_HEIGHT = 18;
    private static final double ROBOT_WIDTH = 18;
    private final MecanumDrive drive;
    private final ViperSlideArm viperSlideArm;
    private final Claw claw;
    private final Telemetry telemetry;

    public AutonomousSpecimens(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        double robotStartingPositionY = 3 * TILE_HEIGHT - ROBOT_HEIGHT / 2;
        double robotStartingPositionX = -TILE_WIDTH * 0.5;
        Pose2d beginningPose = new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270));
        drive = new MecanumDrive(hardwareMap,beginningPose);
        viperSlideArm = new ViperSlideArm(hardwareMap);
        viperSlideArm.disableArmCompensation();
        claw = new Claw(hardwareMap);
    }
    private void prepareToHangSpecimenHighChamber(){
        viperSlideArm.prepareToHangHighSpecimen();
        claw.prepareToHangHighSpecimen();
        viperSlideArm.execute();
    }
    private void prepareToPickUpWallLilBitHigher() {
        viperSlideArm.prepareToPickUpWallSpecimenLilBitHigher();
        claw.prepareToPickUpWallSpecimen();
        viperSlideArm.execute();


    }
    private void prepareToPickupVerticalSample() {
        viperSlideArm.prepareToPickupVerticalSample();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute();
    }
    private void pickupVerticalSampleHigher() {
        viperSlideArm.prepareToPickupVerticalSample();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.adjustArm(4);
        viperSlideArm.execute();
    }
    private void prepareToPickupHorizontalSample() {
        viperSlideArm.prepareToPickupVerticalSample();
        claw.prepareToPickupHorizontalSample();
        viperSlideArm.execute();
    }
    private void hangSpecimenHighChamber(){
        viperSlideArm.adjustArm(-19);
        viperSlideArm.execute();

    }
    private void specimenPickUpFromWall() {
        viperSlideArm.prepareToPickUpWallSpecimen();
        viperSlideArm.execute();
        claw.prepareToPickUpWallSpecimen();
    }
    private void retractViperSlide() {
        viperSlideArm.retractViperSlide();
        viperSlideArm.execute();
    }
    private void extendViperSlide(){
        viperSlideArm.extendViperSlide();
        viperSlideArm.execute();
    }
    private void extendViperSlideFurthur() {
        viperSlideArm.extendViperSlideFurthur();
        viperSlideArm.execute();
    }
    private void raiseViperSlide() {
        viperSlideArm.setArmClearBarrier();
        viperSlideArm.execute();
    }
    private void raiseViperSlideHigher() {
        viperSlideArm.raiseViperSlideHigher();
        viperSlideArm.execute();
        claw.pickupSample();
    }
    private void raisedArm() {
        viperSlideArm.raiseArm();
        viperSlideArm.execute();
        claw.wristStraight();
    }

    private void parkRobotSkibidiSigma() {
        viperSlideArm.park();
        viperSlideArm.execute();
        claw.zero();
    }

    private void dropArmIsh() {
        viperSlideArm.dropArmIsh();
        viperSlideArm.execute();

    }

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
    private void prepareToHangSecondSpecimen(){
        viperSlideArm.PrepareToHangSecondSpecimen();
        viperSlideArm.execute();
        claw.prepareToHangHighSpecimenBackwards();
    }

    private void dropArm(double armSpeed, double slideSpeed) {
        viperSlideArm.dropArm();
        viperSlideArm.execute(armSpeed, slideSpeed);
        claw.keepPincherOpen();
    }

    private void retractTuah() {
        viperSlideArm.retractTuah();
        viperSlideArm.execute();
        claw.pickupSample();
    }
    private void waitForViperSlideArmToBeWithinRange(double slideMm, double armDegrees){
        while(true){
            if (viperSlideArm.isSlideAndArmWithinRange(slideMm, armDegrees)) break;
        }
    }
    private void waitForSlideToBeWithinRange(double slideMm){
        while(true){
            if (viperSlideArm.isSlideWithinRange(slideMm)) break;
        }
    }
    private void waitForArmToBeWithinRange(double armDegrees){
        while(true){
            if(viperSlideArm.isArmWithinRange(armDegrees)) break;
        }
    }
    @SuppressWarnings("SameParameterValue")
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }

    }
    @SuppressWarnings("StatementWithEmptyBody")
    private void waitForViperSlideNotBusy() {

        while (viperSlideArm.isBusy()) {

        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    private void waitForViperSlideNotBusyAndAutoCloseClawWhenSampleDetected() {
        while (viperSlideArm.isBusy()) {
        claw.closeIfSampleDetected();
        }
    }
    public void runAutonomous() {

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX = -ROBOT_WIDTH/2;
        double robotFirstSamplePickupLocationY = 34.7;
        double robotSecondSamplePickupLocationY = 34.5;
        double robotThirdSamplePickupLocationY = 20;
        double robotFirstTeamSampleLocationX = -55;
        double robotSecondTeamSampleLocationX = -63;
        double robotThirdTeamSampleLocationX = -62;
        double robotSampleDropX = -57;
        double robotSampleDropY = 51;
        double robotThirdDropY = 54.5;
        double robotObservationZoneX = -TILE_WIDTH*0.5 + 2;
        double robotObservationHangSpecimenY = 32;
        double robotPivotPickupY         =  43.5;
        double robotPivotPickupX         = -37.5;
        double sigmaPickUpX              = -49;
        double sigmaPickUpY              =  56;
        double observationZoneX          = -51;
        double observationZoneY          = 58;
        double hangTheSecondSpecimenX    =  -TILE_WIDTH*0.5 + 2;
        double hangTheSecondSpecimenY    =   29;
        double positioningHelperY        =   50;
        double positioningHelperX        =    8;
        double thirdSpecimenDropOffX     =    10;
        double thirdSpecimenDropOffY     =    50;
        double sigmaSecondPickUpX        = -49;
        double sigmaSecondPickUpY        =  51;


        claw.pickupSample();
        prepareToHangHighSpecimenBackwards();
        // Goes to bar and hangs preloaded sample
        Actions.runBlocking(drive.actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                .lineToY(robotObservationHangSpecimenY)
                .build());
        claw.dropSample();
        retractViperSlide();
        waitForViperSlideArmToBeWithinRange(20,2);
        //Picks up first sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(robotPivotPickupX,robotPivotPickupY,Math.toRadians(214)),Math.toRadians(220))
                .build());
        pickUpFirstSpecimen(0.5, 3);
        waitForViperSlideArmToBeWithinRange(20,3.5);
        claw.pickupSample();
        sleep(150);
        raisedArm();
        waitForViperSlideArmToBeWithinRange(10,5);
        //drop off first sample in oberservation zone
       /* Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(-110))
                .build());
        sleep(100);
        extendViperSlideFurthur();
        waitForSlideToBeWithinRange(10);
        dropArmIsh();
        waitForArmToBeWithinRange(2);
        sleep(150);
        claw.dropSample();
        sleep(100);
        retractTuah();

        */
        // second specimen pickup
        retractTuah();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(sigmaPickUpX,sigmaPickUpY,Math.toRadians(90)),Math.toRadians(90))
                .build());
        sleep(100);
        claw.dropSample();
        prepareToPickUpWallLilBitHigher();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .lineToY(57)
                        .build());
        sleep(250);
        claw.pickupSample();
        sleep(100);
        raisedArm();
        prepareToHangSecondSpecimen();
        sleep(200);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(315))
                .splineToLinearHeading(new Pose2d(positioningHelperX,positioningHelperY,Math.toRadians(270)),Math.toRadians(315))
                .build());
        sleep(100);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(34)
                .build());
        claw.dropSample();
        retractViperSlide();
        waitForViperSlideArmToBeWithinRange(10,5);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(sigmaSecondPickUpX,sigmaSecondPickUpY,Math.toRadians(90)),Math.toRadians(90))
                .build());
        prepareToPickUpWallLilBitHigher();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(57)
                .build());
        sleep(250);
        claw.pickupSample();
        sleep(200);
        raisedArm();
        prepareToHangSecondSpecimen();
        sleep(200);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
        .setTangent(Math.toRadians(315))
                        .splineToLinearHeading(new Pose2d(thirdSpecimenDropOffX,thirdSpecimenDropOffY,Math.toRadians(270)),Math.toRadians(315))
                        .build());

        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .lineToY(34)
                        .build());
        sleep(100);
        claw.dropSample();
        retractViperSlide();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .lineToY(38)
                .build());
        parkRobotSkibidiSigma();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setTangent(135)
                .splineToLinearHeading(new Pose2d(observationZoneX,observationZoneY,Math.toRadians(270)),Math.toRadians(135))
                .build());



































































































































































































































































































































































































































































































































































































































































































































































































































































        /*  old auto code
               // Pick up first team sample
         retractViperSlide();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotFirstTeamSampleLocationX,robotFirstSamplePickupLocationY,Math.toRadians(270)),Math.toRadians(220))
                .build());
        prepareToPickupVerticalSample();
        waitForViperSlideNotBusy();
        sleep(100);
        claw.pickupSample();
        sleep(100);
        raiseViperSlide();
        // Drops off first team sample in observation zone
        extendViperSlide();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(robotSampleDropX, robotSampleDropY, Math.toRadians(90)), Math.toRadians(130))
                .build());
        waitForViperSlideNotBusy();
        sleep(100);
        claw.dropSample();
        sleep(100);
        pickupVerticalSampleHigher();
        // Picks up second team sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(-90))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotSecondTeamSampleLocationX,robotSecondSamplePickupLocationY,Math.toRadians(270)),Math.toRadians(270))
                .build());
        prepareToPickupVerticalSample();
        waitForViperSlideNotBusy();
        sleep(100);
        claw.pickupSample();
        sleep(100);
        raiseViperSlide();
        // Drops off second team sample in observation zone
       Actions.runBlocking(drive.actionBuilder(drive.pose)
               .turn(Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(robotSampleDropX, robotSampleDropY, Math.toRadians(90)), Math.toRadians(130))
                .build());
        waitForViperSlideNotBusy();
        claw.dropSample();
        // Picks up third team sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotThirdTeamSampleLocationX,robotThirdSamplePickupLocationY,Math.toRadians(180)),Math.toRadians(135))
                .build());
        prepareToPickupHorizontalSample();
        waitForViperSlideNotBusy();
        sleep(100);
        claw.pickupSample();
        sleep(100);
        raiseViperSlide();
       // Drops off third team sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(robotSampleDropX, robotThirdDropY, Math.toRadians(90)), Math.toRadians(130))
                .build());
        waitForViperSlideNotBusy();
        claw.dropSample();
        //Parks robot and resets viperslide
        parkRobotSigma();

         */
    }
}