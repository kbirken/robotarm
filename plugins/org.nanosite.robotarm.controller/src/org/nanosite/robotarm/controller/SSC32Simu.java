package org.nanosite.robotarm.controller;

import org.nanosite.robotarm.controller.ISSC32;

public class SSC32Simu implements ISSC32 {

	public SSC32Simu() {
		System.out.println("SSC32: SIMULATION");
	}

	public void shutdown() {
		reset();
		
		// no serial connection, we do not have to shutdown anything
	}

	public void delay(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean reset() {
		int t = 1000;
		delay(t);
		System.out.println("SSC32: RESET");
		delay(t);
		return true;
	}

	public boolean move (int ch0, int ch1, int ch2, int ch3, int ch5, int t) {
		String cmd = "0=" + ch0 + " 1=" + ch1 + " 2=" + ch2 + " 3=" + ch3 + " R=" + ch5 + " T=" + t;
		System.out.println("SSC32: MOVE " + cmd + "\n");
		delay(t);
		return true;
	}

	public boolean grip (int ch) {
		String cmd = "4=" + ch;
		System.out.println("SSC32: MOVE " + cmd + "\n");
		return true;
	}
}
