package view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import controller.manager.CustomerManager;
import controller.validator.RequiredFieldException;
import controller.validator.Validator;
import model.Customer;

public class EditCustomerDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	JComboBox<String> icList; //global
	private JPanel btnPnl = new JPanel(new GridLayout(1, 2, 10, 10));
	private JTextField txtName = new JTextField();
	private JTextField txtEmail = new JTextField();
	private JTextField txtPhoneNo = new JTextField();
	private JButton btnSubmit = new JButton("Submit");
	private JButton btnReset = new JButton("Reset");

	public EditCustomerDialog(MainFrame mainframe) throws ClassNotFoundException, SQLException {
		super(mainframe, "Edit Customer", true);
		
		icList = new JComboBox<>(CustomerManager.customerList());
		
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		this.add(pnlCenter);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		Font btnFont = new Font("Verdana", Font.BOLD, 15);
				
		JLabel labelICNo = new JLabel("IC No.", JLabel.RIGHT);
		labelICNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		icList.setFont(new Font("Verdana", Font.PLAIN, 15));
		//icList.setHorizontalAlignment(JLabel.CENTER);
		pnlCenter.add(labelICNo);
		pnlCenter.add(icList);
		
		
		JLabel labelName = new JLabel("Name", JLabel.RIGHT);
		labelName.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtName.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtName.setHorizontalAlignment(JLabel.CENTER);
		pnlCenter.add(labelName);
		pnlCenter.add(txtName);		
		
		JLabel labelEmail = new JLabel("Email", JLabel.RIGHT);
		labelEmail.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtEmail.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtEmail.setHorizontalAlignment(JLabel.CENTER);
		pnlCenter.add(labelEmail);
		pnlCenter.add(txtEmail);
		
		JLabel labelPhoneNo = new JLabel("Phone Number", JLabel.RIGHT);
		labelPhoneNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtPhoneNo.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtPhoneNo.setHorizontalAlignment(JLabel.CENTER);
		pnlCenter.add(labelPhoneNo);
		pnlCenter.add(txtPhoneNo);
		
		btnSubmit.setFont(btnFont);
		btnReset.setFont(btnFont);
		
		btnPnl.setPreferredSize(new Dimension(80, 100));
		btnPnl.add(btnSubmit);
		btnPnl.add(btnReset);
		
		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);
		
		pnlSouth.add(btnSubmit);
		pnlSouth.add(btnReset);
		
		//this.getRootPane().setDefaultButton();
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
				icNum = Validator.validate("IC No", icList.getSelectedItem().toString().trim(), true);
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
					if (CustomerManager.updateCustomer(customer) > 0) {
						JOptionPane.showMessageDialog(null, "Customer with Customer IC:" + customer.getIcNum() + " has been successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Unable to edit room!", "Unsucessful", JOptionPane.WARNING_MESSAGE);
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
