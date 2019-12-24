package controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.Room;

public class RoomManager {
	private static Vector<Room> rooms = new Vector<>();
	
	public static int addRoom(Room room) throws SQLException, ClassNotFoundException {
		int status = 0;
		
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");

		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO room (roomNo, roomType, price, occupied)"
				+ "VALUES(?, ?, ?, ?)"
				);
			
			
		ps.setInt(1, room.getRoomNo());
		ps.setString(2, room.getRoomType());
		ps.setDouble(3, room.getPrice());
		ps.setBoolean(4, room.isOccupied());

		status = ps.executeUpdate();
			
		connection.close();
		return status;
	}
	
	//update	
	public static int updateRoom(Room room) throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"UPDATE room"
				+ " SET roomType = ?, price = ?"
				+ " WHERE roomNo = ? "
				);
		
		ps.setString(1, room.getRoomType());
		ps.setDouble(2, room.getPrice());
		ps.setInt(3, room.getRoomNo());
		
		int status = ps.executeUpdate();
		
		return status;
	}
	
	//delete	
	public static int deleteRoom(int roomNo) throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"DELETE FROM room"
				+ " WHERE roomNo = ? "
				);
		
		ps.setInt(1, roomNo);
		
		int status = ps.executeUpdate();
		
		return status;
	}
	
	//display all
	public static DefaultTableModel displayRooms() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");
		
		PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM room"
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
		
	//display individual room. Invoked in displayRooms()
	public static void displayRoom(Room room) {
		System.out.println("\nRoom ID: " + room.getUniqueID());
		System.out.println("Number: " + room.getRoomNo());
		System.out.println("Type: " + room.getRoomType());
		System.out.println("Price: " + room.getPrice());
		System.out.println("Occupied: " + room.isOccupied());
	}
	
	//display by max price
	public static Vector<Room> displayRooms(double maxPrice) {
		Vector<Room> temp = new Vector<>(); //hold query result
		
		for (Room room: rooms) {
			if (room.getPrice() <= maxPrice) {
				temp.add(room); //add into temp
			}
		}
		
		return temp; //return result
	}
	
	//display by room type
	public static Vector<Room> displayRooms(String roomType){
		Vector<Room> temp = new Vector<>();
		
		for (Room room: rooms) {
			if (room.getRoomType() != null && room.getRoomType().toLowerCase().contains(roomType.toLowerCase())) {
				temp.add(room);
			}
		}
		return temp;
	}
	
	/* This function show the room in vector, which will be passed to several JComboBox at view.gui
	 * The argument decide whether to query the DB for unoccupied rooms only (occupied = false)
	 * or all the available room.
	 */
	public static Vector<String> roomList(boolean unOccupiedOnly) throws ClassNotFoundException, SQLException {
		Vector<String> rooms = new Vector<>();
		
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");		

		PreparedStatement ps;
		
		if (unOccupiedOnly) {
			ps = connection.prepareStatement(
					"SELECT roomNo FROM room "
					+ "WHERE occupied = false"
					);
		} else {
			ps = connection.prepareStatement(
					"SELECT roomNo FROM room"
					);
		}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) { //while there is next row
				rooms.add(rs.getString(1));			
		}				
		return rooms;		
	}
	
	/*This function will change the occupied attribute of room to either true or false
	 * The argument isOccupied is used to determine the occupied property
	 * */
	public static void updateOccupied(boolean isOccupied, int roomNo) throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");		
		
		PreparedStatement ps;
		
		if (isOccupied) { //occupied = true
			ps = connection.prepareStatement(
					"UPDATE room SET occupied=true"
					+ " WHERE roomNo = ?"
					);
		
			ps.setInt(1, roomNo);
		} else { //occupied = false
			ps = connection.prepareStatement(
					"UPDATE room SET occupied = false"
					+ " WHERE roomNo = ?"
					);
		
			ps.setInt(1, roomNo);
		}			
		
		if (ps != null) {
			ps.executeUpdate();
		}
	}
	
	public static double getRoomPrice(int roomNo) throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_hrms", "root", "HTC1x2012");		
		
		PreparedStatement ps = connection.prepareStatement("SELECT price FROM room "
										     			 + " WHERE roomNo = ?");
		
		ps.setInt(1, roomNo);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		double price = rs.getDouble(1);		
		
		return price;		
	}
}