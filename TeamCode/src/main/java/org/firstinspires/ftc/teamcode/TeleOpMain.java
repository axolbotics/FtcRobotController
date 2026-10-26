package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOpMain")
public class TeleOpMain extends LinearOpMode
{
    DcMotor blDrive = null;
    DcMotor brDrive = null;
    DcMotor flDrive = null;
    DcMotor frDrive = null;
//    DcMotor intake = null;

    // @Override
    public void runOpMode()
    {
        blDrive = hardwareMap.get(DcMotor.class, "blDrive");
        brDrive = hardwareMap.get(DcMotor.class, "brDrive");
        flDrive = hardwareMap.get(DcMotor.class, "flDrive");
        frDrive = hardwareMap.get(DcMotor.class, "frDrive");
//        intake = hardwareMap.get(DcMotor.class, "intake");

        // Left side always forward, right side always reserve -- if this gives problems, flip it around
        blDrive.setDirection(DcMotor.Direction.REVERSE);
        brDrive.setDirection(DcMotor.Direction.FORWARD);
        flDrive.setDirection(DcMotor.Direction.REVERSE);
        frDrive.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive())
        {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double brDrivePower =  y + x + rx;
            double blDrivePower =  y - x + rx;
            double frDrivePower =  y - x - rx;
            double flDrivePower =  y + x - rx;

            double max = Math.max(Math.abs(flDrivePower), Math.abs(blDrivePower));
            max = Math.max(max, Math.abs(frDrivePower));
            max = Math.max(max, Math.abs(brDrivePower));

            if (max > 1.0)
            {
                flDrivePower /= max;
                blDrivePower /= max;
                frDrivePower /= max;
                brDrivePower /= max;
            }

            double speedMultiplier = 0.60;
            if (gamepad1.left_bumper)
            {
                speedMultiplier = 0.30;
            }



            flDrive.setPower(flDrivePower * speedMultiplier);
            blDrive.setPower(blDrivePower * speedMultiplier);
            frDrive.setPower(frDrivePower * speedMultiplier);
            brDrive.setPower(brDrivePower * speedMultiplier);

 /*           if (gamepad1.right_trigger > 0.1 )
            {
                intake.setPower(1.0);
            } else if (gamepad1.left_trigger > 0.1)
            {
                intake.setPower(-1.0);
            } else {
                intake.setPower(0.0);
            }*/

            telemetry.addData("Joystick Turn Input", "%.2f", rx);
        }
    }
}
