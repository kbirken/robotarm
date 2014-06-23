package org.nanosite.robotarm.common;

public interface IRobotArmBaseControl {

	/**
	 * Set gripper to a defined gripping width.
	 * 
	 * @param openMM the target gripping width (in millimeters)
	 * @return true if successful, false on error
	 */
	public abstract boolean grab(int openMM);

	/**
	 * Delay execution for a defined amount of time.
	 * 
	 * @param millisec delay time in milliseconds
	 */
	public abstract void delay(int millisec);

	/**
	 * Move the robot arm to a safe position.
	 */
	public abstract boolean reset();

	/**
	 * Shutdown the robot arm.
	 * 
	 *  Usually this will move the arm to a safe position first and then shutdown the hardware.
	 */
	public abstract boolean shutdown();

}