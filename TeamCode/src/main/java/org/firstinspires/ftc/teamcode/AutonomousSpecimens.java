package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
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
    private void prepareToPickUpVerticalSample() {
        viperSlideArm.prepareToPickupVerticalSample();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute();
    }
    private void hangSpecimenHighChamber(){
        viperSlideArm.adjustArm(-19);
        waitForViperSlideNotBusy();
        claw.dropSample();
    }
    private void waitForViperSlideNotBusy(){

        while(viperSlideArm.isBusy()){

        }
    }
    public void runAutonomous() {

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX = -ROBOT_WIDTH/2;
        double robotSamplePickupLocationY = 40;
        double robotFirstTeamSampleLocationX = -48;
        double robotSecondTeamSampleLocationX = -58;
        double robotThirdTeamSampleLocationX = -54;
        double robotSpecimenDropX = -57;
        double robotSpecimenDropY = 50;
        double robotThirdSampleLocationY = 26;
        double robotObservationZoneX = -TILE_WIDTH*0.5 + 2;
        double robotObservationY = 38;

        claw.pickupSample();
        prepareToHangSpecimenHighChamber();
        waitForViperSlideNotBusy();
        // Goes to bar and hangs preloaded sample
        Actions.runBlocking(drive.actionBuilder(new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270)))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotObservationZoneX,robotObservationY,Math.toRadians(270)),Math.toRadians(270))
                .build());
        hangSpecimenHighChamber();
        // Pick up sample
        // claw.pickupSample();
        // prepareToPickUpVerticalSample();
        // waitForViperSlideNotBusy();
        // Actions.runBlocking(drive.actionBuilder(drive.pose));



    }
}