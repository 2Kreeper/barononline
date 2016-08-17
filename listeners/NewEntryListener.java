package eu.barononline.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eu.barononline.DBGUI;
import eu.barononline.dialogs.NewEntryDialog;

public class NewEntryListener implements ActionListener {

	private DBGUI gui;
	
	public NewEntryListener(DBGUI gui) {
		super();
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		NewEntryDialog newEntryDialog = new NewEntryDialog();
	}

}
