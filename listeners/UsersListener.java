package eu.barononline.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eu.barononline.UsersGUI;

public class UsersListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		UsersGUI users = new UsersGUI();
	}

}
