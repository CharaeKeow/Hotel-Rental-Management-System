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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.manager.StaffManager;
import controller.validator.MaximumLengthException;
import controller.validator.RequiredFieldException;
import controller.validator.Validator;

public class LoginDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField txtStaffID = new JTextField();
	private JPasswordField txtPassword = new JPasswordField("");
	private JButton btnSubmit = new JButton("Submit");
	private JButton btnReset = new JButton("Reset");
	
	public LoginDialog(MainFrame mf) {
		super(mf, "Login", true);
		
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		btnSubmit.addActionListener(this);
		
		this.add(pnlCenter);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		
		pnlCenter.add(new JLabel("Staff ID: ", JLabel.CENTER));
		pnlCenter.add(txtStaffID);
		pnlCenter.add(new JLabel("Password: ", JLabel.CENTER));
		pnlCenter.add(txtPassword);
		
		pnlSouth.add(btnSubmit);
		pnlSouth.add(btnReset);
		
		this.getRootPane().setDefaultButton(btnSubmit);
		this.setSize(600, 400);				
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == btnSubmit) {
			
			Vector<Exception> exceptions = new Vector<>();
					
			String staffID = null, password = null;
			
			try {
				staffID = Validator.validate("Staff ID", txtStaffID.getText(), true, 20);
			} catch (RequiredFieldException | MaximumLengthException e1) {	
				exceptions.add(e1); //add the thrown exception into vector
			}
			
			try {
				password = Validator.validate("Password", txtPassword.getText(), true, 30);
				System.out.println(password);
			} catch (RequiredFieldException | MaximumLengthException e1) {
				exceptions.add(e1);
			}
			
			int size = exceptions.size();
			
			if (size == 0) {
				try {
					if (StaffManager.loginStaff(staffID, password) == true) {
						
						JOptionPane.showMessageDialog(this, "Login successful ^_^", "Successful", JOptionPane.INFORMATION_MESSAGE);
						new MainMenu(this);
						
					} else {
						JOptionPane.showMessageDialog(this, "Wrong staffID or password ^_^", "Login Failed", JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException | SQLException e1) {
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "Cannot retrieve login data from database :(", "Database Error", JOptionPane.WARNING_MESSAGE);
				}
				
				//JOptionPane.showMessageDialog(this, "Successful");
			} else {
				String message = null;
				
				if (size == 1) {
					message = exceptions.firstElement().getMessage();
				} else {
					message = "Please fix the following errors: ";
					
					for (int i = 0; i < size; i++) {
						message += "\n" + (i+1) + ". " + exceptions.get(i).getMessage();
					}
				}
				
				JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.WARNING_MESSAGE);	
			}
		}
		
	}

}
