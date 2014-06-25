package org.nanosite.robotarm.examples;

import org.nanosite.robotarm.common.IRobotArmPosControl;

public class CubeTower {

	private IRobotArmPosControl robot = null;

	public CubeTower (IRobotArmPosControl robot) {
		this.robot = robot;
	}

	public void run() {
//while(true) {
		robot.delay(2000);
		
		toNeutral();
		robot.grab(20);

		
		if (! fetch(left, 3, true)) return;
		if (! fetch(right, 1, false)) return;

		if (! fetch(left, 2, true)) return;
		show();
		if (! fetch(right, 2, false)) return;

		if (! fetch(left, 1, true)) return;
		if (! fetch(right, 3, false)) return;

	
		if (! fetch(right, 3, true)) return;
		show();
		if (! fetch(left, 1, false)) return;

		if (! fetch(right, 2, true)) return;
		if (! fetch(left, 2, false)) return;

		if (! fetch(right, 1, true)) return;
		if (! fetch(left, 3, false)) return;


		
		//		show();
//		if (! fetch(middle, 1, false)) return;
		
//		if (! fetchLeft(1, true)) return;
//		if (! fetchRight(2, false)) return;
		
		robot.grab(grabOpen);
		
//		// move slightly up after release
//		if (! robot.move(dx, 0, 160, 82, 90, 500)) return;
//		robot.delay(1000);

//		robot.grip(7);
//		System.out.println("Tool grabbed.");
		toNeutral();
		robot.delay(3000);
//}

	}


	// x, y, z, rot, aa, corr_x
	final int[] left =		{ 132, 94, 10,	-28, 90,	3 };
	final int[] middle =	{ 142, 0, 12,		0, 90,		0 };
	final int[] right =		{ 140, -90, 8,	28, 90,		2 };
	
	final int zHigh = 50;

	final int grabOpen = 28;
	final int grabClosed = 16;
	
	final int dCube = 23;
	
	private boolean fetch(int[] pos, int h, boolean fetch) {
		int x = pos[0] + (h-1)*pos[5];
		int z = pos[2] + (h-1)*dCube;
		if (!fetch) z = z + 3;
		
		if (! robot.move(x, pos[1], z+zHigh, pos[4], pos[3], 1000)) return false;
		robot.delay(1000);
		
		if (fetch)
			robot.grab(grabOpen);

		if (! robot.move(x, pos[1], z, pos[4], pos[3], 500)) return false;
		robot.delay(1000);
		
		System.out.println("*** at h=" + h + "\n");
		
		if (fetch)
			robot.grab(grabClosed);
		else
			robot.grab(grabOpen);
		robot.delay(500);

		if (! robot.move(x, pos[1], z+zHigh, pos[4], pos[3], 1000)) return false;
		robot.delay(2000);

		return true;
	}
	
	private boolean show() {
		if (!robot.move(120, 0, 280, -45, 0, 1000)) return false;
		System.out.println("*** show\n");
		robot.delay(2000);
		return true;
	}

	private void toNeutral() {
		if (! robot.move(200, 0, 180, 45, 0, 2000))
			return;

//		ssc32.delay(1000);
	}

}
