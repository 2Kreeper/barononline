package eu.barononline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryFinishedListener implements ActionListener {

	DBGUI gui;
	
	public EntryFinishedListener(DBGUI dbgui) {
		gui = dbgui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int[] selected = gui.jTable1.getSelectedRows();
		for(int i: selected) {
			int haID = Integer.parseInt((String) gui.jTable1.getValueAt(i, 5));
			DBAccessor.finishEntry(haID, gui.userID);
			gui.updateHAs();
		}
	}

}
