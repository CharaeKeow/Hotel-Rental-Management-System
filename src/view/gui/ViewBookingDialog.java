package view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.manager.BookingManager;

public class ViewBookingDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	public ViewBookingDialog(ManageBookingDialog dialog) throws ClassNotFoundException, SQLException {
		JTable table = new JTable(BookingManager.displayBookings());
		JOptionPane.showMessageDialog(null, new JScrollPane(table));
	}
	
	public void actionPerformed(ActionEvent e) {
			
		

	}

}
