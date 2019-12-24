package view.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddUserInfoDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JPanel btnPnl = new JPanel(new GridLayout(1, 2, 10, 10));
	private JPanel txtField = new JPanel(new GridLayout(4, 1, 10, 10));
	private JPanel labelField = new JPanel(new GridLayout(4, 1, 10, 10));
	private JTextField txtName = new JTextField();
	private JTextField txtICNo = new JTextField();
	private JTextField txtEmail = new JTextField();
	private JTextField txtPhoneNo = new JTextField();
	
	private JButton btnSubmit = new JButton("SUBMIT");
	private JButton btnCancel = new JButton("CANCEL");
	
	public int flag = 0;
	
	public AddUserInfoDialog()
	{
		JLabel tittleCDS = new JLabel("Customer Details Booking");
		tittleCDS.setFont(new Font("Verdana", Font.BOLD, 30));
		tittleCDS.setHorizontalAlignment(JLabel.CENTER);
		tittleCDS.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		
		Font btnFont = new Font("Verdana", Font.BOLD, 15);
		
		btnPnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		labelField.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
		txtField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel labelName = new JLabel("Name", JLabel.RIGHT);
		labelName.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtName.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelField.add(labelName);
		txtField.add(txtName);
		
		JLabel labelICNo = new JLabel("IC No.", JLabel.RIGHT);
		labelICNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtICNo.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelField.add(labelICNo);
		txtField.add(txtICNo);
		
		JLabel labelEmail = new JLabel("Email", JLabel.RIGHT);
		labelEmail.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtEmail.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelField.add(labelEmail);
		txtField.add(txtEmail);
		
		JLabel labelPhoneNo = new JLabel("Start date", JLabel.RIGHT);
		labelPhoneNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtPhoneNo.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelField.add(labelPhoneNo);
		txtField.add(txtPhoneNo);
		
		btnSubmit.setFont(btnFont);
		btnCancel.setFont(btnFont);
		
		btnPnl.setPreferredSize(new Dimension(80, 100));
		btnPnl.add(btnSubmit);
		btnPnl.add(btnCancel);
		
		btnSubmit.addActionListener(this);
		btnCancel.addActionListener(this);
		
		this.add(tittleCDS, BorderLayout.NORTH);
		this.add(labelField, BorderLayout.WEST);
		this.add(txtField, BorderLayout.CENTER);
		this.add(btnPnl, BorderLayout.SOUTH);
		this.setSize(800, 500);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		
		if(source == btnSubmit)
		{			
			if(txtName.getText().isEmpty() || txtICNo.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPhoneNo.getText().isEmpty())
				JOptionPane.showMessageDialog(null, "Please fill all the blanks!");
			else
			{
				txtName.setText("");
				txtICNo.setText("");
				txtEmail.setText("");
				txtPhoneNo.setText("");
				
				this.dispose();
			}
		}
		else
			this.dispose();
	}
}
