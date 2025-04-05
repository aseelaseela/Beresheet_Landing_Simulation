# Beresheet_Landing_Simulation

## Overview

This project simulates the **autonomous landing of the Beresheet spacecraft** on the Moon. The simulation models various aspects of the spacecraft's behavior, such as its **vertical and horizontal velocities**, **angular velocity**, and the influence of the **Moon's gravity**. The primary goal is to develop a **PID-based control system** that enables the spacecraft to land safely, minimizing both its vertical and horizontal velocities while maximizing fuel efficiency.

## Objective

The primary objective of this project is to simulate the **safe landing of Beresheet on the Moon** using a basic model, with a strong focus on:
- **Safety**: Ensuring the spacecraft lands with minimal speed (both vertical and horizontal) to avoid crashing.
- **Fuel Conservation**: Retaining as much fuel as possible upon landing.

The simulation includes:
- **Modeling the spacecraft’s physical aspects**.
- **Implementing a PID controller** to manage the spacecraft’s orientation and velocities during descent.
- Ensuring that the final landing has both **vertical and horizontal velocities below 2.5 m/s**, with as much fuel remaining as possible.

## Task Breakdown

### Part 1: Technical Analysis
- Analyzing the technical reasons behind the **Beresheet crash** based on available data.

### Part 2: Development of the Landing Simulation
1. **Modeling the Descent**  
   - Simulate the descent from a height of **30 km** above the Moon, at a speed of approximately **1700 m/s**.
   
2. **Spacecraft Modeling**  
   - Model the spacecraft’s **mass** (changing with fuel consumption), **vertical and horizontal velocities**, and **rotational velocity**.
   
3. **PID Control Implementation**  
   - Implement a **PID controller** to adjust the spacecraft's attitude and velocities.
   - Simulate the landing sequence using this controller to achieve a safe landing.

4. **Final Report**  
   - Write a detailed **final report** including the **best results** achieved from the simulation.

## Technologies Used

- **Java**: The primary language used to implement the simulation.
- **PID Controller**: A Proportional-Integral-Derivative (PID) controller is used to adjust the spacecraft’s orientation and velocities during descent.
- **CSV for Data**: The results of the simulation are stored in a **CSV file format**, allowing for easy evaluation of the data.

## Files Overview

- **Beresheet_103.java**: The **main simulation code** for the Beresheet spacecraft.
- **PID.java**: The **PID control class**, which manages the spacecraft’s velocity and orientation during descent.
- **Moon.java**: A class that contains the **physical properties of the Moon**, including gravity and equatorial speed.
- **landing_data.csv**: A **CSV file** that contains data used for testing and evaluating the simulation results.

## Installation and Setup

Follow the steps below to set up the project on your local machine:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/Beresheet_Landing_Simulation.git
   ```

2. Open the project in your preferred **IDE** (e.g., **IntelliJ IDEA**, **Eclipse**).

3. Compile and run the Java files to start the simulation.

4. Ensure that you have the necessary environment to run Java programs.
   
This project was developed by Aseel Ahmad and Ben.
Feel free to fork this repository, make improvements, and submit **pull requests**. Contributions to optimize the PID controller or enhance the spacecraft model are welcome!
