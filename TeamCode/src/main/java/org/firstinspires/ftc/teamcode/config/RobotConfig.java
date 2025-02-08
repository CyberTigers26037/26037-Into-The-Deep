package org.firstinspires.ftc.teamcode.config;

import com.acmerobotics.roadrunner.ftc.GoBildaPinpointDriver;
import com.acmerobotics.roadrunner.ftc.GoBildaPinpointDriverRR;
import com.qualcomm.robotcore.util.Device;

public class RobotConfig {
    private static boolean initialized;
    private static final String ROBOT_SERIAL_COACH_ANTHONY = "5838f408422f581c";
    private static final String ROBOT_SERIAL_COACH_ROB     = "803b7c0c22973496";
    private static final String ROBOT_SERIAL_MILLENNIUM    = "a542e45d0aef5058";

    private static String robotName = "Unknown";

    private static double pincherMinSafeDegrees = -66;
    private static double pincherMaxSafeDegrees = 0;

    // RoadRunner Parameters
    private static double inPerTick = 1.0;
    private static double lateralInPerTick = 0.7163243818864545;
    private static double trackWidthTicks = 15.414620486088669;
    private static double kS = 1.2253604027312655;
    private static double kV = 0.155652890472807;
    private static double kA = 0.039;
    private static double axialGain = 1.0;
    private static double lateralGain = 0.5;
    private static double headingGain = 1.2;
    private static double axialVelGain = 0.0;
    private static double lateralVelGain = 0.0;
    private static double headingVelGain = 0.0;

    private static double par0YTicks = -1493.1792311132783;
    private static double par1YTicks = 1285.7674249294002;
    private static double perpXTicks = -2221.960330454141;

    // Pinpoint Paramters
    private static double xPinpointOffsetMM = -104;
    private static double yPinpointOffsetMM = -172;
    private static double encoderPinpointResolution = GoBildaPinpointDriverRR.goBILDA_SWINGARM_POD;
    private static GoBildaPinpointDriver.EncoderDirection xPinpointDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD;
    private static GoBildaPinpointDriver.EncoderDirection yPinpointDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED;

    private static boolean hasWheelieBar = true;

    @SuppressWarnings("unused")
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

    public static double getxPinpointOffsetMM() {
        init();

        return xPinpointOffsetMM;
    }

    public static double getyPinpointOffsetMM() {
        init();

        return yPinpointOffsetMM;
    }

    public static double getEncoderPinpointResolution() {
        init();

        return encoderPinpointResolution;
    }

    public static GoBildaPinpointDriver.EncoderDirection getxPinpointDirection() {
        init();

        return xPinpointDirection;
    }

    public static GoBildaPinpointDriver.EncoderDirection getyPinpointDirection() {
        init();

        return yPinpointDirection;
    }

    public static boolean hasWheelieBar() {
        init();

        return hasWheelieBar;
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

        pincherMinSafeDegrees = -65;
        pincherMaxSafeDegrees = -15;

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

        par0YTicks = -1482.288027000346;
        par1YTicks = 1291.951314151336;
        perpXTicks = -2274.184381449579;

        hasWheelieBar = true;
    }

    private static void initCoachRobRobot() {
        robotName = "Coach Rob";

        inPerTick = 120.0 / 40684.0;
        kV = 0.0004078444590686122;
        kS = 1.1645064050089036;
        kA = 0.00014;
        lateralInPerTick = 0.002328958872727025;
        trackWidthTicks = 5140.188964806299;

        axialGain      = 1.0;
        lateralGain    = 1.0;
        headingGain    = 2.6;
        axialVelGain   = 0.0;
        lateralVelGain = 0.0;
        headingVelGain = 0.0;

        par0YTicks = -1518.1894955342736;
        par1YTicks =  1240.6263803752079;
        perpXTicks = -2266.0179717433575;

        pincherMinSafeDegrees = -62;

        hasWheelieBar = false;
    }
}
