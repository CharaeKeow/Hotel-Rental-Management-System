package view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

public class MainFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private int flag = 0;
	
	private JPanel pnlLeft = new JPanel(new GridLayout(5, 1, 20, 20));
	private JPanel pnlRight = new JPanel(new GridLayout(1, 1, 20, 20));
	private JPanel pnlAddCustomer = new JPanel(new GridLayout(10, 1, 20, 20));
	private JPanel pnlAddBooking = new JPanel(new GridLayout(8, 1, 20, 20));
	private JPanel pnlManageRoom = new JPanel(new GridLayout(1, 1, 20, 20));
	private JPanel pnlViewBooking = new JPanel(new BorderLayout());
	private JPanel pnlViewCustomer = new JPanel(new BorderLayout());
	
	private JTextField txtStaffID = new JTextField();
	private JPasswordField txtPassword = new JPasswordField("");
	
	private JButton btnSubmit = new JButton("Submit");
	private JButton btnReset = new JButton("Reset");
	private JButton btnAddCustomer = new JButton("ADD CUSTOMER");
	private JButton btnAddBooking = new JButton("ADD BOOKING");
	private JButton btnManageRoom = new JButton("MANAGE ROOM");
	private JButton btnViewBooking = new JButton("VIEW BOOKING");
	private JButton btnViewCustomer = new JButton("VIEW CUSTOMER");
	
	public MainFrame()
	{
		super("HRMS");  
		
		JLabel HRMSlabel = new JLabel("HOTEL RESERVATION MANAGEMENT SYSTEM");
		JPanel pnlLogin = new JPanel(new GridLayout(5, 2, 20, 20));
		JPanel pnlBtn = new JPanel(new GridLayout(1, 2, 20, 20));
		
		Font btnFont = new Font("Verdana", Font.BOLD, 15);
		
		HRMSlabel.setFont(new Font("Verdana", Font.BOLD, 50));
		HRMSlabel.setHorizontalAlignment(JLabel.CENTER);
		
		HRMSlabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
		pnlLeft.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));
		pnlRight.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
		
		btnSubmit.setFont(btnFont);
		btnReset.setFont(btnFont);
		btnAddCustomer.setFont(btnFont);
		btnAddBooking.setFont(btnFont);
		btnManageRoom.setFont(btnFont);
		btnViewBooking.setFont(btnFont);
		btnViewCustomer.setFont(btnFont);
		
		pnlAddCustomer.setPreferredSize(new Dimension(1500, 1500)); //
		pnlAddBooking.setPreferredSize(new Dimension(1500, 1500));
		pnlManageRoom.setPreferredSize(new Dimension(1500, 1500));
		pnlViewBooking.setPreferredSize(new Dimension(1500, 1500));
		
		pnlBtn.add(btnSubmit);
		pnlBtn.add(btnReset);
		
		JLabel staffIDLabel = new JLabel("Staff ID: ", JLabel.CENTER);
		staffIDLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
		txtStaffID.setFont(new Font("Verdana", Font.PLAIN, 30));
		txtStaffID.setHorizontalAlignment(JLabel.CENTER);
		pnlLogin.add(staffIDLabel);
		pnlLogin.add(txtStaffID);
		
		JLabel passwordLabel = new JLabel("Password: ", JLabel.CENTER);
		passwordLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
		txtPassword.setFont(new Font("Verdana", Font.PLAIN, 30));
		txtPassword.setHorizontalAlignment(JLabel.CENTER);
		pnlLogin.add(passwordLabel);
		pnlLogin.add(txtPassword);
		
		pnlLogin.add(pnlBtn);
		
		pnlRight.add(pnlLogin);
		pnlLeft.add(btnAddCustomer); //
		pnlLeft.add(btnAddBooking);
		pnlLeft.add(btnViewBooking);
		pnlLeft.add(btnManageRoom);
		pnlLeft.add(btnViewCustomer);
		
		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);
		btnAddCustomer.addActionListener(this); //
		btnAddBooking.addActionListener(this);
		btnManageRoom.addActionListener(this);
		btnViewBooking.addActionListener(this);
		btnViewCustomer.addActionListener(this);
		
		this.add(HRMSlabel, BorderLayout.NORTH);
		this.add(pnlLeft, BorderLayout.WEST);
		this.add(pnlRight, BorderLayout.CENTER);

		this.setVisible(true);
		this.setResizable(true);
		this.setSize(1500, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		Object source = event.getSource();
		
		if(source == btnSubmit)
		{
			if(txtStaffID.getText().isEmpty() || txtPassword.getPassword().length == 0)
			{
				JOptionPane.showMessageDialog(null, "Please fill all the blanks!");
				//flag = 0;
			}
			else
			{
				//flag = 1;
				
				Vector<Exception> exceptions = new Vector<>();
				
				String staffID = null, password = null;
				
				try 
				{
					staffID = Validator.validate("Staff ID", txtStaffID.getText(), true, 20);
				} 
				catch (RequiredFieldException | MaximumLengthException e1) 
				{	
					exceptions.add(e1); //add the thrown exception into vector
					//flag = 0;
				}
				
				try 
				{
					password = Validator.validate("Password", txtPassword.getText(), true, 30);
					System.out.println(password);
				} 
				catch (RequiredFieldException | MaximumLengthException e1) 
				{
					exceptions.add(e1);
					//flag = 0;
				}
				
				int size = exceptions.size();
				
				if (size == 0) 
				{
					try 
					{
						if (StaffManager.loginStaff(staffID, password) == true) 
						{
							JOptionPane.showMessageDialog(this, "Login successful ^_^", "Successful", JOptionPane.INFORMATION_MESSAGE);
						} 
						else 
						{
							JOptionPane.showMessageDialog(this, "Wrong staffID or password ^_^", "Login Failed", JOptionPane.WARNING_MESSAGE);
							//flag = 0;
						}
					} 
					catch (ClassNotFoundException | SQLException e1) 
					{
						//e1.printStackTrace();
						JOptionPane.showMessageDialog(this, "Cannot retrieve login data from database :(", "Database Error", JOptionPane.WARNING_MESSAGE);
						//flag = 0;
					}
					
					//JOptionPane.showMessageDialog(this, "Successful");
				} 
				else 
				{
					String message = null;
					
					if (size == 1) {
						message = exceptions.firstElement().getMessage();
					} 
					else 
					{
						message = "Please fix the following errors: ";
						
						for (int i = 0; i < size; i++) {
							message += "\n" + (i+1) + ". " + exceptions.get(i).getMessage();
						}
					}
					
					JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.WARNING_MESSAGE);
					//flag = 0;
				}
			}
		}
		else if(source == btnReset)
		{
			txtStaffID.setText("");
			txtPassword.setText("");
		} else if (source == btnAddCustomer) {
			pnlRight.removeAll();
			pnlAddCustomer.removeAll();
			new AddCustomerPanel(pnlAddCustomer);
			pnlRight.add(pnlAddCustomer);
			pnlRight.updateUI();
		}
		
		else if(source == btnAddBooking/* && flag == 1*/)
		{
			pnlRight.removeAll();
			pnlAddBooking.removeAll();
			try {
				new AddBookingPanel(pnlAddBooking);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnlRight.add(pnlAddBooking);
			pnlRight.updateUI();
		}
		else if(source == btnManageRoom/* && flag == 1*/)
		{
			pnlRight.removeAll();
			pnlManageRoom.removeAll();
			new ManageRoomPanel(pnlManageRoom);
			pnlRight.add(pnlManageRoom);
			pnlRight.updateUI();
		}
		else if(source == btnViewBooking/* && flag == 1*/)
		{
			pnlRight.removeAll();
			pnlViewBooking.removeAll();
			try {
				new ViewBookingPanel(pnlViewBooking);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnlRight.add(pnlViewBooking);
			pnlRight.updateUI();
		}
		else if(source == btnViewCustomer/* && flag == 1*/)
		{
			pnlRight.removeAll();
			pnlViewCustomer.removeAll();
			try {
				//new ViewCustomerPanel(pnlViewCustomer);
				new ViewCustomerPanel(pnlViewCustomer);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnlRight.add(pnlViewCustomer);
			pnlRight.updateUI();
		}
		else
			JOptionPane.showMessageDialog(this, "You need to login, if you want to proceed.");
	}
	
	public static void main(String[] args)
	{
		new MainFrame();
	}
}
