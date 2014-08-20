package com.sensorsomething.xbee.jssc;

import com.rapplogic.xbee.XBeeConnection;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Gregg D. Harrington
 * Date: 7/20/14
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSSCInputStream extends InputStream {
	static Logger logger = Logger.getLogger(JSSCInputStream.class);

	private SerialPort serialPort;
	private JSSCNotifyThread notifyThread;

	public JSSCInputStream(SerialPort serialPort, XBeeConnection connection) {
		this.serialPort = serialPort;

		notifyThread = new JSSCNotifyThread(serialPort, connection);

		notifyThread.start();
	}

	@Override
	public int read() throws IOException {
		try {
			// wait until there is data to return
			while(serialPort.getInputBufferBytesCount() < 1) {
				Thread.sleep(1); // sleep for a milli and wait for the buffer a bit
			}

			byte[] bytes = serialPort.readBytes(1);

			if(bytes.length > 1) {
				throw new IllegalStateException("Unexpected number of bytes returned");
			}

			byte returnByte = bytes[0];

			// sometimes these bytes come in as negitive, they should be subtracted from 256 to be corrected.
			return returnByte < 0 ? 256 + (int) returnByte : returnByte;
		} catch (SerialPortException e) {
			logger.error("Could not read byte from the serial port", e);
			return -1;
		} catch (InterruptedException e) {
			logger.error("JSSCInputStream interrupted while reading.", e);
			return -1;
		}
	}

	@Override
	public int available() throws IOException {
		try {
			return serialPort.getInputBufferBytesCount();
		} catch (SerialPortException e) {
			logger.error("Could not read the number of input bytes ready", e);
			return 0;
		}
	}

	@Override
	public void close() throws IOException {
		notifyThread.interrupt();
	}
}
