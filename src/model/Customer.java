package model;

public class Customer extends Model {
	private static int LATEST_ID;
	private String name;
	private String email;
	private String phoneNo;
	private String icNum;
	
	public Customer() {
		super(++LATEST_ID); //preincrement because first id is 1
	}
	 
	public Customer(int customerID) { //for update
		super(customerID);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getIcNum() {
		return icNum;
	}
	public void setIcNum(String icNum) {
		this.icNum = icNum;
	}
}
