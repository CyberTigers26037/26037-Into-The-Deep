package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subassembly.Pincher;
import org.firstinspires.ftc.teamcode.subassembly.SampleDetector;

@SuppressWarnings("unused")
@TeleOp
public class TestSampleDetector extends OpMode {
    private SampleDetector sampleDetector;
    private Pincher pincher;

    @Override
    public void init() {
        pincher = new Pincher(hardwareMap);
        sampleDetector = new SampleDetector(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad2.x) {
            pincher.zero();
        }

        if (gamepad2.a) {
            pincher.open();
        }

        if (gamepad2.b) {
            pincher.close();
        }
        SampleDetector.SampleType detectedSampleType = sampleDetector.getDetectedSample();
        if (detectedSampleType != SampleDetector.SampleType.NONE){
            pincher.close();
        }
        sampleDetector.outputTelemetry(telemetry);
    }
}
