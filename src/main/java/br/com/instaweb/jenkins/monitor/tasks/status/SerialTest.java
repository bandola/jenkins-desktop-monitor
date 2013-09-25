package br.com.instaweb.jenkins.monitor.tasks.status;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Random;

public class SerialTest implements SerialPortEventListener {
	SerialPort serialPort;
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() throws Exception {
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier("COM8");
		//First, Find an instance of serial port as set in PORT_NAMES.
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
		serialPort.setSerialPortParams(DATA_RATE,SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();

		// add event listeners
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
	}
	
	public synchronized void write(String msg) throws IOException{
		output.write(msg.getBytes());
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int inputLine=input.read();
				System.out.println((char)inputLine);
			} catch (Exception e) {
				System.err.println(e.toString());
				e.printStackTrace();
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

}
