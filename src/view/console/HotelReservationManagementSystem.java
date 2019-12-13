package view.console;

import java.util.Scanner;

import controller.manager.CustomerManager;
import controller.manager.RoomManager;
import model.Customer;
import model.Room;

public class HotelReservationManagementSystem extends View {
	public static void main (String[] args) {
		
		//*readily adding several customers and rooms for testing
				Customer customer  = new Customer();		
				customer.setName("Hariz");
				customer.setEmail("hariz_haraz@gmail.com");
				customer.setPhoneNo("01110906039");
				customer.setIcNum("970202116612");
				
				Customer customer2 = new Customer();
				customer2.setName("Nur");
				customer2.setEmail("nur_iman@gmail.com");
				customer2.setPhoneNo("0191243039");
				customer2.setIcNum("990606066699");
				
				Customer customer3 = new Customer();
				customer3.setName("Aiman");
				customer3.setEmail("skyman_manman@gmail.com");
				customer3.setPhoneNo("0100110111");
				customer3.setIcNum("991010100011");
				
				Customer customer4 = new Customer();
				customer4.setName("Naufal");
				customer4.setEmail("naufal_ms@gmail.com");
				customer4.setPhoneNo("0177899876");
				customer4.setIcNum("991212122211");
				
				Customer customer5 = new Customer();
				customer5.setName("Hopkin");
				customer5.setEmail("hopkin@gmail.com");
				customer5.setPhoneNo("999");
				customer5.setIcNum("970310035573");
				
				CustomerManager.addCustomer(customer);
				CustomerManager.addCustomer(customer2);
				CustomerManager.addCustomer(customer3);
				CustomerManager.addCustomer(customer4);
				CustomerManager.addCustomer(customer5);
				
				Room room  = new Room();		
				room.setRoomNo(401);
				room.setRoomType("Single");
				room.setPrice(60);
				
				Room room2 = new Room();		
				room2.setRoomNo(402);
				room2.setRoomType("Double");
				room2.setPrice(90);
				
				Room room3 = new Room();		
				room3.setRoomNo(403);
				room3.setRoomType("Triple");
				room3.setPrice(130);
				
				Room room4 = new Room();		
				room4.setRoomNo(404);
				room4.setRoomType("Queen");
				room4.setPrice(180);
				
				Room room5 = new Room();		
				room5.setRoomNo(405);
				room5.setRoomType("King");
				room5.setPrice(300);
				
				RoomManager.addRoom(room);
				RoomManager.addRoom(room2);
				RoomManager.addRoom(room3);
				RoomManager.addRoom(room4);
				RoomManager.addRoom(room5);
				
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
	void processOption(Scanner scanner, int choice) {
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
