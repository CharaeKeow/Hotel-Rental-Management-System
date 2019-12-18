package view.console;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

import controller.manager.RoomManager;
import model.Room;

public class RoomView extends View{

	@Override
	void displayOption() {
		System.out.println("Manage Rooms");
		System.out.println("============");
		System.out.println("1. Add room");
		System.out.println("2. Update room");
		System.out.println("3. Delete room");
		System.out.println("4. Display all rooms");
		System.out.println("5. Display rooms by maximum price");
		System.out.println("6. Display rooms by type");
		System.out.println("7. Back to main menu");
	}

	@Override
	void processOption(Scanner scanner, int choice) throws ClassNotFoundException, SQLException {
		if (choice == 1) {
			
			System.out.println("Add room");
			Room room  = new Room();
			
			scanner.nextLine();
			System.out.println("\nPlease enter the room's details");
			System.out.println("=================================");
			
			System.out.println("Room number: ");
			room.setRoomNo(scanner.nextInt());
			
			scanner.nextLine();
			System.out.println("RoomType: ");
			room.setRoomType(scanner.nextLine());
			
			System.out.println("Price: ");
			room.setPrice(scanner.nextDouble());
			
			try {
				if (RoomManager.addRoom(room) != 0) {
					System.out.println("Successfully added a new room");
				} else {
					System.out.println("Unseccessful operation :(");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (choice == 2) {
			
			System.out.println("Update room");
			System.out.println("===========");
			
			scanner.nextLine();
			
			System.out.println("\nPlease enter the ID of the room that you wanna update");
			int roomID = scanner.nextInt();

			Room room = new Room(roomID); //pass id into new room
			
			System.out.println("\nPlease enter the new room's details");	
			
			System.out.println("Room No: ");
			room.setRoomNo(scanner.nextInt());
			
			scanner.nextLine();
			
			System.out.println("Room Type: ");
			room.setRoomType(scanner.nextLine());
			
			System.out.println("Price");
			room.setPrice(scanner.nextDouble());
			
			if (RoomManager.updateRoom(room)) {
				System.out.println("Update successful!");
				System.out.println("The new updated room list are: ");
				RoomManager.displayRooms();
			} else {
				System.out.println("Update error");
			}
			
		} else if (choice == 3) {
			
			System.out.println("Delete room");
			System.out.println("===========");
			
			System.out.println("\nPlease enter the ID of the customer that you wanna update");
			
			scanner.nextLine();
			System.out.println("Room ID: ");

			RoomManager.deleteRoom(scanner.nextInt());
			
		} else if (choice == 4) {
			
			System.out.println("Display all rooms");
			System.out.println("=================");
			RoomManager.displayRooms();
			
		} else if (choice == 5) {
			
			System.out.println("Display rooms by max price\n");
			System.out.println("==========================");
			
			System.out.println("Enter the max price: ");
			Vector<Room> rooms = RoomManager.displayRooms(scanner.nextDouble());
			
			for(Room room : rooms) {
				displayRoom(room);
			}
			
		} else if (choice == 6) {
			
			System.out.println("Display rooms by type");
			System.out.println("=====================");
			
			scanner.nextLine();
			System.out.println("Enter the type that you wanna see: ");
			Vector<Room> rooms= RoomManager.displayRooms(scanner.nextLine());
			
			for (Room room : rooms) {
				displayRoom(room);
			}
			
		}
		displayOption();
	}
	
	public static void displayRoom(Room room) {
		System.out.println("Room ID: " + room.getUniqueID());
		System.out.println("Number: " + room.getRoomNo());
		System.out.println("Type: " + room.getRoomType());
		System.out.println("Price: " + room.getPrice());
	}
}
