package org.nanosite.robotarm.examples;

import java.util.ArrayList;
import java.util.List;

import org.nanosite.robotarm.common.IRobotArmPosControl;

public class PlaymobilStopMotion {

	private final int turn = 90;

	private final int gOpen = 24;
	private final int gClose = 15;
	
	private final int zHigh = 100;
	private final int zLow = 42;
	
	private IRobotArmPosControl robot = null;

	class Man {
		
		int x = 0;
		int y = 0;
		
		public Man (int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
		public void setPos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	List<Man> all = new ArrayList<Man>();
	
	public PlaymobilStopMotion (IRobotArmPosControl robot) {
		this.robot = robot;
	}

	public void run() {
		
		int x = 220;
		
		all.add(new Man(x, -100));
		all.add(new Man(x, -70));
		all.add(new Man(x, -40));
		all.add(new Man(x, -10));
		
		robot.delay(2000);
		
		toNeutral();
		robot.grab(20);

		setup();
//		play();
//		hit();
		
		toNeutral();
		robot.delay(3000);
	}
	
	private void setup() {
		for(Man m : all) {
			setup(m);
		}
	}

	private void setup (Man m) {
		
		int fetchX = 162;
		int fetchY = 100;
		int fetchA = -30;
		
		robot.grab(gOpen);
		
		if (! robot.move(fetchX, fetchY, zHigh, turn, fetchA, 800)) return;
		robot.delay(2000);

		if (! robot.move(fetchX, fetchY, 58, turn, fetchA, 800)) return;
		robot.delay(1000);

		robot.grab(gClose);
		robot.delay(500);

		if (! robot.move(fetchX, fetchY, zHigh, turn, fetchA, 800)) return;
		robot.delay(1000);

		if (Math.random()>.5)
			pickAux2(m, false);
		else
			pickAux(m, false);
		
	}
	
	private void pick (Man m) {
		pickAux(m, true);
	}

	private void place (Man m, int x, int y) {
		m.setPos(x, y);
		pickAux(m, false);
	}

	private void pickAux2 (Man m, boolean pick) {
		int speed = 2; // 4
		if (! robot.move(m.getX()+90, m.getY(), zHigh+100, -45, 40, 300*speed)) return;
		robot.delay(1200*speed);
		robot.grab(gOpen);

//		if (! robot.move(m.getX(), m.getY(), zLow, turn, 0, 300*speed)) return;
//		robot.delay(250*speed);
//
//		robot.grip(pick ? gClose : gOpen);
//		robot.delay(125*speed);
//
//		if (! robot.move(m.getX(), m.getY(), zHigh, turn, 0, 200*speed)) return;
//		robot.delay(250*speed);
		
	}
	
	private void pickAux (Man m, boolean pick) {
		int speed = 2; // 4
		if (! robot.move(m.getX(), m.getY(), zHigh, turn, 0, 400*speed)) return;
		robot.delay(250*speed);

		if (! robot.move(m.getX(), m.getY(), zLow, turn, 0, 300*speed)) return;
		robot.delay(250*speed);

		robot.grab(pick ? gClose : gOpen);
		robot.delay(125*speed);

		if (! robot.move(m.getX(), m.getY(), zHigh, turn, 0, 200*speed)) return;
		robot.delay(250*speed);
		
	}
	
	private void play() {
		
		for(int i=1; i<=1; i++) {
			System.out.println("KNIPS! (Bild " + i + ")");
			robot.delay(5000);

			for(Man m : all) {
				playStep(m);
			}
		}
	}
	
	private void playStep (Man m) {
		robot.grab(gOpen);
		pick(m);
		place(m, m.getX()+35, m.getY());
	}
	
	private void hit() {
		robot.grab(gClose);
		for(Man m : all) {
			hit(m);
		}
	}

	
	private void hit (Man m) {
		if (! robot.move(m.getX(), m.getY(), zHigh, turn+40, 0, 800)) return;
		robot.delay(500);

		if (! robot.move(m.getX(), m.getY(), zLow, turn+10, 0, 200)) return;
		if (! robot.move(m.getX(), m.getY(), zLow, turn-20, 0, 200)) return;
		robot.delay(500);
		
		if (! robot.move(m.getX(), m.getY(), zHigh, turn, 0, 800)) return;
		robot.delay(500);

	}

	private void toNeutral() {
		if (! robot.move(200, 0, 180, 45, 0, 2000))
			return;
	}

}
