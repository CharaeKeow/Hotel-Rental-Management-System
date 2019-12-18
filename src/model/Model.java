package model;

abstract class Model {
	protected int uniqueID; //for Customer, Room, and Booking
	
	public Model(int uniqueID) {
		this.uniqueID = uniqueID;
	}
	
	public int getUniqueID() {
		return uniqueID;
	}
}
