package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subassembly.Claw;
import org.firstinspires.ftc.teamcode.subassembly.ViperSlideArm;
import org.firstinspires.ftc.teamcode.subassembly.WheelieBar;

public class AutonomousBaskets {
    private static final double TILE_WIDTH = 23.5;
    private static final double TILE_HEIGHT = 23.5;
    private static final double ROBOT_HEIGHT = 18;
    private final MecanumDrive drive;
    private final ViperSlideArm viperSlideArm;
    private final Claw claw;
    private final WheelieBar wheelieBar;
    private static final double  ROBOT_STARTING_POSITION_Y = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
    private static final double ROBOT_STARTING_POSITION_X = TILE_WIDTH*1.5 - 3;

    public AutonomousBaskets(HardwareMap hardwareMap) {
        Pose2d beginningPose = new Pose2d(ROBOT_STARTING_POSITION_X, ROBOT_STARTING_POSITION_Y, Math.toRadians(270));
        drive = new MecanumDrive(hardwareMap,beginningPose);
        viperSlideArm = new ViperSlideArm(hardwareMap);
        viperSlideArm.disableArmCompensation();
        claw = new Claw(hardwareMap);
        wheelieBar = new WheelieBar(hardwareMap);
    }

    private void prepareToDriveArmUp(){
        viperSlideArm.prepareToDriveArmUp();
        claw.prepareToDropSampleHighBasket();
        wheelieBar.close();
        viperSlideArm.execute();
    }

    private void prepareToDriveArmDown(){
        viperSlideArm.chill();
        claw.pickupSample();
        viperSlideArm.execute();
    }

    private void resetViperSlideArm(){
        claw.zero();
        viperSlideArm.park();
        wheelieBar.close();
        viperSlideArm.execute();
    }

    @SuppressWarnings("SameParameterValue")
    private void pickUpSample (double armSpeed, double slideSpeed){
        viperSlideArm.pickUpVerticalSampleAuto();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute(armSpeed, slideSpeed);
    }

    @SuppressWarnings("SameParameterValue")
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

    private void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    private void prepareToDropHighBackwards(double armSpeed, double slideSpeed){
        viperSlideArm.prepareToDropSampleHighBasketBackwards();
        claw.prepareToDropSampleHighBasketBackwards();
        wheelieBar.open();
        viperSlideArm.execute(armSpeed, slideSpeed);
    }

    private void waitForViperSlideNotBusy(){
        while(true){
            if (!viperSlideArm.isBusy()) break;
        }
    }

    private void waitForViperSlideToBeWithinRange(double slideMm, double armDegrees){
        while(true){
            if (viperSlideArm.isSlideAndArmWithinRange(slideMm, armDegrees)) break;
        }
    }

    private void waitForViperSlideToBeReadyToDrive(){
        waitForViperSlideToBeWithinRange(20,5);
    }

    private void waitForViperSlideToBeReadyToPickupSample(){
        waitForViperSlideToBeWithinRange(5,2);
    }

    private void waitForViperSlideToBeReadyToDropSample(){
        waitForViperSlideToBeWithinRange(10,2);
    }

    @SuppressWarnings("SameParameterValue")
    private void waitForViperSlideArmToBeInRange(double armDegrees){
        while(true){
            if (viperSlideArm.isArmWithinRange(armDegrees)) break;
        }
    }

    public void runAutonomous() {
        double robotBasketDeliveryFirstSampleLocationX =   59.6;
        double robotBasketDeliveryFirstSampleLocationY =   51;
        double thirdSampleLocationX                    = 58.5;
        double thirdSampleLocationY                    =   45;
        double robotBasketDeliveryThirdSampleLocationX =   52;
        double robotBasketDeliveryThirdSampleLocationY =   55;
        double submersibleZoneX                        =   19;
        double submersibleZoneY                        =    0;

       // Goes to basket and drops off first sample
        prepareToDriveArmDown();
        drive.setExtraCorrectionTime(0);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryFirstSampleLocationX,robotBasketDeliveryFirstSampleLocationY,Math.toRadians(230)),Math.toRadians(45))
                .build());
        prepareToDropHighBackwards(2,2);
        waitForViperSlideToBeWithinRange(2,2);
        sleep(400);
        claw.dropSample();
        sleep(400);
        prepareToDriveArmUp();
        waitForViperSlideToBeReadyToDrive();
        // Goes to pick up first sample off the field
        drive.setExtraCorrectionTime(1);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryFirstSampleLocationX,49,Math.toRadians(247)),Math.toRadians(252.3))
                .build());
        pickUpSample(.7,3);
        waitForViperSlideToBeReadyToPickupSample();
        claw.pickupSample();
        sleep(150);
        prepareToDriveArmUp();
        waitForViperSlideToBeReadyToDrive();
        // Drop first sample in
        drive.setExtraCorrectionTime(0.2);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(235))
                .build());
        prepareToDropHighBackwards(0.5,1.5);
        waitForViperSlideToBeWithinRange(10,1);
        sleep(100);
        claw.dropSample();
        sleep(400);
        prepareToDriveArmUp();
        // Pick up second sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(264.8))
                .build());
        sleep(200);
        pickUpSample2(0.7,3);
        waitForViperSlideToBeReadyToPickupSample();
        sleep(200);
        claw.pickupSample();
        sleep(150);
        prepareToDriveArmUp();
        waitForViperSlideToBeReadyToDrive();
        // Drop off second sample
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(233))
                .build());
        prepareToDropHighBackwards(2,3);
        waitForViperSlideToBeReadyToDropSample();
        sleep(50);
        claw.dropSample();
        sleep(400);
        // Pick up the third sample
        prepareToDriveArmUp();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(293))
                .setTangent(Math.toRadians(293))
                .splineToLinearHeading(new Pose2d(thirdSampleLocationX,thirdSampleLocationY,Math.toRadians(293)),Math.toRadians(284.7))
                .build());
        pickUpSample3(1,0);
        waitForViperSlideArmToBeInRange(30);
        claw.preparetoPickUpHorizontalAuto();
        pickUpSample3(0.2,3);
        waitForViperSlideToBeReadyToPickupSample();
        sleep(150);
        claw.pickupSample();
        sleep(150);
        // Drop third sample off
        prepareToDriveArmUp();
        waitForViperSlideToBeReadyToDrive();
        drive.setExtraCorrectionTime(1);
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(221))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryThirdSampleLocationX,robotBasketDeliveryThirdSampleLocationY,Math.toRadians(221)),Math.toRadians(200))
                .build());
        prepareToDropHighBackwards(0.5,3);
        waitForViperSlideToBeReadyToDropSample();
        sleep(150);
        claw.dropSample();
        sleep(400);
        // Drive to the submersible
        resetViperSlideArm();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(235))
                .splineToLinearHeading(new Pose2d(submersibleZoneX, submersibleZoneY, Math.toRadians(180)), Math.toRadians(180))
                .build());


        /*
        Backup Auto

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
    private void prepareToDropSampleInHighBasket(double armSpeed, double slideSpeed){
        viperSlideArm.prepareToDropSampleHighBasket();
        claw.prepareToDropSampleHighBasket();
        viperSlideArm.execute(armSpeed, slideSpeed);
        viperSlideArm.execute(armSpeed, slideSpeed);
    }
    private void retractArm(){
        claw.elbowStraight();
        viperSlideArm.retractViperSlide();
        viperSlideArm.execute();
    }
    private void pickUpHorizontal(){
        viperSlideArm.pickUpHorizontalSampleAuto();
        claw.prepareToPickupHorizontalSample();
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
        viperSlideArm.execute(armSpeed, slideSpeed);
    }
    private void prepareToDropOffSample(){
        viperSlideArm.prepareToDropSampleHighBasket();
        claw.prepareToDropSampleHighBasket();
        viperSlideArm.execute();

    }
    private void keepArmUp(){
        viperSlideArm.prepareToDropSampleHighBasket();
    }

        double robotStartingPositionY = 3*TILE_HEIGHT-ROBOT_HEIGHT/2;
        double robotStartingPositionX = TILE_WIDTH*1.5;
        double robotSecondSamplePickupLocationY = 36;
        double robotFirstSamplePickupLocationY = 36.8;
        double robotThirdSamplePickupLocationY = 22.3;
        double robotBasketDeliverySecondSampleLocationX= 52;
        double robotBasketDeliveryTwoLocationX = 58;
        double robotBasketDeliveryLocationThreeX = 57.6;
        double robotBasketDeliveryLocationThreeY = 60;
              double robotSecondBasketDeliveryLocationY= 60;
        double robotFirstSampleLocationX = 49.2;
        double robotSecondSampleLocationX = 61;
        double robotThirdSampleLocationX = 50.8;
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
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryThirdSampleLocationY,robotSecondBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(45))
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