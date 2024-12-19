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
        Pose2d beginningPose = new Pose2d(robotStartingPositionX, robotStartingPositionY, Math.toRadians(270));
        drive = new MecanumDrive(hardwareMap,beginningPose);
        viperSlideArm = new ViperSlideArm(hardwareMap);
        claw = new Claw(hardwareMap);
    }

    private void prepareToDropSampleInHighBasket(double armSpeed, double slideSpeed){
        viperSlideArm.prepareToDropSampleHighBasket();
        claw.prepareToDropSampleHighBasket();
        viperSlideArm.execute(armSpeed, slideSpeed);
        viperSlideArm.execute(armSpeed, slideSpeed);
    }

private void keepSampleHeld(){
        viperSlideArm.keepSampleHeld();
        claw.prepareToDropSampleHighBasket();
        viperSlideArm.execute();
}
    private void pickUpHorizontal(){
        viperSlideArm.pickUpHorizontalSampleAuto();
        claw.prepareToPickupHorizontalSample();
        viperSlideArm.execute();
    }

    private void chill(){
        viperSlideArm.chill();
        claw.pickupSample();

    }

    private void retractArm(){
        claw.elbowStraight();
        viperSlideArm.retractViperSlide();
        viperSlideArm.execute();
    }
    private void green(){
        claw.zero();
        viperSlideArm.park();
        viperSlideArm.execute();
    }
    private void prepareToPickUpVerticalSample(double armsSpeed, double slideSpeed){
        viperSlideArm.prepareToPickUpVerticalSampleAuto();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute();

    }
    private void prepareToPickUpHorizontalSAmple(){
        viperSlideArm.prepareToPickupHorizontalSample();
        claw.prepareToPickupHorizontalSample();
        viperSlideArm.execute();

    }
    private void prepareToPickUpHorizontalBefore(double armSpeed, double slideSpeed){
        viperSlideArm.prepareToPickUpHorizontalPregame();
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
    private void prepareToDropHighBackwards(){
        viperSlideArm.prepareToDropSampleHighBasketBackwards();
        claw.prepareToDropSampleHighBasketBackwards();
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
        double robotSecondSamplePickupLocationY = 37;
        double robotFirstSamplePickupLocationY = 36.8;
        double robotThirdSamplePickupLocationY = 22.3;
        double robotBasketDeliveryOneLocationX = 55;
        double robotBasketDeliveryOneLocationY = 48;
        double robotBasketDeliveryTwoLocationX = 58;
        double robotBasketDeliveryLocationTwoY = 60;
        double robotBasketDeliveryLocationThreeX = 57.6;
        double robotBasketDeliveryLocationThreeY = 60;
        double robotBasketDeliveryThreeLocationX = 57.5;
        double robotSecondBasketDeliveryLocationY= 60;
        double robotFirstSampleLocationX = 49.2;
        double robotSecondSampleLocationX = 61.3;
        double robotThirdSampleLocationX = 50.8;
        double submersibleZoneX = 34;
        double submersibleZoneY = 10;



       chill();

                        // Goes to basket and drops off first sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(robotBasketDeliveryOneLocationX,robotBasketDeliveryOneLocationY,Math.toRadians(230)),Math.toRadians(45))
                        .build());
        prepareToDropHighBackwards();
        sleep(2600);
        claw.dropSample();
        sleep(150);
        chill();
        sleep(2600);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        // Picks up first sample off of the field
                        .setTangent(Math.toRadians(230))
                        .splineToLinearHeading(new Pose2d(robotFirstSampleLocationX,robotFirstSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(270))
                        .build());
        prepareToPickUpVerticalSample(3,3);
        sleep(1100);
        claw.pickupSample();
        sleep(125);
        chill();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setTangent(Math.toRadians(45))
                        .splineToLinearHeading(new Pose2d(robotBasketDeliveryOneLocationX,robotBasketDeliveryOneLocationY,Math.toRadians(230)),Math.toRadians(45))
                        .build());
        prepareToDropHighBackwards();
        sleep(2600);
        claw.dropSample();
        sleep(2600);
        chill();

        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .setTangent(Math.toRadians(225))
                        .splineToLinearHeading(new Pose2d(robotSecondSampleLocationX,robotSecondSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(0))
                        .build());

    prepareToPickUpVerticalSample(3,3);
    sleep(1100);
    claw.pickupSample();
    sleep(125);
    chill();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryOneLocationX, robotBasketDeliveryOneLocationY, Math.toRadians(230)), Math.toRadians(45))
                        .build());
        prepareToDropHighBackwards();
        sleep(2600);
        claw.dropSample();
        sleep(2600);
        chill();









                // Goes to basket and drops off preloaded sample
        /*
        claw.pickupSample();
        prepareToDropSampleInHighBasket(3, 3);

        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .waitSeconds(.01)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryOneLocationX,robotBasketDeliveryOneLocationY,Math.toRadians(45)),Math.toRadians(45))
                        .build());

        claw.dropSample()
        sleep(200);
        retractArm();




        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(230))
                .splineToLinearHeading(new Pose2d(robotFirstSampleLocationX,robotFirstSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(270))
                .build());

        prepareToPickUpVerticalSample(3,3);
        sleep(1000);
        claw.pickupSample();
        sleep(200);
        prepareToDropSampleInHighBasket(0.8, 0.28);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryThreeLocationX,robotSecondBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(45))
                .build());
        claw.dropSample();
        sleep(50);
        retractArm();


        Actions.runBlocking(drive.actionBuilder(drive.pose)
          .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(robotSecondSampleLocationX,robotSecondSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(0))
                .build());
        prepareToPickUpVerticalSample(3,3);
       sleep(1000);
        claw.pickupSample();
        sleep(125);
        prepareToDropSampleInHighBasket(0.8, 0.28);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryTwoLocationX,robotBasketDeliveryLocationTwoY,Math.toRadians(45)),Math.toRadians(45))
                .build());
        waitForViperSlideNotBusy();
        claw.dropSample();
        sleep(50);
        retractArm();

/*
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotThirdSampleLocationX,robotThirdSamplePickupLocationY, Math.toRadians(0)), Math.toRadians(0))
                .build());
        prepareToPickUpHorizontalBefore(3,3);
        sleep(1050);
        pickUpHorizontal();
        sleep(410);
        claw.pickupSample();
        sleep(100);
       keepSampleHeld();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(100))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryLocationThreeX,robotBasketDeliveryLocationThreeY,Math.toRadians(45)),Math.toRadians(100))
                .build());
        prepareToDropSampleInHighBasket(2,2);


        waitForViperSlideNotBusy();
        claw.dropSample();
        sleep(100);
        retractArm();



        waitForViperSlideNotBusy();
     green();

     Actions.runBlocking(drive.actionBuilder(drive.pose)
   .setTangent(Math.toRadians(235))
                .splineToLinearHeading(new Pose2d(submersibleZoneX, submersibleZoneY, Math.toRadians(180)), Math.toRadians(180))
                .build());


*/




    }
}