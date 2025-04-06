package Drone;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import simulation.Moon;

public class Bereshit_103 {
    public static final double WEIGHT_EMP = 165;
    public static final double WEIGHT_FUEL = 420;
    public static final double WEIGHT_FULL = WEIGHT_EMP + WEIGHT_FUEL;
    public static final double MAIN_ENG_F = 430;
    public static final double SECOND_ENG_F = 25;
    public static final double MAIN_BURN = 0.15;
    public static final double SECOND_BURN = 0.009;
    public static final double ALL_BURN = MAIN_BURN + 8 * SECOND_BURN;

    // Vertical PID constants
    private static final double VS_KP = 3.0;
    private static final double VS_KI = 0.01;
    private static final double VS_KD = 1.0;
    private static final double VS_MAX_I = 5;
        
    // More aggressive horizontal PID constants
    private static final double HS_KP = 10.0;  // Increased from 8.0
    private static final double HS_KI = 0.05;   // Increased from 0.02
    private static final double HS_KD = 4.0;    // Increased from 3.0
    private static final double HS_MAX_I = 15;   // Increased from 10

    public static double accMax(double weight) {
        return acc(weight, true, 8);
    }

    public static double acc(double weight, boolean main, int seconds) {
        double t = 0;
        if (main) { t += MAIN_ENG_F; }
        t += seconds * SECOND_ENG_F;
        return t / weight;
    }

    public static void main(String[] args) {
        try {
            // Create log file
            PrintWriter log = new PrintWriter(new FileWriter("landing_data.csv"));
            log.println("Time,VS,HS,Distance,Altitude,Angle,Weight,Acceleration,Throttle,Fuel"); // CSV header

            System.out.println("Simulating Bereshit's Landing with PID:");

            double vs = 43;
            double hs = 1700;
            double dist = 181000;
            double ang = 58.3;
            double alt = 30000;
            double time = 0;
            double dt = 1;
            double acc = 0;
            double fuel = 216.06;
            double weight = WEIGHT_EMP + fuel;
            double NN = 0.7;
            double hs_target, vs_target, hs_Error, vs_Error, hsControl, vsControl;

            PID pid_vs = new PID(VS_KP, VS_KI, VS_KD, VS_MAX_I);
            PID pid_hs = new PID(HS_KP, HS_KI, HS_KD, HS_MAX_I);

            System.out.println("time, vs, hs, dist, alt, ang, weight, acc, NN, fuel");

            while (alt > 0) {
                if (true) { //time % 10 == 0 || alt < 100
                    // Print values for console logging
                    System.out.printf("%.1f, %.2f, %.2f, %.1f, %.1f, %.1f, %.1f, %.2f, %.2f, %.1f%n",
                        time, vs, hs, dist, alt, ang, weight, acc, NN,fuel);

                    // Write values to CSV file
                    log.printf("%.1f,%.2f,%.2f,%.1f,%.1f,%.1f,%.1f,%.2f,%.2f,%.1f%n",
                        time, vs, hs, dist, alt, ang, weight, acc, NN, fuel);
                }

                // Calculate targets - Vertical targets unchanged
                if (alt > 10000) {
                    vs_target = vs*0.7;
                    hs_target = hs * 0.7;  // Reduce by 30% each iteration
                    ang = Math.min(77, ang + 4);  // Very fast angle change for braking
                } 
                else if (alt > 2000) {
                    vs_target = vs*0.5;
                    hs_target = hs * 0.5;  // Reduce by 50% each iteration
                    ang = Math.max(0, ang - 0.9);  // Faster rotation to vertical
                }
                else if (alt > 100) {
                    vs_target = 5;
                    hs_target = 0;  // Must stop completely
                    ang = Math.max(0, ang - 3);  // Very fast rotation to vertical
                }
                else {
                    vs_target = 2;
                    hs_target = 0;
                    ang = 0;
                }

                // Vertical control
                vs_Error = vs - vs_target;
                vsControl = pid_vs.update(vs_Error, dt);
                
                // New aggressive horizontal control
                hs_Error = hs - hs_target;
                hsControl = pid_hs.update(hs_Error, dt);  // Strong boost to horizontal control
                
                // Calculate thrust components with priority to horizontal braking
                double thrustAngle = Math.toRadians(ang);
                double maxThrust = MAIN_ENG_F + 8*SECOND_ENG_F;
                
                // Calculate maximum possible horizontal thrust
                double maxHorizontalThrust = maxThrust * Math.sin(thrustAngle);
                double horizontalNN = 0;
                if (hs > 1) {  // Always apply horizontal braking if any speed remains
                    horizontalNN = Math.min(1, (fuel * hsControl) / maxHorizontalThrust);
                }
                
                // Vertical thrust calculation
                double requiredVerticalForce = 0.5* fuel * (Moon.ACC + vsControl);
                double availableVerticalThrust = maxThrust * Math.cos(thrustAngle);
                double verticalNN = Math.min(1, requiredVerticalForce / availableVerticalThrust);
                
                // Use maximum required throttle with horizontal priority
                NN = (hs > 10) ? Math.max(horizontalNN, verticalNN * 0.7) : verticalNN;
                NN = Math.min(1, Math.max(0.3, NN));

                // Physics calculations with horizontal priority
                if (NN > 0 && fuel > 0) {
                    acc = NN * accMax(weight);
                    double ang_rad = Math.toRadians(ang);
                    double h_acc = Math.sin(ang_rad) * acc;
                    double v_acc = Math.cos(ang_rad) * acc;
                    double moon_acc = Moon.getAcc(hs);

                    // Update speeds with boosted horizontal braking
                    hs = Math.max(0, hs - h_acc * dt * 2.0);  // Double effectiveness of horizontal braking
                    double netVerticalAcc = Math.abs(v_acc - moon_acc);
                    double requiredDeceleration = (vs * vs) / (2 * alt + 0.0001);  // prevent div by 0
                    double scaleFactor = requiredDeceleration / (netVerticalAcc + 0.0001);  // also safe
                    vs -= netVerticalAcc * dt * scaleFactor;

                    vs = Math.max(0, vs);  // Prevent negative vertical speed
                } else {
                    acc = 0;
                }

                // Update position and fuel
                time += dt;
                if (fuel > 0) {
                    double dw = dt * ALL_BURN * NN;
                    fuel -= dw;
                    weight = WEIGHT_EMP + fuel;
                }
                
                dist -= hs * dt;
                alt -= vs * dt;
                
                // Emergency full braking when close to ground with horizontal speed
                if (alt < 200 && hs > 5) {
                    ang = 30;  // Significant angle increase for braking
                    NN = Math.min(1, NN + 0.4);
                }
                
                // Safety timeout
                if (time > 7000) {
                    System.out.println("Emergency break - simulation taking too long");
                    break;
                }
            }

            log.close(); //  Close file after writing data

            // Final result
            System.out.println("Landing complete!");
            System.out.printf("Final values: Time=%.1f, VS=%.2f, HS=%.2f, Alt=%.1f, Fuel=%.1f%n",
                time, vs, hs, alt, fuel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
