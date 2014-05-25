package org.nanosite.robotarm.common;

public interface IRobotArmPosControl extends IRobotArmBaseControl {

	/**
	 * Move robot arm to a given cartesian position (in millimeters)
	 * 
	 * The parameters x, y, z define the position of the center of the gripping area
	 * in a Cartesian coordinate system. The origin of this coordinate system is at the 
	 * bottom center of the robot arm base.
	 *     
	 * @param x the x position
	 * @param y the y position
	 * @param z the z position
	 * @param aa the angle of attack of the gripper at the target position
	 * @param rot the wrist rotate angle
	 * @param t the time interval for moving to the new position
	 * @return true if successful, false on error
	 */
	public abstract boolean move(double x, double y, double z, double aa, double rot, int t);
}
