package com.sensorsomething.xbee.jssc;

import com.rapplogic.xbee.api.XBee;
import com.sensorsomething.xbee.jssc.JSSCSerialComm;
import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;

/**
 * Test opening a JSSC Type connection to the xbee
 */
public class JSSCXbeeTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
	}

	public void testJSSCStartup() throws Exception {
		// create the JSSCSerialComm object, this is the replacement object for the default RxTx one used internally.
		JSSCSerialComm serial = new JSSCSerialComm();

		// connect to COM7 at 115200 baud. 8 bits, 1 stop bit, 0 parity is assumed
		serial.openSerialPort("COM7", 115200);

		// xbee object that we will connect up too
		XBee xbee = new XBee();

		// replaces xbee.open() which uses the RxTx model
		xbee.initProviderConnection(serial);

		// test we are connected (This is a jUnit test after all)
		assertTrue(xbee.isConnected());

		// close the xBee
		xbee.close();
	}

	public void testJSSCGetAddress() throws Exception {
		JSSCSerialComm serial = new JSSCSerialComm();

		serial.openSerialPort("COM7", 115200);

		XBee xbee = new XBee();

		xbee.initProviderConnection(serial);

		assertTrue(xbee.isConnected());

//		xbee.sendRequest();

		xbee.close();
	}
}
