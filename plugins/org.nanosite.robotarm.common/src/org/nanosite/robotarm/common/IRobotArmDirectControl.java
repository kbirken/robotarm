package org.nanosite.robotarm.common;

public interface IRobotArmDirectControl extends IRobotArmBaseControl {

	/**
	 * Move motors to given angles (in degrees)
	 * 
	 * @return true if successful, false on error
	 */
	public abstract boolean move(double base, double humerus, double ulna, double hand, double rot, int t);
}
