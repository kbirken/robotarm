package org.nanosite.robotarm.controller;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

public class SerialConnection {
	private String port;

	private SerialPort serialPort = null;
	private OutputStream outputStream = null;

	private int baudrate = 115200;
	private int dataBits = SerialPort.DATABITS_8;
	private int stopBits = SerialPort.STOPBITS_1;
	private int parity = SerialPort.PARITY_NONE;


	public SerialConnection (String port) {
		this.port = port;
	}

	public boolean open() {
		CommPortIdentifier portId = getSerialPortId(port);
		if (portId==null) {
			System.err.println("ERROR: cannot open serial port '" + port + "'!");
			return false;
		}

		try {
			serialPort = (SerialPort)portId.open("SSC32", 2000);
		} catch (PortInUseException e) {
			System.err.println("ERROR: serial port " + port + " already in use!");
			e.printStackTrace();
			return false;
		}

		try {
			serialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);
		} catch (UnsupportedCommOperationException e) {
			System.err.println("ERROR: couldn't set serial port parameters!");
			e.printStackTrace();
			serialPort.close();
			return false;
		}

		try {
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
			System.err.println("ERROR: couldn't get output stream for serial port!");
			e.printStackTrace();
			serialPort.close();
			return false;
		}

		return true;
	}


	public void close() {
		if (outputStream!=null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			outputStream = null;
		}
		if (serialPort!=null)
		{
			serialPort.close();
			serialPort = null;
		}
	}


	public boolean send (String msg) {
		if (outputStream!=null) {
			String msg1 = msg + "\r";
			try {
				outputStream.write(msg1.getBytes());
			} catch (IOException e) {
				System.err.println("ERROR: couldn't send message '" + msg + "' to output stream!");
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}

		return true;
	}


	private CommPortIdentifier getSerialPortId (String name) {
		
		@SuppressWarnings("rawtypes")
		Enumeration enumComm = CommPortIdentifier.getPortIdentifiers();

		CommPortIdentifier serialPortId;
	    while (enumComm.hasMoreElements()) {
	    	serialPortId = (CommPortIdentifier) enumComm.nextElement();
	    	//System.out.println("port=" + serialPortId.getName() + " is " + serialPortId.getPortType());
	     	if(serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	    		//System.out.println(serialPortId.getName());
	    		if (serialPortId.getName().equals(name)) {
	    			return serialPortId;
	    		}
	    	}
	    }
	    return null;
	}
}
