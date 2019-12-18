package view.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class MainMenu extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton btnManageCustomers = new JButton("Manage Customers");
	private JButton btnManageRooms = new JButton("Manage Rooms");
	private JButton btnManageBookings = new JButton("Manage Booking");
	
	public MainMenu(LoginDialog dialog) {
		super(dialog, "Hotel Rental Management System", true);
		
		GridLayout layout = new GridLayout(3, 1);
		btnManageCustomers.addActionListener(this);
		btnManageRooms.addActionListener(this);
		btnManageBookings.addActionListener(this);
		
		this.add(btnManageCustomers);
		this.add(btnManageRooms);
		this.add(btnManageBookings);
		
		this.setSize(600, 200);
		this.setLayout(layout);
		this.setLocationRelativeTo(dialog);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public MainMenu(MainFrame mainFrame) {
		super(mainFrame, "Hotel Rental Management System", true);
		
		GridLayout layout = new GridLayout(3, 1);
		btnManageCustomers.addActionListener(this);
		btnManageRooms.addActionListener(this);
		btnManageBookings.addActionListener(this);
		
		this.add(btnManageCustomers);
		this.add(btnManageRooms);
		this.add(btnManageBookings);
		
		this.setSize(600, 200);
		this.setLayout(layout);
		this.setLocationRelativeTo(mainFrame);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == btnManageRooms) {
			new ManageRoomDialog(this);
		}
	}
	
}
