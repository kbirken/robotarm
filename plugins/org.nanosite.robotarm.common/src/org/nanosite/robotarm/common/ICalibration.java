package org.nanosite.robotarm.common;

public interface ICalibration {

	public class Channel {
		public final double factor;
		public final double offset;
		public final int min;
		public final int max;
		
		public Channel(double factor, double offset, int min, int max) {
			this.factor = factor;
			this.offset = offset;
			this.min = min;
			this.max = max;
		}
	}

	Channel getCh0();
	Channel getCh1();
	Channel getCh2();
	Channel getCh3();
	Channel getCh5();
}
