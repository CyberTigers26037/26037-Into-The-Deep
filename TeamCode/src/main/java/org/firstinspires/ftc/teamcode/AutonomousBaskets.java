package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
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
        viperSlideArm.disableArmCompensation();
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
        viperSlideArm.execute();
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
    private void prepareToPickUpVerticalSample(double armSpeed, double slideSpeed){
        viperSlideArm.prepareToPickUpVerticalSampleAuto();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute(armSpeed, slideSpeed);

    }
    private void prepareToPickUSampleAuto(){
        viperSlideArm.prepareToPickUp();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute();
    }
    private void pickupFirst(){
        viperSlideArm.prepareToPickUp();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute();

    }
    private void pickUpSample (double armSpeed, double slideSpeed){
        viperSlideArm.pickUpVerticalSampleAuto();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute(armSpeed, slideSpeed);
    }
    private void pickUpSample2 (double armSpeed, double slideSpeed){
        viperSlideArm.pickUpVerticalSampleTwoAuto();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute(armSpeed, slideSpeed);

    }
    private void pickUpSample3 (double armSpeed, double slideSpeed) {
        viperSlideArm.pickUpVerticalSampleThreeAuto();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute(armSpeed, slideSpeed);
    }

    private void prepareToPickUpHorizontalSAmple(){
        viperSlideArm.prepareToPickupHorizontalSample();
        claw.prepareToPickupHorizontalSample();
        viperSlideArm.execute();

    }
    private void prepareToPickUpHorizontalBefore(double armSpeed, double slideSpeed){
        viperSlideArm.prepareToPickUpHorizontalPregame();
        claw.prepareToPickupHorizontalSample();
        viperSlideArm.execute(armSpeed, slideSpeed);
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
    private void prepareToDropHighBackwards(double armSpeed, double slideSpeed){
        viperSlideArm.prepareToDropSampleHighBasketBackwards();
        claw.prepareToDropSampleHighBasketBackwards();
        viperSlideArm.execute(armSpeed, slideSpeed);

    }
    private void keepArmUp(){
        viperSlideArm.prepareToDropSampleHighBasket();
    }
    private void waitForViperSlideNotBusy(){

        while(viperSlideArm.isBusy()){

        }

    }

    private void waitForViperSlideToBeWithinRange(double slideMm, double armDegrees){
        while(!viperSlideArm.isSlideAndArmWithinRange(slideMm,armDegrees)){

        }

    }

    public void runAutonomous() {

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX = TILE_WIDTH*1.5;
        double robotSecondSamplePickupLocationY = 36;
        double robotFirstSamplePickupLocationY = 36.8;
        double robotThirdSamplePickupLocationY = 22.3;
        double robotBasketDeliveryFirstSampleLocationX = 53;
        double robotBasketDeliveryFirstSampleLocationY = 49;
        double robotBasketDeliverySecondSampleLocationX= 52;
        double robotBasketDeliveryTwoLocationX = 58;
        double robotBasketDeliveryLocationTwoY = 48;
        double robotBasketDeliveryLocationThreeX = 57.6;
        double robotBasketDeliveryLocationThreeY = 60;
        double robotBasketDeliveryThreeLocationX = 52;
        double robotSecondBasketDeliveryLocationY= 60;
        double robotFirstSampleLocationX = 49.2;
        double robotSecondSampleLocationX = 61;
        double robotThirdSampleLocationX = 50.8;
        double submersibleZoneX = 35;
        double submersibleZoneY = 11;


       chill();

       // Goes to basket and drops off first sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(robotBasketDeliveryFirstSampleLocationX,robotBasketDeliveryFirstSampleLocationY,Math.toRadians(230)),Math.toRadians(45))
                        .build());


        prepareToDropHighBackwards(0.8,2);
        waitForViperSlideNotBusy();
        sleep(100);
        claw.dropSample();
        sleep(400);
        keepSampleHeld();
        sleep(1000);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .turnTo(Math.toRadians(252.8))
                                .build());
        pickUpSample(0.7,3);
        waitForViperSlideToBeWithinRange(5,1);
        claw.pickupSample();
        sleep(150);
        keepSampleHeld();
        sleep(1000);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .turnTo(Math.toRadians(224))
                        .build());
        prepareToDropHighBackwards(0.5,1.5);
        waitForViperSlideToBeWithinRange(10,1);
        sleep(100);
        claw.dropSample();
        sleep(400);
        keepSampleHeld();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(273.5))
                .build());
        sleep(200);
        pickUpSample2(1,3);
        waitForViperSlideToBeWithinRange(5,0.5);
        claw.pickupSample();
        sleep(150);
        keepSampleHeld();
        sleep(1000);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(224))
                .build());
        prepareToDropHighBackwards(0.5,3);
        waitForViperSlideToBeWithinRange(5,2);
        sleep(50);
        claw.dropSample();
        sleep(400);
        keepSampleHeld();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(290))
                .setTangent(Math.toRadians(290))
                .splineToLinearHeading(new Pose2d(57.1,46,Math.toRadians(290)),Math.toRadians(284.7))
                .build());
        pickUpSample3(1,0);
        waitForViperSlideToBeWithinRange(1000000,30 );
        claw.preparetoPickUpHorizontalAuto();
        pickUpSample3(0.3,2);
        waitForViperSlideNotBusy();
        sleep(150);
        claw.pickupSample();
        sleep(150);
        keepSampleHeld();
        sleep(1000);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(221))
                .setTangent(Math.toRadians(221))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryThreeLocationX,robotBasketDeliveryLocationTwoY,Math.toRadians(221)),Math.toRadians(200))
                .build());
        prepareToDropHighBackwards(0.5,3);
        waitForViperSlideToBeWithinRange(10,3);
        claw.dropSample();
        sleep(400);
        green();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(235))
                .splineToLinearHeading(new Pose2d(submersibleZoneX, submersibleZoneY, Math.toRadians(180)), Math.toRadians(180))
                .build());


        /*
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .turnTo(Math.toRadians(0))
                        .build());
        prepareToDropHighBackwards();
        sleep(2600);
        claw.dropSample();
        sleep(400);
          prepareToPickUpSampleAuto();
        sleep(500);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .turnTo(Math.toRadians(400))
                                .build());
           pickupFirst();        sleep(3000);
        claw.pickupSample();
        sleep(150);
        keepSampleHeld();
         */












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