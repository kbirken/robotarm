package org.nanosite.robotarm.controller.blackbox;

import java.util.List;

import org.nanosite.robotarm.common.ICalibration;
import org.nanosite.robotarm.common.InverseKinematics;
import org.nanosite.robotarm.controller.PhysicalMapper;
import org.nanosite.robotarm.controller.SSC32Protocol;

public class RobotArmLibrary implements IRobotArmLibrary {

	private final boolean verbose;
	
	private final PhysicalMapper mapper;
	
	public RobotArmLibrary(ICalibration calibration) {
		this(calibration, false);
	}

	public RobotArmLibrary(ICalibration calibration, boolean verbose) {
		this.mapper = new PhysicalMapper(calibration);
		this.verbose = verbose;
	}

	@Override
	public String getMoveCommand(double x, double y, double z, double aa, double rot, int t) {
		InverseKinematics ik = new InverseKinematics(x, y, z, aa);
		if (! ik.isValid())
			return null;
		
		List<Integer> mapped = mapper.move(ik.getA(), ik.getB(), ik.getC(), ik.getD(), rot);
		if (mapped==null)
			return null;
		
		return SSC32Protocol.buildMoveCommand(
				mapped.get(0),
				mapped.get(1),
				mapped.get(2),
				mapped.get(3),
				mapped.get(4),
				t);
	}

	@Override
	public String getGrabCommand(int openMM) {
		Integer mapped = mapper.grab(openMM);
		if (mapped==null)
			return null;

		return SSC32Protocol.buildGrabCommand(mapped, 500);
	}

}
