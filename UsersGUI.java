package eu.barononline;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import eu.barononline.listeners.DeleteUserListener;
import eu.barononline.listeners.ModifyAdminListener;
import eu.barononline.listeners.NewAccountListener;

public class UsersGUI extends JFrame {
	
	public static UsersGUI gui;
	
	private DefaultTableModel model;
	private int[] ids;
	
	public UsersGUI() {
		ids = DBAccessor.getUserIDs();
		gui = this;
		setTitle("Accountverwaltung");
		setSize(1920 / 2, 1080 / 2);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		initComponents();
		
		showUsers();
		
		setVisible(true);
	}
	
	public static void refreshUsers() {
		gui.clearTable();
		gui.showUsers();
	}
	
	private void clearTable() {
		int rowCount = model.getRowCount();
		for(int i = 0; i < rowCount; i++) {
			model.removeRow(0);
		}
	}
	
    private void showUsers() {
		for(int i  = 0; i < DBAccessor.getUserAmount(); i++) {
			addRow(ids[i], DBAccessor.getUsername(ids[i]), DBAccessor.isUserAdmin(DBAccessor.getUsername(ids[i])));
		}
	}
    
    private void addRow(int id, String username, boolean administrator) {
    	model.addRow(new Object[] {id, username, administrator});
    }

	private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();


        model = new javax.swing.table.DefaultTableModel( new Object [][] {},
            new String [] {
                "ID", "Username", "Administrator"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        jTable1.setModel(model);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }
        jTable1.getTableHeader().setReorderingAllowed(false);
        getContentPane().add(BorderLayout.CENTER, jScrollPane1);
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        JButton createAccountButton = new JButton("Neuen Account erstellen");
        createAccountButton.addActionListener(new NewAccountListener());
        buttonPanel.add(createAccountButton);
        
        JButton removeButton = new JButton("Account löschen");
        removeButton.addActionListener(new DeleteUserListener());
        buttonPanel.add(removeButton);
        
        JButton switchAdminButton = new JButton("Account befördern/degradieren");
        switchAdminButton.addActionListener(new ModifyAdminListener());
        buttonPanel.add(switchAdminButton);
        
        
        getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        
        pack();
    }
    
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
}
