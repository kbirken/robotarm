package org.nanosite.robotarm.examples;

import org.nanosite.robotarm.common.DirectControlDispatcher;
import org.nanosite.robotarm.common.IRobotArmDirectControl;
import org.nanosite.robotarm.common.IRobotArmPosControl;
import org.nanosite.robotarm.common.InverseKinematicsControl;
import org.nanosite.robotarm.controller.RobotArmPhysical;
import org.nanosite.robotarm.simulator.RobotArmSimulator;


public class RobotArmApplication {
	private static RobotArmApplication self = null;

	public static void main(String[] args) {
		self = new RobotArmApplication();
		self.run(args);
	}

	private void run (String[] args) {
		System.out.println("RobotArmController.");

		boolean verbose = true;
		
		boolean simulateOnly = true;

		DirectControlDispatcher dispatcher = new DirectControlDispatcher();
		RobotArmSimulator simulator = new RobotArmSimulator();
		dispatcher.addClient(simulator.init());
		if (! simulateOnly) {
			IRobotArmDirectControl robotPhys = RobotArmPhysical.createInstance("/dev/tty.usbserial-FTF7AJ8S", verbose);
			if (robotPhys==null) {
				System.err.println("RobotArm couldn't be initialized, aborting.");
				return;
			}
			dispatcher.addClient(robotPhys);
		}
		
		// create inverse-kinematics controller
		IRobotArmPosControl robot = new InverseKinematicsControl(dispatcher, verbose);
		
		// calibration procedure (set 0 for whole procedure or a number 1..6 for a single step)
//		calibrate(0, dispatcher);
		
		robot.delay(2000);
//		Calibrator ctrl = new Calibrator(robot);
//		DominoTurm ctrl = new DominoTurm(robot);
//		SelfProgrammer ctrl = new SelfProgrammer(robot);
		ShowcaseGENIVI ctrl = new ShowcaseGENIVI(robot);
//		PlaymobilStopMotion ctrl = new PlaymobilStopMotion(robot);
		ctrl.run();

		robot.shutdown();
		

		System.out.println("RobotArmController finished.");
	}

	private void calibrate(int step, IRobotArmDirectControl robotDirect) {
		final int dt = 8000;
		
		if (step==0 || step==1) {
			System.out.println("Calibration step 1: humerus straight up, ulna parallel to ground, hand straight down");
			robotDirect.move(0, 0, 0, -90, 0, 2000);
			robotDirect.delay(dt);
		}
		
		if (step==0 || step==2) {
			System.out.println("Calibration step 2: humerus 45\" backwards, ulna parallel to ground, hand straight down");
			robotDirect.move(0, 45, -45, -90, 0, 2000);
			robotDirect.delay(dt);
		}

		if (step==0 || step==3) {
			System.out.println("Calibration step 3: humerus 30\" backwards, ulna parallel to ground, hand straight down");
			robotDirect.move(0, 30, -30, -90, 0, 2000);
			robotDirect.delay(dt);
		}

		if (step==0 || step==4) {
			System.out.println("Calibration step 4: humerus straight up, ulna straight up, hand straight up");
			robotDirect.move(0, 0, 90, 0, 0, 2000);
			robotDirect.delay(dt);
		}
		if (step==0 || step==5) {
			System.out.println("Calibration step 5: repeat 1");
			robotDirect.move(0, 0, 0, 0, 0, 2000);
			robotDirect.delay(dt);
		}
		if (step==0 || step==6) {
			System.out.println("Calibration step 6: humerus straight up, ulna parallel to ground, hand straight up");
			robotDirect.move(0, 0, 0, 90, 0, 2000);
			robotDirect.delay(dt);
		}
		if (step==0 || step==6) {
			System.out.println("Calibration step 6: base turn 90 DEG anti-clockwise, " +
					"humerus straight up, ulna parallel to ground, hand straight down");
			robotDirect.move(90, 0, 0, -90, 0, 2000);
			robotDirect.delay(dt);
		}	
	}

	private void test1(IRobotArmDirectControl robotDirect) {
		robotDirect.move(0, 0, 0, -90, 0, 2000);

		final int dt = 5000;
		for(int i=-45; i<=30; i+=15) {
			robotDirect.move(0, i, -i, -90, 0, 500);
			robotDirect.delay(dt);
		}
		for(int i=45; i>=-45; i-=15) {
			robotDirect.move(0, i, -i, -90, 0, 500);
			robotDirect.delay(dt);
		}	
	}

	private void test2(IRobotArmDirectControl robotDirect) {
		final int dt = 5000;

		robotDirect.move(0, 20, 70, -90, 0, 2000);
		robotDirect.delay(dt);

		for(int i=45; i>=-30; i-=15) {
			robotDirect.move(0, 0, i, -i, 0, 500);
			robotDirect.delay(dt);
		}
		for(int i=-45; i<=30; i+=15) {
			robotDirect.move(0, 0, i, -i, 0, 500);
			robotDirect.delay(dt);
		}
	}

	// use this to calibrate the wrist rotate
	private void test3(IRobotArmDirectControl robotDirect) {
		final int dt = 5000;

		robotDirect.move(0, 20, 70, -90, 0, 2000);
		robotDirect.delay(dt);

		for(int i=45; i>=-90; i-=45) {
			robotDirect.move(0, 45, 0, i, 0, 500);
			robotDirect.delay(dt);
		}
		for(int i=-45; i<=45; i+=45) {
			robotDirect.move(0, 45, 0, i, 0, 500);
			robotDirect.delay(dt);
		}
	}
	
}

