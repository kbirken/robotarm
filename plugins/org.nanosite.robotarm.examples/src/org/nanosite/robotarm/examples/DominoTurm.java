package org.nanosite.robotarm.examples;

import org.nanosite.robotarm.common.IRobotArmPosControl;

public class DominoTurm {

	private IRobotArmPosControl robot = null;

	private double lastRot = 0.0; 

	public DominoTurm (IRobotArmPosControl robot) {
		this.robot = robot;
	}

	public void run() {
		robot.delay(2000);

		toNeutral();

//		for(int p=0; p<5; p++) {
////			toNeutral();
//			get(p);
//			put(5+p);
////			toNeutral();
//			
//			get(5+p);
//			put(p+1);
//		}
//
//		get(5);
//		put(0);

//		for(int i=0; i<20; i++) {
//			for(int p=0; p<7; p+=2) {
//				get(p);
//				put(p+4);
//			}
//			get(8);
//			put(2);
//			get(10);
//			put(0);
//
//			toNeutral();
//			robot.grip(5);
//			robot.grip(30);
//		}
		

//		for(int p=0; p<10; p++) {
//			get(p);
//			put(p+1);
//		}
//		get(10);
//		put(0);
		
		
		int dominoZ = 26;
		int dy = 32;
		int dx = 25;
		int y0 = 0;

//		moveY_(0, 60, 0, 0, false);
//		robot.delay(5000);
//		moveY_(0, 60, dominoZ, 0, false);
//		robot.delay(5000);
//		moveY_(0, 60, dominoZ*2, 0, false);
//		robot.delay(5000);
//		moveY_(0, 60, dominoZ*3, 0, false);
//		robot.delay(5000);

		get(0);
		moveY(0, y0, 0, 0, false);

		get(0);
		moveY(0, y0+dy, 0, 0, false);

		
		get(0);
		moveY(-10, y0+dy/2, dominoZ, 90, false);
		get(0);
		moveY(-10+dx, y0+dy/2, dominoZ, 90, false);
	

		get(0);
		moveY(0, y0, 2*dominoZ, 0, false);
		get(0);
		moveY(0, y0+dy, 2*dominoZ, 0, false);


		get(0);
		moveY(-10, y0+dy/2, 3*dominoZ, 90, false);
		get(0);
		moveY(-10+dx, y0+dy/2, 3*dominoZ, 90, false);
	
		
		toNeutral();
		robot.move(100, 0, 160, 70, lastRot, 3000);
		robot.move(100, 0, 120, 90, lastRot, 3000);
		robot.delay(3000);
	}

	
	private void toNeutral() {
		robot.move(220, 0, 180, 45, 0, 2000);
		lastRot = 0;

//		ssc32.delay(1000);
	}

	private void get(int p) {
		movePos(p, true);
	}
	
	private void put(int p) {
		movePos(p, false);
	}
	
	private void movePos (int p, boolean get) {
		int yMin = -100;
		int yMax = 100;
		double dy = (yMax-yMin) / (double)10;
		int y = yMin + (int)Math.round(dy*p);
		moveY(0, y, 0, 0, get);
	}
	
	private void moveY_ (int dx, int y, int h, int rot, boolean get) {
		moveY(dx, y, h, rot, get);
		moveY(dx, y+20, h, rot, get);
	}
	
	private void moveY (int dx, int y, int h, int rot, boolean get) {
		int x = 150 + dx;

		int zrot = (int)Math.round(- Math.toDegrees(Math.atan(y/(double)x)));
		int r = zrot + rot;
		lastRot = r;
	
		robot.move(x, y, h+60, 90, r, 1000);
		if (get)
			robot.grab(18);
		robot.delay(500);

		robot.move(x, y, h+10, 90, r, 2000);
		if (get)
			robot.grab(6);
		else
			robot.grab(15);
		robot.delay(500);

		robot.move(x, y, 160, 90, r, 800);
		robot.delay(200);
	}

}
