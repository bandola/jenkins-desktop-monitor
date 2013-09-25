package br.com.instaweb.jenkins.monitor.tasks.status;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import br.com.instaweb.jenkins.monitor.bean.BuildState;
import br.com.instaweb.jenkins.monitor.events.BuildStateChangedEvent;

import com.google.common.eventbus.Subscribe;

public class SerialPortNotifier implements SerialPortEventListener {

	private SerialPort serialPort;
	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;
	
	private static Map<BuildState, String> BUILDSTATE_TO_SERIAL_COMMAND = new HashMap<BuildState, String>();
	static{
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.blue_anime, "gb");
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.red_anime, "rb");
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.aborted_anime, "yb");
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.yellow, "y");
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.yellow_anime, "yb");
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.aborted, "y");
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.red, "r");
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.blue, "g");
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.disabled, "y");
		BUILDSTATE_TO_SERIAL_COMMAND.put(BuildState.unknown, "o");
	}
	
	public SerialPortNotifier(){
		try {
			initialize();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Subscribe
	public void buildStatusChanged(BuildStateChangedEvent event) throws IOException {
		checkArgument(BUILDSTATE_TO_SERIAL_COMMAND.containsKey(event.getCurrent()), "Serial command not found for status " + event.getCurrent());
		output.write(BUILDSTATE_TO_SERIAL_COMMAND.get(event.getCurrent()).getBytes());
		if(buildFailed(event)){
			output.write('m');
		}
	}
	
	private boolean buildFailed(BuildStateChangedEvent event){
		return (event.getCurrent() == BuildState.red
				&& (event.getPrevious() == BuildState.blue 
					|| event.getPrevious() == BuildState.blue_anime
					|| event.getPrevious() == BuildState.yellow
					|| event.getPrevious() == BuildState.yellow_anime
				));
	}

	public void initialize() throws Exception {
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier("COM3");
		checkNotNull(portId, "Could not find COM port.");
		serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
		serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
	}

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
	}
}
