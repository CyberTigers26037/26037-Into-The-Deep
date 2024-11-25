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

    private void pickUpHorizontal(){
        viperSlideArm.pickUpHorizontalSample();
        claw.prepareToPickupHorizontalSample();
        viperSlideArm.execute();
    }

    private void retractArm(){
        claw.elbowStraight();
        viperSlideArm.retractViperSlide();
        viperSlideArm.execute();
    }
    private void prepareToPickUpVerticalSample(){
        viperSlideArm.prepareToPickUpVerticalSampleAuto();
        claw.prepareToPickupVerticalSample();
        viperSlideArm.execute();

    }
    private void prepareToPickUpHorizontalSAmple(){
        viperSlideArm.prepareToPickupHorizontalSample();
        claw.prepareToPickupHorizontalSample();
        viperSlideArm.execute();

    }
    private void prepareToPickUpHorizontalBefore(){
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
        double robotSecondSamplePickupLocationY = 38.2;
        double robotFirstSamplePickupLocationY = 37;
        double robotThirdSamplePickupLocationY = 26.4;
        double robotBasketDeliveryOneLocationX = 59.6;
        double robotBasketDeliveryOneLocationY = 53.5;
        double robotBasketDeliveryTwoLocationX = 58; // crash free for the second
        double robotBasketDeliveryLocationTwoY = 58.4;
        double robotBasketDeliveryThreeLocationX = 59.8;
        double robotSecondBasketDeliveryLocationY= 57.5;
        double robotFirstSampleLocationX = 50.5;
        double robotSecondSampleLocationX = 59.6;
        double robotThirdSampleLocationX = 56;


        // Goes to basket and drops off preloaded sample
        claw.pickupSample();
        prepareToDropSampleInHighBasket();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryOneLocationX,robotBasketDeliveryOneLocationY,Math.toRadians(45)),Math.toRadians(45))
                        .build());

        claw.dropSample();
        sleep(200);
        retractArm();




        Actions.runBlocking(drive.actionBuilder(drive.pose)
                        .waitSeconds(.15)
                .setTangent(Math.toRadians(230))
                .splineToLinearHeading(new Pose2d(robotFirstSampleLocationX,robotFirstSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(270))
                .build());

        prepareToPickUpVerticalSample();
        waitForViperSlideNotBusy();
        claw.pickupSample();
        sleep(200);
        prepareToDropSampleInHighBasket();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryThreeLocationX,robotSecondBasketDeliveryLocationY,Math.toRadians(45)),Math.toRadians(45))
                .build());
        claw.dropSample();
        sleep(150);
        retractArm();


        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .waitSeconds(.15)
          .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(robotSecondSampleLocationX,robotSecondSamplePickupLocationY,Math.toRadians(270)), Math.toRadians(0))
                .build());
        prepareToPickUpVerticalSample();
        waitForViperSlideNotBusy();
        claw.pickupSample();
        sleep(150);
        prepareToDropSampleInHighBasket();
        waitForViperSlideNotBusy();
        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(robotBasketDeliveryTwoLocationX,robotBasketDeliveryLocationTwoY,Math.toRadians(45)),Math.toRadians(45))
                .build());
        claw.dropSample();
        sleep(100);
        retractArm();


        Actions.runBlocking(drive.actionBuilder(drive.pose)
                .waitSeconds(.15)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(robotThirdSampleLocationX,robotThirdSamplePickupLocationY, Math.toRadians(0)), Math.toRadians(0))
                .build());
        prepareToPickUpHorizontalBefore();
        waitForViperSlideNotBusy();
        pickUpHorizontal();
        waitForViperSlideNotBusy();
        claw.pickupSample();







    }
}