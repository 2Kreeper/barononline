package eu.barononline.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import eu.barononline.dialogs.NewAccountDialog;

public class NewAccountListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		NewAccountDialog dialog = new NewAccountDialog();
	}
	
}
