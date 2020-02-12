package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implementation of HSQLDB database
 * 
 * NOT FINISHED YET
 * 
 * @author Enes Oezver (line 1-98)
 * 
 * @author Cemil Camci (line 98+)
 *
 */
public class Database {
	Connection con;
	String db;
	String user;
	String password;

	/**
	 * Constructor, connects with database
	 */
	public Database() {
		this.db = "jdbc:hsqldb:file:datenbank/data; shutdown=true";
		this.user = "root";
		this.password = "root";

		try {
			Class.forName("org.hsqldb.jdbcDriver");
			con = DriverManager.getConnection(db, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints all customer
	 */
	public void getAllCustomer() {
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM Customer";
			ResultSet res = stmt.executeQuery(sql);
			while (res.next()) {
				String cid = res.getString(1);
				String firstname = res.getString(2);
				String lastname = res.getString(3);
				String zip = res.getString(4);
				String city = res.getString(5);
				String street = res.getString(6);
				String streetNr = res.getString(7);
				System.out.println(cid + ", " + firstname + ", " + lastname + ", " + zip + ", " + city + ", " + street
						+ ", " + streetNr);
			}
			res.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Inserts a customer into database
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param zip
	 * @param city
	 * @param street
	 * @param streetNr
	 */
	public void insertCustomer(int cid, String firstname, String lastname, int zip, String city, String street,
			int streetNr) {
		PreparedStatement prep = null;
		try {
			String sql = "INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?)";
			prep = con.prepareStatement(sql);
			prep.setInt(1, cid);
			prep.setString(2, firstname);
			prep.setString(3, lastname);
			prep.setInt(4, zip);
			prep.setString(5, city);
			prep.setString(6, street);
			prep.setInt(7, streetNr);
			prep.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				prep.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Deletes customer with customer ID
	 * @param cid
	 */
	public void deleteCustomer(int cid) {
		Statement stmt = null;
		
		try {
			stmt = con.createStatement();
			String sql = "DELETE FROM Customer Where cid="+cid;
			stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * TODO:
	 * Searches a customer
	 * @param cid
	 * @param firstname
	 * @param lastname
	 */
	public void searchCustomer(String cid, String firstname, String lastname) {
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if(cid != null) {
			try {
			String sql = "SELECT CID FROM Customer WHERE CID="+cid;
			stmt.executeQuery(sql);
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(lastname != null) {
			
		}else if(firstname != null) {
			
		} else {
			
		}
	}

	/**
	 * TODO
	 * exports database into csv
	 */
	public void tableToCSV() {
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT * INTO TEXT test.csv FROM Customer";
			stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	//For testing purposes only
	 
	public static void main(String[] args) {
		Database datenbank = new Database();
		datenbank.getAllCustomer();
		datenbank.insertCustomer(1, "'Enes'", "'Oezver'", 71067, "Sindelfingen", "'Unistrasse'", 38);
		datenbank.insertCustomer(2, "'Cemil'", "'Camci'", 71067, "Sindelfingen", "'Unistrasse'", 38);
		datenbank.getAllCustomer();
		System.out.println("---------");
		datenbank.deleteCustomer(1);
		datenbank.getAllCustomer();
	}
	*/
}
