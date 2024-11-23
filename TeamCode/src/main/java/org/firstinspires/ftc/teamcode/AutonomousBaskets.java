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
    private double robotStartingPositionX = TILE_WIDTH*1.5 - 3;

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

    private void retractArm(){
        claw.elbowStraight();
        viperSlideArm.retractSlide();
        viperSlideArm.execute();
    }
    private void prepareToPickUpVerticalSample(){
        viperSlideArm.prepareToPickupVerticalSample();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute();

    }
    private void prepareToPickUpHorizontalSAmple(){
        viperSlideArm.prepareToPickupVerticalSample();
        claw.prepareToPickupHorizontalSample();
        viperSlideArm.execute();

    }
    private void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {

        }

    }
    private void prepareToDropOffSample(){
        viperSlideArm.prepareToDropSampleHighBasket();
        claw.prepareToDropSampleHighBasket();
        viperSlideArm.execute();

    }
    private void keepArmUp(){
        viperSlideArm.prepareToDropSampleHighBasket();
    }
    private void waitForViperSlideNotBusy(){

        while(viperSlideArm.isBusy()){

        }
    }

    public void runAutonomous() {

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX = TILE_WIDTH*1.5;
        double robotSamplePickupLocationY = 37.8;
        double robotThirdSamplePickupLocationY = 25.25;
        double robotBasketDeliveryLocationX = 60.3;
        double robotBasketDeliveryLocationY = 59.5;
        double robotFirstSampleLocationX = 51;
        double robotSecondSampleLocationX = 59.8;
        double robotThirdSampleLocationX = 56;
        double observationZoneLocationX = -34;
        double observationZoneLocationY = 58;
        double backupRobotFromBasketX = 48;
        double backupRobotFromBasketY = 48;

        // Goes to basket and drops off preloaded sample
        claw.pickupSample();
        prepareToDropSampleInHighBasket();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(300))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationX,robotBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(45))
                        .build());
        claw.dropSample();
        sleep(200);
        retractArm();



        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(230))
                .splineToLinearHeading(new Pose2d(robotFirstSampleLocationX,robotSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(270))
                .build());

        prepareToPickUpVerticalSample();
        waitForViperSlideNotBusy();
        sleep(200);
        claw.pickupSample();
        sleep(200);
        prepareToDropSampleInHighBasket();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationX,robotBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(45))
                .build());
        claw.dropSample();
        sleep(200);
        retractArm();

        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
          .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(robotSecondSampleLocationX,robotSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(0))
                .build());
        prepareToPickUpVerticalSample();
        waitForViperSlideNotBusy();
        claw.pickupSample();
        sleep(200);
        prepareToDropSampleInHighBasket();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationX,robotBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(45))
                .build());
        claw.dropSample();
        sleep(300);
        retractArm();

        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotThirdSampleLocationX,robotThirdSamplePickupLocationY, Math.toRadians(0)), Math.toRadians(0))                .build());
        prepareToPickUpHorizontalSAmple();
        claw.pickupSample();
        waitForViperSlideNotBusy();






    }
}