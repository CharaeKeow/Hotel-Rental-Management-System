package controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.Customer;

public class CustomerManager {
	private static Vector<Customer> customers = new Vector<>();
	
	public static int addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO customer (name, email, phoneNo, icNum)"
				+ "VALUES(?, ?, ?, ?)"
				);
		
		ps.setString(1, customer.getName());
		ps.setString(2, customer.getEmail());
		ps.setString(3, customer.getPhoneNo());
		ps.setString(4, customer.getIcNum());
		
		int status = ps.executeUpdate();
		connection.close();
		
		return status;
	}
	
	public static DefaultTableModel displayCustomers() throws ClassNotFoundException, SQLException {
		
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM customer"
				);
		
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while(rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		
		return new DefaultTableModel(data, columnNames);		
	}
	
	public static void displayCustomer(Customer customer) {
		System.out.println("\nCustomer ID: " + customer.getUniqueID());
		System.out.println("Name: " + customer.getName());
		System.out.println("Email: " + customer.getEmail());
		System.out.println("Phone number: " + customer.getPhoneNo());
		System.out.println("IC Number: " + customer.getIcNum());
	}
	
	public static boolean updateCustomer(Customer customer) {
		int index = -1;
		boolean success = false;
		
		for (int i = 0; i < customers.size(); i++) {
			Customer temp = customers.get(i); //return customer at this index
			
			if (temp != null && temp.getUniqueID() == customer.getUniqueID()) {
				index = i;
				break;
			}
		}
		
		if (index < customers.size()) { //valid index
			customers.set(index, customer); //update the customer object at the specified index
			success = true;
		}
		
		return success;
	}
	
	public static boolean deleteCustomer(int customerID) {
		Customer customer = null;
		
		for (Customer c : customers) {
			if (c.getUniqueID() == customerID) {
				customer = c; //set customer = null
				break;
			}
		}
		return customers.remove(customer); //remove that customer in vector
	}
	
	//select specific customer in the customer list
	//select by icNum
	public static Customer selectCustomer(String icNum) {
		Customer temp = null;
		
		for (Customer customer : customers) {
			if (customer != null && customer.getIcNum().equalsIgnoreCase(icNum)) {
				temp = customer;
			}
		}
		
		return temp;
	}
	
	//select by id
	public static Customer selectCustomer(int customerID) {
		Customer temp = null;
		
		for (Customer customer : customers) {
			if (customer != null && customer.getUniqueID() == customerID) {
				temp = customer;	
			}
		}
		return temp;
	}
}
