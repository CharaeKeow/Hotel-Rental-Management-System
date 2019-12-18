package view.gui;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.validator.RequiredFieldException;
import controller.validator.Validator;
import model.Booking;

public class AddBookingPanel implements ActionListener
{	
	private JTextField txtRoomType = new JTextField();
	private JTextField txtRoomNum = new JTextField();
	private JCheckBox chkBreakfast = new JCheckBox("", true);
	private JTextField txtRoomStartDate = new JTextField();
	private JTextField txtDuration = new JTextField();
	private JButton btnNext = new JButton("NEXT");
	private JButton btnReset = new JButton("RESET");
	
	private Date date = new Date();  
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
    private String strDate= formatter.format(date);
	
	public AddBookingPanel(JPanel pnlAddBooking)
	{
		 
		
		Font btnFont = new Font("Verdana", Font.BOLD, 15);
		
		btnNext.setFont(btnFont);
		btnReset.setFont(btnFont);
		/*
		JLabel labelRoomType = new JLabel("Room type", JLabel.RIGHT);
		labelRoomType.setFont(new Font("Verdana", Font.PLAIN, 25));
		txtRoomType.setFont(new Font("Verdana", Font.PLAIN, 25));
		txtRoomType.setHorizontalAlignment(JLabel.CENTER);
		pnlAddBooking.add(labelRoomType);
		pnlAddBooking.add(txtRoomType);
		*/
		JLabel labelRoomNum = new JLabel("Room number", JLabel.RIGHT);
		labelRoomNum.setFont(new Font("Verdana", Font.PLAIN, 25));
		txtRoomNum.setFont(new Font("Verdana", Font.PLAIN, 25));
		txtRoomNum.setHorizontalAlignment(JLabel.CENTER);
		pnlAddBooking.add(labelRoomNum);
		pnlAddBooking.add(txtRoomNum);
		
		JLabel labelChkBreakfast = new JLabel("Breakfast", JLabel.RIGHT);
		labelChkBreakfast.setFont(new Font("Verdana", Font.PLAIN, 25));
		chkBreakfast.setFont(new Font("Verdana", Font.PLAIN, 25));
		pnlAddBooking.add(labelChkBreakfast);
		pnlAddBooking.add(chkBreakfast);
		
		JLabel labelStartDate = new JLabel("Start date", JLabel.RIGHT);
		labelStartDate.setFont(new Font("Verdana", Font.PLAIN, 25));
		txtRoomStartDate.setFont(new Font("Verdana", Font.PLAIN, 25));
		txtRoomStartDate.setHorizontalAlignment(JLabel.CENTER);
		txtRoomStartDate.setText(strDate);
		pnlAddBooking.add(labelStartDate);
		pnlAddBooking.add(txtRoomStartDate);
		
		JLabel labelDuration = new JLabel("Duration", JLabel.RIGHT);
		labelDuration.setFont(new Font("Verdana", Font.PLAIN, 25));
		txtDuration.setFont(new Font("Verdana", Font.PLAIN, 25));
		txtDuration.setHorizontalAlignment(JLabel.CENTER);
		pnlAddBooking.add(labelDuration);
		pnlAddBooking.add(txtDuration);
		
		pnlAddBooking.add(btnNext);
		pnlAddBooking.add(btnReset);
		
		btnNext.addActionListener(this);
		btnReset.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		Object source = event.getSource();
		
		if(source == btnNext)
		{			
			Vector<Exception> exceptions = new Vector<>();
			
			int roomNo = 0, customerIcNo = 0, duration = 0;
			Date start = null;
			boolean hasBreakfast = false;
			double total;
			
			try {
				roomNo = Validator.validate("Room Number", Integer.parseInt(txtRoomNum.getText()), true);
			} catch (NumberFormatException | RequiredFieldException e) {
				// TODO Auto-generated catch block
				exceptions.add(e);
			}
			
			try {
				start = new SimpleDateFormat("yyyy/MM/dd").parse(strDate);
			} catch(ParseException e) {
				exceptions.add(e);
			}
			
			Booking booking = new Booking(customerIcNo, roomNo);
			booking.setDuration(Integer.parseInt(txtDuration.getText()));
			booking.setHasBreakfast(chkBreakfast.isSelected());
			booking.setStart(start);
			booking.setHasBreakfast(hasBreakfast);
						
			if(txtRoomNum.getText().isEmpty() || txtRoomStartDate.getText().isEmpty() || txtDuration.getText().isEmpty())
				JOptionPane.showMessageDialog(null, "Please fill all the blanks!");
			else
			{
				new addUserInfoDialog(booking);
			}
		}
		else
		{
			txtRoomType.setText("");
			txtRoomNum.setText("");
			chkBreakfast.setSelected(true);
			txtRoomStartDate.setText("");
			txtDuration.setText("");
		}
	}
}