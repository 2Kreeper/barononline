package eu.barononline.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eu.barononline.DBAccessor;
import eu.barononline.UsersGUI;

public class DeleteUserListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		int[] selectedAccountIndexes = UsersGUI.gui.jTable1.getSelectedRows();
		
		for(int i: selectedAccountIndexes) {
			int id = (int) UsersGUI.gui.jTable1.getValueAt(i, 0);
			DBAccessor.removeUser(id);
		}
		
		UsersGUI.gui.refreshUsers();
	}

}
