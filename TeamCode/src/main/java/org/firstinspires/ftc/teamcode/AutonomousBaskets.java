package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subassembly.Claw;
import org.firstinspires.ftc.teamcode.subassembly.ViperSlideArm;

public class AutonomousBaskets {

    private static final double TILE_WIDTH = 23.5;
    private static final double TILE_HEIGHT = 23.5;
    private static final double ROBOT_HEIGHT = 18;
    private static final double SAMPLE_HEIGHT = 3.5;
    private MecanumDrive drive;
    private ViperSlideArm viperSlideArm;
    private Claw claw;
    private double  robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
    private double robotStartingPositionX = TILE_WIDTH*1.5;

    public AutonomousBaskets(HardwareMap hardwareMap) {
        Pose2d beginningPose = new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(0));
        drive = new MecanumDrive(hardwareMap,beginningPose);
        viperSlideArm = new ViperSlideArm(hardwareMap);
        claw = new Claw(hardwareMap);
    }

    private void prepareToDropSampleInHighBasket(){
        viperSlideArm.prepareToDropSampleHighBasket();
        claw.prepareToDropSampleHighBasket();
        viperSlideArm.execute();
    }
    private void waitForViperSlideNotBusy(){

        while(viperSlideArm.isBusy()){

        }
    }

    public void runAutonomous() {

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX = TILE_WIDTH*1.5;
        double robotSamplePickupLocationY = 40;
        double robotThirdSamplePickupLocationY = 25.25;
        double robotBasketDeliveryLocationX = 50;
        double robotBasketDeliveryLocationY = 50;
        double robotFirstSampleLocationX = 48;
        double robotSecondSampleLocationX = 58;
        double robotThirdSampleLocationX = 56;
        double observationZoneLocationX = -34;
        double observationZoneLocationY = 58;

        // Goes to basket and drops off first sample
        claw.pickupSample();
        prepareToDropSampleInHighBasket();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(315))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationX,robotBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(315))
                .build());
    }
}