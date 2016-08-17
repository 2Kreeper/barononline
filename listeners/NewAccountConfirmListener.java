package eu.barononline.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.omg.PortableInterceptor.SUCCESSFUL;

import eu.barononline.DBAccessor;
import eu.barononline.dialogs.NewAccountDialog;

public class NewAccountConfirmListener implements ActionListener {

	private NewAccountDialog dialog;

	public NewAccountConfirmListener(NewAccountDialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int selection = JOptionPane.showConfirmDialog(dialog, "Wollen Sie diesen Account wirklich erstellen? Sämtliche Hausaufgabeneinträge werden dann ins Archiv verschoben!", "Sicher?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		
		if(selection == JOptionPane.OK_OPTION) {
			String username = dialog.nameTfld.getText(), password = new String(dialog.passwordPfld.getPassword());
			boolean success = DBAccessor.addUser(username.toUpperCase(), password);
			if(success) {
				JOptionPane.showMessageDialog(dialog, "Der neue Account \"" + username + "\" wurde erfolgreich erstellt", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(dialog, "Der Account \"" + username + "\" konnte leider nicht erstellt werden", "Kein Erfolg", JOptionPane.ERROR_MESSAGE);
			}
			dialog.dispose();
		} else {
			dialog.dispose();
		}

	}

}
