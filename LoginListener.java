package eu.barononline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginListener implements ActionListener {

	LoginDialog dialog;
	
	public LoginListener(LoginDialog loginDialog) {
		dialog = loginDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.login(dialog.usernameTfld.getText().toUpperCase(), String.valueOf(dialog.passwordPfld.getPassword()));
	}

}
