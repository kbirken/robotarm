package org.nanosite.robotarm.common;

import java.util.HashSet;
import java.util.Set;

public class DirectControlDispatcher implements IRobotArmDirectControl {

	private Set<IRobotArmDirectControl> clients;
	
	public DirectControlDispatcher() {
		clients = new HashSet<IRobotArmDirectControl>(); 
	}
	
	public void addClient(IRobotArmDirectControl client) {
		clients.add(client);
	}

	@Override
	public boolean grab(int openMM) {
		boolean ret = true;
		for(IRobotArmDirectControl c : clients) {
			if (! c.grab(openMM))
				ret = false;
		}
		return ret;
	}

	@Override
	public void delay(int millisec) {
		for(IRobotArmDirectControl c : clients) {
			c.delay(millisec);
		}
	}

	@Override
	public boolean reset() {
		boolean ret = true;
		for(IRobotArmDirectControl c : clients) {
			if (! c.reset())
				ret = false;
		}
		return ret;
	}

	@Override
	public boolean shutdown() {
		boolean ret = true;
		for(IRobotArmDirectControl c : clients) {
			if (! c.shutdown())
				ret = false;
		}
		return ret;
	}

	@Override
	public boolean move(double base, double humerus, double ulna,
			double gripper, double rot, int t)
	{
		boolean ret = true;
		for(IRobotArmDirectControl c : clients) {
			if (! c.move(base, humerus, ulna, gripper, rot, t))
				ret = false;
		}
		return ret;
	}
}
