package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(preselectTeleOp = "TeleOpA")
public class AutoA extends LinearOpMode {

    Boolean allianceRed = null;

    Boolean startLeft = null;

    Boolean pathDirect = null;

    Boolean parkingLeft = null;

    boolean initialized = false;

    @Override
    public void runOpMode() {
        boolean previousX = false;
        boolean previousB = false;

        while (!initialized) {
            boolean currentX = gamepad1.x;
            boolean currentB = gamepad1.b;

            if (allianceRed == null) {
                telemetry.addData("Alliance", "X = blue, B = Red");
                telemetry.update();
                if (currentX && !previousX) {
                    allianceRed = false;
                }
                if (currentB && !previousB) {
                    startLeft = false;
                }
            }
            else if (startLeft = null) {
                telemetry.addData("Start", "X = left, B = right");
                telemetry.update();
                if (currentX && !previousX) {
                    startLeft = true;
                }
                if (currentB && !previousB) {
                    startLeft = false;
                }
            }
            else if (pathDirect = null) {
                telemetry.addData("Path", "X = direct, B = indirect");
                telemetry.update();
                if (currentX && !previousX) {
                    pathDirect = true;
                }
                if (currentB && !previousB) {
                    pathDirect = false;
                }
            }
            else if (parkingLeft = null) {
                telemetry.addData("Parking", "X = left, B = right");
                telemetry.update();
                if (currentX && !previousX) {
                    parkingLeft = true;
                }
                if (currentB && !previousB) {
                    parkingLeft = false;
                }
            }
            else {
                initialized = true;
            }
            previousX = currentX;
            previousB = currentB;
        }
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Alliance", allianceRed ? "Red" : "Blue");
        telemetry.addData("Status", startLeft ? "Left" : "Right");
        telemetry.addData("Status", pathDirect ? "Direct" : "Indirect");
        telemetry.addData("Status", parkingLeft ? "Left" : "Right");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }
}
