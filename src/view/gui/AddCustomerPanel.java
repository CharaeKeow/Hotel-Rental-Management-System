package view.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.manager.CustomerManager;
import controller.validator.RequiredFieldException;
import controller.validator.Validator;
import model.Customer;

public class AddCustomerPanel implements ActionListener {
	
	private JPanel btnPnl = new JPanel(new GridLayout(1, 2, 10, 10));
	private JTextField txtName = new JTextField();
	private JTextField txtICNo = new JTextField();
	private JTextField txtEmail = new JTextField();
	private JTextField txtPhoneNo = new JTextField();
	
	private JButton btnSubmit = new JButton("SUBMIT");
	private JButton btnReset = new JButton("RESET");
	
	public AddCustomerPanel(JPanel pnlAddCustomer) {
		Font btnFont = new Font("Verdana", Font.BOLD, 15);
		
		JLabel labelName = new JLabel("Name", JLabel.RIGHT);
		labelName.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtName.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtName.setHorizontalAlignment(JLabel.CENTER);
		pnlAddCustomer.add(labelName);
		pnlAddCustomer.add(txtName);
		
		JLabel labelICNo = new JLabel("IC No.", JLabel.RIGHT);
		labelICNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtICNo.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtICNo.setHorizontalAlignment(JLabel.CENTER);
		pnlAddCustomer.add(labelICNo);
		pnlAddCustomer.add(txtICNo);
		
		JLabel labelEmail = new JLabel("Email", JLabel.RIGHT);
		labelEmail.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtEmail.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtEmail.setHorizontalAlignment(JLabel.CENTER);
		pnlAddCustomer.add(labelEmail);
		pnlAddCustomer.add(txtEmail);
		
		JLabel labelPhoneNo = new JLabel("Phone Number", JLabel.RIGHT);
		labelPhoneNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtPhoneNo.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtPhoneNo.setHorizontalAlignment(JLabel.CENTER);
		pnlAddCustomer.add(labelPhoneNo);
		pnlAddCustomer.add(txtPhoneNo);
		
		btnSubmit.setFont(btnFont);
		btnReset.setFont(btnFont);
		
		btnPnl.setPreferredSize(new Dimension(80, 100));
		btnPnl.add(btnSubmit);
		btnPnl.add(btnReset);
		
		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);
		
		pnlAddCustomer.add(btnSubmit);
		pnlAddCustomer.add(btnReset);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == btnSubmit) {
			Vector<Exception> exceptions = new Vector<>();
			
			//Customer
			String name = null, email = null, phoneNo = null, icNum = null;
			
			try {
				name = Validator.validate("Name", txtName.getText(), true);
			} catch (RequiredFieldException e1) {
				exceptions.add(e1);
			}
			
			try {
				email = Validator.validate("Email", txtEmail.getText(), true);
			} catch (RequiredFieldException e1) {
				// TODO Auto-generated catch block
				exceptions.add(e1);
			}
			
			try {
				phoneNo = Validator.validate("Phone No", txtPhoneNo.getText(), true);
			} catch (RequiredFieldException e1) {
				// TODO Auto-generated catch block
				exceptions.add(e1);
			}
			
			try {
				icNum = Validator.validate("IC No", txtICNo.getText(), true);
			} catch (RequiredFieldException e1) {
				// TODO Auto-generated catch block
				exceptions.add(e1);
			}
			
			int size = exceptions.size();
			
			if (size == 0) {
				Customer customer = new Customer();
				customer.setEmail(email);
				customer.setIcNum(icNum);
				customer.setName(name);
				customer.setPhoneNo(phoneNo);
				
				try {
					if (CustomerManager.addCustomer(customer) > 0) {
						JOptionPane.showMessageDialog(null, "Customer with Customer IC:" + customer.getIcNum() + " has been successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
						
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
		}

	}

}
