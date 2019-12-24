package view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import controller.manager.RoomManager;

public class ViewRoomPanel extends JDialog implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	
	private TableModel model = RoomManager.displayRooms();
	private JTable table = new JTable(model);
	private TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
	private JScrollPane jsp = new JScrollPane(table);
	
	private JTextField txtFilter = new JTextField();
	private JButton btnFilter = new JButton("Filter");
	private JButton btnClearFilter = new JButton("Clear filter");
	
	public ViewRoomPanel(JPanel ViewRoomPnl) throws ClassNotFoundException, SQLException 
	{
		JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); 
		JPanel pnlCenter = new JPanel(new GridLayout(2, 1, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		
		table.setRowSorter(sorter);
		
		btnClearFilter.addActionListener(this);
		btnFilter.addActionListener(this);
		
		JLabel labelFilter = new JLabel("Filter keyword: ", JLabel.LEFT);
		txtFilter.setSize(100, 10);
		labelFilter.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtFilter.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtFilter.setPreferredSize(new Dimension(200, 30));
		
		pnlNorth.add(labelFilter);
		pnlNorth.add(txtFilter);
		pnlNorth.add(btnFilter);
		pnlNorth.setSize(500, 10);
		
		pnlCenter.add(jsp);
		//pnlCenter.setPreferredSize(new Dimension(1000, 2000));
		//pnlCenter.setSize(1000, 2000);
		
		pnlSouth.add(btnClearFilter);		
		
		ViewRoomPnl.add(pnlNorth, BorderLayout.NORTH);
		ViewRoomPnl.add(pnlCenter, BorderLayout.CENTER);
		ViewRoomPnl.add(pnlSouth, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == btnFilter) {
			String input = txtFilter.getText().trim();
			if (input.length() != 0) {
				String regex = "(?i)"; //occur one or more time and ignore stupid case
				sorter.setRowFilter(RowFilter.regexFilter(regex + input));
			} else {
				JOptionPane.showMessageDialog(this, "Must enter the keyword!", "No keyword",JOptionPane.WARNING_MESSAGE);
			}
		} else if (source == btnClearFilter) {
			txtFilter.setText("");
			sorter.setRowFilter(RowFilter.regexFilter(""));
		}
	}
}
