package view.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;

import controller.manager.CustomerManager;
import model.Customer;

public class ManageCustomerDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton btnAddRoom = new JButton("Add Customer");
	private JButton btnViewRoom = new JButton("View Customer");
	
	public ManageCustomerDialog(MainMenu mainMenu) {
		super(mainMenu, "Manage Customer", true);
		
		GridLayout layout = new GridLayout(2,1);
		
		btnAddRoom.addActionListener(this);
		btnViewRoom.addActionListener(this);
		
		this.add(btnAddRoom);
		this.add(btnViewRoom);
		
		this.setLayout(layout);
		this.setSize(600,200);
		this.setLocationRelativeTo(mainMenu);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if (source == btnAddRoom) {			
		
			Customer customer = new Customer();
			customer.setEmail("@gmail.com");
			customer.setIcNum("1212");
			customer.setName("Koli");
			customer.setPhoneNo("5934580");
			
			try {
				CustomerManager.addCustomer(customer);
			} catch (ClassNotFoundException | SQLException e1) {
				
				e1.printStackTrace();
			}
		} else if (source == btnViewRoom) {
			try {
				new ViewCustomerDialog(this);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	

}
