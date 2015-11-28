package org.nanosite.robotarm.common;

import static java.lang.Math.acos;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

/**
 * This class represents a inverse kinematics computation.
 */
public class InverseKinematics {

	/** Is this the result of a valid inverse kinematics computation? */
	private final boolean valid;
	
	/** Base angle. */
	private final double a;
	
	/** Humerus angle. */
	private final double b;
	
	/** Ulna angle. */
	private final double c;
	
	/** Hand angle. */
	private final double d;
	
	public InverseKinematics(double x, double y, double z, double aa) {
		this(x, y, z, aa, false);
	}
	
	public InverseKinematics(double x, double y, double z, double aa, boolean verbose) {
		// robot arm geometry
		double s1 = GeometryAL5D.HUMERUS_LENGTH;
		double s2 = GeometryAL5D.ULNA_LENGTH;
		double s3 = GeometryAL5D.HAND_LENGTH;

		if (verbose)
				System.out.println("ik(x=" + x + ", y=" + y + ", z=" + z + ", aa=" + aa + ")");

		// turn base into xy-plane
		this.a = toDegrees(atan2(y, x));
		double x2 = sqrt(x*x + y*y);
		if (verbose)
			System.out.println("x=" + x + ", y=" + y + ", atan(x/y)=" + a + ", x2=" + x2);

		double xHand = s3 * cos(toRadians(aa));
		double zHand = s3 * sin(toRadians(aa));
		if (verbose) {
			logNumber("xHand", xHand);
			logNumber("zHand", zHand);
		}
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
		
		b = b1+b2-90;
		c = toDegrees(acos((s1*s1 + s2*s2 - sw*sw) / (2*s1*s2))) - 90;
		d = (-aa) - (b+c);
		if (verbose) {
			logNumber("b1", b1);
			logNumber("b2", b2);
			logNumber("b", b);
			logNumber("c", c);
			logNumber("d", d);
		}

		if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)) {
			System.err.println("ERROR: invalid movement!");
			valid = false;
		} else {
			valid = true;
		}
	}

	public boolean isValid() {
		return valid;
	}
	
	public double getA() {
		return a;
	}
	
	public double getB() {
		return b;
	}
	
	public double getC() {
		return c;
	}
	
	public double getD() {
		return d;
	}
	
	private void logNumber(String name, double value) {
		System.out.format("%s = %5.1f\n", name, value);
	}
				
}
