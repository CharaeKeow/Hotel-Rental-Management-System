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
	//private static Vector<Customer> customers = new Vector<>();
	
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
	
	public static int updateCustomer(Customer customer) throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"UPDATE customer"
				+ " SET name = ?, email = ?, phoneNo = ? "
				+ " WHERE icNum = ? "
				);
		
		ps.setString(1, customer.getName());
		ps.setString(2, customer.getEmail());
		ps.setString(3, customer.getPhoneNo());
		ps.setString(4, customer.getIcNum());
		
		int status = ps.executeUpdate();
		
		return status;
	}
	
	public static int deleteCustomer(String customerIC) throws SQLException, ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"DELETE FROM customer"
				+ " WHERE icNum = ? "
				);
		
		ps.setString(1, customerIC);
		
		int status = ps.executeUpdate();
		
		return status;
	}
	
	//Return vector of all Ic numbers
	public static Vector<String> customerList() throws ClassNotFoundException, SQLException {
		Vector<String> customers = new Vector<>();
		
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");		
		
		PreparedStatement ps = connection.prepareStatement(
				"SELECT icNum FROM customer"
				);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next( )) {
			customers.add(rs.getString(1));
		}
		
		return customers;		
	}
}
