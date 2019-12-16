package view.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField staffID = new JTextField();
	private JTextField password = new JTextField();
	private JButton btnSubmit = new JButton("Submit");
	private JButton btnReset = new JButton("Reset");
	
	public LoginDialog(MainFrame mf) {
		super(mf, "Login", true);
		
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		this.add(pnlCenter);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		
		pnlCenter.add(new JLabel("Staff ID: ", JLabel.CENTER));
		pnlCenter.add(staffID);
		pnlCenter.add(new JLabel("Password: ", JLabel.CENTER));
		pnlCenter.add(password);
		
		pnlSouth.add(btnSubmit);
		pnlSouth.add(btnReset);
		
		this.getRootPane().setDefaultButton(btnSubmit);
		this.setSize(600, 400);				
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == btnSubmit) {
			
			String staffID = null, password = null;
			
		}
		
	}

}
