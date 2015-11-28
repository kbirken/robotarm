package org.nanosite.robotarm.common;

import static java.lang.Math.acos;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

public class InverseKinematicsControl implements IRobotArmPosControl {

	private final boolean verbose;
	
	private final IRobotArmDirectControl base;
	
	public InverseKinematicsControl(IRobotArmDirectControl base, boolean verbose) {
		this.verbose = verbose;
		this.base = base;
	}

	@Override
	public boolean grab(int openMM) {
		return base.grab(openMM);
	}

	@Override
	public void delay(int millisec) {
		base.delay(millisec);
	}

	@Override
	public boolean reset() {
		return base.reset();
	}

	@Override
	public boolean shutdown() {
		return base.shutdown();
	}

	@Override
	public boolean move(double x, double y, double z, double aa, double rot, int t) {
		return ik(x, y, z, aa, rot, t);
	}


	/**
	 * Inverse kinematics computation for robot arm.
	 * 
	 * This computes an angle for each motor in order to position the center of the gripper
	 * under a given angle of attack.
	 *  
	 * @param x
	 * @param y
	 * @param z
	 * @param aa the angle of attack
	 * @param rot the wrist rotation angle
	 * @param t the time for moving to the new position
	 * @return true if successful, false on error
	 */
	private boolean ik (double x, double y, double z, double aa, double rot, int t) {
		InverseKinematics ik = new InverseKinematics(x, y, z, aa, verbose);
		if (! ik.isValid()) {
			System.err.println("ERROR: invalid movement!");
			return false;
		}
		
		if (Double.isNaN(rot)) {
			System.err.println("ERROR: invalid wrist rotation!");
			return false;
		}

		return base.move(ik.getA(), ik.getB(), ik.getC(), ik.getD(), rot, t);
	}
				
}
