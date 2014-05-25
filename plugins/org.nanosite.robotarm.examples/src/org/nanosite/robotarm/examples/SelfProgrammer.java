package org.nanosite.robotarm.examples;

import org.nanosite.robotarm.common.IRobotArmPosControl;

public class SelfProgrammer {

	private IRobotArmPosControl robot = null;

	public SelfProgrammer (IRobotArmPosControl robot) {
		this.robot = robot;
	}

	public void run() {
		robot.delay(2000);
		
		toNeutral();

		robot.move(220, 0, 180, -45, 0, 2000);
		System.out.println("Please insert tool!");
		robot.delay(3000);
		robot.grab(7);
		System.out.println("Tool grabbed.");
		robot.delay(3000);

		System.out.println("Moving to neutral position.");
		robot.move(200, 0, 100, 90, 0, 2000);
		robot.delay(2000);

		pressKey(250, 0);
		pressKey(260, -50);
		pressKey(230, 70);

		
		System.out.println("Releasing tool...");
		robot.delay(2000);
		robot.move(140, -80, 170, 30, 0, 2000);
		robot.delay(2000);
		robot.grab(20);

	}

	private void pressKey (int x, int y) {
		System.out.println("Moving to key position.");
		robot.move(x, y, 120, 90, 0, 2000);
		robot.delay(2000);
		System.out.println("Pressing key.");
		robot.move(x, y, 70, 90, 0, 500);
		robot.move(x, y, 120, 90, 0, 500);
		robot.delay(2000);
	}
	
	private void toNeutral() {
		robot.move(220, 0, 180, 45, 0, 2000);
//		ssc32.delay(1000);
	}

}
