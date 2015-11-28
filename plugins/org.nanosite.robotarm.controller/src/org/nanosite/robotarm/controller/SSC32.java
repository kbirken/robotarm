package org.nanosite.robotarm.controller;

import org.nanosite.robotarm.controller.ISSC32;

public class SSC32 implements ISSC32 {
	private ISerialConnection conn;

	public SSC32(ISerialConnection conn) {
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
		String cmd = SSC32Protocol.buildPreParkingCommand(t);
		if (! conn.send(cmd))
			return false;
		delay(t/2);

		// final parking
		cmd = SSC32Protocol.buildParkingCommand(t);
		if (! conn.send(cmd))
			return false;
		delay(t/2);

		return true;
	}

	@Override
	public boolean move(int ch0, int ch1, int ch2, int ch3, int ch5, int t) {
		String cmd = SSC32Protocol.buildMoveCommand(ch0, ch1, ch2, ch3, ch5, t);
		if (! conn.send(cmd))
			return false;
		delay(t);
		return true;
	}

	@Override
	public boolean grab(int ch) {
		int t = 500;
		String cmd = SSC32Protocol.buildGrabCommand(ch, t);
		if (! conn.send(cmd))
			return false;
		delay(t);
		return true;
	}

}
