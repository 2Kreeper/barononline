package eu.barononline.threads;

import eu.barononline.dialogs.NewEntryDialog;

public class NewEntryThread extends Thread {
	
	NewEntryDialog toCheck;
	
	public NewEntryThread(NewEntryDialog toCheck) {
		this.toCheck = toCheck;
	}
	
	@Override
	public void run() {
		while(true) {
			if(!toCheck.subjectTfld.getText().equals("") && !toCheck.mediaTfld.getText().equals("") && !toCheck.pageTfld.getText().equals("") && !toCheck.tasksTfld.getText().equals("") && !toCheck.datePicker.getJFormattedTextField().getText().equals("")) {
				toCheck.newEntryConfirmBt.setEnabled(true);
			} else {
				toCheck.newEntryConfirmBt.setEnabled(false);
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
