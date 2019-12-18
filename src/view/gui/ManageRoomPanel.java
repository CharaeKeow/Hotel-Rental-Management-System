package view.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ManageRoomPanel implements ActionListener 
{	
	private JButton btnAddRoom = new JButton("Add Room");
	private JButton btnViewRoom = new JButton("View Room");
	
	private JPanel holderPnl = new JPanel(new GridLayout(1, 1, 20, 20));
	private JPanel btnPnl = new JPanel(new GridLayout(2, 1, 20, 20));
	private JPanel addRoomPnl = new JPanel(new GridLayout(4, 2, 20, 20));
	private JPanel ViewRoomnPnl = new JPanel(new GridLayout(2, 1, 20, 20));
	
	public ManageRoomPanel(JPanel pnlManageRoom)
	{
		Font btnFont = new Font("Verdana", Font.BOLD, 20);
		
		btnPnl.add(btnAddRoom);
		btnPnl.add(btnViewRoom);
		
		btnAddRoom.setFont(btnFont);
		btnViewRoom.setFont(btnFont);
		
		btnAddRoom.addActionListener(this);
		btnViewRoom.addActionListener(this);
		
		holderPnl.add(btnPnl);
		
		pnlManageRoom.add(holderPnl);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if (source == btnAddRoom) 
		{			
			holderPnl.removeAll();
			addRoomPnl.removeAll();
			new addRoomPanel(addRoomPnl);
			holderPnl.add(addRoomPnl);
			holderPnl.updateUI();
		} 
		else if (source == btnViewRoom) 
		{
			try 
			{
				holderPnl.removeAll();
				ViewRoomnPnl.removeAll();
				new viewRoomPanel(ViewRoomnPnl);
				holderPnl.add(ViewRoomnPnl);
				holderPnl.updateUI();
			} 
			catch (ClassNotFoundException | SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		
	}
	
	

}