package eu.barononline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class NewEntryConfirmListener implements ActionListener {

	private NewEntryDialog dialog;
	
	public NewEntryConfirmListener(NewEntryDialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String subject = dialog.subjectTfld.getText(), media = dialog.mediaTfld.getText(), page = dialog.pageTfld.getText(), tasks = dialog.tasksTfld.getText(), until = dialog.untilTfld.getText();
		
		DBAccessor.addRow(subject, media, page, tasks, until);
		
		JOptionPane.showConfirmDialog(dialog, "Neuer Eintrag wurde erfolgreich erstellt", "Neuer Eintrag erstellt", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		DBGUI.gui.updateHAs();
		dialog.dispose();
	}

}
