package controller.manager;

import java.util.Vector;

import model.Staff;

public class StaffManager {
	private static Vector<Staff> staffs = new Vector<>();
	
	public static int addStaff(Staff staff) {
		int status = 0;
		int size = staffs.size();
		staffs.add(staff);
		
		if (staffs.size() > size) {
			status++;
		}
		
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
	
	public static Staff loginStaff(String staffID, String password) {
		Staff staff = null;
		
		for (Staff s : staffs) {
			if (s.getStaffID().equalsIgnoreCase(staffID) && s.getPassword().equalsIgnoreCase(password)) {
				staff = s;
				break;
			}
		}
		return staff;
	}
}
