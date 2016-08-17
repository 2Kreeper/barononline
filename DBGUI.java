package eu.barononline;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import eu.barononline.dialogs.LoginDialog;
import eu.barononline.listeners.EntryNotFinishedListener;
import eu.barononline.listeners.KeyboardListener;
import eu.barononline.listeners.NewEntryListener;
import eu.barononline.listeners.RemoveEntryListener;
import eu.barononline.listeners.UsersListener;
import eu.barononline.threads.DBGUIUpdateThread;
import eu.barononline.threads.UpdateAdminThread;

public class DBGUI extends JFrame {
	
	public static DBGUI gui;
	
	public JTable jTable1;
	public String username;
	public int userID;
	public boolean admin = false;
	
	private JButton removeEntryBt, usersButton;
	
	private DefaultTableModel model;

	public static void main(String[] args) {
		gui = new DBGUI("Hausaufgabenverwaltung");
		boolean success = DBAccessor.connect();
		System.out.println(success ? "Connection to db was successfull" : "Connection to db was not successfull");
		if(!success) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(gui, "Verbindung zur Datenbank ist nicht möglich. Bitte Internetverbindung überprüfen und erneut versuchen", "Verbindungsaufbau gescheitert", JOptionPane.OK_OPTION, null);
		}
	}
	
	public DBGUI(String title) {
		super(title);
		
		LoginDialog dialog = new LoginDialog();
	}
	
	public void init() {
		//setting L&F:
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setSize(960, 540);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));
		
		//init table:
		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        model = new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                
                admin ? new String [] { "Fach", "Medium", "Seite(n)", "Aufgabe(n)", "Bis", "ID" , "Erledigt", "Erstellt von"} : new String [] { "Fach", "Medium", "Seite(n)", "Aufgabe(n)", "Bis", "ID" , "Erledigt"}
            ) {
                Class[] types = new Class [] { java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class };
                boolean[] canEdit = new boolean [] { false, false, false, false, false, false, false, false };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            };
        jTable1.setModel(model);
        jScrollPane1.setViewportView(jTable1);
		jTable1.addKeyListener(new KeyboardListener());
		jTable1.getTableHeader().setReorderingAllowed(false);
		jTable1.setDoubleBuffered(true);
		
        getContentPane().add(BorderLayout.CENTER, jScrollPane1);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        //adding "new"-Button:
        JButton newEntryBt = new JButton("Neuer Eintrag");
        newEntryBt.addActionListener(new NewEntryListener(this));
        buttonPanel.add(newEntryBt);
        
        //adding "remove"-button:
        removeEntryBt = new JButton("Ausgewählten Eintrag ins Archiv verschieben");
        removeEntryBt.addActionListener(new RemoveEntryListener(this));
        removeEntryBt.setVisible(admin);
        buttonPanel.add(removeEntryBt);
        
        //adding "entry finished"-button:
        JButton entryFinishedButton = new JButton("Ausgewählten Eintrag als erledigt markieren");
        entryFinishedButton.addActionListener(new EntryFinishedListener(this));
        buttonPanel.add(entryFinishedButton);
        
        //adding "entry not finished"-button:
        JButton entryNotFinishedButton = new JButton("Ausgewählten Eintrag als nicht erledigt markieren");
        entryNotFinishedButton.addActionListener(new EntryNotFinishedListener(this));
        buttonPanel.add(entryNotFinishedButton);
        
        //adding "users"-button:
        usersButton = new JButton("Accountverwaltung öffnen");
        usersButton.addActionListener(new UsersListener());
        usersButton.setVisible(admin);
        buttonPanel.add(usersButton);
        
        getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        
        pack();
        printHAs();
        
        //Creating auto-update-thread:
        DBGUIUpdateThread guiUpdateThread = new DBGUIUpdateThread();
        guiUpdateThread.start();
        
        //Creating admin-check-thread:
        UpdateAdminThread adminUpdateThread = new UpdateAdminThread();
        adminUpdateThread.start();
	}

	
	public void updateHAs() {
		gui.clearTable();
		printHAs();
	}
	
	private static void printHAs() {
		ResultSet rs = DBAccessor.selectAllHAs();
		try {
			while(rs.next()) {
				if(gui.admin) {
					gui.addTableRow(rs.getString("fach"), rs.getString("medium"), rs.getString("seite"), rs.getString("aufgaben"), rs.getDate("bis").toLocaleString().substring(0, 10), Integer.toString(rs.getInt("ID")), DBAccessor.hasUserFinished(gui.userID, rs.getInt("ID")), DBAccessor.getUsername(rs.getInt("erstellt_von")));
				} else {
					gui.addTableRow(rs.getString("fach"), rs.getString("medium"), rs.getString("seite"), rs.getString("aufgaben"), rs.getDate("bis").toLocaleString().substring(0, 10), Integer.toString(rs.getInt("ID")), DBAccessor.hasUserFinished(gui.userID, rs.getInt("ID")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addTableRow(String subject, String media, String page, String numbers, String to, String id, boolean finished) {
		model.addRow(new Object[] {subject, media, page, numbers, to, id, finished});
	}
	
	public void addTableRow(String subject, String media, String page, String numbers, String to, String id, boolean finished, String created_by) {
		model.addRow(new Object[] {subject, media, page, numbers, to, id, finished, created_by});
	}
	
	public void clearTable() {
		int rowCount = model.getRowCount();
		for(int i = 0; i < rowCount; i++) {
			model.removeRow(0);
		}
	}
	
	public void removeRow(int tableIndex) {
		model.removeRow(tableIndex);
	}
	
	public void removeEntry(int tableIndex) {
		int id = Integer.parseInt((String) jTable1.getValueAt(tableIndex, 5));
		DBAccessor.removeRow(id);
		removeRow(tableIndex);
	}

	
	public void updateAdmin() {
		removeEntryBt.setVisible(admin);
		usersButton.setVisible(admin);
	}
}
