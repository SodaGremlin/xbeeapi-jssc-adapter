package com.sensorsomething.xbee.jssc;

import com.rapplogic.xbee.XBeeConnection;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Gregg D. Harrington
 * Date: 7/20/14
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
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
				}
			} catch (SerialPortException e) {
				logger.error("Could not check input count, exiting", e);
				return;
			}
		}
	}
}
