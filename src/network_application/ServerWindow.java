package network_application;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ServerWindow
		implements MouseListener, Runnable, ActionListener, AdjustmentListener, WindowListener, ChangeListener {

	private JFrame frame;

	private Canvas canvas;

	private JTextField name;
	private JTextField pos_x;
	private JTextField pos_y;
	private JTextField speed;

	private JLabel robot_pos_X;
	private JLabel robot_pos_Y;
	private JLabel robot_speed;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;

	private Server my_serv = null;

	private int array_size = 3;

	private int selected_bot = -1;
	private boolean bot_selected = false;

	private boolean display_previous_location;
	private boolean bool_pause;

	private JToggleButton previous_location;
	private JToggleButton pause;
	private JLabel robot_speed_1;
	private JTextField size;
	private JSlider collision;
	private JTextField collision_value;

	private int collision_safety = 0;
	private JLabel lblNewLabel;
	private JLabel robot_speed_2;
	private JTextField direction;

	public static void main(String[] args, Server ref) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ServerWindow window = new ServerWindow(ref);
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
	public ServerWindow(Server ref) {

		initialize(ref);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Server ref) {

		display_previous_location = false;
		bool_pause = false;

		this.my_serv = ref;

		frame = new JFrame("Server Interface");
		frame.getContentPane().setBackground(Color.GRAY);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		lblNewLabel_1 = new JLabel("       ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		this.canvas = new Canvas();
		this.canvas.setSize(500, 500);
		canvas.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_canvas = new GridBagConstraints();
		gbc_canvas.insets = new Insets(0, 0, 5, 5);
		gbc_canvas.fill = GridBagConstraints.BOTH;
		gbc_canvas.gridheight = 31;
		gbc_canvas.gridwidth = 16;
		gbc_canvas.ipady = 10;
		gbc_canvas.ipadx = 10;
		gbc_canvas.gridx = 1;
		gbc_canvas.gridy = 1;
		frame.getContentPane().add(canvas, gbc_canvas);

		JLabel robot_name = new JLabel("ROBOT NAME");
		robot_name.setForeground(Color.WHITE);
		GridBagConstraints gbc_robot_name = new GridBagConstraints();
		gbc_robot_name.insets = new Insets(0, 0, 5, 5);
		gbc_robot_name.gridx = 18;
		gbc_robot_name.gridy = 1;
		frame.getContentPane().add(robot_name, gbc_robot_name);

		name = new JTextField();
		name.setBackground(Color.GRAY);
		name.setEditable(false);
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.insets = new Insets(0, 0, 5, 5);
		gbc_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_name.gridx = 18;
		gbc_name.gridy = 2;
		frame.getContentPane().add(name, gbc_name);
		name.setColumns(10);

		robot_pos_X = new JLabel("POS X");
		robot_pos_X.setForeground(Color.WHITE);
		GridBagConstraints gbc_robot_pos_X = new GridBagConstraints();
		gbc_robot_pos_X.insets = new Insets(0, 0, 5, 5);
		gbc_robot_pos_X.gridx = 18;
		gbc_robot_pos_X.gridy = 3;
		frame.getContentPane().add(robot_pos_X, gbc_robot_pos_X);

		pos_x = new JTextField();
		pos_x.setForeground(Color.WHITE);
		pos_x.setBackground(Color.GRAY);
		pos_x.setEditable(false);
		GridBagConstraints gbc_pos_x = new GridBagConstraints();
		gbc_pos_x.insets = new Insets(0, 0, 5, 5);
		gbc_pos_x.fill = GridBagConstraints.HORIZONTAL;
		gbc_pos_x.gridx = 18;
		gbc_pos_x.gridy = 4;
		frame.getContentPane().add(pos_x, gbc_pos_x);
		pos_x.setColumns(10);

		robot_pos_Y = new JLabel("POS Y");
		robot_pos_Y.setForeground(Color.WHITE);
		GridBagConstraints gbc_robot_pos_Y = new GridBagConstraints();
		gbc_robot_pos_Y.insets = new Insets(0, 0, 5, 5);
		gbc_robot_pos_Y.gridx = 18;
		gbc_robot_pos_Y.gridy = 5;
		frame.getContentPane().add(robot_pos_Y, gbc_robot_pos_Y);

		pos_y = new JTextField();
		pos_y.setForeground(Color.WHITE);
		pos_y.setBackground(Color.GRAY);
		pos_y.setEditable(false);
		GridBagConstraints gbc_pos_y = new GridBagConstraints();
		gbc_pos_y.insets = new Insets(0, 0, 5, 5);
		gbc_pos_y.fill = GridBagConstraints.HORIZONTAL;
		gbc_pos_y.gridx = 18;
		gbc_pos_y.gridy = 6;
		frame.getContentPane().add(pos_y, gbc_pos_y);
		pos_y.setColumns(10);

		robot_speed = new JLabel("SPEED");
		robot_speed.setForeground(Color.WHITE);
		GridBagConstraints gbc_robot_speed = new GridBagConstraints();
		gbc_robot_speed.insets = new Insets(0, 0, 5, 5);
		gbc_robot_speed.gridx = 18;
		gbc_robot_speed.gridy = 7;
		frame.getContentPane().add(robot_speed, gbc_robot_speed);

		speed = new JTextField();
		speed.setForeground(Color.WHITE);
		speed.setBackground(Color.GRAY);
		speed.setEditable(false);
		GridBagConstraints gbc_speed = new GridBagConstraints();
		gbc_speed.insets = new Insets(0, 0, 5, 5);
		gbc_speed.fill = GridBagConstraints.HORIZONTAL;
		gbc_speed.gridx = 18;
		gbc_speed.gridy = 8;
		frame.getContentPane().add(speed, gbc_speed);
		speed.setColumns(10);

		robot_speed_1 = new JLabel("SIZE");
		robot_speed_1.setForeground(Color.WHITE);
		GridBagConstraints gbc_robot_speed_1 = new GridBagConstraints();
		gbc_robot_speed_1.insets = new Insets(0, 0, 5, 5);
		gbc_robot_speed_1.gridx = 18;
		gbc_robot_speed_1.gridy = 9;
		frame.getContentPane().add(robot_speed_1, gbc_robot_speed_1);

		size = new JTextField();
		size.setForeground(Color.WHITE);
		size.setEditable(false);
		size.setColumns(10);
		size.setBackground(Color.GRAY);
		GridBagConstraints gbc_size = new GridBagConstraints();
		gbc_size.insets = new Insets(0, 0, 5, 5);
		gbc_size.fill = GridBagConstraints.HORIZONTAL;
		gbc_size.gridx = 18;
		gbc_size.gridy = 10;
		frame.getContentPane().add(size, gbc_size);

		robot_speed_2 = new JLabel("DIRECTION");
		robot_speed_2.setForeground(Color.WHITE);
		GridBagConstraints gbc_robot_speed_2 = new GridBagConstraints();
		gbc_robot_speed_2.insets = new Insets(0, 0, 5, 5);
		gbc_robot_speed_2.gridx = 18;
		gbc_robot_speed_2.gridy = 11;
		frame.getContentPane().add(robot_speed_2, gbc_robot_speed_2);

		direction = new JTextField();
		direction.setForeground(Color.WHITE);
		direction.setEditable(false);
		direction.setColumns(10);
		direction.setBackground(Color.GRAY);
		GridBagConstraints gbc_direction = new GridBagConstraints();
		gbc_direction.insets = new Insets(0, 0, 5, 5);
		gbc_direction.fill = GridBagConstraints.HORIZONTAL;
		gbc_direction.gridx = 18;
		gbc_direction.gridy = 12;
		frame.getContentPane().add(direction, gbc_direction);

		lblNewLabel_2 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 18;
		gbc_lblNewLabel_2.gridy = 15;
		frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

		lblNewLabel = new JLabel("COLLISION SAFETY");
		lblNewLabel.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 18;
		gbc_lblNewLabel.gridy = 16;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		collision = new JSlider();
		collision.setValue(0);
		collision.setMaximum(50);
		collision.setBackground(Color.GRAY);
		GridBagConstraints gbc_collision = new GridBagConstraints();
		gbc_collision.fill = GridBagConstraints.HORIZONTAL;
		gbc_collision.insets = new Insets(0, 0, 5, 5);
		gbc_collision.gridx = 18;
		gbc_collision.gridy = 17;
		frame.getContentPane().add(collision, gbc_collision);

		this.collision.addChangeListener(this);

		collision_value = new JTextField();
		collision_value.setForeground(Color.WHITE);
		collision_value.setBackground(Color.GRAY);
		GridBagConstraints gbc_collision_value = new GridBagConstraints();
		gbc_collision_value.insets = new Insets(0, 0, 5, 0);
		gbc_collision_value.fill = GridBagConstraints.HORIZONTAL;
		gbc_collision_value.gridx = 19;
		gbc_collision_value.gridy = 17;
		frame.getContentPane().add(collision_value, gbc_collision_value);
		collision_value.setColumns(10);
		this.collision_value.setText("" + 0);

		lblNewLabel_3 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 18;
		gbc_lblNewLabel_3.gridy = 18;
		frame.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

		previous_location = new JToggleButton("See previous location");
		GridBagConstraints gbc_previous_location = new GridBagConstraints();
		gbc_previous_location.insets = new Insets(0, 0, 5, 5);
		gbc_previous_location.gridx = 18;
		gbc_previous_location.gridy = 19;
		frame.getContentPane().add(previous_location, gbc_previous_location);
		previous_location.addActionListener(this);

		pause = new JToggleButton("Pause");
		GridBagConstraints gbc_pause = new GridBagConstraints();
		gbc_pause.insets = new Insets(0, 0, 5, 5);
		gbc_pause.gridx = 18;
		gbc_pause.gridy = 22;
		frame.getContentPane().add(pause, gbc_pause);
		pause.addActionListener(this);
		frame.setBounds(100, 100, 800, 600);

		canvas.addMouseListener(this);

		frame.setVisible(true);

	}

	public void display_bots(Robot bot_array[]) {
		Graphics g = this.canvas.getGraphics();

		// clearing canvas
		g.clearRect(0, 0, 550, 550);

		if (display_previous_location == true) {
			// if the user choose to see previous locations
			for (int i = 0; i < this.array_size; i++) {
				// for each robot
				if (bot_array[i] != null) {
					// if it exists
					if (bot_array[i].getLast_position_X().isEmpty() != true) {
						// if the robot has past locations
						for (int j = 0; j < (bot_array[i].getLast_position_X().size() - 1); j++) {
							// for each pst location
							// using fancy colors
							if (j == 2)
								g.setColor(Color.CYAN);
							if (j == 0 || j == 1)
								g.setColor(Color.gray);
							// drawing a square at the past location
							g.fillRect(bot_array[i].getLast_position_X().get(j),
									bot_array[i].getLast_position_Y().get(j), bot_array[i].getSize(),
									bot_array[i].getSize());
						}
					}
				}

			}
		}

		// DRAWING THE CURRENT POSITION
		g.setColor(Color.BLUE);

		for (int i = 0; i < this.array_size; i++) {
			// for each robot
			if (bot_array[i] != null) {
				// if it exists
				if (this.collision_safety > bot_array[i].getSize()) {
					// if the collision safety if logic
					// drawing the collision safety
					g.drawOval((bot_array[i].getPos_X() - this.collision_safety / 2 + bot_array[i].getSize() / 2),
							(bot_array[i].getPos_Y() - this.collision_safety / 2 + bot_array[i].getSize() / 2),
							this.collision_safety, this.collision_safety);
				}

				// drawing the current position with a square
				g.fillRect(bot_array[i].getPos_X(), bot_array[i].getPos_Y(), bot_array[i].getSize(),
						bot_array[i].getSize());
			}
		}

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
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	// toggle button handler
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(previous_location)) {
			// button to see past location changed
			AbstractButton abstractButton = (AbstractButton) e.getSource();
			this.display_previous_location = abstractButton.isSelected(); // store new sate

		}

		if (e.getSource().equals(pause)) {
			// pause button changed
			AbstractButton abstractButton = (AbstractButton) e.getSource();
			this.bool_pause = abstractButton.isSelected(); // store new sate

		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(collision)) { // scrollbar update
			// actualize collision safety
			this.collision_safety = (collision.getValue());
			this.collision_value.setText("" + this.collision_safety);

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		Robot robot_array[] = new Robot[this.array_size]; // allocating array
		robot_array = this.my_serv.getBot_array(); // copying the array of bot at this time

		this.bot_selected = false; // no bot selected

		if (e.getX() <= 500 && e.getY() <= 500) {
			// if the click is in the canvas
			for (int i = 0; i < this.array_size; i++) {
				// for each robot stored
				if (robot_array[i] != null) {
					// if exists
					if ((e.getX() >= robot_array[i].getPos_X())
							&& (e.getX() <= robot_array[i].getPos_X() + robot_array[i].getSize())) {
						if ((e.getY() >= robot_array[i].getPos_Y())
								&& (e.getY() <= robot_array[i].getPos_Y() + robot_array[i].getSize())) {
							// if the click is on the robot square
							this.selected_bot = i; // storing the robot index
							this.bot_selected = true; // a robot has been selected
						}
					}
				}
			}

		}

		// if no robot has been selected
		if (this.bot_selected == false) {
			this.selected_bot = -1; // impossible index
		}
	}

	public void display_data(Robot display_bot) {
		// display robot info in the server gui
		this.name.setForeground(Color.GREEN);
		this.pos_x.setText("" + display_bot.getPos_X());
		this.pos_y.setText("" + display_bot.getPos_Y());
		this.speed.setText("" + display_bot.getActual_speed());
		this.name.setText("" + display_bot.getName());
		this.size.setText("" + display_bot.getSize());
		this.direction.setText("" + display_bot.getDirection());

	}

	public void clear_display_data() {
		// clear robot info textfields of server gui
		this.name.setForeground(Color.RED);
		this.name.setText("No bot selected");
		this.pos_y.setText("");
		this.pos_x.setText("");
		this.speed.setText("");
		this.size.setText("");
		this.direction.setText("");

	}

	public boolean getBool_pause() {
		return this.bool_pause;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public Server getMy_serv() {
		return my_serv;
	}

	public void setMy_serv(Server my_serv) {
		this.my_serv = my_serv;
	}

	public boolean isBot_selected() {
		return bot_selected;
	}

	public void setBot_selected(boolean bot_selected) {
		this.bot_selected = bot_selected;
	}

	public int getSelected_bot() {
		return selected_bot;
	}

	public void setSelected_bot(int selected_bot) {
		this.selected_bot = selected_bot;
	}

}
