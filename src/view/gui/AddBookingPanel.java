package view.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.manager.BookingManager;
import controller.manager.CustomerManager;
import controller.manager.RoomManager;
import controller.validator.RequiredFieldException;
import controller.validator.Validator;
import model.Booking;

public class AddBookingPanel implements ActionListener
{	
	//private JTextField txtIcNum = new JTextField();
	//private JTextField txtRoomNum = new JTextField();
	
	private JComboBox<String> icList;
	private JComboBox<String> roomList;
	
	private JCheckBox chkBreakfast = new JCheckBox("", true);
	private JTextField txtRoomStartDate = new JTextField();
	private JTextField txtDuration = new JTextField();
	
	private JPanel btnPnl = new JPanel(new GridLayout(1, 2, 10, 10));
	JButton btnSubmit = new JButton("SUBMIT");
	private JButton btnReset = new JButton("RESET");
	
	private Date date = new Date();  
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	private String strDate = formatter.format(date); 
	
	public AddBookingPanel(JPanel pnlAddBooking) throws ClassNotFoundException, SQLException
	{		
		
		roomList = new JComboBox<>(RoomManager.roomList(true));
		icList = new JComboBox<>(CustomerManager.customerList());
		
		Font btnFont = new Font("Verdana", Font.BOLD, 15);
		
		btnSubmit.setFont(btnFont);
		btnReset.setFont(btnFont);		
		
		JLabel labelIcNum = new JLabel("IC Number", JLabel.RIGHT);
		labelIcNum.setFont(new Font("Verdana", Font.PLAIN, 20));
		icList.setFont(new Font("Verdana", Font.PLAIN, 15));
		//txtIcNum.setHorizontalAlignment(JLabel.CENTER);
		pnlAddBooking.add(labelIcNum);
		pnlAddBooking.add(icList);
		
		JLabel labelRoomNum = new JLabel("Room number", JLabel.RIGHT);
		labelRoomNum.setFont(new Font("Verdana", Font.PLAIN, 20));
		roomList.setFont(new Font("Verdana", Font.PLAIN, 15));
		//txtRoomNum.setHorizontalAlignment(JLabel.CENTER);
		pnlAddBooking.add(labelRoomNum);
		pnlAddBooking.add(roomList);
		
		JLabel labelChkBreakfast = new JLabel("Breakfast", JLabel.RIGHT);
		labelChkBreakfast.setFont(new Font("Verdana", Font.PLAIN, 20));
		chkBreakfast.setFont(new Font("Verdana", Font.PLAIN, 15));
		pnlAddBooking.add(labelChkBreakfast);
		pnlAddBooking.add(chkBreakfast);
		
		JLabel labelStartDate = new JLabel("Start date", JLabel.RIGHT);
		labelStartDate.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRoomStartDate.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtRoomStartDate.setHorizontalAlignment(JLabel.CENTER);
		txtRoomStartDate.setText(strDate);
		pnlAddBooking.add(labelStartDate);
		pnlAddBooking.add(txtRoomStartDate);
		
		JLabel labelDuration = new JLabel("Duration", JLabel.RIGHT);
		labelDuration.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtDuration.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtDuration.setHorizontalAlignment(JLabel.CENTER);
		pnlAddBooking.add(labelDuration);
		pnlAddBooking.add(txtDuration);
				
		
		btnSubmit.setFont(btnFont);
		btnReset.setFont(btnFont);
		
		btnPnl.setPreferredSize(new Dimension(80, 100));
		btnPnl.add(btnSubmit);
		btnPnl.add(btnReset);
		
		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);
		
		pnlAddBooking.add(btnSubmit);
		pnlAddBooking.add(btnReset);
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		Object source = event.getSource();
		
		if(source == btnSubmit)
		{			
			
			Vector<Exception> exceptions = new Vector<>();

			//Booking
			String customerIcNo = null;
			int roomNo = 0, duration = 0;
			Date start = null;
			
			try {
				customerIcNo = Validator.validate("Ic Num", icList.getSelectedItem().toString().trim(), true);
			} catch (RequiredFieldException e) {
				exceptions.add(e);
			}			
			
			try {
				roomNo = Validator.validate("Room No", Integer.parseInt(roomList.getSelectedItem().toString().trim()), true);
			} catch (NumberFormatException | RequiredFieldException e) {
				exceptions.add(e);
			}
			
			try {
				duration = Validator.validate("Duration", Integer.parseInt(txtDuration.getText()), true);
			} catch (RequiredFieldException e) {
				exceptions.add(e);
			}
			
			try {
				start = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			} catch(ParseException e) {
				exceptions.add(e);
			}		
			
			int size = exceptions.size();
			
			if (size == 0) {
				
				Booking booking = new Booking(customerIcNo, roomNo);
				booking.setDuration(duration);
				booking.setHasBreakfast(chkBreakfast.isSelected());
				booking.setStart(start);
				
				try {
					booking.setTotal(BookingManager.calculateTotal(booking));
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				
				try {
					if(BookingManager.addBooking(booking) > 0) {
						RoomManager.updateOccupied(true, booking.getRoomNo());
						JOptionPane.showMessageDialog(null, "Successfully added a new booking!\nThe total for that booking is: RM " + booking.getTotal(), "Sucessful", JOptionPane.INFORMATION_MESSAGE);
					}  else {
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
						
		}
	}
}