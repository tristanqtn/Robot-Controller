package network_application;

import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client implements Runnable {

	// NETWORKING
	private static int portNumber = 5050;
	private String IP_address;
	private Socket socket = null;
	private ObjectOutputStream os = null;
	private ObjectInputStream is = null;
	private String last_update_time = null;

	// GUI
	private ClientWindow gui;

	// ROBOT MANAGED BY THE CLIENT
	public Robot my_robot;

	// TIMER FOR AUTOMATIC UPDATE
	public Timer clock;
	public TimerTask task;

	// CLIENT CTOR (needs the serve IP and the name of the robot to create)
	public Client(String serverIP, String name) {
		if (!connect_to_server(serverIP)) {
			// FAILED to connect to server
			System.out.println("XX. Failed to open socket connection to: " + serverIP);
		} else {// SUCCESSFULLY connected to server
				// storing IP address
			this.IP_address = new String();
			this.IP_address = serverIP;
			// creating new robot
			my_robot = new Robot(name); // creating using name
			my_robot.display(); // display new robot
		}
	}

	// FUNCTION USED TO CONNECT TO SERVER using IP address
	private boolean connect_to_server(String serverIP) {
		try {
			// open a new socket to the server
			this.socket = new Socket(serverIP, portNumber);
			this.os = new ObjectOutputStream(this.socket.getOutputStream());
			this.is = new ObjectInputStream(this.socket.getInputStream());

			// display success message
			System.out.println("00. -> Connected to Server:" + this.socket.getInetAddress() + " on port: "
					+ this.socket.getPort());
			System.out.println("    -> from local address: " + this.socket.getLocalAddress() + " and port: "
					+ this.socket.getLocalPort());
		} catch (Exception e) {// exception handler
			// in case of failure we display an error message
			System.out.println("XX. Failed to Connect to the Server at port: " + portNumber);
			System.out.println("    Exception: " + e.toString());
			return false; // return failure
		}
		return true; // return success
	}

	// FUNCTION USED TO SEND ROBOT AND RECEIVE DATE
	private boolean send_robot_get_date() {
		// variables
		boolean response = false;
		String server_response;

		System.out.println("01. -> Sending Command (" + this.my_robot.getName() + ") to the server...");
		// send the robot to server command to server
		this.send(this.my_robot); // sending robot to server

		try {
			server_response = (String) receive(); // receiving the response of the server
			// displaying server response
			System.out.println("05. <- The Server responded with: ");
			System.out.println("    <- " + server_response);
			// response interpreter
			if (!server_response.equals("Error: Robot already existing under this name OR Server full ")) {
				// the server has accepted the robot
				this.last_update_time = server_response;
				response = true;
				// updating UI
				this.gui.speed.setText("" + this.my_robot.getActual_speed());
				this.gui.last_update.setText(this.last_update_time.substring(28));
				this.gui.last_update.setForeground(Color.GREEN);
				this.gui.server_status.setText("Connected to server");
			}
		} catch (Exception e) {// exception handler
			System.out.println("XX. There was an invalid object sent back from the server");
		}
		System.out.println("06. -- Disconnected from Server.");
		return response; // returning response to be able to know the output of the process
	}

	// METHOD USED TO SEND A GENERIC OBJECT
	private void send(Object o) {
		try {
			System.out.println("02. -> Sending an object...");
			os.writeObject(o); // sending object
			os.flush(); // cleaning output stream
		} catch (Exception e) {// exception handler
			System.out.println("XX. Exception Occurred on Sending:" + e.toString());
		}
	}

	// METHOD USED TO RECEIVE A GENERIC OBJECT
	private Object receive() {
		Object o = null;
		try {
			System.out.println("03. -- About to receive an object...");
			o = is.readObject(); // reading input stream
			System.out.println("04. <- Object received...");
		} catch (Exception e) {// exception handler
			System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
		}
		return o; // return received object
	}

	// FUNCTION USED TO SEND AN UPDATE OF THE ROBOT PROFILE
	public boolean send_update() {
		this.connect_to_server(this.IP_address); // connecting to server
		boolean response = this.send_robot_get_date(); // sending robot and wait for response
		// response interpreter
		if (response) {
			System.out.println("Update send succesfully");
		}

		return response; // returning response
	}

	// CLASS MAIN
	public static void main(String args[]) {
		System.out.println("**. Java Client Application - Robot Manager");

		if (args.length == 2) {// if we have the IP address of server and robots name

			Client theApp = new Client(args[0], args[1]); // building the client

			boolean response = theApp.send_robot_get_date(); // sending the created robot to server
			if (response) {// robot added to server successfully
							// theApp.thread = new Thread (theApp); //creating the thread (used for UI)
				theApp.clock = new Timer(); // creating the timer for update count-down
				theApp.task = new TimerTask() // creating the task to send updates
				{// configure task to send update
					@Override
					public void run() {
						theApp.send_update(); // send an update of robot profile
					}

				};
				theApp.my_robot.setStatus(true); // robot has been added to server so now we only send update of this
													// robot profile to server
				// theApp.thread.start(); //start UI
				theApp.gui = new ClientWindow(theApp.my_robot.getName(), theApp);
				theApp.clock.scheduleAtFixedRate(theApp.task, 10000, 10000); // throw task every 10s after the beginning
																				// of client
			}
		} else {// wrong arguments entered
			System.out.println("Error: you must provide the address of the server");
			System.out.println("Usage is:  java Client x.x.x.x  (e.g. java Client 192.168.7.2)");
			System.out.println("      or:  java Client hostname (e.g. java Client localhost)");
		}
		System.out.println("**. End of Application.");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	// END OF CLASS
}