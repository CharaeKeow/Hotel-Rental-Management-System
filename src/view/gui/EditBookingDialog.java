package view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.manager.BookingManager;
import controller.validator.RequiredFieldException;
import controller.validator.Validator;
import model.Booking;

public class EditBookingDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	JComboBox<String> bookingList; //global
	private JTextField txtRoomNum = new JTextField();
	private JCheckBox chkBreakfast = new JCheckBox("", true);
	private JTextField txtDuration = new JTextField();
	
	private JPanel btnPnl = new JPanel(new GridLayout(1, 2, 10, 10));
	JButton btnSubmit = new JButton("SUBMIT");
	private JButton btnReset = new JButton("RESET");
	
	private Date date = new Date();  
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	private String strDate = formatter.format(date); 
	

	public EditBookingDialog(MainFrame mainframe) throws ClassNotFoundException, SQLException {
		super(mainframe, "Edit Booking", true);
		Font btnFont = new Font("Verdana", Font.BOLD, 15);
		
		bookingList = new JComboBox<>(BookingManager.bookingList());
		
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		this.add(pnlCenter);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		JLabel labelBookingID = new JLabel("Booking ID", JLabel.RIGHT);
		labelBookingID.setFont(new Font("Verdana", Font.PLAIN, 20));
		bookingList.setFont(new Font("Verdana", Font.PLAIN, 15));
		pnlCenter.add(labelBookingID);
		pnlCenter.add(bookingList);
		
		JLabel labelRoomNum = new JLabel("Room number", JLabel.RIGHT);
		labelRoomNum.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRoomNum.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtRoomNum.setHorizontalAlignment(JLabel.CENTER);
		pnlCenter.add(labelRoomNum);
		pnlCenter.add(txtRoomNum);
		
		JLabel labelChkBreakfast = new JLabel("Breakfast", JLabel.RIGHT);
		labelChkBreakfast.setFont(new Font("Verdana", Font.PLAIN, 20));
		chkBreakfast.setFont(new Font("Verdana", Font.PLAIN, 15));
		pnlCenter.add(labelChkBreakfast);
		pnlCenter.add(chkBreakfast);
		
		
		//I don't think we need to edit the start date
		/*
		JLabel labelStartDate = new JLabel("Start date", JLabel.RIGHT);
		labelStartDate.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRoomStartDate.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtRoomStartDate.setHorizontalAlignment(JLabel.CENTER);
		txtRoomStartDate.setText(strDate);
		pnlCenter.add(labelStartDate);
		pnlCenter.add(txtRoomStartDate);
		*/
		
		JLabel labelDuration = new JLabel("Duration", JLabel.RIGHT);
		labelDuration.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtDuration.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtDuration.setHorizontalAlignment(JLabel.CENTER);
		pnlCenter.add(labelDuration);
		pnlCenter.add(txtDuration);
				
		
		btnSubmit.setFont(btnFont);
		btnReset.setFont(btnFont);
		
		btnPnl.setPreferredSize(new Dimension(80, 100));
		btnPnl.add(btnSubmit);
		btnPnl.add(btnReset);
		
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
	public void actionPerformed(ActionEvent event) {
		
			Object source = event.getSource();
			
			if(source == btnSubmit) {
				Vector<Exception> exceptions = new Vector<>();
				
				//Customer
				String customerIcNo = null;
				int roomNo = 0, duration = 0;
				Date start = null;				
				int bookingID = 0;
				
				
				try {
					duration = Validator.validate("Duration", duration, true);
				} catch (RequiredFieldException e) {
					exceptions.add(e);
				}
				
				try {
					bookingID = Validator.validate("Booking ID", Integer.parseInt(bookingList.getSelectedItem().toString().trim()), true);
				} catch (NumberFormatException | RequiredFieldException e) {
					// TODO Auto-generated catch block
					exceptions.add(e);
				}
				
				try {
					roomNo = Validator.validate("Room Number", Integer.parseInt(txtRoomNum.getText()), true);
				} catch (NumberFormatException | RequiredFieldException e) {

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
					booking.setDuration(Integer.parseInt(txtDuration.getText()));
					booking.setHasBreakfast(chkBreakfast.isSelected());
					booking.setStart(start);
					//booking.setTotal(BookingManager.calculateTotal(booking));
					
					try {
						if(BookingManager.updateBooking(booking, bookingID) > 0) {							
							JOptionPane.showMessageDialog(null, "Booking has been successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "Unable to edit booking!", "Unsucessful", JOptionPane.WARNING_MESSAGE);
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


