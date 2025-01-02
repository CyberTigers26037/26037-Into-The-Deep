package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Device;

@SuppressWarnings("unused")
@Autonomous(preselectTeleOp = "AutoOp")
public class AutoOp extends LinearOpMode {

    Boolean basketDelivery = null;

    boolean initialized = false;

    @Override
    public void runOpMode() {
        GamepadEx driverOp = new GamepadEx(gamepad1);

        while (!initialized) {
            driverOp.readButtons();

            if (basketDelivery == null) {
                telemetry.addData("Auto Mode", "A = Baskets, B = Specimens");
                telemetry.addData("Serial", Device.getSerialNumberOrUnknown());
                telemetry.update();
                if (driverOp.wasJustPressed(GamepadKeys.Button.A)) {
                    basketDelivery = true;
                }
                if (driverOp.wasJustPressed(GamepadKeys.Button.B)) {
                    basketDelivery = false;
                }
            }
            else {
                initialized = true;
            }
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
