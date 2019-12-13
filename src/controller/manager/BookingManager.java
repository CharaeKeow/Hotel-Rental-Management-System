package controller.manager;

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
			
			if (temp != null && temp.getBookingID() == booking.getBookingID()) {
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
			if (b.getBookingID() == bookingID) {
				booking = b;
				break;
			}
		}
		return bookings.remove(booking);
	}
	
	//Return booking object to be updated
	public static Booking getBooking(int bookingID) {
		Booking temp = null;
		
		for (int i = 0; i < bookings.size(); i++) {
			temp = bookings.get(i);
			
			if (temp != null && temp.getBookingID() == bookingID) {
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
		System.out.println("\nBooking ID: " + booking.getBookingID());
		System.out.println("Customer ID: " + booking.getCustomer());
		System.out.print("Rooms: ");
		displayBookedRooms(booking);
		System.out.println("\nStart date: " + booking.getStart());
		System.out.println("Duration: " + booking.getDuration());
		System.out.println("Total: " + booking.getTotal());
		System.out.println("Breakfast included: " + booking.isHasBreakfast());
	}
	
}
