package org.nanosite.robotarm.common;

/**
 * Geometry of Lynxmotion AL5D robot arm.
 * 
 * @author KBirken
 */
public class GeometryAL5D {

	// base
	public final static float BASE_WIDTH = 93.0f;
	public final static float BASE_HEIGHT = 67.31f;

	// common width for all arm segments
	public final static float SEGMENT_WIDTH = 14.0f;
	
	// shoulder-to-elbow (5.75")
	public final static float HUMERUS_LENGTH = 146.05f;
	
	// elbow-to-wrist (7.375")
	public final static float ULNA_LENGTH = 187.325f;

	// gripper, incl. heavy-duty wrist rotate (3.94", wrist to middle of gripper)
	public final static float GRIPPER_LENGTH = 103.0f;
	

	// stepper motor bounds for gripper
	// TODO: scale in mm, mapping to stepper bounds has to be done the layer below
	public final static int gripOpen = 800;
	public final static int gripClosed = 2080;
	public final static int gripOpenMM = 31;

}
