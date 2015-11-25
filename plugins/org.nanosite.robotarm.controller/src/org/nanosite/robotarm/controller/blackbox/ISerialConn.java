package org.nanosite.robotarm.controller.blackbox;

/**
 * This is an interface as a blueprint for the Franca=>Java generator.
 */
public interface ISerialConn {

	static interface Client {
		public void sendCommandResponse();
	}
	
	public void sendCommand(String command);
	
}
