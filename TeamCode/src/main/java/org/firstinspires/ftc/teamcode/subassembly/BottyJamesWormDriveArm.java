package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;


public class BottyJamesWormDriveArm {
    private final DcMotor wormDriveMotor;
    private final TouchSensor limitSwitch;
    private double currentMotorPower;
    private boolean isInitialized;
    private static final double WORM_DRIVE_TICKS_PER_DEGREE = ((6334524.0/3179.0)/(360.0/28.0));
    private static final double ARM_ZERO_TICKS = 52 * WORM_DRIVE_TICKS_PER_DEGREE;
    private static final double ARM_MAX_TICKS = 2* ARM_ZERO_TICKS;
    private static final double CLOSE_TO_LIMIT_SWITCH_TICKS = 5* WORM_DRIVE_TICKS_PER_DEGREE;
    private static final double CLOSE_TO_MAX_TICKS = ARM_MAX_TICKS - CLOSE_TO_LIMIT_SWITCH_TICKS;

    public BottyJamesWormDriveArm(HardwareMap hardwareMap){
        wormDriveMotor = hardwareMap.dcMotor.get("wormDriveMotor");
        limitSwitch = hardwareMap.get(TouchSensor.class,"wormDriveLimitSwitch");
        wormDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wormDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void reset(){
     //   linearActuatorMotor.setTargetPosition(0);
        wormDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wormDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        isInitialized = true;
    }

    public void setMotorPower(double power){
        currentMotorPower = power;

    }

    public boolean isAtLimitSwitchPosition(){
        return limitSwitch.isPressed();

    }

    public boolean isAtZeroPosition(){
        return Math.abs(wormDriveMotor.getCurrentPosition() -ARM_ZERO_TICKS) < WORM_DRIVE_TICKS_PER_DEGREE;
    }

    public void execute(){

        if(limitSwitch.isPressed()&&currentMotorPower<0){
            currentMotorPower=0;
        }
        if (isInitialized){
            if (wormDriveMotor.getCurrentPosition() > ARM_MAX_TICKS && currentMotorPower > 0) {
                currentMotorPower = 0;
            }
            if (wormDriveMotor.getCurrentPosition() < CLOSE_TO_LIMIT_SWITCH_TICKS && currentMotorPower < 0) {
                currentMotorPower = Math.max(currentMotorPower, -0.2);
            }
            if (wormDriveMotor.getCurrentPosition() > CLOSE_TO_MAX_TICKS && currentMotorPower > 0) {
                currentMotorPower = Math.min(currentMotorPower, 0.2);
            }

        }
        wormDriveMotor.setPower(currentMotorPower);

    }

    public int getCurrentArmAngleDegrees(){
        return (int)(wormDriveMotor.getCurrentPosition()/ WORM_DRIVE_TICKS_PER_DEGREE);
    }

}
