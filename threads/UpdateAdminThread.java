package eu.barononline.threads;

import eu.barononline.DBAccessor;
import eu.barononline.DBGUI;

public class UpdateAdminThread extends Thread {
	
	@Override
	public void run() {
		while(true) {
			DBGUI.gui.admin = DBAccessor.isUserAdmin(DBGUI.gui.username);
			DBGUI.gui.updateAdmin();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
