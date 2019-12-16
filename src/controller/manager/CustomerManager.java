package controller.manager;

import java.util.Vector;
import model.Customer;

public class CustomerManager {
	private static Vector<Customer> customers = new Vector<>();
	
	public static int addCustomer(Customer customer) {
		int status = 0;
		int size = customers.size();
		customers.add(customer);
		
		if (customers.size() > size) { //add succeed
			status++; //status != 0
		}
		
		return status;
	}
	
	public static Vector<Customer> displayCustomers() {
		for (Customer customer : customers) {
			displayCustomer(customer);
		}
		return customers;
	}
	
	public static void displayCustomer(Customer customer) {
		System.out.println("\nCustomer ID: " + customer.getUniqueID());
		System.out.println("Name: " + customer.getName());
		System.out.println("Email: " + customer.getEmail());
		System.out.println("Phone number: " + customer.getPhoneNo());
		System.out.println("IC Number: " + customer.getIcNum());
	}
	
	public static boolean updateCustomer(Customer customer) {
		int index = -1;
		boolean success = false;
		
		for (int i = 0; i < customers.size(); i++) {
			Customer temp = customers.get(i); //return customer at this index
			
			if (temp != null && temp.getUniqueID() == customer.getUniqueID()) {
				index = i;
				break;
			}
		}
		
		if (index < customers.size()) { //valid index
			customers.set(index, customer); //update the customer object at the specified index
			success = true;
		}
		
		return success;
	}
	
	public static boolean deleteCustomer(int customerID) {
		Customer customer = null;
		
		for (Customer c : customers) {
			if (c.getUniqueID() == customerID) {
				customer = c; //set customer = null
				break;
			}
		}
		return customers.remove(customer); //remove that customer in vector
	}
	
	//select specific customer in the customer list
	//select by icNum
	public static Customer selectCustomer(String icNum) {
		Customer temp = null;
		
		for (Customer customer : customers) {
			if (customer != null && customer.getIcNum().equalsIgnoreCase(icNum)) {
				temp = customer;
			}
		}
		
		return temp;
	}
	
	//select by id
	public static Customer selectCustomer(int customerID) {
		Customer temp = null;
		
		for (Customer customer : customers) {
			if (customer != null && customer.getUniqueID() == customerID) {
				temp = customer;	
			}
		}
		return temp;
	}
}
