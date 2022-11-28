package network_application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler {

	// NETWORKING
	private Socket clientSocket = null; // Client socket object
	private ObjectInputStream is = null; // Input stream
	private ObjectOutputStream os = null; // Output stream
	private DateTimeService theDateService;

	// The constructor for the connection handler
	public ConnectionHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		// Set up a service object to get the current date and time
		theDateService = new DateTimeService();

	}

	// Will eventually be the thread execution method - can't pass the exception
	// back
	public void init(Server _serv) {
		try {
			// defining input/output streams
			this.is = new ObjectInputStream(clientSocket.getInputStream());
			this.os = new ObjectOutputStream(clientSocket.getOutputStream());
			while (this.readCommand(_serv)) {// reading received command and handle process
			}
		} catch (IOException e) {// exception handler
			System.out.println("XX. There was a problem with the Input/Output Communication:");
			e.printStackTrace();
		}
	}

	// Receive and process incoming string commands from client socket
	private boolean readCommand(Server _serv) {
		Robot s = null;
		boolean response = false;
		try {
			s = (Robot) is.readObject(); // reading robot profile received
			response = _serv.accept_bot(s); // check if we can accept the boot
		}

		catch (Exception e) { // catch a general exception
			this.closeSocket();
			return false;
		}
		System.out.println("01. <- Received a String object from the client (" + s.getName() + ").");
		System.out.println(s.getClass().getName());
		// At this point there is a valid String object
		// invoke the appropriate function based on the command
		if (s.getClass().getName().equalsIgnoreCase("network_application.Robot") && response) {
			// checking if we received a valid robot
			this.getDate(); // sending date process
		}
		// response interpreter
		else if (!response) {
			this.sendError("Robot already existing under this name");
		} else {
			this.sendError("Invalid command: " + s);
		}
		return false;
	}

	// Use our custom DateTimeService Class to get the date and time
	private void getDate() { // use the date service to get the date
		String currentDateTimeText = theDateService.getDateAndTime();
		this.send(currentDateTimeText);
	}

	// Send a generic object back to the client
	private void send(Object o) {
		try {
			System.out.println("02. -> Sending (" + o + ") to the client.");
			this.os.writeObject(o);
			this.os.flush();
		} catch (Exception e) {
			System.out.println("XX." + e.getStackTrace());
		}
	}

	// Send a pre-formatted error message to the client
	public void sendError(String message) {
		this.send("Error:" + message); // remember a String IS-A Object!
	}

	// Close the client socket
	public void closeSocket() { // gracefully close the socket connection
		try {
			this.os.close();
			this.is.close();
			this.clientSocket.close();
		} catch (Exception e) {
			System.out.println("XX. " + e.getStackTrace());
		}
	}

	// END OF CLASS
}