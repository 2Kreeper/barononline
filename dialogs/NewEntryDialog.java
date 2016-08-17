package eu.barononline.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import eu.barononline.listeners.NewEntryConfirmListener;
import eu.barononline.threads.NewEntryThread;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class NewEntryDialog extends JDialog {
	
	public JTextField subjectTfld, mediaTfld, pageTfld, tasksTfld, untilTfld;
	public JDatePickerImpl datePicker;
	public JButton newEntryConfirmBt;
	
	public NewEntryDialog() {
		//setting L&F:
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SwingUtilities.updateComponentTreeUI(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				setTitle("Neuen Eintrag erstellen");
				setSize(960, 540);
				getContentPane().setLayout(new BorderLayout());
				
				JPanel mainPanel = new JPanel();
				mainPanel.setLayout(new GridLayout(0, 2, 0, 0));
				
				JPanel subjectPnl = new JPanel();
				mainPanel.add(subjectPnl);
				subjectPnl.setLayout(new MigLayout("", "[23px][][227px][][][]", "[20px][][]"));
				JLabel subjectLbl = new JLabel("Fach");
				subjectPnl.add(subjectLbl, "cell 2 2,alignx center,aligny center");
				subjectTfld = new JTextField();
				subjectPnl.add(subjectTfld, "cell 5 2,growx,aligny center");
				subjectTfld.setColumns(20);
				
				JPanel mediaPnl = new JPanel();
				mainPanel.add(mediaPnl);
				mediaPnl.setLayout(new MigLayout("", "[36px][231px][][][][]", "[20px][][]"));
				JLabel mediaLbl = new JLabel("Medium");
				mediaPnl.add(mediaLbl, "cell 1 2,alignx center,aligny center");
				mediaTfld = new JTextField();
				mediaTfld.setColumns(20);
				mediaPnl.add(mediaTfld, "cell 4 2,growx,aligny center");
				
				JPanel pagePnl = new JPanel();
				mainPanel.add(pagePnl);
				pagePnl.setLayout(new MigLayout("", "[38px][231px][][][][]", "[20px][][]"));
				JLabel pageLbl = new JLabel("Seite(n)");
				pagePnl.add(pageLbl, "cell 1 2,alignx center,aligny center");
				pageTfld = new JTextField();
				pagePnl.add(pageTfld, "cell 5 2,growx,aligny center");
				pageTfld.setColumns(20);
				
				JPanel tasksPnl = new JPanel();
				mainPanel.add(tasksPnl);
				tasksPnl.setLayout(new MigLayout("", "[55px][231px][][][][][]", "[20px][][]"));
				JLabel tasksLbl = new JLabel("Aufgabe(n)");
				tasksPnl.add(tasksLbl, "cell 1 2,alignx center,aligny center");
				tasksTfld = new JTextField();
				tasksPnl.add(tasksTfld, "cell 4 2,grow");
				tasksTfld.setColumns(20);
				
				JPanel untilPnl = new JPanel();
				mainPanel.add(untilPnl);
				JLabel untilLbl = new JLabel("Datum");
				UtilDateModel model = new UtilDateModel();
				JDatePanelImpl datePanel = new JDatePanelImpl(model);
				datePicker = new JDatePickerImpl(datePanel);
				untilPnl.add(untilLbl);
				untilPnl.add(datePicker);


				
				JPanel northMainPanel = new JPanel();
				
				mainPanel.add(northMainPanel);
				northMainPanel.setLayout(new MigLayout("", "[145px][][][][]", "[23px][][][][][]"));
				
				newEntryConfirmBt = new JButton("Neuen Eintrag erstellen");
				newEntryConfirmBt.setEnabled(false);
				northMainPanel.add(newEntryConfirmBt, "cell 2 3,alignx left,aligny top");
				newEntryConfirmBt.addActionListener(new NewEntryConfirmListener(this));
				
				getContentPane().add(BorderLayout.CENTER, mainPanel);
				
				JLabel label_1 = new JLabel("");
				mainPanel.add(label_1);
				
				NewEntryThread newEntryThread = new NewEntryThread(this);
				newEntryThread.start();
				
				setVisible(true);
	}
}
