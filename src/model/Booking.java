package model;

//import java.sql.Date;
import java.util.Date;

public class Booking extends Model {
	private static int BOOKING_ID; //auto increment
	private int customerIcNo;
	//private Vector<Room> rooms = new Vector<>(); //one customer can book more than one room. So, vectors
	private int roomNo;
	private Date start; //auto sedt current time
	private int duration;
	private double total; //calculate after end
	private boolean hasBreakfast;
	
	public Booking(int customerIcNo, int roomNo) {
		super(++BOOKING_ID);
		this.customerIcNo = customerIcNo;
		this.roomNo = roomNo;
		 //set start to current time
	}
	
	public Booking(int bookingID) {
		//Booking.BOOKING_ID = bookingID;
		super(bookingID);
	}
	
	/*
	public int getBookingID() {
		return ;
	}*/
	
	public int getCustomerIcNo() {
		return customerIcNo;
	}
	
	public void setCustomerIcNo(int customerIcNo) {
		this.customerIcNo = customerIcNo;
	}
	
	/*
	public Vector<Room> getRooms() {
		return room;
	}
	
	public void setRoom(Vector<Room> rooms) {
		this.room = rooms;
	} */
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
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

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoom(int roomNo) {
		this.roomNo = roomNo;
	}
}
