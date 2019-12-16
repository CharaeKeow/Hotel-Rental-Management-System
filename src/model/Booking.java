package model;

import java.util.Vector;

public class Booking extends Model {
	private static int BOOKING_ID; //auto increment
	private Customer customer;
	private Vector<Room> rooms = new Vector<>(); //one customer can book more than one room. So, vectors
	private long start; //auto set current time
	private int duration;
	private double total; //calculate after end
	private boolean hasBreakfast;
	
	public Booking(Customer customer, Vector<Room> rooms) {
		super(++BOOKING_ID);
		this.customer = customer;
		this.rooms = rooms;
		this.start = System.currentTimeMillis(); //set start to current time
	}
	
	public Booking(int bookingID) {
		//Booking.BOOKING_ID = bookingID;
		super(bookingID);
	}
	
	/*
	public int getBookingID() {
		return ;
	}*/
	
	public String getCustomer() {
		return customer.getIcNum();
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Vector<Room> getRooms() {
		return rooms;
	}
	
	public void setRoom(Vector<Room> rooms) {
		this.rooms = rooms;
	}
	
	public long getStart() {
		return start;
	}
	
	public void setStart(long start) {
		this.start = start;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		if (duration > 0) { //validate valid date
			this.duration = duration;
			// * room.getPrice();
		}
	}
	
	public double getTotal() {
		return total;
	}
	
	//total setter is not needed as it could be derived
	
	public boolean isHasBreakfast() {
		return hasBreakfast;
	}
	
	public void setHasBreakfast(boolean hasBreakfast) {
		this.hasBreakfast = hasBreakfast;
	}
}
