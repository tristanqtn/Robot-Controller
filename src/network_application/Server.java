package network_application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Server implements Runnable {
	// NETWORKING
	private static int portNumber = 5050;

	// INDEXES
	private int next_add_index = 0;
	private int client_number = 0;

	// CONTAINERS
	private static int array_size = 3; // size of the array
	private Robot bot_array[]; // array containing robots

	// TIMER FOR AUTOMATIC UPDATE
	private Timer clock;
	private TimerTask task;

	// THREAD FOR GUI
	private ServerWindow gui;

	// DEFAULT CTOR
	public Server() {
		boolean listening = true; // infinite loop
		ServerSocket serverSocket = null; // server socket
		this.bot_array = new Robot[array_size]; // allocating array

		// initializing array
		for (Integer i = 0; i < array_size; i++) {
			this.bot_array[i] = null;
		}

		try {// set up the Server Socket
			serverSocket = new ServerSocket(portNumber); // allocating server socket
			System.out.println("New Server has started listening on port: " + portNumber);

			this.gui = new ServerWindow(this); // creating the gui
			this.clock = new Timer(); // creating the timer for position update count-down
			this.task = new TimerTask() // creating the task to update position
			{// configure task to update position
				@Override
				public void run() {

					for (Integer i = 0; i < array_size; i++) {// for every bots stored
						if (bot_array[i] != null) {
							if (gui.getBool_pause() == false) {
								bot_array[i].update_position(); // update position
								gui.display_bots(bot_array); // displaying bots on board
							}
							if (gui.isBot_selected() == true) {
								gui.display_data(bot_array[gui.getSelected_bot()]);
							} else if (gui.isBot_selected() == false) {
								gui.clear_display_data();
							}
						}
					}
				}

			};
			// planning bot position update
			this.clock.scheduleAtFixedRate(task, 10000, 2000);
		} catch (IOException e) {// exception handler
			System.out.println("Cannot listen on port: " + portNumber + ", Exception: " + e);
			System.exit(1); // exit error
		}

		// server is now listening for connections
		while (listening) // almost infinite loop - loop once for each client request
		{
			Socket clientSocket = null;
			try {
				System.out.println("**. Listening for a connection...");
				clientSocket = serverSocket.accept();// waiting for a connection and accept it
				// display client socket connection data
				System.out.println("00. <- Accepted socket connection from a client: ");
				System.out.println("    <- with address: " + clientSocket.getInetAddress().toString());
				System.out.println("    <- and port number: " + clientSocket.getPort());
			} catch (IOException e) {// exception handler
				System.out.println("XX. Accept failed: " + portNumber + e);
				listening = false; // end the loop - stop listening for further client requests
			}

			ConnectionHandler con = new ConnectionHandler(clientSocket); // creating a connection handler for this
																			// socket
			con.init(this); // process connection

			System.out.println("02. -- Finished communicating with client:" + clientSocket.getInetAddress().toString());
			System.out.println(" ");
			System.out.println(" ");

			// displaying bot list

			System.out.println("BOT LIST ");
			System.out.println(" ");
			for (Integer i = 0; i < array_size; i++) {
				if (this.bot_array[i] != null)
					this.bot_array[i].display();
			}
		}
		// server is no longer listening for client connections - time to shut down.
		try {// closing socket - put server offline
			System.out.println("04. -- Closing down the server socket gracefully.");
			serverSocket.close();
		} catch (IOException e) {// exception handler
			System.err.println("XX. Could not close server socket. " + e.getMessage());
		}
	}

	// MAIN
	public static void main(String args[]) {
		Server used_serv = new Server(); // creating new server
		System.out.println(used_serv.getClass());
	}

	// FUNCTION USED TO ADD BOT IN CONTAINER
	public boolean accept_bot(Robot bot_to_add) {
		boolean autor_add = true, final_autor_add = false, autor_update = false;
		int index = 0;

		// testing if can add a new robot
		for (Integer i = 0; i < array_size; i++) {// browsing each index of array
			if (this.bot_array[i] != null) {
				if (this.bot_array[i].getName().equalsIgnoreCase(bot_to_add.getName())) {// robot with a same name
																							// already existing
					autor_add = false; // robot refused
				}
			}
		}

		// testing if can update a new robot
		for (Integer i = 0; i < array_size; i++) {// browsing each index of array
			if (this.bot_array[i] != null) {
				if (this.bot_array[i].getName().equalsIgnoreCase(bot_to_add.getName()) && bot_to_add.isStatus()) {
					// robot with the same name and update status identified
					index = i; // storing index of the updated robot
					autor_update = true; // accept update
				}
			}
		}

		// adding or updating robot
		if (autor_add && client_number <= (array_size - 1)) {// adding robot to array
			System.out.println("New bot ACCEPTED in array");
			this.bot_array[next_add_index] = bot_to_add; // add to array
			// updating indexes
			next_add_index++;
			client_number++;
			final_autor_add = true; // allow to return true

		}
		if (autor_update) {// updating a robot
			System.out.println("Updated bot ACCEPTED in array");
			// copying current position of robot and correct position of update
			bot_to_add.setPos_X(this.bot_array[index].getPos_X());
			bot_to_add.setPos_Y(this.bot_array[index].getPos_Y());
			bot_to_add.setLast_position_X(this.bot_array[index].getLast_position_X());
			bot_to_add.setLast_position_Y(this.bot_array[index].getLast_position_Y());

			this.bot_array[index] = bot_to_add; // update array at right index

		}
		// refusing robot
		else {
			System.out.println("New bot REFUSED in array");

		}
		// returning final response
		if (autor_update || final_autor_add)
			return true;
		else
			return false;
	}

	public Robot[] getBot_array() {

		return this.bot_array;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	// END OF CLASS
}