package com.sensorsomething.xbee.jssc;


import com.rapplogic.xbee.XBeeConnection;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Replacement for the RxTxSerialComm object.
 */
public class JSSCSerialComm implements XBeeConnection {
	static Logger logger = Logger.getLogger(JSSCSerialComm.class);

	private SerialPort serialPort;
	private InputStream inputStream;
	private OutputStream outputStream;

	public void openSerialPort(String port, int baudRate) throws SerialPortException {
		serialPort = new SerialPort(port);

		serialPort.openPort();
		serialPort.setParams(baudRate, 8, 1, 0);

		inputStream = new JSSCInputStream(serialPort, this);
		outputStream = new JSSCOutputStream(serialPort);
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public void close() {
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			logger.error("Could not close the serial port", e);
		}
	}
}
