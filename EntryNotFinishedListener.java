package eu.barononline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryNotFinishedListener implements ActionListener {

	DBGUI gui;
	
	public EntryNotFinishedListener(DBGUI dbgui) {
		gui = dbgui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int[] selected = gui.jTable1.getSelectedRows();
		for(int i: selected) {
			int id = Integer.parseInt((String) gui.jTable1.getValueAt(i, 5));
			DBAccessor.unfinishEntry(id, gui.colName);
			gui.updateHAs();
		}
	}

}
