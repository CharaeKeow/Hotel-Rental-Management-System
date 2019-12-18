package view.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;

import controller.manager.BookingManager;
import model.Booking;
import model.Customer;
import model.Room;

public class ManageBookingDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton btnAddRoom = new JButton("Add Booking");
	private JButton btnViewRoom = new JButton("View Booking");
	
	public ManageBookingDialog(MainMenu mainMenu) {
		super(mainMenu, "Manage Booking", true);
		
		GridLayout layout = new GridLayout(2,1);
		
		btnAddRoom.addActionListener(this);
		btnViewRoom.addActionListener(this);
		
		this.add(btnAddRoom);
		this.add(btnViewRoom);
		
		this.setLayout(layout);
		this.setSize(600,200);
		this.setLocationRelativeTo(mainMenu);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == btnAddRoom) {			
			
			
			/*
			try {
				BookingManager.addBooking(booking);
				System.out.println("UU");
			} catch (ClassNotFoundException | SQLException e1) {
				
				e1.printStackTrace();
			}
			*/
		} else if (source == btnViewRoom) {
			try {
				new ViewBookingDialog(this);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
