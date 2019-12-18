package view.gui;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.manager.BookingManager;

public class ViewBookingPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	static JTable table;
	
	public ViewBookingPanel(JPanel pnlViewBooking) throws ClassNotFoundException, SQLException
	{
		JTable table = new JTable(BookingManager.displayBookings());
		
		pnlViewBooking.add(new JScrollPane(table));
		pnlViewBooking.setVisible(true);
	}
}
