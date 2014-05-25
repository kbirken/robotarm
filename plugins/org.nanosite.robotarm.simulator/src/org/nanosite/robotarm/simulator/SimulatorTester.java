package org.nanosite.robotarm.simulator;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import org.nanosite.robotarm.common.IRobotArmDirectControl;

public class SimulatorTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		new MainFrame(new Example(), 700, 500);
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				go();
			}
		});
	}

	private static Timer timer = new Timer();

	private static void go() {
//		Controller controller = new Controller();
//		LeapListener ll = new LeapListener();
//		controller.addListener(ll);

		JFrame.setDefaultLookAndFeelDecorated(true);
		RobotArmSimulator ex = new RobotArmSimulator();
		IRobotArmDirectControl robotarm = ex.init();
		run(robotarm);
		
//		controller.removeListener(ll);
	}


	public static void run(final IRobotArmDirectControl robotarm) {
		TimerTask task = new TimerTask() {
			
//			private float camY = 0.5f;
//			private float vCamY = 0.1f;
			
			private final static double V_POS = 10.0f;
			
			private double aBase = 0.0f;
			private double vBase = V_POS;
			
			private double aHumerus = 0.0f;
			private double vHumerus = V_POS;
			
			private double aUlna = 0.0f;
			private double vUlna = V_POS;
			
			private double aGripper = 0.0f;
			private double vGripper = V_POS;
			
			@Override
			public void run() {
//				Alpha alpha = new Alpha(1, // cycles
//						0, // trigger time
//						0, // phase delay
//						800,// cycle duration up
//						600,// ramp duration for up
//						0); // duration at one

//				tgMoveCode.removeChild(piCode);
//				piCode = new PositionInterpolator(alpha, tgMoveCode);
//				BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
//				piCode.setSchedulingBounds(bounds);

//				piCode.setStartPosition(currentLine * dy);

				aBase = aBase+vBase;
				if (aBase>90.0f) vBase = -V_POS;
				if (aBase < -90.0f) vBase = V_POS;

				aHumerus = aHumerus+vHumerus;
				if (aHumerus>35.0f) vHumerus = -V_POS;
				if (aHumerus < -45.0f) vHumerus = V_POS;

				aUlna = aUlna+vUlna;
				if (aUlna>20.0f) vUlna = -V_POS*2;
				if (aUlna < -40.0f) vUlna = V_POS*2;

				aGripper = aGripper+vGripper;
				if (aGripper>20.0f) vGripper = -V_POS;
				if (aGripper < -30.0f) vGripper = V_POS;

				robotarm.move(aBase, aHumerus, aUlna, -aHumerus-aUlna-90, 0.0d, 400);

				
//				if (camY > 0.9) vCamY = -0.02f;
//				if (camY < 0.1) vCamY = 0.02f;
//				camY = camY + vCamY;
//			    Transform3D t3d = new Transform3D();
//			    t3d.lookAt(new Point3d(0,camY,1.8), new Point3d(0,0,0), new Vector3d(0,1,0));
//			    t3d.invert();
//			    viewTransform.setTransform(t3d);

//				TransformGroup tg = universe.getViewingPlatform().getViewPlatformTransform();
//				System.out.println("CAMERA: " + tg.toString());
//				Transform3D tOld = null;
//				tg.getTransform(tOld);
//				
//				Transform3D tRot = new Transform3D();
//				tRot.rotX(Math.toRadians(1.0));
//				tOld.mul(tRot);
//				tg.setTransform(tRot);

				
//				Transform3D trot = new Transform3D();
//				trot.rotX(loc.getRot());
//				Transform3D ttrans = new Transform3D();
//				ttrans.setTranslation(new Vector3f(-pos.x, -pos.y, -pos.z));
//				scroll.mul(trot, ttrans);
//				tgMovingCode.setTransform(scroll);

//				tCode.setTranslation(new Vector3f(0.0f, currentLine * dy/10, 0.0f));
//				tgCode.setTransform(tCode);
//				rot.rotZ((float)Math.PI * -.5f);

//				piCode.setEndPosition(currentLine * dy);
//				tgMoveCode.addChild(piCode);
				

			}
		};
		timer.schedule(task, 500, 500); 
		

	}
	
}
