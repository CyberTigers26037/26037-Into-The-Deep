package org.firstinspires.ftc.teamcode.subassembly;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class ViperSlideArm {
    public DcMotor armMotor; //the arm motor
    public DcMotor viperSlideMotor;
    private boolean useArmSlideCompensation = true;

    /* This constant is the number of encoder ticks for each degree of rotation of the arm.
        To find this, we first need to consider the total gear reduction powering our arm.
        First, we have an external 20t:100t (5:1) reduction created by two spur gears.
        But we also have an internal gear reduction in our motor.
        The motor we use for this arm is a 117RPM Yellow Jacket. Which has an internal gear
        reduction of ~50.9:1. (more precisely it is 250047/4913:1)
        We can multiply these two ratios together to get our final reduction of ~254.47:1.
        The motor's encoder counts 28 times per rotation. So in total you should see about 7125.16
        counts per rotation of the arm. We divide that by 360 to get the counts per degree. */
    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation

    /* These constants hold the position that the arm is commanded to run to.
    These are relative to where the arm was located when you start the OpMode. So make sure the
    arm is reset to collapsed inside the robot before you start the program.

    In these variables you'll see a number in degrees, multiplied by the ticks per degree of the arm.
    This results in the number of encoder ticks the arm needs to move in order to achieve the ideal
    set position of the arm. For example, the ARM_SCORE_SAMPLE_IN_LOW is set to
    160 * ARM_TICKS_PER_DEGREE. This asks the arm to move 160° from the starting position.
    If you'd like it to move further, increase that number. If you'd like it to not move
    as far from the starting position, decrease it. */

    final double ARM_COLLAPSED_INTO_ROBOT                     =    0;
    final double ARM_SCORE_SECOND_SPECIMEN_A                  =   55 * ARM_TICKS_PER_DEGREE;
    final double ARM_COLLECT                                  =    9 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER                            =   13 * ARM_TICKS_PER_DEGREE;
    final double ARM_CHILL                                    =   10 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN_LOW_CHAMBER               =   35 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN_HIGH_CHAMBER              =   80 * ARM_TICKS_PER_DEGREE;
    final double ARM_PICKUP_HANGING_SPECIMEN                  =   22 * ARM_TICKS_PER_DEGREE;
    final double ARM_PICKUP_FIELD_SPECIMEN                    =    4 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_LOW                      =   82 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_HIGH                     = 97.5 * ARM_TICKS_PER_DEGREE;
    final double ARM_ATTACH_HANGING_HOOK                      =  110 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT                              =    0 * ARM_TICKS_PER_DEGREE;
    final double ARM_MINIMUM                                  =    0 * ARM_TICKS_PER_DEGREE;
    final double ARM_MAXIMUM                                  =  115 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_HIGH_BACKWARDS           =  115 * ARM_TICKS_PER_DEGREE;
    final double ARM_PICKUP_PREPARE                           =   16 * ARM_TICKS_PER_DEGREE;
    final double ARM_PICKUP                                   =   12 * ARM_TICKS_PER_DEGREE;
    final double ARM_PICK_UP_FIRST_SPECIMEN_AUTO              =   11 * ARM_TICKS_PER_DEGREE;
    final double ARM_RAISE_VIPER_SLIDE_HIGHER                 =   20 * ARM_TICKS_PER_DEGREE;
    final double ARM_RAISED                                   =   38 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN_HIGH_CHAMBER_BACKWARDS    =   54 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_THIRD_SPECIMEN =   56 * ARM_TICKS_PER_DEGREE;
    final double ARM_PICKUP_SPECIMEN_WALL_HIGHER_FOR_AUTO     =   28 * ARM_TICKS_PER_DEGREE;
    /* A number in degrees that the triggers can adjust the arm position by */
    final double FUDGE_FACTOR = 15 * ARM_TICKS_PER_DEGREE;

    /* Variables that are used to set the arm to a specific position */
    double armPosition = ARM_COLLAPSED_INTO_ROBOT;
    double armPositionFudgeFactor;
    double armViperSlideComp = 0;


    final double VIPERSLIDE_TICKS_PER_MM = (111132.0 / 289.0) / 120.0;
    final double VIPERSLIDE_COLLAPSED                         =   0 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_SCORING_IN_HIGH_BASKET            = 460 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_SCORING_IN_LOW_BASKET             = 120 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_HIGH_CHAMBER                      =  67 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_LOW_CHAMBER                       =   0 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_PICKUP_SAMPLE                     = 102 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_FIRST_SAMPLE                      = 440 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_SECOND_SAMPLE                     = 420 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_THIRD_SAMPLE                      = 365 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_FIELD_SPECIMEN                    =  97 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_SCORING_IN_HIGH_BASKET_BACKWARDS  = 460 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_HIGH_CHAMBER_BACKWARDS            = 105 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_PICK_UP_FIRST_SPECIMEN_AUTO       = 385 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_RETRACT_SLIDE_NATHAN              =   0 * VIPERSLIDE_TICKS_PER_MM;
    final double VIPERSLIDE_HANG_SECOND_SPECIMEN              = 105 * VIPERSLIDE_TICKS_PER_MM;
    double viperSlidePosition = VIPERSLIDE_COLLAPSED;

    public ViperSlideArm(HardwareMap hardwareMap) {
        viperSlideMotor = hardwareMap.dcMotor.get("viperSlideMotor");
        armMotor        = hardwareMap.get(DcMotor.class, "armMotor"); //the arm motor

        /* Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to slow down
        much faster when it is coasting. This creates a much more controllable drivetrain. As the robot
        stops much quicker. */
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*This sets the maximum current that the control hub will apply to the arm before throwing a flag */
        ((DcMotorEx) armMotor).setCurrentAlert(5, CurrentUnit.AMPS);

        /* Before starting the armMotor. We'll make sure the TargetPosition is set to 0.
        Then we'll set the RunMode to RUN_TO_POSITION. And we'll ask it to stop and reset encoder.
        If you do not have the encoder plugged into this motor, it will not run in this code. */
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        viperSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        viperSlideMotor.setTargetPosition(0);
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setArmPositionFudgeFactor(double fudgeFactor) {
        fudgeFactor            = Range.clip(fudgeFactor,-1,1);
        armPositionFudgeFactor = FUDGE_FACTOR * fudgeFactor;
    }

    public void prepareToDropSampleLowBasket() {
        armPosition        = ARM_SCORE_SAMPLE_IN_LOW;
        viperSlidePosition = VIPERSLIDE_SCORING_IN_LOW_BASKET;
    }

    public void pickUpFirstSpecimen(){
        armPosition        = ARM_PICK_UP_FIRST_SPECIMEN_AUTO;
        viperSlidePosition = VIPERSLIDE_PICK_UP_FIRST_SPECIMEN_AUTO;
    }
    public void retractViperSlide() {
        viperSlidePosition = VIPERSLIDE_COLLAPSED;

    }
    public void retractViperSlideNathan() {
        viperSlidePosition = VIPERSLIDE_RETRACT_SLIDE_NATHAN;
        armPosition        = ARM_RAISE_VIPER_SLIDE_HIGHER;
    }
    public void prepareToPickUpWallSpecimenLilBitHigher() {
        armPosition        = ARM_PICKUP_SPECIMEN_WALL_HIGHER_FOR_AUTO;
    }
    public void prepareToHangHighSpecimenBackwardsForAuto() {
        armPosition        = ARM_SCORE_SECOND_SPECIMEN_A;
        viperSlidePosition = VIPERSLIDE_HANG_SECOND_SPECIMEN;
    }

    public void disableArmCompensation(){
        useArmSlideCompensation = false;

    }

    public void prepareToDriveArmUp(){
        viperSlidePosition = VIPERSLIDE_COLLAPSED;
        armPosition        = ARM_SCORE_SAMPLE_IN_HIGH;


    }

    public void setArmClearBarrier() {
        /* This is about 20° up from the collecting position to clear the barrier
        Note here that we don't set the wrist position or the intake power when we
        select this "mode", this means that the intake and wrist will continue what
        they were doing before we clicked left bumper. */
        armPosition = ARM_CLEAR_BARRIER;
    }
    public void chill(){
        armPosition =  ARM_CHILL;
        viperSlidePosition = VIPERSLIDE_COLLAPSED;

    }

    public void prepareToPickupVerticalSample() {
        /* This is the vertical claw pick-up/collecting arm position */
        armPosition        = ARM_COLLECT;
        viperSlidePosition = VIPERSLIDE_PICKUP_SAMPLE;
    }

    public void raiseArm() {
        armPosition        = ARM_RAISED;
    }


    public void pickUpVerticalSampleAuto(){
        armPosition        = ARM_PICKUP;
        viperSlidePosition = VIPERSLIDE_FIRST_SAMPLE;
    }
    public void pickUpVerticalSampleTwoAuto(){
        armPosition        = ARM_PICKUP;
        viperSlidePosition = VIPERSLIDE_SECOND_SAMPLE;
    }

    public void pickUpVerticalSampleThreeAuto(){
        armPosition        = ARM_PICKUP;
        viperSlidePosition = VIPERSLIDE_THIRD_SAMPLE;
    }
/*
// These methods are used in the old baskets auto
    public void prepareToPickupHorizontalSample(){

        armPosition        = ARM_COLLECT;
        viperSlidePosition = VIPERSLIDE_PICKUP_SAMPLE + 130;
    }
    public void pickUpHorizontalSampleAuto(){
        armPosition        = ARM_COLLECT + (0.335 * ARM_TICKS_PER_DEGREE);
        viperSlidePosition = VIPERSLIDE_PICKUP_SAMPLE + 470;

    }
    public void prepareToPickUpHorizontalPregame() {
        armPosition        = ARM_CLEAR_BARRIER;
        viperSlidePosition = VIPERSLIDE_COLLAPSED;
    }
    public void prepareToPickUpVerticalSampleAuto(){
        armPosition        = ARM_COLLECT + (2 * ARM_TICKS_PER_DEGREE);
        viperSlidePosition = VIPERSLIDE_COLLAPSED;

    }
    public void prepareToPickUp(){
        armPosition        = ARM_PICKUP_PREPARE;
        viperSlidePosition = VIPERSLIDE_FIRST_SAMPLE;
    }

 */

    public void prepareToDropSampleHighBasket() {
        /* This is the correct height to score the sample in the HIGH BASKET*/
        armPosition        = ARM_SCORE_SAMPLE_IN_HIGH;
        viperSlidePosition = VIPERSLIDE_SCORING_IN_HIGH_BASKET;
    }

    public void prepareToDropSampleHighBasketBackwards() {

        armPosition        = ARM_SCORE_SAMPLE_IN_HIGH_BACKWARDS;
        viperSlidePosition = VIPERSLIDE_SCORING_IN_HIGH_BASKET_BACKWARDS;
    }

    public void prepareToHangLowSpecimen() {
        armPosition        = ARM_SCORE_SPECIMEN_LOW_CHAMBER;
        viperSlidePosition = VIPERSLIDE_LOW_CHAMBER;
    }

    public void prepareToHangHighSpecimenBackwards(){

        armPosition        = ARM_SCORE_SPECIMEN_HIGH_CHAMBER_BACKWARDS;
        viperSlidePosition = VIPERSLIDE_HIGH_CHAMBER_BACKWARDS;

    }
    public void PrepareToHangThirdSpecimen(){

        armPosition        = ARM_SCORE_THIRD_SPECIMEN;
        viperSlidePosition = VIPERSLIDE_HIGH_CHAMBER_BACKWARDS;

    }
    public void park() {
        armPosition        = ARM_COLLAPSED_INTO_ROBOT;
        viperSlidePosition = VIPERSLIDE_COLLAPSED;
    }

    public void prepareToHangHighSpecimen() {
        /* This is the correct height to score SPECIMEN on the HIGH CHAMBER */
        armPosition        = ARM_SCORE_SPECIMEN_HIGH_CHAMBER;
        viperSlidePosition = VIPERSLIDE_HIGH_CHAMBER;
    }

    public void prepareToPickUpWallSpecimen() {
        armPosition = ARM_PICKUP_HANGING_SPECIMEN;
    }

    public void prepareToPickUpFieldSpecimen() {
        armPosition        = ARM_PICKUP_FIELD_SPECIMEN;
        viperSlidePosition = VIPERSLIDE_FIELD_SPECIMEN;
    }

    public void prepareToHang() {
        armPosition        = ARM_ATTACH_HANGING_HOOK;
        viperSlidePosition = VIPERSLIDE_COLLAPSED;
    }

    public void setArmWinch() {
        armPosition = ARM_WINCH_ROBOT;
    }

    public void adjustArm(double armDegree) {
        armPosition += armDegree * ARM_TICKS_PER_DEGREE;
        armPosition  = Range.clip(armPosition, ARM_MINIMUM, ARM_MAXIMUM);
    }

    public void adjustViperSlidePosition(double slideMm) {
        viperSlidePosition += slideMm * VIPERSLIDE_TICKS_PER_MM;
    }
    public void armClearBarrierIfBelow(){
        if (armPosition < ARM_CLEAR_BARRIER) {
            armPosition = ARM_CLEAR_BARRIER;
        }
    }

    public void execute(){
        execute(1.0, 1.0);

    }

    public void execute(double armSpeed, double slideSpeed) {
        /*
        This is probably my favorite piece of code on this robot. It's a clever little software
        solution to a problem the robot has.
        This robot has an extending viper slide on the end of an arm shoulder. That arm shoulder should
        run to a specific angle, and stop there to collect from the field. And the angle that
        the shoulder should stop at changes based on how long the arm is (how far the slide is extended)
        so here, we add a compensation factor based on how far the viper slide is extended.
        That comp factor is multiplied by the number of mm the slide is extended, which
        results in the number of degrees we need to fudge our arm up by to keep the end of the arm
        the same distance from the field.
        Now we don't need this to happen when the arm is up and in scoring position. So if the arm
        is above 45°, then we just set armViperSlideComp to 0. It's only if it's below 45° that we set it
        to a value.
         */

        if (useArmSlideCompensation && (armPosition < 45 * ARM_TICKS_PER_DEGREE)) {
            armViperSlideComp = (0.25568 * viperSlidePosition);
        }
        else {
            armViperSlideComp = 0;
        }

        /* Here we set the target position of our arm to match the variable that was selected
        by the driver. We add the armPosition Variable to our armPositionFudgeFactor, before adding
        our armViperSlideComp, which adjusts the arm height for different viper slide extensions.
        We also set the target velocity (speed) the motor runs at, and use setMode to run it.*/
        armMotor.setTargetPosition((int) (armPosition + armPositionFudgeFactor + armViperSlideComp));
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ((DcMotorEx) armMotor).setVelocity(2100 * armSpeed);

        /*here we check to see if the viper slide is trying to go higher than the maximum extension.
         *if it is, we set the variable to the max.
         */
        if (viperSlidePosition > VIPERSLIDE_SCORING_IN_HIGH_BASKET) {
            viperSlidePosition = VIPERSLIDE_SCORING_IN_HIGH_BASKET;
        }
        //same as above, we see if the viper slide is trying to go below 0, and if it is, we set it to 0.
        if (viperSlidePosition < 0) {
            viperSlidePosition = 0;
        }

        viperSlideMotor.setTargetPosition((int) (viperSlidePosition));
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ((DcMotorEx) viperSlideMotor).setVelocity(2100 * slideSpeed);
    }

    public boolean isBusy(){

        return viperSlideMotor.isBusy() || armMotor.isBusy();

    }
    public boolean isSlideAndArmWithinRange(double slideMm, double armDegrees){
        return isArmWithinRange(armDegrees) && isSlideWithinRange(slideMm);
    }
    public boolean isArmWithinRange(double  armDegrees){
        double targetArmPosition = armPosition/ARM_TICKS_PER_DEGREE;
        double actualArmPosition = armMotor.getCurrentPosition()/ARM_TICKS_PER_DEGREE;

        return (Math.abs(actualArmPosition - targetArmPosition) <= armDegrees);

    }
    public boolean isSlideWithinRange(double slideMm){
        double targetSlidePosition = viperSlidePosition/VIPERSLIDE_TICKS_PER_MM;
        double actualSlidePosition = viperSlideMotor.getCurrentPosition()/VIPERSLIDE_TICKS_PER_MM;

        return (Math.abs(actualSlidePosition - targetSlidePosition) <= slideMm);
    }

    public void outputTelemetrySimple(Telemetry telemetry) {
        telemetry.addData("arm Position (degrees): ", armPosition/ARM_TICKS_PER_DEGREE);
        telemetry.addData("slide Position (mm) : ", viperSlidePosition/VIPERSLIDE_TICKS_PER_MM);
    }

    public void outputTelemetry(Telemetry telemetry) {
        /* Check to see if our arm is over the current limit, and report via telemetry. */
        if (((DcMotorEx) armMotor).isOverCurrent()) {
            telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
        }

        telemetry.addData("arm Target Position: ", armMotor.getTargetPosition());
        telemetry.addData("arm Position (degrees): ", armPosition/ARM_TICKS_PER_DEGREE);
        telemetry.addData("arm Encoder: ", armMotor.getCurrentPosition());
        telemetry.addData("slide variable", viperSlidePosition);
        telemetry.addData("slide Position (mm) : ", viperSlidePosition/VIPERSLIDE_TICKS_PER_MM);
        telemetry.addData("slide Target Position", viperSlideMotor.getTargetPosition());
        telemetry.addData("slide current position", viperSlideMotor.getCurrentPosition());
        telemetry.addData("slideMotor Current:",((DcMotorEx) viperSlideMotor).getCurrent(CurrentUnit.AMPS));
    }
}
