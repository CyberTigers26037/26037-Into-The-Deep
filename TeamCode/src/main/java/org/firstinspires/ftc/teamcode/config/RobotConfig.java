package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.util.Device;

public class RobotConfig {
    private static boolean initialized;
    private static final String ROBOT_SERIAL_COACH_ANTHONY = "5838f408422f581c";
    private static final String ROBOT_SERIAL_COACH_ROB     = "803b7c0c22973496";
    private static final String ROBOT_SERIAL_MILLENNIUM    = "a542e45d0aef5058";

    private static String robotName = "Unknown";

    private static double pincherMinSafeDegrees = -68;
    private static double pincherMaxSafeDegrees = -15;

    // RoadRunner Parameters
    private static double inPerTick = 0.00294479;
    private static double lateralInPerTick = 0.0021816593024624738;
    private static double trackWidthTicks = 5191.979563103292;
    private static double kS = 1.020971104258798;
    private static double kV = 0.0005820210887890002;
    private static double kA = 0.0002;
    private static double axialGain = 1.0;
    private static double lateralGain = 1.0;
    private static double headingGain = 1.0;
    private static double axialVelGain = 0.0;
    private static double lateralVelGain = 0.0;
    private static double headingVelGain = 0.0;

    private static double par0YTicks = -1500.1021466083985;
    private static double par1YTicks = 1272.2998829608118;
    private static double perpXTicks = -2331.2327839370423;

    public static String getRobotName() {
        init();

        return robotName;
    }

    public static double getPincherMinSafeDegrees() {
        init();

        return pincherMinSafeDegrees;
    }

    public static double getPincherMaxSafeDegrees() {
        init();

        return pincherMaxSafeDegrees;
    }

    public static double getInPerTick() {
        init();

        return inPerTick;
    }

    public static double getLateralInPerTick() {
        init();

        return lateralInPerTick;
    }

    public static double getTrackWidthTicks() {
        init();

        return trackWidthTicks;
    }

    public static double getKs() {
        init();

        return kS;
    }

    public static double getKv() {
        init();

        return kV;
    }

    public static double getKa() {
        init();

        return kA;
    }

    public static double getPar0YTicks() {
        init();

        return par0YTicks;
    }

    public static double getPar1YTicks() {
        init();

        return par1YTicks;
    }

    public static double getPerpXTicks() {
        init();

        return perpXTicks;
    }

    public static double getAxialGain() {
        init();

        return axialGain;
    }

    public static double getLateralGain() {
        init();

        return lateralGain;
    }

    public static double getHeadingGain() {
        init();

        return headingGain;
    }

    public static double getAxialVelGain() {
        init();

        return axialVelGain;
    }

    public static double getLateralVelGain() {
        init();

        return lateralVelGain;
    }

    public static double getHeadingVelGain() {
        init();

        return headingVelGain;
    }

    private static void init() {
        if (initialized) return;

        String robotSerialNumber = Device.getSerialNumberOrUnknown();

        switch (robotSerialNumber) {
            case ROBOT_SERIAL_COACH_ANTHONY:
                initCoachAnthonyRobot();
                break;
            case ROBOT_SERIAL_COACH_ROB:
                initCoachRobRobot();
                break;
            case ROBOT_SERIAL_MILLENNIUM:
                initMillenniumRobot();
                break;
            default:
                // Unknown robot
                break;
        }

        initialized = true;
    }

    private static void initMillenniumRobot() {
        robotName = "Millennium";

        // do nothing, defaults ok
    }

    private static void initCoachAnthonyRobot() {
        robotName = "Coach Anthony";

        pincherMinSafeDegrees = -90;
        pincherMaxSafeDegrees = -30;

        inPerTick = 120.0 / 40744.75;
        lateralInPerTick = 0.002216341570521334;
        trackWidthTicks = 5238.6953844664395;
        kS = 0.72765269020422;
        kV = 0.0005601380199301534;
        kA = 0.0001;
        axialGain = 0.4;
        lateralGain = 1.8;
        headingGain = 0.8;
        axialVelGain = 0.0;
        lateralVelGain = 0.0;
        headingVelGain = 0.0;

        //par0YTicks = -1482.288027000346;
        //par1YTicks = 1291.951314151336;
        //perpXTicks = -2274.184381449579;

    }

    private static void initCoachRobRobot() {
        robotName = "Coach Rob";

        pincherMinSafeDegrees = -62;

    }
}
