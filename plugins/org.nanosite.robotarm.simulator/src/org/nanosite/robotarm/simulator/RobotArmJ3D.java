package org.nanosite.robotarm.simulator;

import java.util.Timer;
import java.util.TimerTask;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import org.nanosite.robotarm.common.GeometryAL5D;
import org.nanosite.robotarm.common.IRobotArmDirectControl;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Box;


public class RobotArmJ3D implements IRobotArmDirectControl {

	// some colors
	private static Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	private static Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
	private static Color3f red = new Color3f(0.7f, .15f, .15f);
	private static Color3f blue = new Color3f(0.15f, .15f, .7f);
	private static Color3f green = new Color3f(0.15f, .7f, .15f);

	// the transform groups for doing the arm movements
	private TransformGroup rotateBase = null;
	private TransformGroup rotateHumerus = null;
	private TransformGroup rotateUlna = null;
	private TransformGroup rotateHand = null;
	
	// the current angles
	private double angleBase;
	private double angleHumerus;
	private double angleUlna;
	private double angleHand;
	
	// the simulation time step duration (in milliseconds)
	private static final int DT = 50;

	private Timer timer = new Timer();
	private TimerTask task;
	
	private enum STATE { IDLE, MOVING };

	private STATE state = STATE.IDLE; 
	
	public double startAngleBase;
	private double startAngleHumerus;
	private double startAngleUlna;
	private double startAngleHand;

	private double targetAngleBase;
	private double targetAngleHumerus;
	private double targetAngleUlna;
	private double targetAngleHand;

	private int i;
	private int n;

	public RobotArmJ3D() {
		this.angleBase = 0.0f;
		this.angleHumerus = 0.0f;
		this.angleUlna = 90.0f;
		this.angleHand = 45.0f;

		// start simulation timer task
		task = new TimerTask() {

			@Override
			public void run() {
				switch (state) {
				case IDLE:
					break;
				case MOVING:
					i = i + DT;
					if (i>n) i = n;

					double d = i/(double)n;
//					System.out.println("  d=" + d);
					setAngleBase(startAngleBase + d*(targetAngleBase-startAngleBase));
					setAngleHumerus(startAngleHumerus+ d*(targetAngleHumerus-startAngleHumerus));
					setAngleUlna(startAngleUlna+ d*(targetAngleUlna-startAngleUlna));
					setAngleGripper(startAngleHand+ d*(targetAngleHand-startAngleHand));
					
					if (i>=n)
						state = STATE.IDLE;
					break;
				}
			}
		};
		timer.schedule(task, DT, DT); 
	}

	@Override
	public boolean move(double base, double humerus, double ulna, double hand, double rot, int t) {
		System.out.println("RobotArmJ3D.move(" + base + ", " + humerus + ", " + ulna + ", " + hand + ")");
		startAngleBase = angleBase;
		startAngleHumerus= angleHumerus;
		startAngleUlna= angleUlna;
		startAngleHand = angleHand;

		// positive angles at humerus, ulna and gripper should move robot upwards
		targetAngleBase = base;
		targetAngleHumerus= -humerus;
		targetAngleUlna= 90.0 - ulna;
		targetAngleHand = -hand;// + 90.0;
		
		i = 0;
		n = t;
		state = STATE.MOVING;

		// TODO: handle rot

		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean grab(int openMM) {
		// TODO
		return true;
	}

	@Override
	public void delay(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean shutdown() {
		delay(3000);
		move(0.0, 0.0, 0.0, -45.0, 0.0, 500);
		return true;
	}


	public void setAngleBase(double aDeg) {
//		System.out.println("  base=" + aDeg);
		angleBase = aDeg;
		setRotateY(rotateBase, aDeg);
	}

	public void setAngleHumerus(double aDeg) {
//		System.out.println("  humerus=" + aDeg);
		angleHumerus = aDeg;
		setRotateZ(rotateHumerus, aDeg);
	}

	public void setAngleUlna(double aDeg) {
//		System.out.println("  ulna=" + aDeg);
		angleUlna = aDeg;
		setRotateZ(rotateUlna, aDeg);
	}

	public void setAngleGripper(double aDeg) {
//		System.out.println("  gripper=" + aDeg);
		angleHand = aDeg;
		setRotateZ(rotateHand, aDeg);
	}

	private static void setRotateY(TransformGroup tg, double aDeg) {
		Transform3D tRotate1 = new Transform3D();
		tRotate1.rotY(Math.toRadians(aDeg));
		tg.setTransform(tRotate1);
	}

	private static void setRotateZ(TransformGroup tg, double aDeg) {
		Transform3D tRotate1 = new Transform3D();
		tRotate1.rotZ(Math.toRadians(aDeg));
		tg.setTransform(tRotate1);
	}


	/**
	 * Initialize J3D scene graph.
	 * 
	 * It consists of the full 3D robot arm model.
	 * 
	 * @return the J3D TransformGroup representing the robot arm model.
	 */
	public BranchGroup createSceneGraph() {
		TransformGroup tgAll = new TransformGroup();
		
//		TransformGroup tgGripper = new TransformGroup();
//		tgGripper.addChild(createAxis(GeometryAL5D.GRIPPER_LENGTH, red));

		TransformGroup tgHand = new TransformGroup();
		tgHand.addChild(createAxis(GeometryAL5D.HAND_LENGTH, red));

		TransformGroup tgUlna = new TransformGroup();
		tgUlna.addChild(createAxis(GeometryAL5D.ULNA_LENGTH, white));

		rotateHand = attachSegment(tgUlna, GeometryAL5D.ULNA_LENGTH, tgHand, angleHand);

		TransformGroup tgHumerus = new TransformGroup();
		tgHumerus.addChild(createAxis(GeometryAL5D.HUMERUS_LENGTH, red));
//		Transform3D yAxis = new Transform3D();
//		Alpha rotationAlpha = new Alpha(-1, 4000);
//		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
//				tgCube, yAxis, 0.0f, (float) Math.PI * 2.0f);
//		BoundingSphere bounds1 = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
//		rotator.setSchedulingBounds(bounds1);
//		tgCube.addChild(rotator);

		rotateUlna = attachSegment(tgHumerus, GeometryAL5D.HUMERUS_LENGTH, tgUlna, angleUlna);
		
//		tgAll.addChild(tgHumerus);

		TransformGroup tg2 = new TransformGroup();
		Transform3D tRotate1 = new Transform3D();
		tRotate1.rotX(Math.toRadians(90.0f));
		tg2.setTransform(tRotate1);
		Appearance ap2 = new Appearance();
		ap2.setMaterial(new Material(red, black, red, black, 1.0f));
		tg2.addChild(new Cylinder(1.0f, GeometryAL5D.BASE_WIDTH*2.2f, ap2));
		tgHumerus.addChild(tg2);
		

		TransformGroup tgBase = new TransformGroup();
		tgBase.addChild(createBase(GeometryAL5D.BASE_WIDTH, GeometryAL5D.BASE_HEIGHT));
//		Transform3D tBase = new Transform3D();
//		tBase.setTranslation(new Vector3f(0.0f, BASE_HEIGHT/2, 0.0f));
//		tgBase.setTransform(tBase);
		
		// pack arm above base into TransformGroup to allow rotating at the base
		rotateBase = new TransformGroup();
		rotateBase.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D tRotate = new Transform3D();
		tRotate.rotY(Math.toRadians(angleBase));
		rotateBase.setTransform(tRotate);

		
		rotateHumerus = attachSegment(rotateBase, GeometryAL5D.BASE_HEIGHT, tgHumerus, angleHumerus);
		
		tgBase.addChild(rotateBase);
		tgAll.addChild(tgBase);
		
		// add ground plane
//		TransformGroup tgPlane = new TransformGroup();
		tgAll.addChild(createGroundPlane());
		
		// add pole for orientation and debugging
//		TransformGroup tgPole = new TransformGroup();
		tgAll.addChild(createPole(120.f, GeometryAL5D.BASE_HEIGHT));
		
		// scale and move whole scene
		Transform3D tScale = new Transform3D();
		tScale.setScale(0.001f);
		Transform3D tMove = new Transform3D();
		tMove.setTranslation(new Vector3f(0.0f, -0.2f, 0.0f));
		tMove.mul(tScale);
		tgAll.setTransform(tMove);
//		tgAll.setTransform(tScale);
		
		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(tgAll);

		// create lights
		Color3f light1Color = new Color3f(1f, 1f, 1f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);
		AmbientLight ambientLight = new AmbientLight(new Color3f(.5f, .5f, .5f));
		ambientLight.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLight);

		objRoot.compile();
		return objRoot;
	}
	

	private Node createAxis(float length, Color3f color) {
		TransformGroup tg = new TransformGroup();

		Appearance ap = new Appearance();
		ap.setMaterial(new Material(color, black, color, black, 1.0f));
		   
		tg.addChild(new Cylinder(GeometryAL5D.SEGMENT_WIDTH / 2, length, ap));

		Transform3D t = new Transform3D();
		t.setTranslation(new Vector3f(0.0f, length/2, 0.0f));
		tg.setTransform(t);

		return tg;
	}
	
	private Node createBase(float width, float height) {
		TransformGroup tg = new TransformGroup();

		Appearance ap1 = new Appearance();
		ap1.setMaterial(new Material(blue, black, blue, black, 1.0f));
		tg.addChild(new Cylinder(width/2, height, ap1));

		Transform3D t = new Transform3D();
		t.setTranslation(new Vector3f(0.0f, height/2, 0.0f));
		tg.setTransform(t);

		return tg;
	}
	
	private Node createGroundPlane() {
		TransformGroup tg = new TransformGroup();

		Appearance ap = new Appearance();
		ap.setMaterial(new Material(white, black, white, black, 1.0f));
		   
		tg.addChild(new Box(400.0f, 0.1f, 300.0f, ap));

		Transform3D t = new Transform3D();
		t.setTranslation(new Vector3f(-100.0f, 0.0f, 0.0f));
		tg.setTransform(t);

		return tg;
	}
	
	private Node createPole(float x, float height) {
		TransformGroup tg = new TransformGroup();

		Appearance ap = new Appearance();
		ap.setMaterial(new Material(green, black, green, black, 1.0f));
		   
		tg.addChild(new Cylinder(2.0f, height, ap));

		Transform3D t = new Transform3D();
		t.setTranslation(new Vector3f(-x, height/2, 0.0f));
		tg.setTransform(t);

		return tg;
	}
	
	private TransformGroup attachSegment(
			TransformGroup host, float hostLength,
			Node segment, double angleDeg
	) {
		// pack segment into TransformGroup for rotate
		TransformGroup tg = new TransformGroup();
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D tRotate = new Transform3D();
		tRotate.rotZ(Math.toRadians(angleDeg));
		
		tg.setTransform(tRotate);
		tg.addChild(segment);
		
		// put this transform group into another for translate
		TransformGroup tg2 = new TransformGroup();
		tg2.addChild(tg);

		Transform3D tTrans = new Transform3D();
		tTrans.setTranslation(new Vector3f(0.0f, hostLength, 0.0f));
		tg2.setTransform(tTrans);

		host.addChild(tg2);

		return tg;
	}


}
