package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subassembly.Claw;
import org.firstinspires.ftc.teamcode.subassembly.ViperSlideArm;

public class AutonomousSpecimens {
    private static final double TILE_WIDTH   = 23.5;
    private static final double TILE_HEIGHT  = 23.5;
    private static final double ROBOT_HEIGHT = 18;
    private static final double ROBOT_WIDTH  = 18;
    private final MecanumDrive drive;
    private final ViperSlideArm viperSlideArm;
    private final Claw claw;

    public AutonomousSpecimens(HardwareMap hardwareMap) {
        double robotStartingPositionY = 3 * TILE_HEIGHT - ROBOT_HEIGHT / 2;
        double robotStartingPositionX = -TILE_WIDTH * 0.5;
        Pose2d beginningPose = new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270));
        drive = new MecanumDrive(hardwareMap,beginningPose);
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

    private void waitForViperSlideArmToBeWithinRange(double slideMm, double armDegrees){
        while(true){
            if (viperSlideArm.isSlideAndArmWithinRange(slideMm, armDegrees)) break;
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
        double robotStartingPositionY    = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX    = -ROBOT_WIDTH/2;
        double robotObservationHangSpecimenY = 30;
        double robotPivotPickupY         =    43.5;
        double robotPivotPickupX         =   -37.5;
        double sigmaPickUpX              =   -49;
        double sigmaPickUpY              =    56;
        double observationZoneX          =   -51;
        double observationZoneY          =    58;
        double positioningHelperY        =    50;
        double positioningHelperX        =     8;
        double thirdSpecimenDropOffX     =    10;
        double thirdSpecimenDropOffY     =    50;
        double sigmaSecondPickUpX        =   -49;
        double sigmaSecondPickUpY        =    51;

        claw.pickupSample();
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
        //Picks up first sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(Math.toRadians(90))
            .splineToLinearHeading(new Pose2d(robotPivotPickupX,robotPivotPickupY,Math.toRadians(214)),Math.toRadians(220))
            .build());
        pickUpFirstSpecimen(0.5, 3);
        waitForViperSlideArmToBeWithinRange(20,3.5);
        claw.pickupSample();
        sleep(150);
        //raisedArm();
        //waitForViperSlideArmToBeWithinRange(10,5);
        //drop off first sample in observation zone
        retractViperSlideNathan();
        drive.setExtraCorrectionTime(0);
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
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(Math.toRadians(315))
            .splineToLinearHeading(new Pose2d(positioningHelperX,positioningHelperY,Math.toRadians(270)),Math.toRadians(0))
            .build());
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
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(Math.toRadians(90))
            .splineToLinearHeading(new Pose2d(sigmaSecondPickUpX,sigmaSecondPickUpY,Math.toRadians(90)),Math.toRadians(90))
            .build());
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
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(Math.toRadians(315))
            .splineToLinearHeading(new Pose2d(thirdSpecimenDropOffX,thirdSpecimenDropOffY,Math.toRadians(270)),Math.toRadians(315))
            .build());
        drive.setExtraCorrectionTime(0);
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
        drive.setExtraCorrectionTimeDefault();
        parkRobot();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
            .setTangent(135)
            .splineToLinearHeading(new Pose2d(observationZoneX,observationZoneY,Math.toRadians(270)),Math.toRadians(135))
            .build());
    }
}