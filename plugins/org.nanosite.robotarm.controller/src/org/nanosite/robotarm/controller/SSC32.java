package org.nanosite.robotarm.controller;

import org.nanosite.robotarm.controller.ISSC32;

public class SSC32 implements ISSC32 {
	private SerialConnection conn;

	public SSC32(SerialConnection conn) {
		this.conn = conn;
	}

	@Override
	public void shutdown() {
		// reset position 
		reset();
		
		// shutdown serial connection
		if (conn!=null) {
			conn.close();
		}
	}

	@Override
	public void delay(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean reset() {
		int t = 1000;
		
		// initial delay
		delay(t/2);

		// go to pre-parking
		String cmd = "#0P1500#1P2000#2P2000#3P700#4P1000#5P1475T" + t;
		if (! conn.send(cmd))
			return false;
		delay(t/2);

		// final parking
		cmd = "#0P1500#1P2020#2P2020#3P750T" + t;
		if (! conn.send(cmd))
			return false;
		delay(t/2);

		return true;
	}

	@Override
	public boolean move (int ch0, int ch1, int ch2, int ch3, int ch5, int t) {
		String cmd = "#0P" + ch0 + "#1P" + ch1 + "#2P" + ch2 + "#3P" + ch3 + "#5P" + ch5 + "T" + t;
		if (! conn.send(cmd))
			return false;
		delay(t);
		return true;
	}

	@Override
	public boolean grip (int ch) {
		int t = 500;
		String cmd = "#4P" + ch + "T" + t;
		if (! conn.send(cmd))
			return false;
		delay(t);
		return true;
	}

}
