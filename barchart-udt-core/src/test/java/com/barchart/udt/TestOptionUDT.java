/**
 * Copyright (C) 2009-2012 Barchart, Inc. <http://www.barchart.com/>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.barchart.udt;

import static org.junit.Assert.*;
import static util.UnitHelp.*;

import java.net.InetSocketAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.TestAny;

public class TestOptionUDT extends TestAny {

	@Test
	public void testOptionBasic() throws Exception {

		final SocketUDT socket = new SocketUDT(TypeUDT.DATAGRAM);

		OptionUDT option;

		option = OptionUDT.UDT_SNDSYN;
		boolean booleanValue;
		booleanValue = true;
		socket.setOption(option, booleanValue);
		assertEquals(booleanValue, socket.getOption(option));
		booleanValue = false;
		socket.setOption(option, booleanValue);
		assertEquals(booleanValue, socket.getOption(option));

		log.info("pass: boolean");

		option = OptionUDT.UDP_RCVBUF;
		int intValue;
		intValue = 123456789;
		socket.setOption(option, intValue);
		assertEquals(intValue, socket.getOption(option));
		intValue = 987654321;
		socket.setOption(option, intValue);
		assertEquals(intValue, socket.getOption(option));

		log.info("pass: int");

		option = OptionUDT.UDT_MAXBW;
		long longValue;
		longValue = 1234567890123456789L;
		socket.setOption(option, longValue);
		assertEquals(longValue, socket.getOption(option));
		longValue = 8765432109876543210L;
		socket.setOption(option, longValue);
		assertEquals(longValue, socket.getOption(option));

		log.info("pass: long");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testOptionLingerRange1() throws ExceptionUDT {
		new LingerUDT(12345678);
	}

	@Test
	public void testOptionLingerRange2() throws ExceptionUDT {
		new LingerUDT(-12345678);
		new LingerUDT(0);
		new LingerUDT(65535);
	}

	@Test
	public void testOptionLinger() throws Exception {

		final SocketUDT socket = new SocketUDT(TypeUDT.DATAGRAM);

		final OptionUDT option = OptionUDT.UDT_LINGER;

		final LingerUDT linger1 = new LingerUDT(65432);
		socket.setOption(option, linger1);
		assertEquals(linger1, socket.getOption(option));

		final LingerUDT linger2 = new LingerUDT(-12345);
		socket.setOption(option, linger2);
		assertEquals(LingerUDT.LINGER_ZERO, socket.getOption(option));

		log.info("pass: linger");

	}

	@Test
	public void testOptionsPrint() throws Exception {

		final SocketUDT serverSocket = new SocketUDT(TypeUDT.DATAGRAM);
		final InetSocketAddress serverAddress = localSocketAddress();
		serverSocket.bind(serverAddress);
		serverSocket.listen(1);
		assertTrue(serverSocket.isBound());

		final SocketUDT clientSocket = new SocketUDT(TypeUDT.DATAGRAM);
		final InetSocketAddress clientAddress = localSocketAddress();
		clientSocket.bind(clientAddress);
		assertTrue(clientSocket.isBound());

		clientSocket.connect(serverAddress);
		assertTrue(clientSocket.isConnected());

		final SocketUDT acceptSocket = serverSocket.accept();
		assertTrue(acceptSocket.isConnected());

		log.info("client options:{}", clientSocket.toStringOptions());
		log.info("accept options:{}", acceptSocket.toStringOptions());

	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
