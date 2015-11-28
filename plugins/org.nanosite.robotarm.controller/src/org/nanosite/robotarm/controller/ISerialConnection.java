package org.nanosite.robotarm.controller;

public interface ISerialConnection {

	boolean open();
	void close();

	boolean send(String msg);

}