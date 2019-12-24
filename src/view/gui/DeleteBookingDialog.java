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

import controller.manager.BookingManager;

public class DeleteBookingDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> bookingList; //global
	private JPanel btnPnl = new JPanel(new GridLayout(1, 2, 10, 10));
	private JButton btnDelete = new JButton("Delete");
	private JButton btnCancel = new JButton("Cancel");

	public DeleteBookingDialog(MainFrame mainframe) throws ClassNotFoundException, SQLException {
		super(mainframe, "Delete Booking", true);
		
		bookingList = new JComboBox<>(BookingManager.bookingList());
		
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		
		this.add(pnlCenter);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		Font btnFont = new Font("Verdana", Font.BOLD, 15);
		
		JLabel labelBookingID = new JLabel("BookingID", JLabel.RIGHT);
		labelBookingID.setFont(new Font("Verdana", Font.PLAIN, 20));
		bookingList.setFont(new Font("Verdana", Font.PLAIN, 15));
		pnlCenter.add(labelBookingID);
		pnlCenter.add(bookingList);
		
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

	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == btnDelete) {
		try {
			if (BookingManager.deleteBooking(bookingList.getSelectedItem().toString().trim()) > 0) {
				JOptionPane.showMessageDialog(null, "Booking with Booking ID:" + bookingList.getSelectedItem().toString().trim() + " has been successfully deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Unable to delete the selected booking!", "Unsuccessful", JOptionPane.WARNING_MESSAGE);
			}
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
		}
		} else if (source == btnCancel) {
			this.dispose();
		}
	}

}
