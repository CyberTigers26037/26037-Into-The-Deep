package org.firstinspires.ftc.teamcode.subassembly;

import android.content.Context;

import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop;
import org.firstinspires.ftc.teamcode.config.RobotConfig;

@SuppressWarnings("unused")
public class LightingSystem implements OpModeManagerNotifier.Notifications {
    private static LightingSystem instance;

    private static final double OFF = 0.0;
    private static final double BLUE = 0.611;
    private static final double RED = 0.279;

    private Servo rgbLight;
    private NumberPlateSwitch numberPlateSwitch;

    @OnCreateEventLoop
    public static void attachEventLoop(Context context, FtcEventLoop eventLoop) {
        OpModeManagerImpl opModeManager = eventLoop.getOpModeManager();
        opModeManager.registerListener(LightingSystem.getInstance());
    }

    public static LightingSystem getInstance() {
        if (instance == null) {
            instance = new LightingSystem();
        }

        return instance;
    }

    @Override
    public void onOpModePreInit(OpMode opMode) {
        try {
            rgbLight = opMode.hardwareMap.get(Servo.class, "rgbLight");
            numberPlateSwitch = new NumberPlateSwitch(opMode.hardwareMap);
        }
        catch (IllegalArgumentException e) {
            rgbLight = null;
            opMode.telemetry.addLine("Unable to init lighting system: " + e);
        }
    }

    @Override
    public void onOpModePreStart(OpMode opMode) {
        if (rgbLight == null) return;

        if (numberPlateSwitch.isNumberPlateBlue()) {
            rgbLight.setPosition(BLUE);
        }
        else {
            rgbLight.setPosition(RED);
        }
    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        if (rgbLight == null) return;

        rgbLight.setPosition(OFF);
    }
}
