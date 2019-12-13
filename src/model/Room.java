package model;

public class Room extends Model {
	private static int LATEST_ID;
	private int roomNo;
	private String roomType;
    //private int capacity; //do we need this?
	private double price;
	private boolean occupied; //whether the room is free or not
	
	/*When newly created, all room are not occupied.
	 *This value will be changed later when the room
	 *is booked in  BookingManager class
	 */
	public Room() {
		super(++LATEST_ID);
		this.occupied = false; 
	} 
	
	public Room(int roomID) {
		super(roomID);
	}
	
	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
}
