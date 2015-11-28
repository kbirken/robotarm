package org.nanosite.robotarm.controller.blackbox.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.nanosite.robotarm.common.ICalibration;
import org.nanosite.robotarm.common.ICalibration.Channel;
import org.nanosite.robotarm.controller.blackbox.IRobotArmLibrary;
import org.nanosite.robotarm.controller.blackbox.RobotArmLibrary;

/**
 * This class implements some end-to-end tests for the RobotArmLibrary.
 */
public class RobotArmLibraryTest {

	IRobotArmLibrary library = null;

	ICalibration calibration = new ICalibration() {
		@Override
		public Channel getCh0() {
			return new Channel(455.0 / 45.0, 1500.0, 800, 2200);
		}

		@Override
		public Channel getCh1() {
			return new Channel(345.0 / 45.0, 1490.0, 850, 2100);
		}

		@Override
		public Channel getCh2() {
			return new Channel(480.0 / 45.0, 1455.0, 820, 2100);
		}

		@Override
		public Channel getCh3() {
			return new Channel(470.0 / 45.0, 1600.0, 500, 2500);
		}

		@Override
		public Channel getCh5() {
			return new Channel(480.0 / 45.0, 1475.0, 500, 2500);
		}
	};

	
	@Before
	public void init() {
		library = new RobotArmLibrary(calibration);
	}
	
	@Test
	public void testMove() {
		String cmd = library.getMoveCommand(100.0, 100.0, 100.0, 90.0, 0.0, 1000);
		System.out.println("cmd: " + cmd);
		assertEquals("#0P1045#1P1630#2P1669#3P678#5P1475T1000", cmd);
	}

	@Test
	public void testGrab() {
		String cmd = library.getGrabCommand(20);
		System.out.println("cmd: " + cmd);
		assertEquals("#4P1254T500", cmd);
	}

}
