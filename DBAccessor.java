package eu.barononline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAccessor {
	
	public static final String URL = "jdbc:derby://localhost:1527/Hausaufgaben", USER = "Administrator", PWD = "alexanderbaron";
	
	private static PreparedStatement addPrepStmt, addPrepStmt2, removePrepStmt, removePrepStmt2, selectUserPrepStmt;
	private static final String ADD_PREP_STMT_SCRIPT = "INSERT INTO HA (FACH, MEDIUM, SEITE, AUFGABEN, BIS, ERSTELLT_VON) VALUES (?, ?, ?, ?, ?, ?)", ADD_PREP_STMT_SCRIPT_2 = "INSERT INTO ERLEDIGT VALUES (?, ?, false)", REMOVE_PREP_STMT_SCRIPT = "DELETE FROM HA WHERE ID = ?", REMOVE_PREP_STMT_SCRIPT_2 = "DELETE FROM ERLEDIGT WHERE HA_ID = ?", SELECT_USER_PREP_STMT_SCRIPT = "SELECT * FROM ACCOUNTS WHERE USERNAME = ?";
	
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
	
	public static void finishEntry(int haID, int userID) {
		try {
			con.createStatement().executeUpdate("UPDATE ERLEDIGT SET ERLEDIGT = TRUE WHERE HA_ID = " + haID + " AND USER_ID = " + userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void unfinishEntry(int haID, int userID) {
		try {
			con.createStatement().executeUpdate("UPDATE ERLEDIGT SET ERLEDIGT = FALSE WHERE HA_ID = " + haID + " AND USER_ID = " + userID);
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
			addPrepStmt.setInt(6, DBGUI.gui.userID);
			
			addPrepStmt.executeUpdate();
			
			addPrepStmt = con.prepareStatement(ADD_PREP_STMT_SCRIPT);
			
			ResultSet rs = con.createStatement().executeQuery("SELECT ID FROM HA WHERE FACH = '" + subject + "' AND MEDIUM = '" + media + "' AND SEITE = '" + page + "' AND AUFGABEN = '" + numbers + "' AND BIS = '" + to + "' AND ERSTELLT_VON = " + DBGUI.gui.userID);
			rs.next();
			
			
			int users = getUserAmount();
			for(int i = 0; i < users; i++) {
				//adding to "finished"-table:
				addPrepStmt2.setInt(1, rs.getInt("ID"));
				addPrepStmt2.setInt(2, i + 1);
				addPrepStmt2.executeUpdate();
			
				addPrepStmt2 = con.prepareStatement(ADD_PREP_STMT_SCRIPT_2);
			}
			
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean removeUser(String username) {
		try {
			con.createStatement().executeUpdate("DELETE * FROM ACCOUNTS WHERE USERNAME = '" + username + "'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeUser(int userID) {
		try {
			con.createStatement().executeUpdate("DELETE FROM ERLEDIGT WHERE USER_ID = " + userID);
			con.createStatement().executeUpdate("DELETE FROM ACCOUNTS WHERE ID = " + userID);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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

		selectUserPrepStmt.setString(1, username.toUpperCase());
			
		ResultSet rs = selectUserPrepStmt.executeQuery();
		
		selectUserPrepStmt = con.prepareStatement(SELECT_USER_PREP_STMT_SCRIPT);
		
		if(rs.next()) {
			return rs.getString("passwort").equals(password);
		} else {
			System.out.println("rs.next() = false");
			return false;
		}
	}
	
	public static boolean isUserAdmin(String username) {
		try {
			selectUserPrepStmt.setString(1, username);
			ResultSet rs = selectUserPrepStmt.executeQuery();
		
			selectUserPrepStmt = con.prepareStatement(SELECT_USER_PREP_STMT_SCRIPT);
			
			if(rs.next()) {
				return rs.getBoolean("administrator");
			} else {
				System.err.println("User \"" + username + "\" was not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getUserID(String username) {
		try {
			selectUserPrepStmt.setString(1, username.toUpperCase());
			ResultSet rs = selectUserPrepStmt.executeQuery();
		
			selectUserPrepStmt = con.prepareStatement(SELECT_USER_PREP_STMT_SCRIPT);
			
			if(rs.next()) {
				return rs.getInt("ID");
			} else {
				System.err.println("User \"" + username + "\" was not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static String getUsername(int userid) {
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM ACCOUNTS WHERE ID = " + userid);
			if(rs.next()) {
				return rs.getString("USERNAME");
			} else {
				System.err.println("Nothing found. (DBAccessor.getUsername(" + userid + "))");
				return "";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
		
	}

	public static boolean hasUserFinished(int userID, int taskID) throws SQLException {
		ResultSet rs = con.createStatement().executeQuery("SELECT ERLEDIGT FROM ERLEDIGT WHERE HA_ID = " + taskID + " AND USER_ID = " + userID);
		if(!rs.next()) {
			throw new SQLException("User ID " + userID + " or task ID " + taskID + " does not exist!");
		}
		return rs.getBoolean("ERLEDIGT");
	}

	public static int getUserAmount() {
		ResultSet rs;
		int userAmount = -1;
		try {
			rs = con.createStatement().executeQuery("SELECT * FROM ACCOUNTS");
			userAmount = 0;
			while(rs.next()) {
				userAmount += 1;
			}
			return userAmount;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} 
		
	}

	public static int[] getUserIDs() {
		int[] ids = new int[getUserAmount()];
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM ACCOUNTS");
			
			int i = -1;
			while(rs.next()) {
				i++;
				ids[i] = rs.getInt("ID");
			}
			return ids;
		} catch (SQLException e) {
			e.printStackTrace();
			return ids;
		}
		
	}

	public static void setAdmin(String username, boolean admin) {
		try {
			con.createStatement().executeUpdate("UPDATE ACCOUNTS SET ADMINISTRATOR = " + Boolean.toString(admin) + " WHERE USERNAME = '" + username + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void setAdmin(int userID, boolean admin) {
		try {
			con.createStatement().executeUpdate("UPDATE ACCOUNTS SET ADMINISTRATOR = " + Boolean.toString(admin) + " WHERE ID = " + userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateAdmin() {
		DBGUI.gui.admin = isUserAdmin(DBGUI.gui.username);
	}

	public static boolean addUser(String username, String password) {
		try {
			con.createStatement().executeUpdate("TRUNCATE TABLE HA");
			con.createStatement().executeUpdate("TRUNCATE TABLE ERLEDIGT");
			
			con.createStatement().executeUpdate("INSERT INTO ACCOUNTS (USERNAME, PASSWORT) VALUES ('" + username.toUpperCase() + "', '" + password + "')");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int getHAAmount() {
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM HA");
			int amount = 0;
			while(rs.next()) {
				amount++;
			}
			return amount;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public static int[] getHAIDs() {
		int[] haids = new int[getHAAmount()];
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM HA");
			int counter = -1;
			while(rs.next()) {
				counter++;
				haids[counter] = rs.getInt("ID");
			}
			return haids;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
