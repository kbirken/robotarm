package org.nanosite.robotarm.controller.blackbox;

/**
 * This is an interface as a blueprint for the Franca=>Java generator.
 */
public interface IRobotArm {

	static interface Client {
		public void moveResponse(boolean ok);
		public void grabResponse(boolean ok);
		public void releaseResponse(boolean ok);

		public void griptime(Integer t);
	}
	
	static interface Position {
		public Integer getX();
		public Integer getY();
		public Integer getZ();
		public void setX(Integer x);
		public void setY(Integer y);
		public void setZ(Integer z);
	}
	
	public void move(Position pos, Double angleOfAttack);
	public void grab(Integer openMM); // ???
	public void release();
}
