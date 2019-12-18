package view.console;

import java.sql.SQLException;
import java.util.Scanner;

import controller.manager.CustomerManager;
import controller.manager.RoomManager;
import model.Customer;
import model.Room;

public class HotelReservationManagementSystem extends View {
	public static void main (String[] args) throws ClassNotFoundException, SQLException {
		
		//*readily adding several customers and rooms for testing
				
				
				//end of readily added objects
		
		Scanner scanner = new Scanner(System.in);
		HotelReservationManagementSystem hrms = new HotelReservationManagementSystem();
		
		hrms.displayOption();
		hrms.selectOption(scanner, 4);
		scanner.close();

	}
	
	public void displayOption() {
		System.out.println("Welcome to Hotel Reservation Management System");
		System.out.println("==============================================");
		System.out.println("1. Manage Customer");
		System.out.println("2. Manage Booking");
		System.out.println("3. Manage Room");
		System.out.println("4. Exit");
	}

	@Override
	void processOption(Scanner scanner, int choice) throws ClassNotFoundException, SQLException {
		if (choice == 1) {
			
			CustomerView cv = new CustomerView();
			cv.displayOption();
			cv.selectOption(scanner, 5);
			displayOption();
		
		} else if (choice == 2) {
			BookingView bv = new BookingView();
			bv.displayOption();
			bv.selectOption(scanner, 5);
			displayOption();
			
		} else if (choice == 3) {
			
			RoomView rv = new RoomView();
			rv.displayOption();
			rv.selectOption(scanner, 7);
			displayOption();
			
		}
		
	}
}
