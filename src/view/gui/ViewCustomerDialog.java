package view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.manager.CustomerManager;

public class ViewCustomerDialog implements ActionListener {

	public ViewCustomerDialog(ManageCustomerDialog manageCustomerDialog) throws ClassNotFoundException, SQLException {
		JTable table = new JTable(CustomerManager.displayCustomers());
		
		JOptionPane.showMessageDialog(null, new JScrollPane(table));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
