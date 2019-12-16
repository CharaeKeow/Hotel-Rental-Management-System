package view.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton btnLogin = new JButton("Login");
	private JButton btnExit = new JButton("Exit");	
	
	public MainFrame() {
		super("Hotel Rental Management System");
		
		GridLayout layout = new GridLayout(2, 1);
		
		this.add(btnLogin);
		this.add(btnExit);
		
		btnLogin.addActionListener(this);
		btnExit.addActionListener(this);
		
		this.setSize(1000, 400);
		this.setLayout(layout);
		//this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == btnLogin) {
			new LoginDialog(this);
		}
		
	}

	public static void main (String[] args) {
		new MainFrame();
	}
}
