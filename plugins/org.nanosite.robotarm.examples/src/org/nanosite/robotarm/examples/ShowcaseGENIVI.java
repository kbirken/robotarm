package org.nanosite.robotarm.examples;

import org.nanosite.robotarm.common.IRobotArmPosControl;

public class ShowcaseGENIVI {

	private IRobotArmPosControl robot = null;

	public ShowcaseGENIVI (IRobotArmPosControl robot) {
		this.robot = robot;
	}

	public void run() {
//while(true) {
		robot.delay(2000);
		
		toNeutral();
		robot.grab(20);

		// distance of object in x-direction
		final int dx = 253;
		
		// height of grip position of object
		final int h = 60;
		
		// playing around...
		if (! robot.move(180, 80, 100, 60, 0, 800)) return;
		robot.delay(500);
		if (! robot.move(250, -70, 160, 20, 45, 800)) return;
		robot.delay(500);
		if (! robot.move(240, -70, 130, 45, -50, 800)) return;
		robot.delay(2000);
		if (! robot.move(200, -30, 260, -70, -30, 800)) return;
		robot.delay(2000);
		if (! robot.move(200, 30, 160, 45, 0, 1000)) return;
		robot.delay(2500);
		
		// move to position above object
		if (! robot.move(dx, 0, 130, 82, 90, 2000)) return;
		robot.delay(500);
		
		// move down to object
		if (! robot.move(dx, 0, h, 82, 90, 1000)) return;
		robot.delay(200);

		// grab object and lift it
		robot.grab(6);
		robot.delay(200);
		if (! robot.move(dx, 0, 130, 82, 90, 1000)) return;
		robot.delay(200);

		// move to the "show" position
		if (! robot.move(160, -60, 160, 82, 90, 1500)) return;
		robot.delay(200);
		if (! robot.move(190, -130, 320, -25, -20, 1500)) return;
		robot.delay(8000);
		
		// move back to release object
		if (! robot.move(160, -30, 160, 82+5, 90, 2500)) return;
		robot.delay(300);
		if (! robot.move(dx+3, 0, 130, 82+5, 90, 1500)) return;
		robot.delay(300);
		if (! robot.move(dx+3, 0, h+3, 82+5, 90, 1500)) return;
		robot.delay(300);

		// release object
		robot.grab(20);
		
		// move slightly up after release
		if (! robot.move(dx, 0, 160, 82, 90, 500)) return;
		robot.delay(1000);

//		robot.grip(7);
//		System.out.println("Tool grabbed.");
		toNeutral();
		robot.delay(3000);
//}

	}

	
	private void toNeutral() {
		if (! robot.move(200, 0, 180, 45, 0, 2000))
			return;

//		ssc32.delay(1000);
	}

}
