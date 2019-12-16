package controller.manager;

import java.util.Vector;

import model.Room;

public class RoomManager {
	private static Vector<Room> rooms = new Vector<>();
	
	public static int addRoom(Room room) {
		int status = 0;
		int size = rooms.size();
		rooms.add(room);
		
		if (rooms.size() > size) { //add succeed
			status++; //status != 0
		}
		
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
	public static void displayRooms() {
		for (Room room : rooms) {
			displayRoom(room);
		}
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