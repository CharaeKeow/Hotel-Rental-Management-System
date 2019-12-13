package view.console;

import java.util.Scanner;
//import java.util.Vector;

import controller.manager.CustomerManager;
import model.Customer;

public class CustomerView extends View {

	@Override
	void displayOption() {
		System.out.println("Manage Customers");
		System.out.println("================");
		System.out.println("1. Add customer");
		System.out.println("2. Update customer");
		System.out.println("3. Delete customer");
		System.out.println("4. Display all customer");
		System.out.println("5. Back to main menu");
	}

	@Override
	void processOption(Scanner scanner, int choice) {
		if (choice == 1) {
			
			System.out.println("Add customer");
			System.out.println("============");
			
			Customer customer  = new Customer();			
			
			scanner.nextLine();
			System.out.println("\nPlease enter the customer details");
			
			System.out.println("Name: ");
			customer.setName(scanner.nextLine());
			
			System.out.println("Email: ");
			customer.setEmail(scanner.nextLine());
			
			System.out.println("Phone Number");
			customer.setPhoneNo(scanner.nextLine());
			
			System.out.println("IC Number:");
			customer.setIcNum(scanner.nextLine());
			
			if (CustomerManager.addCustomer(customer) != 0) {
				System.out.println("Successfully added a new customer");
			} else {
				System.out.println("Unseccessful operation :(");
			}
			
		} else if (choice == 2) {
			
			System.out.println("Update customer");
			System.out.println("===============");
			
			scanner.nextLine();
			
			System.out.println("\nPlease enter the ID of the customer that you wanna update");
			Customer customer = new Customer(scanner.nextInt());
			
			System.out.println("\nPlease enter the new customer's details");
			
			scanner.nextLine();
			
			System.out.println("Name: ");
			customer.setName(scanner.nextLine());
			
			System.out.println("Email: ");
			customer.setEmail(scanner.nextLine());
			
			System.out.println("Phone Number");
			customer.setPhoneNo(scanner.nextLine());
			
			System.out.println("IC Number:");
			customer.setIcNum(scanner.nextLine());
			
			if (CustomerManager.updateCustomer(customer)) {
				System.out.println("Update successful!");
				System.out.println("The new updated customers list are:\n");
				CustomerManager.displayCustomers();
			} else {
				System.out.println("Update error"); //convert this to proper error handling later
			}
			
		} else if (choice == 3) {
			
			System.out.println("Delete customer");
			System.out.println("===============");
			
			System.out.println("\nPlease enter the ID of the customer that you wanna update");
			
			scanner.nextLine();
			System.out.println("Customer ID: ");
			
			CustomerManager.deleteCustomer(scanner.nextInt());
			
		} else if (choice == 4 ) {
			CustomerManager.displayCustomers();
		}
		displayOption();
	}
	
	public static void displayCustomer(Customer customer) {
		System.out.println("Customer ID: " + customer.getUniqueID());
		System.out.println("Name: " + customer.getName());
		System.out.println("IC No: " + customer.getIcNum());
		System.out.println("Email: " + customer.getEmail());
		System.out.println("Phone Number: " + customer.getPhoneNo());		
	}	
}
