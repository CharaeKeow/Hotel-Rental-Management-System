package view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.manager.RoomManager;

public class ViewRoomDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public ViewRoomDialog(ManageRoomDialog dialog) throws ClassNotFoundException, SQLException {
		JTable table = new JTable(RoomManager.displayRooms());
		
		JOptionPane.showMessageDialog(null, new JScrollPane(table));
	}
	
	public void actionPerformed(ActionEvent arg0) {
		

	}

}
