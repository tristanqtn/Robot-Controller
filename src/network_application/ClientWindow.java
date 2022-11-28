package network_application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ClientWindow implements Runnable, ActionListener, AdjustmentListener, WindowListener, ChangeListener {

	private JFrame frame;

	private Client app;

	private JButton up;
	private JButton down;
	private JButton left;
	private JButton right;

	public JTextField direction;
	public JTextField speed;
	public JTextField last_update;
	public JTextField server_status;
	private JSlider speed_slider;
	private JSlider robot_size;
	private JLabel lblNewLabel_5;
	private JTextField display_size;

	/**
	 * Launch the application.
	 */
	public static void main(String name, Client client) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ClientWindow window = new ClientWindow(name, client);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow(String name, Client client) {
		this.app = client;
		initialize(name);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String name) {
		frame = new JFrame(name + " controller");
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setBounds(500, 100, 600, 300);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 73, 69, 0, 65, 63, 70, 73, 0, 79, 0 };
		gridBagLayout.rowHeights = new int[] { 14, 20, 23, 23, 23, 20, 14, 26, 14, 20, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("SPEED");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 1;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		up = new JButton("UP");
		up.setForeground(Color.BLACK);
		up.setBackground(Color.WHITE);
		GridBagConstraints gbc_up = new GridBagConstraints();
		gbc_up.anchor = GridBagConstraints.NORTH;
		gbc_up.fill = GridBagConstraints.HORIZONTAL;
		gbc_up.insets = new Insets(0, 0, 5, 5);
		gbc_up.gridx = 2;
		gbc_up.gridy = 2;
		frame.getContentPane().add(up, gbc_up);

		this.up.addActionListener(this);

		speed = new JTextField();
		speed.setEditable(false);
		speed.setForeground(Color.WHITE);
		speed.setBackground(Color.GRAY);
		GridBagConstraints gbc_speed = new GridBagConstraints();
		gbc_speed.gridwidth = 3;
		gbc_speed.anchor = GridBagConstraints.NORTH;
		gbc_speed.fill = GridBagConstraints.HORIZONTAL;
		gbc_speed.insets = new Insets(0, 0, 5, 5);
		gbc_speed.gridx = 5;
		gbc_speed.gridy = 2;
		frame.getContentPane().add(speed, gbc_speed);
		speed.setColumns(10);

		this.left = new JButton("LEFT");
		left.setBackground(Color.WHITE);
		GridBagConstraints gbc_left = new GridBagConstraints();
		gbc_left.fill = GridBagConstraints.HORIZONTAL;
		gbc_left.anchor = GridBagConstraints.NORTH;
		gbc_left.insets = new Insets(0, 0, 5, 5);
		gbc_left.gridx = 1;
		gbc_left.gridy = 3;
		frame.getContentPane().add(this.left, gbc_left);
		this.left.addActionListener(this);

		this.right = new JButton("RIGHT");
		right.setBackground(Color.WHITE);
		GridBagConstraints gbc_right = new GridBagConstraints();
		gbc_right.fill = GridBagConstraints.HORIZONTAL;
		gbc_right.anchor = GridBagConstraints.NORTH;
		gbc_right.insets = new Insets(0, 0, 5, 5);
		gbc_right.gridx = 3;
		gbc_right.gridy = 3;
		frame.getContentPane().add(this.right, gbc_right);
		this.right.addActionListener(this);

		JLabel lblNewLabel_1 = new JLabel("DIRECTION");
		lblNewLabel_1.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 5;
		gbc_lblNewLabel_1.gridy = 3;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		this.down = new JButton("DOWN");
		down.setBackground(Color.WHITE);
		GridBagConstraints gbc_down = new GridBagConstraints();
		gbc_down.fill = GridBagConstraints.HORIZONTAL;
		gbc_down.anchor = GridBagConstraints.NORTH;
		gbc_down.insets = new Insets(0, 0, 5, 5);
		gbc_down.gridx = 2;
		gbc_down.gridy = 4;
		frame.getContentPane().add(this.down, gbc_down);
		this.down.addActionListener(this);

		this.direction = new JTextField();
		direction.setEditable(false);
		direction.setForeground(Color.WHITE);
		direction.setBackground(Color.GRAY);
		GridBagConstraints gbc_direction = new GridBagConstraints();
		gbc_direction.gridwidth = 3;
		gbc_direction.fill = GridBagConstraints.HORIZONTAL;
		gbc_direction.insets = new Insets(0, 0, 5, 5);
		gbc_direction.gridx = 5;
		gbc_direction.gridy = 4;
		frame.getContentPane().add(this.direction, gbc_direction);
		this.direction.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("SPEED SLIDER");
		lblNewLabel_3.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 6;
		frame.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

		lblNewLabel_5 = new JLabel("ROBOT SIZE");
		lblNewLabel_5.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 5;
		gbc_lblNewLabel_5.gridy = 6;
		frame.getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);

		display_size = new JTextField();
		display_size.setEditable(false);
		display_size.setForeground(Color.WHITE);
		display_size.setBackground(Color.GRAY);
		GridBagConstraints gbc_display_size = new GridBagConstraints();
		gbc_display_size.insets = new Insets(0, 0, 5, 5);
		gbc_display_size.fill = GridBagConstraints.HORIZONTAL;
		gbc_display_size.gridx = 6;
		gbc_display_size.gridy = 6;
		frame.getContentPane().add(display_size, gbc_display_size);
		display_size.setText("" + 10);
		display_size.setColumns(10);

		speed_slider = new JSlider();
		speed_slider.setBackground(Color.GRAY);
		speed_slider.setValue(0);
		GridBagConstraints gbc_speed_slider = new GridBagConstraints();
		gbc_speed_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_speed_slider.anchor = GridBagConstraints.NORTH;
		gbc_speed_slider.insets = new Insets(0, 0, 5, 5);
		gbc_speed_slider.gridwidth = 3;
		gbc_speed_slider.gridx = 1;
		gbc_speed_slider.gridy = 7;
		frame.getContentPane().add(speed_slider, gbc_speed_slider);

		this.speed_slider.addChangeListener(this);

		robot_size = new JSlider();
		robot_size.setMinimum(5);
		robot_size.setMaximum(25);
		robot_size.setValue(10);
		robot_size.setBackground(Color.GRAY);
		GridBagConstraints gbc_robot_size = new GridBagConstraints();
		gbc_robot_size.fill = GridBagConstraints.HORIZONTAL;
		gbc_robot_size.gridwidth = 2;
		gbc_robot_size.insets = new Insets(0, 0, 5, 5);
		gbc_robot_size.gridx = 5;
		gbc_robot_size.gridy = 7;
		frame.getContentPane().add(robot_size, gbc_robot_size);

		this.robot_size.addChangeListener(this);

		JLabel lblNewLabel_2 = new JLabel("LAST UPDATE");
		lblNewLabel_2.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 8;
		frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

		JLabel lblNewLabel_4 = new JLabel("SERVER STATUS");
		lblNewLabel_4.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 5;
		gbc_lblNewLabel_4.gridy = 8;
		frame.getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);

		this.last_update = new JTextField();
		last_update.setEditable(false);
		last_update.setForeground(Color.GREEN);
		last_update.setBackground(Color.GRAY);
		GridBagConstraints gbc_last_update = new GridBagConstraints();
		gbc_last_update.gridwidth = 3;
		gbc_last_update.anchor = GridBagConstraints.NORTH;
		gbc_last_update.fill = GridBagConstraints.HORIZONTAL;
		gbc_last_update.insets = new Insets(0, 0, 5, 5);
		gbc_last_update.gridx = 1;
		gbc_last_update.gridy = 9;
		frame.getContentPane().add(this.last_update, gbc_last_update);
		this.last_update.setColumns(10);

		this.server_status = new JTextField();
		server_status.setEditable(false);
		server_status.setForeground(Color.GREEN);
		server_status.setBackground(Color.GRAY);
		GridBagConstraints gbc_server_status = new GridBagConstraints();
		gbc_server_status.insets = new Insets(0, 0, 5, 5);
		gbc_server_status.gridwidth = 3;
		gbc_server_status.anchor = GridBagConstraints.NORTH;
		gbc_server_status.fill = GridBagConstraints.HORIZONTAL;
		gbc_server_status.gridx = 5;
		gbc_server_status.gridy = 9;
		frame.getContentPane().add(this.server_status, gbc_server_status);
		this.server_status.setColumns(10);

		frame.setVisible(true);

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	// HANDLER FOR SCROLLBAR UPDATES
	public void adjustmentValueChanged(AdjustmentEvent e) {

	}

	@Override
	// HANDLER FOR BUTTON UPDATES
	public void actionPerformed(ActionEvent e) {
		this.movement_daemon(e); // call to daemon for movement buttons
		boolean response = this.app.send_update(); // send update
		// response interpreter
		if (response) {
			System.out.println("Update process finished with success");
		}
	}

	// DAEMON FOR MOVEMENT BUTTONS
	public void movement_daemon(ActionEvent e) {
		if (e.getSource().equals(up)) {// up button pressed
										// modifying robot's movement
			this.app.my_robot.setDirection("up");
			this.app.my_robot.setSpeed_X(0);
			this.app.my_robot.setSpeed_Y(0 - this.app.my_robot.getActual_speed());

			this.direction.setText("Up");
		} else if (e.getSource().equals(down)) {// down button pressed
												// modifying robot's movement
			this.app.my_robot.setDirection("down");
			this.app.my_robot.setSpeed_X(0);
			this.app.my_robot.setSpeed_Y(this.app.my_robot.getActual_speed());

			this.direction.setText("Down");
		} else if (e.getSource().equals(left)) {// left button pressed
												// modifying robot's movement
			this.app.my_robot.setDirection("left");
			this.app.my_robot.setSpeed_X(0 - this.app.my_robot.getActual_speed());
			this.app.my_robot.setSpeed_Y(0);

			this.direction.setText("Left");
		} else if (e.getSource().equals(right)) {// right button pressed
													// modifying robot's movement
			this.app.my_robot.setDirection("right");
			this.app.my_robot.setSpeed_X(this.app.my_robot.getActual_speed());
			this.app.my_robot.setSpeed_Y(0);

			this.direction.setText("Right");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(speed_slider)) { // scrollbar update
			// actualize robot's speed
			this.app.my_robot.setActual_speed(speed_slider.getValue());

			boolean response = this.app.send_update(); // sending update to server
			// response interpreter
			if (response) {
				System.out.println("Update process finished with success");
			}
		}
		if (e.getSource().equals(robot_size)) { // scrollbar update
			// actualize robot's speed
			this.app.my_robot.setSize(robot_size.getValue());
			this.display_size.setText("" + robot_size.getValue());
			boolean response = this.app.send_update(); // sending update to server
			// response interpreter
			if (response) {
				System.out.println("Update process finished with success");
			}
		}
	}
}
