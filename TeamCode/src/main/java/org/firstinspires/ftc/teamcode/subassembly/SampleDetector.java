package org.firstinspires.ftc.teamcode.subassembly;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class SampleDetector {
    public enum SampleType {
        NONE, RED, BLUE, YELLOW
    }
    private final RevColorSensorV3 colorSensor;
    private SampleType lastDetectedSample;
    private double lastDetectedDistance;
    private final float[] hsvValues = new float[3];


    public SampleDetector(HardwareMap hwMap) {
        colorSensor = hwMap.get(RevColorSensorV3.class, "sensor_color_distance");

    }

    public SampleType getDetectedSample() {
        lastDetectedDistance = colorSensor.getDistance(DistanceUnit.MM);
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);
        lastDetectedSample = SampleType.NONE;
        if (lastDetectedDistance <= 10) {
            if (hsvValues[0] >= 210 && hsvValues[0] <= 250) {
                lastDetectedSample = SampleType.BLUE;
            }
            else if (hsvValues[0] >= 10 && hsvValues[0] <= 40) {
                lastDetectedSample = SampleType.RED;
            }
            else if (hsvValues[0] >= 65 && hsvValues[0] <= 90) {
                lastDetectedSample = SampleType.YELLOW;
            }
        }


        return lastDetectedSample;
    }

    public void outputTelemetrySimple(Telemetry telemetry) {
        telemetry.addData("Detected Sample", lastDetectedSample);
    }

    public void outputTelemetry(Telemetry telemetry) {
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.addData("Distance (MM)", lastDetectedDistance);
        telemetry.addData("Detected Sample", lastDetectedSample);
    }
}
