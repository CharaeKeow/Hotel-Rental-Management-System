package view.console;

import java.util.Scanner;
//import java.util.Vector;
import java.util.Vector;

import controller.manager.BookingManager;
import controller.manager.CustomerManager;
import controller.manager.RoomManager;
import model.Booking;
import model.Customer;
import model.Room;

public class BookingView extends View {
//	private static Vector<Booking> bookings = new Vector<>();

	@Override
	void displayOption() {
		System.out.println("Manage Bookings");
		System.out.println("===============");
		System.out.println("1. Add booking");
		System.out.println("2. Update booking");
		System.out.println("3. Delete booking");
		System.out.println("4. Display all bookings");
		System.out.println("5. Back to main menu");
	}

	@Override
	void processOption(Scanner scanner, int choice) {
		if (choice == 1) {
			
			System.out.println("Add Booking");
			System.out.println("===========");
			
			scanner.nextLine();
			//Display all customers			
			System.out.println("The list of all registered customers are: ");
			CustomerManager.displayCustomers();			
			
			//Get a customer by IC number
			System.out.println("Select a customer by ID: ");
			Customer customer = CustomerManager.selectCustomer(scanner.nextInt());
						
			//Display unoccupied rooms
			System.out.println("The list of available rooms are: ");
			RoomManager.getAvailableRooms();
			
			Vector<Room> rooms = new Vector<>(); //new empty vectors
			
			System.out.println("\nHow many rooms?");
			int num = scanner.nextInt();
			
			for (int i = 0; i < num; i++) {
				System.out.println("Select a room: ");
				Room room = RoomManager.selectRoom(scanner.nextInt());
				rooms.add(room);
			}	
			
			Booking booking = new Booking(customer, rooms);		
			
			System.out.println("Set the duration: ");
			booking.setDuration(scanner.nextInt());
			
			System.out.println("Breakfast included?");
			booking.setHasBreakfast(scanner.nextBoolean());		
			
			if (BookingManager.addBooking(booking) != 0) {
				System.out.println("Succesfully added a new booking!");
			} else {
				System.out.println("Unsuccessful operation :(");
			}
			
		} else if (choice == 2) {
			
			System.out.println("Update Booking");
			System.out.println("==============");
			
			scanner.nextLine();
			
			System.out.println("\nPlease enter the ID of the booking that you wanna update");
			Booking booking = BookingManager.getBooking(scanner.nextInt());
			
			System.out.println("\nWhat does customer want to update?");
			
			int option;
			do {
				
				System.out.println("1. Add room");
				System.out.println("2. Delete room");
				System.out.println("3. Duration");
				System.out.println("4. Breakfast");
				System.out.println("5. Exit update");
				
				option = scanner.nextInt();
				
				if (option == 1) {
					
					Vector<Room> rooms = booking.getRooms(); //new empty vectors
					
					System.out.println("\nHow many rooms?");
					int num = scanner.nextInt();
					
					for (int i = 0; i < num; i++) {
						System.out.println("Select a room: ");
						Room room = RoomManager.selectRoom(scanner.nextInt());
						rooms.add(room);
					}
					
					booking.setRoom(rooms);
					
				} else if (option == 2) {					
										
					Vector<Room> rooms = booking.getRooms(); //rooms
					
					System.out.println("\nHow many rooms?");					
					int num = scanner.nextInt();
					
					if (num > rooms.size()) {
						System.out.println("Invalid! Cannot delete all rooms!");
						break;
					}
					
					for (int i = 0; i < num; i++) {
						System.out.println("Enter the room no: ");
						Room room = RoomManager.selectRoom(scanner.nextInt());
						rooms.remove(room);
					} 
					
					booking.setRoom(rooms);
					
				} else if (option == 3) {
					System.out.println("Enter the new duration: ");
					booking.setDuration(scanner.nextInt());
				} else if (option == 4) {
					System.out.println("Breakfast included?");
					booking.setHasBreakfast(scanner.nextBoolean());
				}
			
			} while (option != 5);
			
			scanner.nextLine();
			
			if (BookingManager.updateBooking(booking)) {
				System.out.println("Update successful!");
				System.out.println("The new updated customers list are:\n");
				BookingManager.displayBookings();
			} else {
				System.out.println("Update error"); //convert this to proper error handling later
			}
			
		} else if (choice == 3) {
			
			System.out.println("Delete Booking");
			System.out.println("==============");
			
			System.out.println("\nEnter the ID of the booking that you wanna delete");
			
			scanner.nextLine();
			System.out.println("Booking ID: ");
			
			BookingManager.deleteBooking(scanner.nextInt());
			
		} else if (choice == 4) {
			
			System.out.println("Display all bookings");
			System.out.println("====================");
			
			BookingManager.displayBookings();
			
		}
		displayOption();
	}

}
