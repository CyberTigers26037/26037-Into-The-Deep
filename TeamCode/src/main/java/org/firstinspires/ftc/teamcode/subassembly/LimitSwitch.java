package org.firstinspires.ftc.teamcode.subassembly;


import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimitSwitch {

    private final TouchSensor touchSensor;

    public LimitSwitch(HardwareMap hwMap, String name) {
        touchSensor = hwMap.get(TouchSensor.class, name);
    }
    public boolean isLimitSwitchPressed () {
        return touchSensor.isPressed();
    }

}


