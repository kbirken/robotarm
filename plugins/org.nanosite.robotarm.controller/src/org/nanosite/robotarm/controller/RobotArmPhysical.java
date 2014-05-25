package org.nanosite.robotarm.controller;

import static java.lang.Math.round;

import org.nanosite.robotarm.common.GeometryAL5D;
import org.nanosite.robotarm.common.IRobotArmDirectControl;

public class RobotArmPhysical implements IRobotArmDirectControl {
	private final boolean verbose;
	
	private final ISSC32 ssc32;

	public RobotArmPhysical(ISSC32 ssc32, boolean verbose) {
		this.verbose = verbose;
		this.ssc32 = ssc32;
	}

	/**
	 * Create a new instance of the RobotArmPhysical controller.
	 * 
	 * The port parameter should refer to the serial port name where the SSC32 controller is connected.
	 * If the port parameter is null, a low-level simulation will be created.
	 *  
	 * @param port the serial port or null (for low-level simulation)
	 * @return the actual instance, or null on error
	 */
	public static RobotArmPhysical createInstance (String port, boolean verbose) {
		ISSC32 ssc32 = null;
		if (port==null) {
			ssc32 = new SSC32Simu();
		} else {
			SerialConnection connection = new SerialConnection(port);
			if (! connection.open()) {
				System.err.println("ERROR: serial connection could not be opened, aborting!");
				return null;
			}
			ssc32 = new SSC32(connection);
		}

		return new RobotArmPhysical(ssc32, verbose);
	}


	@Override
	public boolean move(double base, double humerus, double ulna, double gripper, double rot, int t) {
		int a = (int)Math.round(base);
		int b = (int)Math.round(humerus);
		int c = (int)Math.round(ulna);
		int d = (int)Math.round(gripper);
		int r = (int)Math.round(rot);

		if (verbose)
			System.out.println("move(" + a + ", " + b + ", " + c + ", " + d + ", " + r + ")");

		// some basic geometric checks
		if (b+c < -50) {
			System.err.println("WARNING: b/c overrun!");
			return false;
		}

		// positive b, c and d should move robot upwards
		b = -b;
		d = -d;
		
		// rot is moving anti-clockwise
		r = -r;

		// to calibrate these numbers, use the calibration section in RobotArmApplication
		double ch0f = 455.0 / 45.0;
		double ch1f = 345.0 / 45.0;
		double ch2f = 390.0 / 45.0;
		double ch3f = 460.0 / 45.0;
		double ch5f = 480.0 / 45.0;

		int ch0 = (int)round(1500.0 - a*ch0f);
		int ch1 = (int)round(1490.0 - b*ch1f);
		int ch2 = (int)round(1420.0 - c*ch2f);
		int ch3 = (int)round(1600.0 - d*ch3f);
		int ch5 = (int)round(1475.0 - r*ch5f);
		checkRange(0, 800, 2200, ch0);
		checkRange(1, 900, 2100, ch1);
		checkRange(2, 900, 2100, ch2);
		checkRange(3, 500, 2500, ch3);
		checkRange(5, 500, 2500, ch5);

		if (verbose)
			System.out.println("cmd(" + ch0 + ", " + ch1 + ", " + ch2 + ", " + ch3 + ", " + ch5 + ")");

		return ssc32.move(ch0, ch1, ch2, ch3, ch5, t);
		
	}


	@Override
	public boolean grab(int openMM) {
		/* TODO: physical grip width is non linear with motor position! */
		double fClosed = 1.0 - (openMM/(double)GeometryAL5D.gripOpenMM);
		int ch = GeometryAL5D.gripOpen +
				(int)round(fClosed * (GeometryAL5D.gripClosed-GeometryAL5D.gripOpen));

		checkRange(4, GeometryAL5D.gripOpen, GeometryAL5D.gripClosed, ch);

		return ssc32.grip(ch);
	}


	@Override
	public void delay (int millisec) {
		ssc32.delay(millisec);
	}
	

	@Override
	public boolean shutdown() {
		ssc32.shutdown();
		return true;
	}


	private void checkRange (int id, int min, int max, int val) {
		if (val < min) {
			System.err.println("WARNING: channel " + id + " underrun (is " + val + ").");
			val = min;
		}
		if (val > max) {
			System.err.println("WARNING: channel " + id + " overrun (is " + val + ").");
			val = max;
		}
	}


}
