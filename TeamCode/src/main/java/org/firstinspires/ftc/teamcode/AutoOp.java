package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Device;

@Autonomous(preselectTeleOp = "AutoOp")
public class AutoOp extends LinearOpMode {

    Boolean basketDelivery = null;

    boolean initialized = false;

    @Override
    public void runOpMode() {
        boolean previousA = false;
        boolean previousB = false;

        while (!initialized) {
            boolean currentA = gamepad1.a;
            boolean currentB = gamepad1.b;

            if (basketDelivery == null) {
                telemetry.addData("Auto Mode", "A = Baskets, B = Specimens");
                telemetry.addData("Serial", Device.getSerialNumberOrUnknown());
                telemetry.update();
                if (currentA && !previousA) {
                    basketDelivery = true;
                }
                if (currentB && !previousB) {
                    basketDelivery = false;
                }
            }
            else {
                initialized = true;
            }
            previousA = currentA;
            previousB = currentB;
        }
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Auto Mode", basketDelivery ? "Baskets" : "Specimen");
        telemetry.update();

        AutonomousBaskets opModeBaskets = new AutonomousBaskets(hardwareMap);
        AutonomousSpecimens opModeSpecimens = new AutonomousSpecimens(hardwareMap);

        waitForStart();

        if (basketDelivery) {
            opModeBaskets.runAutonomous();
        }
        else {
            opModeSpecimens.runAutonomous();
        }
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }
}
