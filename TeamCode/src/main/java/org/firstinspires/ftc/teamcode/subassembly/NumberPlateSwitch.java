package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class NumberPlateSwitch {

    private final TouchSensor touchSensor;

    public NumberPlateSwitch(HardwareMap hwMap) {
        touchSensor = hwMap.get(TouchSensor.class, "Number Plate Sensor");
    }
    public boolean isNumberPlateBlue () {
        return touchSensor.isPressed();
    }

}
