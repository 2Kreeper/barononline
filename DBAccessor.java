package eu.barononline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAccessor {
	
	public static final String URL = "jdbc:derby://localhost:1527/Hausaufgaben", USER = "Administrator", PWD = "alexanderbaron";
	
	private static PreparedStatement addPrepStmt, addPrepStmt2, removePrepStmt, removePrepStmt2, selectUserPrepStmt;
	private static final String ADD_PREP_STMT_SCRIPT = "INSERT INTO HA (FACH, MEDIUM, SEITE, AUFGABEN, BIS, ERSTELLT_VON) VALUES (?, ?, ?, ?, ?, ?)", ADD_PREP_STMT_SCRIPT_2 = "INSERT INTO ERLEDIGT VALUES (?, false, false, false, false)", REMOVE_PREP_STMT_SCRIPT = "DELETE FROM HA WHERE ID = ?", REMOVE_PREP_STMT_SCRIPT_2 = "DELETE FROM ERLEDIGT WHERE ID = ?", SELECT_USER_PREP_STMT_SCRIPT = "SELECT * FROM ACCOUNTS WHERE NAME = ?";
	
	private static Connection con = null;
	
	/**
	 * @return Returns if connection was successfull
	 */
	public static boolean connect() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			
			con = DriverManager.getConnection(URL, USER, PWD);
			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		try {
			addPrepStmt = con.prepareStatement(ADD_PREP_STMT_SCRIPT);
			addPrepStmt2 = con.prepareStatement(ADD_PREP_STMT_SCRIPT_2);
			removePrepStmt = con.prepareStatement(REMOVE_PREP_STMT_SCRIPT);
			removePrepStmt2 = con.prepareStatement(REMOVE_PREP_STMT_SCRIPT_2);
			selectUserPrepStmt = con.prepareStatement(SELECT_USER_PREP_STMT_SCRIPT);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static ResultSet executeQuery(String query) { 
		try {
			return con.createStatement().executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean executeUpdate(String query) {
		try {
			con.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static ResultSet selectAllHAs() {
		return executeQuery("SELECT * FROM ha");
	}
	
	public static void finishEntry(int id, String colName) {
		try {
			con.createStatement().executeUpdate("UPDATE ERLEDIGT SET " + colName.toUpperCase() + " = TRUE WHERE ID = " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void unfinishEntry(int id, String colName) {
		try {
			con.createStatement().executeUpdate("UPDATE ERLEDIGT SET " + colName.toUpperCase() + " = FALSE WHERE ID = " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean addRow(String subject, String media, String page, String numbers, String to) {
		try {
			addPrepStmt.setString(1, subject);
			addPrepStmt.setString(2, media);
			addPrepStmt.setString(3, page);
			addPrepStmt.setString(4, numbers);
			addPrepStmt.setString(5, to);
			addPrepStmt.setString(6, DBGUI.gui.username.toUpperCase());
			
			addPrepStmt.executeUpdate();
			
			addPrepStmt = con.prepareStatement(ADD_PREP_STMT_SCRIPT);
			
			ResultSet rs = con.createStatement().executeQuery("SELECT ID FROM HA WHERE FACH = '" + subject + "' AND MEDIUM = '" + media + "' AND SEITE = '" + page + "' AND AUFGABEN = '" + numbers + "' AND BIS = '" + to + "'");
			rs.next();
			
			addPrepStmt2.setInt(1, rs.getInt("ID"));
			addPrepStmt2.executeUpdate();
			
			addPrepStmt2 = con.prepareStatement(ADD_PREP_STMT_SCRIPT_2);
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean removeRow(int id) {
		try {
			ResultSet rs = executeQuery("SELECT * FROM HA WHERE ID = " + id);
			if(rs.next()) {
				String addQuery = "INSERT INTO ARCHIV (SELECT * FROM HA WHERE ID = " + id + ")";
				System.out.println("Query: " + addQuery);
				executeUpdate(addQuery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			removePrepStmt.setString(1, Integer.toString(id));
			
			removePrepStmt.executeUpdate();
			
			removePrepStmt = con.prepareStatement(REMOVE_PREP_STMT_SCRIPT);
			
			removePrepStmt2.setInt(1, id);
			removePrepStmt2.executeUpdate();
			
			removePrepStmt2 = con.prepareStatement(REMOVE_PREP_STMT_SCRIPT_2);
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean doesUserExist(String username, String password) throws SQLException {

		selectUserPrepStmt.setString(1, username);
			
		ResultSet rs = selectUserPrepStmt.executeQuery();
		
		selectUserPrepStmt = con.prepareStatement(SELECT_USER_PREP_STMT_SCRIPT);
		
		if(rs.next()) {
			return rs.getString("passwort").equals(password);
		} else {
			return false;
		}
	}
	
	public static boolean isUserAdmin(String username) {
		try {
			selectUserPrepStmt.setString(1, username);
			ResultSet rs = selectUserPrepStmt.executeQuery();
		
			selectUserPrepStmt = con.prepareStatement(SELECT_USER_PREP_STMT_SCRIPT);
			
			if(rs.next()) {
				return rs.getBoolean("admin");
			} else {
				System.err.println("User \"" + username + "\" was not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getColName(String username) {
		try {
			selectUserPrepStmt.setString(1, username);
			ResultSet rs = selectUserPrepStmt.executeQuery();
		
			selectUserPrepStmt = con.prepareStatement(SELECT_USER_PREP_STMT_SCRIPT);
			
			if(rs.next()) {
				return rs.getString("spaltenname");
			} else {
				System.err.println("User \"" + username + "\" was not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean hasUserFinished(String colName, int id) throws SQLException {
		ResultSet rs = con.createStatement().executeQuery("SELECT " + colName.toUpperCase() + " FROM ERLEDIGT WHERE ID = " + id);
		if(!rs.next()) {
			throw new SQLException("Column name \"" + colName + "\" does not exist!");
		}
		return rs.getBoolean(colName.toUpperCase());
	}
}
