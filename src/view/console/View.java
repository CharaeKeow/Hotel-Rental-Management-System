package view.console;

import java.sql.SQLException;
import java.util.Scanner;

public abstract class View {
	abstract void displayOption();
	
	final int selectOption(Scanner scanner, int maximum) throws ClassNotFoundException, SQLException {
		int choice;
		
		do {
			choice = 0;
			
			while (choice < 1 || choice > maximum) {
				System.out.println("Please enter an option");
				choice = scanner.nextInt();
				
				if (choice < 1 || choice > maximum) {
					System.out.println("Invalid option! Please try again!");
				}
			}
			processOption(scanner, choice);
		} while (choice != maximum);
		
		return choice;
	}

	abstract void processOption(Scanner scanner, int choice) throws ClassNotFoundException, SQLException;
}
