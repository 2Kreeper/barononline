package eu.barononline.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import eu.barononline.DBGUI;

public class KeyboardListener implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Key typed");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key pressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Key released");
		switch(e.getKeyCode()) {
			case KeyEvent.VK_F5:
				System.out.println("Neu laden");
				DBGUI.gui.updateHAs();
				break;
		}
	}

}
