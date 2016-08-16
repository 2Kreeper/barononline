package eu.barononline;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LoginDialog extends JDialog {
	
	public static LoginDialog dialog;
	
	public JTextField usernameTfld;
	public JPasswordField passwordPfld;
	
	public LoginDialog() {
		dialog = this;
		
		//setting L&F:
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setLayout(new BorderLayout(15, 15));
		setPreferredSize(new Dimension(480, 270));
		setMinimumSize(new Dimension(480, 135));
		setResizable(false);
		setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.getWidth() / 2), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.getHeight()), 480, 135);
		
		JPanel usernamePnl = new JPanel(), passwordPnl = new JPanel();
		usernamePnl.setLayout(new BorderLayout(10,10));
		passwordPnl.setLayout(new BorderLayout(10,10));
		
		JLabel usernameLbl = new JLabel("Benutzername");
		usernameTfld = new JTextField();
		usernamePnl.add(BorderLayout.WEST, usernameLbl);
		usernamePnl.add(BorderLayout.CENTER, usernameTfld);
		
		JLabel passwordLbl = new JLabel("Passwort");
		passwordPfld = new JPasswordField();
		passwordPnl.add(BorderLayout.WEST, passwordLbl);
		passwordPnl.add(BorderLayout.CENTER, passwordPfld);
		
		JButton confirmBtn = new JButton("Anmelden");
		confirmBtn.addActionListener(new LoginListener(this));
		
		getContentPane().add(BorderLayout.NORTH, usernamePnl);
		getContentPane().add(BorderLayout.CENTER, passwordPnl);
		getContentPane().add(BorderLayout.SOUTH, confirmBtn);
		
		setVisible(true);
	}
	
	public void login(String username, String password) {
		try {
			if(DBAccessor.doesUserExist(username, password)) {
				DBGUI.gui.username = username;
				DBGUI.gui.colName = DBAccessor.getColName(username);
				if(DBAccessor.isUserAdmin(username)) {
					DBGUI.gui.admin = true;
				} else {
					DBGUI.gui.admin = false;
				}
				DBGUI.gui.init();
				DBGUI.gui.setVisible(true);
				this.dispose();
				JOptionPane.showMessageDialog(DBGUI.gui, "Willkommen, " + DBGUI.gui.colName, "Willkommen!", JOptionPane.INFORMATION_MESSAGE);
			} else {
				passwordPfld.setText("");
				JOptionPane.showMessageDialog(this, "Fehler beim Anmelden:\nBenutzername oder Passwort ist leider falsch", "Anmeldefehler", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
