package model;

abstract class Model {
	protected int uniqueID; //for Customer and Room
	
	public Model(int uniqueID) {
		this.uniqueID = uniqueID;
	}
	
	public int getUniqueID() {
		return uniqueID;
	}
}
