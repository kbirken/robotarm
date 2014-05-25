package org.nanosite.robotarm.examples;

import org.nanosite.robotarm.common.GeometryAL5D;
import org.nanosite.robotarm.common.IRobotArmPosControl;

public class Calibrator {

	private IRobotArmPosControl robot = null;

	public Calibrator (IRobotArmPosControl robot) {
		this.robot = robot;
	}

	public void run() {
		robot.delay(2000);
		
		robot.move(120, 0, GeometryAL5D.BASE_HEIGHT, 90, 0, 1000);
		robot.delay(2000);

//		for(int i=0; i<90; i=i+10) {
//			robot.move(170, 0, 125, i, 0, 1000);
//			robot.delay(2000);
//		}
//		robot.move(200, 0, 125, 90, 0, 1000);
//		robot.delay(2000);
//		robot.move(200, 0, 100, 90, 0, 1000);
//		robot.delay(2000);
//		robot.move(200, 0, 75, 90, 0, 1000);
//		robot.delay(2000);
//		robot.move(200, 0, 50, 90, 0, 1000);
//		robot.delay(2000);
//		robot.move(200, 0, 25, 90, 0, 1000);
//		robot.delay(2000);

//		robot.move(0, 200, 140, 90, 0, 2000);

		
//		robot.move(200, 0, 140, 90, 0, 2000);
//		robot.move(200, 0, 140, 90, -70, 400);
//		robot.move(200, 0, 140, 90, 70, 400);
//		robot.move(200, 0, 140, 90, 0, 200);

//		for(int i=140; i>0; i-=20) {
//			robot.move(200, 0, i, 90, 0, 1000);
//			robot.delay(2000);
//		}
	}
	
	
}
