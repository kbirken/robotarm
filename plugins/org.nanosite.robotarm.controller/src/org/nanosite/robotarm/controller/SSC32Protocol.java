package org.nanosite.robotarm.controller;

/**
 * This class builds SSC32 protocol messages from raw input data. 
 */
public class SSC32Protocol {

	/**
	 * Build SSC32 command which moves the robot arm to a pre-parking position.
	 * 
	 * @param t the time interval for moving to the pre-parking position
	 * @return the SSC32 command
	 */
	public static String buildPreParkingCommand(int t) {
		return "#0P1500#1P2000#2P2000#3P700#4P1000#5P1475T" + t;
	}

	/**
	 * Build SSC32 command which moves the robot arm to a parking position.
	 * 
	 * @param t the time interval for moving to the parking position
	 * @return the SSC32 command
	 */
	public static String buildParkingCommand(int t) {
		return "#0P1500#1P2020#2P2020#3P750T" + t;
	}

	/**
	 * Build SSC32 command which controls the joints of the robot arm.
	 * 
	 * @param ch0 value for the base joint
	 * @param ch1 value for the humerus joint
	 * @param ch2 value for the ulna joint
	 * @param ch3 value for the hand joint
	 * @param ch5 value for the wrist rotation
	 * @param t the time interval for moving to the new position
	 * @return the SSC32 command
	 */
	public static String buildMoveCommand(int ch0, int ch1, int ch2, int ch3, int ch5, int t) {
		return "#0P" + ch0 + "#1P" + ch1 + "#2P" + ch2 + "#3P" + ch3 + "#5P" + ch5 + "T" + t;
	}

	/**
	 * Build SSC32 command which controls the gripper of the robot arm.
	 * 
	 * @param ch value for the gripper motor
	 * @param t the time interval for moving to the new position
	 * @return the SSC32 command
	 */
	public static String buildGrabCommand(int ch, int t) {
		return "#4P" + ch + "T" + t;
	}

}
