package eu.barononline.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import eu.barononline.listeners.NewAccountConfirmListener;

public class NewAccountDialog extends JDialog {
	
	public JTextField nameTfld;
	public JPasswordField passwordPfld;
	
	public NewAccountDialog() {
		setTitle("Neuen Account erstellen");
		setSize(1920 / 2, 1080 / 2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		JLabel nameLbl = new JLabel("Name");
		nameTfld = new JTextField(25);
		namePanel.add(nameLbl);
		namePanel.add(nameTfld);
		getContentPane().add(BorderLayout.NORTH, namePanel);
		
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton confirmButton = new JButton("Account erstellen");
		confirmButton.addActionListener(new NewAccountConfirmListener(this));
		buttonPanel.add(confirmButton);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new FlowLayout());
		JLabel passwordLbl = new JLabel("Passwort");
		passwordPfld = new JPasswordField(25);
		passwordPanel.add(passwordLbl);
		passwordPanel.add(passwordPfld);
		
		southPanel.add(BorderLayout.NORTH, passwordPanel);
		southPanel.add(BorderLayout.SOUTH, buttonPanel);
		getContentPane().add(BorderLayout.SOUTH, southPanel);
		
		setVisible(true);
	}
}
