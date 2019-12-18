package view.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

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

public class addRoomPanel extends JDialog implements ActionListener{
	private String[] roomType = {"Single", "Double", "Triple", "Queen", "King"};
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txtRoomNo = new JTextField();
	private JComboBox<Object> txtRoomType = new JComboBox<>(roomType);
	private JTextField txtPrice = new JTextField();
	private JButton btnSubmit = new JButton("Submit");
	private JButton btnReset = new JButton("Reset");
	
	public addRoomPanel(JPanel addRoomPnl) 
	{	
		JLabel labelRoomNo = new JLabel("Room No: ", JLabel.RIGHT);
		labelRoomNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRoomNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		addRoomPnl.add(labelRoomNo);
		addRoomPnl.add(txtRoomNo);
		
		JLabel labelRoomType = new JLabel("Room Type: ", JLabel.RIGHT);
		labelRoomType.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRoomType.setFont(new Font("Verdana", Font.PLAIN, 20));
		addRoomPnl.add(labelRoomType);
		addRoomPnl.add(txtRoomType);
		
		JLabel labelPrice = new JLabel("Room No: ", JLabel.RIGHT);
		labelPrice.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtPrice.setFont(new Font("Verdana", Font.PLAIN, 20));
		addRoomPnl.add(labelPrice);
		addRoomPnl.add(txtPrice);
		
		btnSubmit.setFont(new Font("Verdana", Font.BOLD, 20));
		btnReset.setFont(new Font("Verdana", Font.BOLD, 20));
		
		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);
		
		addRoomPnl.add(btnSubmit);
		addRoomPnl.add(btnReset);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if(source == btnSubmit) 
		{
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
		else if(source == btnReset)
		{
			txtRoomNo.setText("");
			txtPrice.setText("");
		}
	}

}
