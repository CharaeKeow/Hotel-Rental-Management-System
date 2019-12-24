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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.manager.CustomerManager;

public class DeleteCustomerDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	JComboBox<String> icList; //global
	private JPanel btnPnl = new JPanel(new GridLayout(1, 2, 10, 10));
	private JButton btnDelete = new JButton("Delete");
	private JButton btnCancel = new JButton("Cancel");
	
	public DeleteCustomerDialog(MainFrame mainframe) throws ClassNotFoundException, SQLException {
		super(mainframe, "Delete Customer", true);
		
		icList = new JComboBox<>(CustomerManager.customerList());
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		this.add(pnlCenter);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		Font btnFont = new Font("Verdana", Font.BOLD, 15);
		
		JLabel labelICNo = new JLabel("IC No.", JLabel.RIGHT);
		labelICNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		icList.setFont(new Font("Verdana", Font.PLAIN, 15));
		//icList.setHorizontalAlignment(JLabel.CENTER);
		pnlCenter.add(labelICNo);
		pnlCenter.add(icList);
		
		btnDelete.setFont(btnFont);
		btnCancel.setFont(btnFont);
		
		btnPnl.setPreferredSize(new Dimension(80, 100));
		btnPnl.add(btnDelete);
		btnPnl.add(btnCancel);
		
		btnDelete.addActionListener(this);
		btnCancel.addActionListener(this);
		
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnCancel);
		
		this.setSize(500, 400);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(mainframe);
		this.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == btnDelete) {
			try {
				if (CustomerManager.deleteCustomer(icList.getSelectedItem().toString().trim()) > 0) {
					JOptionPane.showMessageDialog(null, "Customer with Customer IC:" + icList.getSelectedItem().toString().trim() + " has been successfully deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Unable to delete the selected customer!", "Unsucessful", JOptionPane.WARNING_MESSAGE);
				}				
			} catch (ClassNotFoundException | SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Database error", JOptionPane.WARNING_MESSAGE);
			}
		} else if (source == btnCancel) {
			this.dispose();
		}
	}

}
