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
    private Servo rgbLight;
    private static final double OFF = 0.0;
    private static final double BLUE = 0.611;
    private static LightingSystem instance;

    @OnCreateEventLoop
    public static void attachEventLoop(Context context, FtcEventLoop eventLoop) {
        if (RobotConfig.hasLighting()) {
            OpModeManagerImpl opModeManager = eventLoop.getOpModeManager();
            opModeManager.registerListener(LightingSystem.getInstance());
        }
    }

    public static LightingSystem getInstance() {
        if (instance == null) {
            instance = new LightingSystem();
        }

        return instance;
    }

    @Override
    public void onOpModePreInit(OpMode opMode) {
        rgbLight = opMode.hardwareMap.get(Servo.class, "rgbLight");
    }

    @Override
    public void onOpModePreStart(OpMode opMode) {
        rgbLight.setPosition(BLUE);
    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        rgbLight.setPosition(OFF);
    }
}