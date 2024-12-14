package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.HardwareMap;

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

    public AutonomousSpecimens(HardwareMap hardwareMap) {
        double robotStartingPositionY = 3 * TILE_HEIGHT - ROBOT_HEIGHT / 2;
        double robotStartingPositionX = -TILE_WIDTH * 0.5;
        Pose2d beginningPose = new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270));
        drive = new MecanumDrive(hardwareMap,beginningPose);
        viperSlideArm = new ViperSlideArm(hardwareMap);
        claw = new Claw(hardwareMap);
    }
    private void prepareToHangSpecimenHighChamber(){
        viperSlideArm.prepareToHangHighSpecimen();
        claw.prepareToHangHighSpecimen();
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
    private void retractViperSlide() {
        viperSlideArm.retractViperSlide();
        viperSlideArm.execute();
    }
    private void extendViperSlide(){
        viperSlideArm.extendViperSlide();
        viperSlideArm.execute();
    }
    private void raiseViperSlide() {
        viperSlideArm.setArmClearBarrier();
        viperSlideArm.execute();
    }

    private void parkRobotSigma() {
        viperSlideArm.park();
        viperSlideArm.execute();
        claw.zero();
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
        double robotObservationHangSpecimenY = 35.5;


        claw.pickupSample();
        prepareToHangSpecimenHighChamber();
        //waitForViperSlideNotBusy();
        // Goes to bar and hangs preloaded sample
        Actions.runBlocking(drive.actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotObservationZoneX,robotObservationHangSpecimenY,Math.toRadians(270)),Math.toRadians(270))
                .build());
        waitForViperSlideNotBusy();
        hangSpecimenHighChamber();
        waitForViperSlideNotBusy();
        claw.dropSample();
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
    }
}