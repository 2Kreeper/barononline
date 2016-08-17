package eu.barononline.threads;

import eu.barononline.DBGUI;

public class DBGUIUpdateThread extends Thread {
	
	@Override
	public void run() {
		while(true) {
			DBGUI.gui.updateHAs();
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
