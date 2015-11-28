package org.nanosite.robotarm.examples;

import org.nanosite.robotarm.common.GeometryAL5D;
import org.nanosite.robotarm.common.ICalibration;

public class Calibration implements ICalibration {

	// to calibrate these numbers, use the calibration section in RobotArmApplication
	private final static Channel CH0 = new Channel(455.0 / 45.0, 1500.0, 800, 2200); 
	private final static Channel CH1 = new Channel(345.0 / 45.0, 1490.0, 850, 2100); 
	private final static Channel CH2 = new Channel(480.0 / 45.0, 1455.0, 820, 2100); 
	private final static Channel CH3 = new Channel(470.0 / 45.0, 1600.0, 500, 2500); 
	private final static Channel CH5 = new Channel(480.0 / 45.0, 1475.0, 500, 2500); 

	@Override
	public Channel getCh0() {
		return CH0;
	}

	@Override
	public Channel getCh1() {
		return CH1;
	}

	@Override
	public Channel getCh2() {
		return CH2;
	}

	@Override
	public Channel getCh3() {
		return CH3;
	}

	@Override
	public Channel getCh5() {
		return CH5;
	}

}
