package com.rapplogic.xbee.jssc;

import com.rapplogic.xbee.JSSCSerialComm;
import com.rapplogic.xbee.api.XBee;
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
		JSSCSerialComm serial = new JSSCSerialComm();

		serial.openSerialPort("COM7", 115200);

		XBee xbee = new XBee();

		xbee.initProviderConnection(serial);

		assertTrue(xbee.isConnected());

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
