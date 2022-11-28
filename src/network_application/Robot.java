package network_application;

import java.io.Serializable;
import java.util.ArrayList;

public class Robot implements Serializable {

	private static final long serialVersionUID = 1L;

	private int robot_size = 10;

	// ATTRIBUTES
	private String name = null;
	private String direction = null;

	private int pos_X;
	private int pos_Y;

	private int speed_X;
	private int speed_Y;

	private int actual_speed;

	private boolean status = false;

	private ArrayList<Integer> last_position_X;
	private ArrayList<Integer> last_position_Y;

	// DEFAULT CTOR
	public Robot(String _name) {
		this.setName(_name);
		this.setPos_X(250);
		this.setPos_Y(250);

		this.speed_X = 0;
		this.speed_Y = 0;

		this.robot_size = 10;

		this.status = false;

		this.actual_speed = 0;

		this.last_position_X = new ArrayList<Integer>();
		this.last_position_Y = new ArrayList<Integer>();
	}

	public static void main(String[] args) {

	}

	// DISPLAY FUNCTION
	public void display() {
		System.out.println("");
		System.out.println("=================== ROBOT = " + this.name);
		System.out.println("Pos X:" + this.pos_X + "  Pos Y:" + this.pos_Y);
		System.out.println("Speed X:" + this.speed_X + "  Speed Y:" + this.speed_Y);
		System.out.println("Actual speed:" + this.actual_speed);
		System.out.println("Direction: " + this.direction);
		System.out.println("Size: " + this.robot_size);

		System.out.println("Last position X: " + last_position_X);
		System.out.println("Last position Y: " + last_position_Y);

		if (!this.status)
			System.out.println("Status : creation");
		else
			System.out.println("Status : update");

		System.out.println("");
	}

	// NAME INTERFACE
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Direction INTERFACE
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	// SPEED X INTERFACE
	public int getSpeed_X() {
		return speed_X;
	}

	public void setSpeed_X(int speed_X) {
		this.speed_X = speed_X;
	}

	// SPEED Y INTERFACE
	public int getSpeed_Y() {
		return speed_Y;
	}

	public void setSpeed_Y(int speed_Y) {
		this.speed_Y = speed_Y;
	}

	// ACTUAL SPEED INTERFACE
	public int getActual_speed() {
		return actual_speed;
	}

	public void setActual_speed(int actual_speed) {
		this.actual_speed = actual_speed;
	}

	// STATUS INTERFACE
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	// POSITION INTERFACE
	// X
	public int getPos_X() {
		return pos_X;
	}

	public void setPos_X(int pos_X) {
		this.pos_X = pos_X;
	}

	// Y
	public int getPos_Y() {
		return pos_Y;
	}

	public void setPos_Y(int pos_Y) {
		this.pos_Y = pos_Y;
	}

	// ROBOT SIZE INTERFACE
	public int getSize() {
		return robot_size;
	}

	public int setSize(int robot_size) {
		return this.robot_size = robot_size;
	}

	// PREVIOUS X INTERFACE
	public ArrayList<Integer> getLast_position_X() {
		return last_position_X;
	}

	public void setLast_position_X(ArrayList<Integer> last_position_X) {
		this.last_position_X = last_position_X;
	}

	// PREVIOUS Y INTERFACE
	public ArrayList<Integer> getLast_position_Y() {
		return last_position_Y;
	}

	public void setLast_position_Y(ArrayList<Integer> last_position_Y) {
		this.last_position_Y = last_position_Y;
	}

	// FUNCTION USED TO UPDATE BOT POSITION ON BOARD
	public void update_position() {
		// calculation temporary position
		int temp_pos_X = this.pos_X + this.speed_X; // X
		int temp_pos_Y = this.pos_Y + this.speed_Y; // Y

		// authorization of updating position
		boolean update = false;

		if (temp_pos_X >= 0 && (temp_pos_X + this.robot_size) <= 500) {
			if (temp_pos_Y >= 0 && (temp_pos_Y + this.robot_size) <= 500) {
				// if temporary position allow the robot to stay on the board
				update = true; // authorization to update
				// updating position
				this.pos_X = temp_pos_X;
				this.pos_Y = temp_pos_Y;

				// if we have more than 3 previous position
				if ((this.last_position_X.size() > 3) && (this.last_position_Y.size() > 3)) {
					// removing the oldest position
					this.last_position_X.remove(0);
					this.last_position_Y.remove(0);
				}

				// adding previous location
				this.last_position_X.add(temp_pos_X);
				this.last_position_Y.add(temp_pos_Y);
			}
		}

		if (!update) {
			// not allowed to update the bot
			// need to stop the boot
			this.speed_X = 0;
			this.speed_Y = 0;
			this.actual_speed = 0;
			System.out.println("Robot stopped to avoid quitting the map");
		}
		System.out.println("Robot " + this.name + " updated with success");
	}

	// END OF CLASS
}
