package eu.barononline.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import eu.barononline.DBAccessor;
import eu.barononline.DBGUI;
import eu.barononline.dialogs.NewEntryDialog;

public class NewEntryConfirmListener implements ActionListener {

	private NewEntryDialog dialog;
	
	public NewEntryConfirmListener(NewEntryDialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String subject = dialog.subjectTfld.getText(), media = dialog.mediaTfld.getText(), page = dialog.pageTfld.getText(), tasks = dialog.tasksTfld.getText(), until = dialog.datePicker.getJFormattedTextField().getText();
		
		String day = until.substring(0, 2), month = until.substring(3, 5), year = until.substring(6);
		until = year + "-" + month + "-" + day;
		Date selectedDate = (Date) dialog.datePicker.getModel().getValue();
	    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	    
	    if(selectedDate.after(new Date()) || selectedDate.equals(new Date())) {
	    	DBAccessor.addRow(subject, media, page, tasks, until);
	    	JOptionPane.showConfirmDialog(dialog, "Neuer Eintrag wurde erfolgreich erstellt", "Neuer Eintrag erstellt", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			DBGUI.gui.updateHAs();
			dialog.dispose();
	    } else {
	    	JOptionPane.showConfirmDialog(dialog, "Das Datum liegt in der Vergangenheit!", "Datum ungültig!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
	    }
	}

}
