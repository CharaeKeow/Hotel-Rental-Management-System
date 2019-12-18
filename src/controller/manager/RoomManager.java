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

		//Check if the roomNo is already exist in DB.
		PreparedStatement ps2 = connection.prepareStatement(
				"SELECT roomID FROM room WHERE roomNo = ?"
				);
		
		ps2.setInt(1, room.getRoomNo());
		ResultSet rs = ps2.executeQuery();
		
		if (rs.next()) { //roomNo already exist in DB
			status = -1;
		} else {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO room (roomNo, roomType, price, occupied)"
					+ "VALUES(?, ?, ?, ?)"
					);
			
			
			ps.setInt(1, room.getRoomNo());
			ps.setString(2, room.getRoomType());
			ps.setDouble(3, room.getPrice());
			ps.setBoolean(4, room.isOccupied());

			status = ps.executeUpdate();
			
		}	
		connection.close();
		return status;
	}
	
	//update	
	public static boolean updateRoom(Room room) {
		int index = -1;
		boolean success = false;
		
		for (int i = 0; i < rooms.size(); i++) {
			Room temp = rooms.get(i); //return customer at this index
			
			if (temp != null && temp.getUniqueID() == room.getUniqueID()) {
				index = i;
				break;
			}
		}
		
		if (index < rooms.size()) {
			rooms.set(index, room);
			success = true;
		}
		
		return success;
	}
	
	//delete	
	public static boolean deleteRoom(int roomID) {
		Room room = null; //empty room
		
		for (Room r : rooms) {
			if (r.getUniqueID() == roomID) {
				room = r; //set room = that room
				break;
			}
		}
		return rooms.remove(room); //remove that customer in vector
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
	
	//get rooms that are not occupied
	public static void getAvailableRooms() {
		
		for (Room room : rooms) {
			if (!room.isOccupied()) {
				displayRoom(room);	
			}
		}
		
	}
	
	//assign specific room by passing roomNo. Return that room
	//Used for adding and updating room only
	public static Room selectRoom(int roomNo) {
		Room temp = null;
		
		for (Room room : rooms) {
			if (room.getRoomNo() == roomNo) {
				if (!room.isOccupied()) {
					room.setOccupied(true);
				} else { //for deleting room during update
					room.setOccupied(false);
				}
				
				
				//update the room's isOccupied first before return the new object
				temp = room; //temp = that room
				break;
			}
		}
		
		return temp; //return that room
	}
}