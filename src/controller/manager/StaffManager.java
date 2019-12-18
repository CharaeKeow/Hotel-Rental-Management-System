package controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Staff;

public class StaffManager {
	private static Vector<Staff> staffs = new Vector<>();
	
	public static int addStaff(Staff staff) throws SQLException, ClassNotFoundException {
		//load DB driver
		//Class.forName("")
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO staff (staffID, password, role)"
				+ "VALUES('ali', 'aaa', 0)"
				);
		
		int status = ps.executeUpdate();
		connection.close();
		
		return status;
	}
	
	public static Vector<Staff> displayStaffs() {
		for (Staff staff : staffs) {
			displayStaff(staff);
		}
		return staffs;
	}
	
	public static void displayStaff(Staff staff) {
		System.out.println("\nStaff ID: " + staff.getStaffID());
		System.out.println("\nName: " + staff.getUserName());
	}
	
	public static boolean updateStaff(Staff staff) {
		int index = -1;
		boolean success = false;
		
		for (int i = 0; i < staffs.size(); i++) {
			Staff temp = staffs.get(i);
			
			if (temp != null && temp.getStaffID() == staff.getStaffID()) {
				index = i;
				break;
			}
		}
		
		if (index < staffs.size()) {
			staffs.set(index, staff);
			success = true;
		}
		
		return success;
	}
	
	public static boolean deleteStaff(String staffID) {
		Staff staff = null;
		
		for (Staff s : staffs) {
			if (s.getStaffID().equalsIgnoreCase(staffID)) {
				staff = s;
				break;
			}
		}
		return staffs.remove(staff);
	}
	
	public static boolean loginStaff(String staffID, String password) throws ClassNotFoundException, SQLException {
		boolean success = false;
		
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement statement = connection.prepareStatement(
				"SELECT staffID FROM staff WHERE staffID = ? and password = ?"
				);
		statement.setString(1, staffID);
		statement.setString(2,  password);
		
		ResultSet rs = statement.executeQuery();
		//System.out.println(rs);
		
		if(rs.next()) {
			success = true;
		}
		
		return success;
	}
}
