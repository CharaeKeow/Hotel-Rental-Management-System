package view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import controller.manager.CustomerManager;

public class ViewCustomerPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	//JTable containing all customer
	private TableModel model = CustomerManager.displayCustomers(); 
	private JTable table = new JTable(model);
	private TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);	
	private JScrollPane jsp = new JScrollPane(table);
		
	private JTextField txtFilter = new JTextField();
	private JButton btnFilter = new JButton("Filter");
	private JButton btnEdit = new JButton("Edit");
	private JButton btnDelete = new JButton("Delete");
	private JButton btnClearFilter = new JButton("Clear Filter");
			
	public ViewCustomerPanel(JPanel pnlViewCustomer) throws ClassNotFoundException, SQLException
	{		
		table.setRowSorter(sorter);
		
		JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JPanel pnlCenter = new JPanel(new GridLayout(2, 1, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		JLabel labelFilter = new JLabel("Filter keyword: ", JLabel.CENTER);
		txtFilter.setSize(100, 10);
		labelFilter.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtFilter.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtFilter.setPreferredSize(new Dimension(200, 30));
		
		//txtIcNum.setHorizontalAlignment(JLabel.CENTER);
		pnlNorth.add(labelFilter);
		pnlNorth.add(txtFilter);
		pnlNorth.add(btnFilter);		
		
		pnlCenter.add(jsp);
		
		pnlSouth.add(btnEdit);
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnClearFilter);		
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		
		btnClearFilter.addActionListener(this);
		btnFilter.addActionListener(this);
		btnEdit.addActionListener(this);
		btnDelete.addActionListener(this);
				
		pnlViewCustomer.add(pnlNorth, BorderLayout.NORTH);
		pnlViewCustomer.add(pnlCenter, BorderLayout.CENTER);
		pnlViewCustomer.add(pnlSouth, BorderLayout.SOUTH);		
		
		pnlViewCustomer.setVisible(true);
		pnlNorth.setVisible(true);
		pnlCenter.setVisible(true);
		pnlSouth.setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == btnEdit) {
			try {
				new EditCustomerDialog(null);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		} else if (source == btnDelete) {
			try {
				new DeleteCustomerDialog(null);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		} else if (source == btnFilter) {
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
