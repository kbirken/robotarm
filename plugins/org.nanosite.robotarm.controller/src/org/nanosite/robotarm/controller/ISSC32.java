package org.nanosite.robotarm.controller;

public interface ISSC32 {

	void shutdown();
	boolean reset();
	void delay(int millisec);
	
	/** Control the arm movement. */
	boolean move(int ch0, int ch1, int ch2, int ch3, int ch5, int t);
	
	/** Control the gripper. */
	boolean grab(int i);
}
