package org.nanosite.robotarm.simulator;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;


public class RobotArmSimulator extends JFrame {

	private static final long serialVersionUID = 123456L;

	private SimpleUniverse universe = null;


	public RobotArmSimulator() {
		super("RobotArmSimulator");
	}
	
	public RobotArmJ3D init() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setFocusableWindowState(true);
//		setFocusable(true);
        
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		canvas.setSize(800, 600);
//		universe = new SimpleUniverse(canvas);
		
		// manually create the viewing platform so that we can customize it
	    ViewingPlatform viewingPlatform = new ViewingPlatform();
	    viewingPlatform.getViewPlatform().setActivationRadius(300f);
	    final TransformGroup viewTransform = viewingPlatform.getViewPlatformTransform();
	    Transform3D t3d = new Transform3D();
	    t3d.lookAt(new Point3d(-1.4,0.3,1.0), new Point3d(0,0,0), new Vector3d(0,1,0));
	    t3d.invert();
	    viewTransform.setTransform(t3d);

	    // Set back clip distance so things don't disappear 
	    Viewer viewer = new Viewer(canvas);
	    viewer.getView().setBackClipDistance(300);
	    universe = new SimpleUniverse(viewingPlatform, viewer);

//		universe.getViewingPlatform().setNominalViewingTransform();

	    final RobotArmJ3D robotarm = new RobotArmJ3D();
	    BranchGroup scene = robotarm.createSceneGraph();
		universe.addBranchGraph(scene);

		setLayout(new BorderLayout());
		add("Center", canvas);

		add(canvas);
		pack();
		setAlwaysOnTop(true);
		
		setVisible(true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toFront();
		repaint();

		return robotarm;
	}

}
