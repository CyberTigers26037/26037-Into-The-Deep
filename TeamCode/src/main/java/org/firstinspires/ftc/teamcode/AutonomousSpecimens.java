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
    private static final double SAMPLE_HEIGHT = 3.5;
    private MecanumDrive drive;
    private ViperSlideArm viperSlideArm;
    private Claw claw;
    private double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
    private double robotStartingPositionX = -TILE_WIDTH*0.5;

    public AutonomousSpecimens(HardwareMap hardwareMap) {
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
    private  void raiseViperSlide() {
        viperSlideArm.setArmClearBarrier();
        viperSlideArm.execute();
    }
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }

    }
    private void waitForViperSlideNotBusy() {

        while (viperSlideArm.isBusy()) {

        }
    }
    public void runAutonomous() {

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX = -ROBOT_WIDTH/2;
        double robotSamplePickupLocationY = 36;
        double robotFirstTeamSampleLocationX = -51;
        double robotSecondTeamSampleLocationX = -62;
        double robotThirdTeamSampleLocationX = -64;
        double robotSpecimenDropX = -57;
        double robotSpecimenDropY = 50;
        double robotThirdDropY = 54;
        double robotThirdSampleLocationY = 26;
        double robotObservationZoneX = -TILE_WIDTH*0.5 + 2;
        double robotObservationY = 36;


        claw.pickupSample();
        prepareToHangSpecimenHighChamber();
        waitForViperSlideNotBusy();
        // Goes to bar and hangs preloaded sample
        Actions.runBlocking(drive.actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotObservationZoneX,robotObservationY,Math.toRadians(270)),Math.toRadians(270))
                .build());
        hangSpecimenHighChamber();
        waitForViperSlideNotBusy();
        claw.dropSample();
        // Pick up first team sample
        retractViperSlide();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotFirstTeamSampleLocationX,robotSamplePickupLocationY,Math.toRadians(270)),Math.toRadians(220))
                .build());
        prepareToPickupVerticalSample();
        waitForViperSlideNotBusy();
        sleep(100);
        claw.pickupSample();
        sleep(100);
        waitForViperSlideNotBusy();
        raiseViperSlide();
        // Drops off first team sample in observation zone
        claw.pickupSample();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(robotSpecimenDropX, robotSpecimenDropY, Math.toRadians(90)), Math.toRadians(130))
                .build());
        claw.dropSample();
        // Picks up second team sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .turn(Math.toRadians(-90))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotSecondTeamSampleLocationX,robotSamplePickupLocationY,Math.toRadians(270)),Math.toRadians(270))
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
                .splineToLinearHeading(new Pose2d(robotSpecimenDropX, robotSpecimenDropY, Math.toRadians(90)), Math.toRadians(130))
                .build());
        claw.dropSample();
        // Picks up third team sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotThirdTeamSampleLocationX,robotThirdSampleLocationY,Math.toRadians(180)),Math.toRadians(135))
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
                .splineToLinearHeading(new Pose2d(robotSpecimenDropX, robotThirdDropY, Math.toRadians(90)), Math.toRadians(130))
                .build());
        claw.dropSample();


    }
}