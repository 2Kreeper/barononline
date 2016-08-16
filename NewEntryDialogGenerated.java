package eu.barononline;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class NewEntryDialogGenerated extends JDialog {
	
	public JTextField subjectTfld, mediaTfld, pageTfld, tasksTfld, untilTfld;
	
	public NewEntryDialogGenerated() {
		setTitle("Neuen Eintrag erstellen");
		setSize(960, 540);
		getContentPane().setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel subjectPnl = new JPanel();
		mainPanel.add(subjectPnl);
		subjectPnl.setLayout(new GridLayout(1, 2, 10, 10));
		JLabel subjectLbl = new JLabel("Fach");
		subjectTfld = new JTextField();
		subjectTfld.setColumns(10);
		subjectPnl.add(subjectLbl);
		subjectPnl.add(subjectTfld);
		
		JPanel mediaPnl = new JPanel();
		mainPanel.add(mediaPnl);
		mediaPnl.setLayout(new GridLayout(1, 2, 10, 10));
		JLabel mediaLbl = new JLabel("Medium");
		mediaTfld = new JTextField();
		mediaTfld.setColumns(10);
		mediaPnl.add(mediaLbl);
		mediaPnl.add(mediaTfld);
		
		JPanel tasksPnl = new JPanel();
		mainPanel.add(tasksPnl);
		tasksPnl.setLayout(new GridLayout(1, 2, 10, 10));
		JLabel tasksLbl = new JLabel("Aufgabe(n)");
		tasksTfld = new JTextField();
		tasksTfld.setColumns(21);
		tasksPnl.add(tasksLbl);
		tasksPnl.add(tasksTfld);
		tasksTfld.setColumns(10);
		
		JPanel pagePnl = new JPanel();
		mainPanel.add(pagePnl);
		pagePnl.setLayout(new GridLayout(1, 2, 10, 10));
		JLabel pageLbl = new JLabel("Seite(n)");
		pagePnl.add(pageLbl);
		pageTfld = new JTextField();
		pagePnl.add(pageTfld);
		pageTfld.setColumns(7);
		
		JPanel untilPnl = new JPanel();
		mainPanel.add(untilPnl);
		untilPnl.setLayout(new GridLayout(1, 2, 10, 10));
		JLabel untilLbl = new JLabel("Bis (Jahr-Monat-Tag)");
		untilTfld = new JTextField();
		untilPnl.add(untilLbl);
		untilPnl.add(untilTfld);
		
		
		JPanel northPanelTop = new JPanel();
		northPanelTop.setLayout(new BorderLayout());
		
		JPanel northPanelBottom = new JPanel();
		northPanelBottom.setLayout(new BorderLayout());
		
		JPanel northMainPanel = new JPanel();
		northMainPanel.setLayout(new BorderLayout());
		northMainPanel.add(BorderLayout.NORTH, northPanelTop);
		northMainPanel.add(BorderLayout.SOUTH, northPanelBottom);
		
		mainPanel.add(northMainPanel);
		
		JButton newEntryConfirmBt = new JButton("Neuen Eintrag erstellen");
		northMainPanel.add(newEntryConfirmBt, BorderLayout.CENTER);
		//newEntryConfirmBt.addActionListener(new NewEntryConfirmListener(this));
		
		getContentPane().add(BorderLayout.CENTER, mainPanel);
		
		JLabel label_1 = new JLabel("");
		mainPanel.add(label_1);
		
		setVisible(true);
	}
}
