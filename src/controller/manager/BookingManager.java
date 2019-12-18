package controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.Booking;
import model.Room;

public class BookingManager {
	private static Vector<Booking> bookings = new Vector<>();
	
	public static int addBooking(Booking booking) throws SQLException, ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO booking (roomID, customerID, duration, total, hasBreakfast,start)"
				+ "VALUES (?, ?, ?, ?, ?, ?)"
				);
		
		/*
		ps.setInt(1, booking.getRoom().getUniqueID());
		ps.setInt(2, booking.getCustomer());*/
		ps.setInt(1, 1);
		ps.setInt(2, 1);
		ps.setInt(3, booking.getDuration());
		ps.setDouble(4, BookingManager.calculateTotal(booking));
		ps.setBoolean(5, booking.isHasBreakfast());
		//ps.setTimestamp(6, displayStart(booking));
		
		int status = ps.executeUpdate();
		
		return status;
	}
	
	public static boolean updateBooking(Booking booking) {
		int index = -1;
		boolean success = false;		
		
		for (int i = 0; i < bookings.size(); i++) {
			Booking temp = bookings.get(i);
			
			if (temp != null && temp.getUniqueID() == booking.getUniqueID()) {
				index = i;
				break;
			}
		}
		
		if (index < bookings.size()) {
			bookings.set(index, booking);
			success = true;
		}
		
		return success;	
	}
	
	public static boolean deleteBooking(int bookingID) {
		Booking booking = null;
		
		for (Booking b : bookings) {
			if (b.getUniqueID() == bookingID) {
				booking = b;
				break;
			}
		}
		
		//Room room = booking.getRoom();
		
		//Booking done, so set all the rooms for this booking
		//to occupied == false
		
		//room.setOccupied(false);
		
		
		return bookings.remove(booking);
	}
	
	//Return booking object to be updated
	public static Booking getBooking(int bookingID) {
		Booking temp = null;
		
		for (int i = 0; i < bookings.size(); i++) {
			temp = bookings.get(i);
			
			if (temp != null && temp.getUniqueID() == bookingID) {
				break;
			}
		}
		
		return temp;
	}
	
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
			if (column == 2) { //RoomNo
				columnNames.add("Room No");
			} else if (column == 3) { //BookingNo
				columnNames.add("Customer IC");
			} else {
				columnNames.add(metaData.getColumnName(column));
			}
		}
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				if (columnIndex == 2) {
					PreparedStatement roomNoStatement = connection.prepareStatement(
							"SELECT roomNo FROM room WHERE roomID = ?"
							);
					roomNoStatement.setInt(1, rs.getInt(2));
					System.out.println(rs.getInt(2));
					
					ResultSet roomNo = roomNoStatement.executeQuery();
					roomNo.first();
					System.out.println(roomNo.getInt(1));
					
					vector.add(roomNo.getInt(1));
					
				} else if (columnIndex == 3) {
					PreparedStatement roomNoStatement = connection.prepareStatement(
							"SELECT icNum FROM customer WHERE customerID = ?"
							);
					roomNoStatement.setInt(1, rs.getInt(3));
					//System.out.println(rs.getInt(3));
					
					ResultSet icNum = roomNoStatement.executeQuery();
					icNum.first();
					System.out.println(icNum.getInt(1));
					
					vector.add(icNum.getInt(1));
				} else {
					vector.add(rs.getObject(columnIndex));
				}
			}
			data.add(vector);
		}
		return new DefaultTableModel(data, columnNames);
	}
	
	//display the rooms booked by that particular customer
	public static void displayBookedRooms(Booking booking) {
		//Room room = booking.getRoom();
		
		
		//System.out.print(" " + room.getRoomNo());
		
		
	}
	
	public static void displayBooking(Booking booking) {
		System.out.println("\nBooking ID: " + booking.getUniqueID());
		//System.out.println("Customer's IC: " + booking.getCustomer());
		System.out.print("Rooms: ");
		displayBookedRooms(booking);
		System.out.println();
		//System.out.println("\nStart date: " + booking.getStart());
		//displayStart(booking); // display start time of that booking
		System.out.println("Duration: " + booking.getDuration());
		System.out.println("Total: RM" + calculateTotal(booking));
		System.out.println("Breakfast included: " + booking.isHasBreakfast());
	}
	/*
	public static Timestamp displayStart(Booking booking) {
		//Timestamp ts = new Timestamp(booking.getStart());
		
		//Actually I think this could be obtained directly from DB
		
		System.out.print("Booking time: ");
		System.out.print(ts.toLocalDateTime().getDayOfWeek());
		System.out.print(" ");
		System.out.print(ts.toLocalDateTime().toLocalDate());
		System.out.println("");
		return ts;
	}*/
	
	//End booking
	public static double calculateTotal(Booking booking) {
		int roomNo = booking.getRoomNo();
		double price = 0;
		
		
		//price += room.getPrice();
		
		
		if (booking.isHasBreakfast()) { //breakfast is RM 10
			price += 10;
		}
		
		return price * booking.getDuration();
	}
}
