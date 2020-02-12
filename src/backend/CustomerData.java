package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * 
 * @author Cemil Camci (line 1-89)
 * @author Dominik Krenz (line 89-143)
 * @author Satoakis Eitschberger (line 143+)
 *
 */
public class CustomerData {

	public String customerID;
	public String lastName, firstName, street, location;
	public String number;
	public String zipCode;
	
	/**
	 * creates an instance of a new customer
	 * 
	 * @param lastName of the customer
	 * @param firstName of the customer
	 * @param street the customer lives in
	 * @param number of the customers house
	 * @param zipCode of the city the customer lives in
	 * @param location the city the customer lives in
	 */
	public CustomerData(String lastName, String firstName, String street, String number, String zipCode, String location) {
		this.customerID = this.generateCustomerID(zipCode);
		this.lastName = lastName;
		this.firstName = firstName;
		this.street = street;
		this.number = number;
		this.zipCode = zipCode;
		this.location = location;
	}
	
	/**
	 * creates an instance of an existing customer
	 * 
	 * @param customerID the ID of the customer
	 * @param lastName of the customer
	 * @param firstName of the customer
	 * @param street the customer lives in
	 * @param number of the customers house
	 * @param zipCode of the city the customer lives in
	 * @param location the city the customer lives in
	 */
	public CustomerData(String customerID, String lastName, String firstName, String street, String number, String zipCode, String location) {
		this.customerID = customerID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.street = street;
		this.number = number;
		this.zipCode = zipCode;
		this.location = location;
	}
	
	/**
	 * saves customer data in customer_data.txt
	 */
	public void saveCustomerData() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/resources/customer_data.txt", true)));
			writer.newLine();
			
			writer.write(this.customerID + " "
					+ this.lastName + " "
					+ this.firstName + " "
					+ this.street + " "
					+ this.number + " "
					+ this.zipCode + " "
					+ this.location);
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param zipCode used to generate a unique ID
	 * @return a unique ID
	 */
	public String generateCustomerID(String zipCode) {
		String newID = null;
		boolean unique = false;
		
		while(!unique) {
			double randomNumber = Math.random() * 1000;
			newID = zipCode + Integer.toString((int) randomNumber);
			
			try {
				String fileName = null;
				String os = System.getProperty("os.name").toLowerCase();
				
				if (os.contains("win")){ //windows
					fileName = "src\\resources\\customer_data.txt";
				}
				else if (os.contains("osx")){ //apple
				}      
				else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){ //linux/unix
					fileName = "src/resources/customer_data.txt";
				}
				
				BufferedReader reader = new BufferedReader(new FileReader(fileName));
				reader.readLine();
				String currentLine = reader.readLine();
				
				if (currentLine == null) {
					unique = true;
					break;
				}
				
				while(currentLine != null) {
					String compareID = currentLine.split(" ")[0];
					
					if (newID.equals(compareID)) {
						unique = false;
						break;
					}
					unique = true;
					currentLine = reader.readLine();
				}
				reader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newID;
	}
	
	/**
	 * 
	 * @param searchIdPrefix prefix of some customer ID
	 * @return a list of all customers that have an ID which starts with the given prefix
	 */
	public static ArrayList<CustomerData> searchCustomer(String searchIdPrefix) {
		ArrayList<CustomerData> customers = new ArrayList<CustomerData>();
		
		try {
			String fileName = null;
			String os = System.getProperty("os.name").toLowerCase();
			
			if (os.contains("win")){ //windows
				fileName = "src\\resources\\customer_data.txt";
			}
			else if (os.contains("osx")){ //apple
			}      
			else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){ //linux/unix
				fileName = "src/resources/customer_data.txt";
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			reader.readLine();
			String currentLine = reader.readLine();
			
			while(currentLine != null) {
				String[] compareData = currentLine.split(" ");
				String compareID = compareData[0];
				
				if (compareID.startsWith(searchIdPrefix)) {
					CustomerData customer = new CustomerData(compareData[0], //ID
							compareData[1], //last name
							compareData[2], //first name
							compareData[3], //street
							compareData[4], //number
							compareData[5], //zip code
							compareData[6]); //location
					
					customers.add(customer);
				}
				currentLine = reader.readLine();
			}
			reader.close();
			
			return customers;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public String getCustomerID() {
		return this.customerID;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getStreet() {
		return this.street;
	}
	
	public String getNumber() {
		return this.number;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	
	public String getLocation() {
		return this.location;
	}
	
}
