package Drone;

public class PID {
    private double kp; // Proportional gain
    private double ki; // Integral gain
    private double kd; // Derivative gain
    private double maxIntegral; // Maximum integral value to prevent windup
    private double integral; // Accumulated error
    private double previousError; // Error from previous update
    private double previousDerivative; // Smoothed derivative
    private boolean firstRun; // Flag to handle first derivative calculation
    
    public PID(double kp, double ki, double kd, double maxIntegral) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.maxIntegral = maxIntegral;
        
        this.integral = 0;
        this.previousError = 0;
        this.previousDerivative = 0;  // Smoothing derivative
        this.firstRun = true;
    }
    
    public double update(double error, double dt) {
        // Proportional term
        double p = kp * error;
        
        // Integral term with windup prevention
        if (Math.abs(error) > 1e-3) { // Avoid integrating small errors
            integral += error * dt;
            integral = Math.max(-maxIntegral, Math.min(maxIntegral, integral)); // Clamp integral
        }
        double i = ki * integral;
        
        // Derivative term with smoothing
        double d = 0;
        if (!firstRun) {
            double rawDerivative = (error - previousError) / dt;
            d = kd * (0.9 * previousDerivative + 0.1 * rawDerivative); // Apply smoothing factor
        } else {
            firstRun = false;
        }

        // Save values for next update
        previousError = error;
        previousDerivative = d;
        
        return p + i + d;
    }
    
    public void reset() {
        integral = 0;
        previousError = 0;
        previousDerivative = 0;
        firstRun = true;
    }
    
    public void setGains(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }
}
