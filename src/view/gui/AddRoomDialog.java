package view.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.manager.RoomManager;
import controller.validator.RequiredFieldException;
import controller.validator.Validator;
import model.Room;

public class AddRoomDialog extends JDialog implements ActionListener{
	private String[] roomType = {
			"Single", "Double", "Triple", "Queen", "King"
	};
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txtRoomNo = new JTextField();
	private JComboBox<Object> txtRoomType = new JComboBox<>(roomType);
	private JTextField txtPrice = new JTextField();
	//private JCheckBox chkOccupied = new JCheckBox("Occupied");
	private JButton btnSubmit = new JButton("Submit");
	private JButton btnReset = new JButton("Reset");
	
	public AddRoomDialog(ManageRoomDialog dialog) {
		super(dialog, "Add Room", true);
		
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		this.add(pnlCenter);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		
		pnlCenter.add(new JLabel("Room No: ", JLabel.RIGHT));
		pnlCenter.add(txtRoomNo);
		pnlCenter.add(new JLabel("Room Type: ", JLabel.RIGHT));
		pnlCenter.add(txtRoomType);
		pnlCenter.add(new JLabel("Price: ", JLabel.RIGHT));
		pnlCenter.add(txtPrice);
		//pnlCenter.add(new JLabel("Occupied: "), JLabel.RIGHT);
		
		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);
		
		pnlSouth.add(btnSubmit);
		pnlSouth.add(btnReset);
		
		this.getRootPane().setDefaultButton(btnSubmit);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(dialog);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == btnSubmit) {
			
			Vector<Exception> exceptions = new Vector<>();
			
			int roomNo = 0;
			String roomType = null;
			double price = 0;
			
			try {
				roomNo = Validator.validate("Room Number", Integer.parseInt(txtRoomNo.getText()), true);
			} catch (RequiredFieldException | NumberFormatException e1) {
				exceptions.add(e1);
			}
						
			try {
				roomType = Validator.validate("Room Type", txtRoomType.getSelectedItem().toString().trim(), true);
			} catch (RequiredFieldException e1) {
				exceptions.add(e1);
			} 
			
			try {
				price = Validator.validate("Price", Double.parseDouble(txtPrice.getText()), true);
			} catch (RequiredFieldException | NumberFormatException e1) {
				exceptions.add(e1);
			}
			
			int size = exceptions.size();
			
			if (size == 0) {
				Room room = new Room();
				
				room.setRoomNo(roomNo);
				room.setRoomType(roomType);
				room.setPrice(price);
				
				try {
					if (RoomManager.addRoom(room) > 0) {
						JOptionPane.showMessageDialog(this, "Room with Room No:" + room.getRoomNo() + " has been successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
					} else if (RoomManager.addRoom(room) == -1) { //roomNo already exist
						JOptionPane.showMessageDialog(this, "Room No: " + room.getRoomNo() + " already exist in the database!", "Record Already Exist", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Unable to add new room!", "Unsucessful", JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(), "Database error", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				String message = null; //display in JOP
				
				if (size == 1) {
					message = exceptions.firstElement().getMessage();
				} else {
					message = "Please fix the following errors";
					
					for (int i = 0; i < size; i++) {
						message += "\n" + (i+1) + ". " + exceptions.get(i).getMessage();
					}
				}
				
				JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
