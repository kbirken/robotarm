package org.nanosite.robotarm.controller.blackbox;

/**
 * This is a sketch of the interface of the library to compute SSC32
 * commands (strings) from RobotArm commands.
 */
public interface IRobotArmLibrary {

	/**
	 * Create a SSC32 protocol command for robot arm control from various
	 * input data (3D position, angle of attack, wrist rotation and time for the move).
	 */
	public String getMoveCommand(double x, double y, double z, double aa, double rot, int t);

	/**
	 * Create a SSC32 protocol command for gripper control.
	 */
	public String getGrabCommand(int openMM);

}
