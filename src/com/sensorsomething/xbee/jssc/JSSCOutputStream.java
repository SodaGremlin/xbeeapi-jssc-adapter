package com.sensorsomething.xbee.jssc;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Small output stream for JSSC to integrate with the xbee-api
 */
public class JSSCOutputStream extends OutputStream {
	static Logger logger = Logger.getLogger(JSSCOutputStream.class);

	private SerialPort serialPort;

	public JSSCOutputStream(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	@Override
	public void write(int b) throws IOException {
		try {
			serialPort.writeByte((byte) b);
		} catch (SerialPortException e) {
			logger.error("Could not write the byte to the serial port", e);
		}
	}
}
