package eu.barononline.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import eu.barononline.DBGUI;

public class RemoveEntryListener implements ActionListener {

	private DBGUI gui;
	
	public RemoveEntryListener(DBGUI dbgui) {
		gui = dbgui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int[] selectedRows = gui.jTable1.getSelectedRows();
		
		if(selectedRows.length <= 0) {
			JOptionPane.showMessageDialog(gui, "Es sind keine Einträge ausgewählt", "Keine Einträge ausgewählt!", JOptionPane.ERROR_MESSAGE);
		} else {
			for(int i: selectedRows) {
				DBGUI.gui.removeEntry(i);
			}
		}
	}

}
