package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subassembly.BottyJamesClaw;
import org.firstinspires.ftc.teamcode.subassembly.BottyJamesLinearActuator;
import org.firstinspires.ftc.teamcode.subassembly.BottyJamesTurret;
import org.firstinspires.ftc.teamcode.subassembly.BottyJamesWormDriveArm;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.Locale;


@TeleOp(name= "BottyJamesTurret")

@SuppressWarnings("unused")
public class BottyJamesWithTurret extends LinearOpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private BottyJamesTurret turret;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        initAprilTag();
        waitForStart();
        while(opModeIsActive()){
            driveRobot();


            AprilTagPoseFtc detectedTagPose = detectAprilTag();

            if(detectedTagPose != null) {
                turret.aim(-detectedTagPose.bearing, detectedTagPose.elevation + 15);
                if(gamepad1.right_trigger > 0.5){
                    turret.startFiring();
                }
            }
            turret.execute();
        }
    }



    public void initialize(){

        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        turret = new BottyJamesTurret(hardwareMap);
        telemetry.addData("Status", "Initialized");

    }


    public void driveRobot(){


    double leftPower;
    double rightPower;

        double drive = gamepad1.left_stick_y * 0.5;
        double turn  =  -gamepad1.left_stick_x * 0.5;
        leftPower    = Range.clip(drive + turn, -0.5, 0.5);
        rightPower   = Range.clip(drive - turn, -0.5, 0.5);
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);


        // Show the elapsed game time and wheel power.
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.update();
    }

    private void initAprilTag() {

        aprilTag = new AprilTagProcessor.Builder()
                .setTagLibrary(new AprilTagLibrary.Builder()
                        .addTag(26, "BottyJamesTarget", 6.25, DistanceUnit.INCH).build())

                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();


        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));


        builder.addProcessor(aprilTag);

        visionPortal = builder.build();


    }

    private AprilTagPoseFtc detectAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format(Locale.US, "\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format(Locale.US, "XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format(Locale.US, "PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format(Locale.US, "RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                return detection.ftcPose;
            } else {
                telemetry.addLine(String.format(Locale.US, "\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format(Locale.US, "Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }

        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");
        return null;
    }



}


