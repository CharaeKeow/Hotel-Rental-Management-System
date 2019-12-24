package view.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

public class EditRoomDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> roomList; 
	private String[] roomType = {"Single", "Double", "Triple", "Queen", "King"};
	private JTextField txtRoomNo = new JTextField();
	private JComboBox<String> txtRoomType = new JComboBox<>(roomType);
	private JTextField txtPrice = new JTextField();
	private JButton btnSubmit = new JButton("Submit");
	private JButton btnReset = new JButton("Reset");

	public EditRoomDialog(MainFrame mainframe) throws ClassNotFoundException, SQLException {
		super(mainframe, "Edit Room", true);
		
		roomList = new JComboBox<>(RoomManager.roomList(false));
		
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		this.add(pnlCenter);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		JLabel labelRoomNo = new JLabel("Room No: ", JLabel.RIGHT);
		labelRoomNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRoomNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRoomNo.setHorizontalAlignment(JLabel.CENTER);
		pnlCenter.add(labelRoomNo);
		pnlCenter.add(roomList);
		
		JLabel labelRoomType = new JLabel("Room Type: ", JLabel.RIGHT);
		labelRoomType.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRoomType.setFont(new Font("Verdana", Font.PLAIN, 20));
		pnlCenter.add(labelRoomType);
		pnlCenter.add(txtRoomType);
		
		JLabel labelPrice = new JLabel("Price: ", JLabel.RIGHT);
		labelPrice.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtPrice.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtPrice.setHorizontalAlignment(JLabel.CENTER);
		pnlCenter.add(labelPrice);
		pnlCenter.add(txtPrice);
		
		btnSubmit.setFont(new Font("Verdana", Font.BOLD, 20));
		btnReset.setFont(new Font("Verdana", Font.BOLD, 20));
		
		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);
		
		pnlSouth.add(btnSubmit);
		pnlSouth.add(btnReset);
		
		this.setSize(500, 400);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(mainframe);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == btnSubmit) {
			Vector<Exception> exceptions = new Vector<>();
			
			//Customer
			int roomNo = 0;
			double price = 0;
			String roomType = null;
			
			try {
				roomNo = Validator.validate("Room Number", Integer.parseInt(roomList.getSelectedItem().toString().trim()), true);
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
					if (RoomManager.updateRoom(room) > 0) {
						JOptionPane.showMessageDialog(null, "Customer with Customer IC:" + room.getRoomNo() + " has been successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Unable to add new room!", "Unsucessful", JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Database error", JOptionPane.WARNING_MESSAGE);
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
				
				JOptionPane.showMessageDialog(null, message, "Validation Error", JOptionPane.WARNING_MESSAGE);

			}
		} else if (source == btnReset) {
			
		}
	}

}
