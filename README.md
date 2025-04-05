# Beresheet_Landing_Simulation
Overview
This project aims to simulate the autonomous landing of the Beresheet spacecraft on the Moon. The simulation models various aspects of the spacecraft's behavior, including its vertical and horizontal velocities, angular velocity, and the influence of the Moon's gravity. The goal is to develop a PID-based control system that allows the spacecraft to land safely, minimizing its speed (both vertical and horizontal) and maximizing fuel efficiency.

Objective
The primary objective of this project is to simulate the landing of Beresheet on the Moon using a basic model, with an emphasis on safety and fuel conservation. The simulation is based on:

Modeling the physical aspects of the spacecraft.

Implementing a PID controller to manage the spacecraft's orientation and velocity during the descent.

Ensuring that the final landing has a vertical and horizontal velocity below 2.5 m/s, while retaining as much fuel as possible.

Task Breakdown
Part 1: Technical analysis of the reasons for the Beresheet crash based on available data.

Part 2: Development of the landing simulation.

2.1: Model the descent from a height of approximately 30 km, with a speed of around 1700 m/s.

2.2: Model the spacecraft's mass (changing with fuel consumption), vertical and horizontal velocities, and rotational velocity.

2.3: Implement a PID controller for attitude control and simulate the landing sequence.

2.4: Write a final report, including the best results achieved from the simulation.

Technologies Used
Java: The primary language used for the simulation.

PID Controller: A Proportional-Integral-Derivative (PID) controller is used to adjust the spacecraft's orientation and velocities.

CSV for Data: Data used to evaluate the simulation results is stored in a CSV file format.

Files Overview
Beresheet_103.java: Main simulation code for the Beresheet spacecraft.

PID.java: PID control class to manage spacecraft's velocity and orientation.

Moon.java: Class containing the physical properties of the Moon, including gravity and equatorial speed.

landing_data.csv: CSV file containing data used for testing and evaluating the simulation results.

Installation and Setup
Clone this repository to your local machine:

bash
Copy
git clone https://github.com/yourusername/Beresheet_Landing_Simulation.git
Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).

Compile and run the Java files to start the simulation. Make sure you have the necessary environment to run Java programs.
