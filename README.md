robotarm
========

This project provides Java software for controlling and simulating a lynxmotion AL5D robot arm with Java.

## Details

The <em>simulator</em> part will show a 3D visualization of the simulated robot arm.
Java 3D (J3D) is used for rendering.

The <em>controller</em> part controls a real AL5D robot arm using an SSC32 board.

The <em>common</em> part contains stuff needed by both, simulator and controller. This includes:
- the geometry of an AL5D robot arm
- a (simple) algorithm for inverse kinematics
- some Java interfaces which abstract from simulation or physical control

