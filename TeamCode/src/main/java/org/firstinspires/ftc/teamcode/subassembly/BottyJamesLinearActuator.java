package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;


public class BottyJamesLinearActuator {
    private final DcMotor linearActuatorMotor;
    private final TouchSensor limitSwitch;
    private double currentMotorPower;
    private boolean isInitialized;
    private static final double LINEAR_ACTUATOR_TICKS_PER_MM = ((1596.0/11.0)/8.0);
    private static final double MAX_EXTENSION_TICKS = 180*LINEAR_ACTUATOR_TICKS_PER_MM ;
    private static final double CLOSE_TO_LIMIT_SWITCH_TICKS = 20*LINEAR_ACTUATOR_TICKS_PER_MM;
    private static final double CLOSE_TO_MAX_TICKS = 160*LINEAR_ACTUATOR_TICKS_PER_MM;

    public BottyJamesLinearActuator(HardwareMap hardwareMap){
        linearActuatorMotor = hardwareMap.dcMotor.get("linearActuatorMotor");
        limitSwitch = hardwareMap.get(TouchSensor.class,"linearActuatorLimitSwitch");
        linearActuatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearActuatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void reset(){
     //   linearActuatorMotor.setTargetPosition(0);
        linearActuatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearActuatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        isInitialized = true;
    }

    public void setMotorPower(double power){
        currentMotorPower = power;

    }

    public boolean isFullyRetracted(){
        return limitSwitch.isPressed();

    }

    public void execute(){

        if(limitSwitch.isPressed()&&currentMotorPower<0){
            currentMotorPower=0;
        }
        if (isInitialized){
            if (linearActuatorMotor.getCurrentPosition() > MAX_EXTENSION_TICKS && currentMotorPower > 0) {
                currentMotorPower = 0;
            }
            if (linearActuatorMotor.getCurrentPosition() < CLOSE_TO_LIMIT_SWITCH_TICKS && currentMotorPower < 0) {
                currentMotorPower = Math.max(currentMotorPower, -0.2);
            }
            if (linearActuatorMotor.getCurrentPosition() > CLOSE_TO_MAX_TICKS && currentMotorPower > 0) {
                currentMotorPower = Math.min(currentMotorPower, 0.2);
            }

        }
        linearActuatorMotor.setPower(currentMotorPower);

    }

    public int getCurrentExtensionMm(){
        return (int)(linearActuatorMotor.getCurrentPosition()/LINEAR_ACTUATOR_TICKS_PER_MM);
    }

}
