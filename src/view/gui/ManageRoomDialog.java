package view.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;

public class ManageRoomDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton btnAddRoom = new JButton("Add Room");
	private JButton btnViewRoom = new JButton("View Room");
	
	public ManageRoomDialog(MainMenu mainMenu) {
		super(mainMenu, "Manage Room", true);
		
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
			new AddRoomDialog(this);
		} else if (source == btnViewRoom) {
			try {
				new ViewRoomDialog(this);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	

}
