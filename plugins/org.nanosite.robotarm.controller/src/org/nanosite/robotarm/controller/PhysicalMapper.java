package org.nanosite.robotarm.controller;

import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.List;

import org.nanosite.robotarm.common.GeometryAL5D;
import org.nanosite.robotarm.common.ICalibration;

public class PhysicalMapper {
	
	private final ICalibration cal;
	
	public PhysicalMapper(ICalibration calibration) {
		this.cal = calibration;
	}

	public List<Integer> move(double base, double humerus, double ulna, double hand, double rot) {
		double a = base;
		double b = humerus;
		double c = ulna;
		double d = hand;

		// some basic geometric checks
		if (b+c < -50) {
			System.err.println("WARNING: b/c overrun!");
			return null;
		}

		// positive b, c and d should move robot upwards
		b = -b;
		d = -d;
		
		// rot is moving anti-clockwise
		double r = -rot;

		List<Integer> result = new ArrayList<Integer>();
		result.add(map(a, 0, cal.getCh0()));
		result.add(map(b, 1, cal.getCh1()));
		result.add(map(c, 2, cal.getCh2()));
		result.add(map(d, 3, cal.getCh3()));
		result.add(map(r, 5, cal.getCh5()));
		return result;
	}

	private int map(double value, int channelId, ICalibration.Channel ch) {
		int result = (int)round(ch.offset - value * ch.factor);
		checkRange(channelId, ch.min, ch.max, result);
		return result;
	}

	public Integer grab(int openMM) {
		// TODO: physical grip width is non linear with motor position!
		double fClosed = 1.0 - (openMM/(double)GeometryAL5D.gripOpenMM);
		int ch = GeometryAL5D.gripOpen +
				(int)round(fClosed * (GeometryAL5D.gripClosed-GeometryAL5D.gripOpen));

		checkRange(4, GeometryAL5D.gripOpen, GeometryAL5D.gripClosed, ch);

		return ch;
	}

	
	private static void checkRange(int id, int min, int max, int val) {
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
