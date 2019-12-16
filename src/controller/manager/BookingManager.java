package controller.manager;

import java.sql.Timestamp;
import java.util.Vector;

import model.Booking;
import model.Room;

public class BookingManager {
	private static Vector<Booking> bookings = new Vector<>();
	
	public static int addBooking(Booking booking) {
		int status = 0;
		int size = bookings.size();
		bookings.add(booking);
		
		if (bookings.size() > size) {
			status++;
		}
		
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
		
		Vector<Room> rooms = booking.getRooms();
		
		//Booking done, so set all the rooms for this booking
		//to occupied == false
		for (Room room : rooms) {
			room.setOccupied(false);
		}
		
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
	
	public static void displayBookings() {
		for (Booking booking : bookings) {
			displayBooking(booking);
		}
	}
	
	//display the rooms booked by that particular customer
	public static void displayBookedRooms(Booking booking) {
		Vector<Room> rooms = booking.getRooms();
		
		for (Room room : rooms) {
			System.out.print(" " + room.getRoomNo());
		}
		
	}
	
	public static void displayBooking(Booking booking) {
		System.out.println("\nBooking ID: " + booking.getUniqueID());
		System.out.println("Customer's IC: " + booking.getCustomer());
		System.out.print("Rooms: ");
		displayBookedRooms(booking);
		System.out.println();
		//System.out.println("\nStart date: " + booking.getStart());
		displayStart(booking); // display start time of that booking
		System.out.println("Duration: " + booking.getDuration());
		System.out.println("Total: RM" + calculateTotal(booking));
		System.out.println("Breakfast included: " + booking.isHasBreakfast());
	}
	
	public static void displayStart(Booking booking) {
		Timestamp ts = new Timestamp(booking.getStart());
		
		//Actually I think this could be obtained directly from DB
		System.out.print("Booking time: ");
		System.out.print(ts.toLocalDateTime().getDayOfWeek());
		System.out.print(" ");
		System.out.print(ts.toLocalDateTime().toLocalDate());
		System.out.println("");
	}
	
	//End booking
	public static double calculateTotal(Booking booking) {
		Vector<Room> rooms = booking.getRooms();
		double price = 0;
		
		for (Room room : rooms ) {
			price += room.getPrice();
		}
		
		if (booking.isHasBreakfast()) { //breakfast is RM 10
			price += 10;
		}
		
		return price * booking.getDuration();
	}
}
