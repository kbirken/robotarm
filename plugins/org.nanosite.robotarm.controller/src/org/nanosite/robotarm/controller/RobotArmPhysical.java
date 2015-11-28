package org.nanosite.robotarm.controller;

import java.util.List;

import org.nanosite.robotarm.common.ICalibration;
import org.nanosite.robotarm.common.IRobotArmDirectControl;

public class RobotArmPhysical implements IRobotArmDirectControl {
	private final boolean verbose;
	
	private final ISSC32 ssc32;
	private final PhysicalMapper mapper;

	public RobotArmPhysical(ISSC32 ssc32, ICalibration calibration, boolean verbose) {
		this.verbose = verbose;
		this.ssc32 = ssc32;
		this.mapper = new PhysicalMapper(calibration);
	}

	@Override
	public boolean move(double base, double humerus, double ulna, double hand, double rot, int t) {
		if (verbose)
			System.out.format("move(%5.1f, %5.1f, %5.1f, %5.1f, %5.1f)\n", base, humerus, ulna, hand, rot);

		List<Integer> mapped = mapper.move(base, humerus, ulna, hand, rot);
		if (mapped==null) {
			return false;
		}

		int ch0 = mapped.get(0);
		int ch1 = mapped.get(1);
		int ch2 = mapped.get(2);
		int ch3 = mapped.get(3);
		int ch5 = mapped.get(4);
		if (verbose)
			System.out.println("cmd(" + ch0 + ", " + ch1 + ", " + ch2 + ", " + ch3 + ", " + ch5 + ")");

		return ssc32.move(ch0, ch1, ch2, ch3, ch5, t);
		
	}


	@Override
	public boolean grab(int openMM) {
		int ch = mapper.grab(openMM);
		return ssc32.grab(ch);
	}


	@Override
	public void delay(int millisec) {
		ssc32.delay(millisec);
	}
	

	@Override
	public boolean reset() {
		return ssc32.reset();
	}

	@Override
	public boolean shutdown() {
		ssc32.shutdown();
		return true;
	}

}
