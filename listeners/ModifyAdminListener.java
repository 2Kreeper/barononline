package eu.barononline.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eu.barononline.DBAccessor;
import eu.barononline.UsersGUI;

public class ModifyAdminListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		int[] selectedAccountIndxes = UsersGUI.gui.jTable1.getSelectedRows();
		
		for(int i: selectedAccountIndxes) {
			boolean isAdmin = (boolean) UsersGUI.gui.jTable1.getValueAt(i, 2);
			int id = (int) UsersGUI.gui.jTable1.getValueAt(i, 0);
			
			DBAccessor.setAdmin(id, !isAdmin);
		}
		
		UsersGUI.gui.refreshUsers();
	}

}
