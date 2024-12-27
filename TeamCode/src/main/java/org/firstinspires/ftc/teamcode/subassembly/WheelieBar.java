package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.RobotConfig;

public class WheelieBar {

    private Servo left = null;
    private Servo right = null;
    private final double leftServoMinSafeDegrees = -84;
    private final double leftServoMaxSafeDegrees = 5;
    private final double rightServoMinSafeDegrees = -87;
    private final double rightServoMaxSafeDegrees = 5;
    private double leftServoCurrentAngle = 0;
    private double rightServoCurrentAngle = 0;
    private static final double SERVO_DEGREES = 300;
    private static final double MIN_PWM = 500;
    private static final double MAX_PWM = 2500;


    public WheelieBar (HardwareMap hwMap) {
        if (!RobotConfig.hasWheelieBar()) return;
        left = hwMap.get(Servo.class, "left_wheelie_servo");
        right = hwMap.get(Servo.class, "right_wheelie_servo");
        right.setDirection(Servo.Direction.REVERSE);
        setServoRange(left);
        setServoRange(right);
    }
    public void adjustAngle(double degrees) {
        if (!RobotConfig.hasWheelieBar()) return;
        setLeftServoToAngle(leftServoCurrentAngle+degrees);
        setRightServoToAngle(rightServoCurrentAngle+degrees);

    }
    public void open() {
        if (!RobotConfig.hasWheelieBar()) return;
        setLeftServoToAngle(leftServoMinSafeDegrees);
        setRightServoToAngle(rightServoMinSafeDegrees);
    }
    public void close() {
        if (!RobotConfig.hasWheelieBar()) return;
        setLeftServoToAngle(leftServoMaxSafeDegrees);
        setRightServoToAngle(rightServoMaxSafeDegrees);
    }

    protected void setLeftServoToAngle(double degrees) {
        degrees = Range.clip(degrees, leftServoMinSafeDegrees, leftServoMaxSafeDegrees);
        leftServoCurrentAngle = degrees;
        left.setPosition(Range.scale(degrees, -SERVO_DEGREES / 2, SERVO_DEGREES / 2, 0, 1));
    }
    protected void setRightServoToAngle(double degrees) {
        degrees = Range.clip(degrees, rightServoMinSafeDegrees, rightServoMaxSafeDegrees);
        rightServoCurrentAngle = degrees;
        right.setPosition(Range.scale(degrees, -SERVO_DEGREES / 2, SERVO_DEGREES / 2, 0, 1));
    }
    private void setServoRange(Servo servo) {
        if (servo.getController() instanceof ServoControllerEx) {
            ServoControllerEx controller = (ServoControllerEx) servo.getController();
            controller.setServoPwmRange(
                    servo.getPortNumber(),
                    new PwmControl.PwmRange(MIN_PWM, MAX_PWM)
            );
        }
    }
    public void outputTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Servo Angle", leftServoCurrentAngle);
        telemetry.addData("Right Servo Angle", rightServoCurrentAngle);
    }
}
