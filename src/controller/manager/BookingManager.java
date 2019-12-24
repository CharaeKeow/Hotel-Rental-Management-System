package controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.Booking;

public class BookingManager {
	
	public static int addBooking(Booking booking) throws SQLException, ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO booking (roomNo, icNum, duration, total, hasBreakfast,start)"
				+ "VALUES (?, ?, ?, ?, ?, ?)"
				);
			
		ps.setInt(1, booking.getRoomNo()); 
		ps.setString(2, booking.getCustomerIcNo()); 
		ps.setInt(3, booking.getDuration());
		ps.setDouble(4, BookingManager.calculateTotal(booking));
		ps.setBoolean(5, booking.isHasBreakfast());
		java.util.Date utilStartDate = booking.getStart();
		java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
		ps.setDate(6, sqlStartDate);
		
		int status = ps.executeUpdate();
		
		if (status > 0) { //succeed
			
		}
		
		return status;
	}
	
	public static int updateBooking(Booking booking, int bookingID) throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"UPDATE booking"
				+ " SET icNum = ?, roomNo = ?, start = ?, duration = ?, total = ?, hasBreakfast = ? "
				+ " WHERE bookingID = ? "
				);
		
		ps.setString(1, booking.getCustomerIcNo());
		ps.setInt(2, booking.getRoomNo());
		java.util.Date utilStartDate = booking.getStart();
		java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
		ps.setDate(3, sqlStartDate);
		ps.setInt(4, booking.getDuration());
		ps.setDouble(5, BookingManager.calculateTotal(booking));
		ps.setBoolean(6, booking.isHasBreakfast());
		ps.setInt(7, bookingID);
		
		int status = ps.executeUpdate();
		
		return status;
	}
	
	public static int deleteBooking(String bookingID) throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement getRoomNoStatement = connection.prepareStatement(
					"SELECT roomNo FROM booking"
					+ " WHERE bookingID = ?"
				);
		
		getRoomNoStatement.setInt(1, Integer.parseInt(bookingID));
		
		ResultSet rs = getRoomNoStatement.executeQuery();
		rs.next();
		int roomNo = rs.getInt(1);
		
		PreparedStatement deleteStatement = connection.prepareStatement(
				"DELETE FROM booking"
				+ " WHERE bookingID = ? "
				);
		
		deleteStatement.setInt(1, Integer.parseInt(bookingID));
		
		int status = deleteStatement.executeUpdate();
		
		RoomManager.updateOccupied(false, roomNo);
		
		return status;
	}
	
	//Return booking object to be updated
	
	
	public static DefaultTableModel displayBookings() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM booking"
				);
		
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));			
		}
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));				
			}
			data.add(vector);
		}
		return new DefaultTableModel(data, columnNames);
	}

	public static double calculateTotal(Booking booking) throws ClassNotFoundException, SQLException {
		double total = 0;		
		
		double price = RoomManager.getRoomPrice(booking.getRoomNo());
		int duration = booking.getDuration();
		boolean hasBreakfast = booking.isHasBreakfast();
		
		total = price * duration;
		
		if (hasBreakfast) {
			total += 10;
		}
		
		return total;
	}
	
	public static Vector<String> bookingList() throws ClassNotFoundException, SQLException {
		Vector<String> bookings = new Vector<>();
		
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");		
		
		PreparedStatement ps = connection.prepareStatement(
				"SELECT bookingID FROM booking"
				);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next( )) {
			bookings.add(rs.getString(1));
		}
		
		return bookings;		
	} 
}
