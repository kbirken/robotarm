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
		// robot arm geometry
		double s1 = GeometryAL5D.HUMERUS_LENGTH;
		double s2 = GeometryAL5D.ULNA_LENGTH;
		double s3 = GeometryAL5D.HAND_LENGTH;

		if (verbose)
				System.out.println("ik(x=" + x + ", y=" + y + ", z=" + z + ", aa=" + aa + ", rot=" + rot + ")");

		// turn base into xy-plane
		double a = toDegrees(atan2(y, x));
		double x2 = sqrt(x*x + y*y);
		if (verbose)
			System.out.println("x=" + x + ", y=" + y + ", atan(x/y)=" + a + ", x2=" + x2);

		double xHand = s3 * cos(toRadians(aa));
		double zHand = s3 * sin(toRadians(aa));
		double x1 = x2 - xHand;
		double zrel = (z + zHand) - GeometryAL5D.BASE_HEIGHT;
		if (verbose) {
			logNumber("x1", x1);
			logNumber("zrel", zrel);
		}
		
		// compute b and c inside xy-plane
		double sw = sqrt(x1*x1 + zrel*zrel);
		if (verbose) {
			logNumber("sw", sw);
		}

		// b1: angle SW->ground
		double b1 = toDegrees(atan2(zrel, x1));
		
		// b2: angle humerus->SW
		double b2 = toDegrees(acos((s1*s1 + sw*sw - s2*s2) / (2*s1*sw)));
		
		double b = b1+b2-90;
		double c = toDegrees(acos((s1*s1 + s2*s2 - sw*sw) / (2*s1*s2))) - 90;
		double d = (-aa) - (b+c);
		if (verbose) {
			logNumber("b1", b1);
			logNumber("b2", b2);
			logNumber("b", b);
			logNumber("c", c);
			logNumber("d", d);
		}

		if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c)
				|| Double.isNaN(d) || Double.isNaN(rot)) {
			System.err.println("ERROR: invalid movement!");
			return false;
		} else {
			return base.move(a, b, c, d, rot, t);
		}
	}

	
	private void logNumber(String name, double value) {
		System.out.format("%s = %5.1f\n", name, value);
	}
				
				
}
