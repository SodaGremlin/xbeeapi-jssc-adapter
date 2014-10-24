package com.sensorsomething.xbee.jssc;

import com.rapplogic.xbee.XBeeConnection;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Logger;

/**
 * This class asks the serial port if there is anything on the buffer
 */
public class JSSCNotifyThread extends Thread {
	static Logger logger = Logger.getLogger(JSSCNotifyThread.class);

	private SerialPort serialPort;
	private XBeeConnection connection;

	public JSSCNotifyThread(SerialPort serialPort, XBeeConnection connection) {
		this.serialPort = serialPort;
		this.connection = connection;
	}

	@Override
	public void run() {
		while(!this.isInterrupted()) {
			try {
				if(serialPort.getInputBufferBytesCount() > 0) {
					synchronized (connection) {
						connection.notify();
					}
				} else {
					Thread.sleep(0, 100); // needed to keep it from spiking out the CPU
				}
			} catch (SerialPortException e) {
				logger.error("Could not check input count, exiting", e);
				return;
			} catch (InterruptedException e) {
				logger.error("Interrupted", e);
				return;
			}
		}
	}
}
