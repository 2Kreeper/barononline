package eu.barononline.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eu.barononline.DBAccessor;
import eu.barononline.DBGUI;

public class EntryNotFinishedListener implements ActionListener {

	DBGUI gui;
	
	public EntryNotFinishedListener(DBGUI dbgui) {
		gui = dbgui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int[] selected = gui.jTable1.getSelectedRows();
		for(int i: selected) {
			int haID = Integer.parseInt((String) gui.jTable1.getValueAt(i, 5));
			DBAccessor.unfinishEntry(haID, gui.userID);
			gui.updateHAs();
		}
	}

}
