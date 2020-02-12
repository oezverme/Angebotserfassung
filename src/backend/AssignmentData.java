package backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Samuel Holderbach (line 1-55)
 * @author Enes Oezver (line 55+)
 *
 */
public class AssignmentData {

	public CustomerData customer;
	public ArrayList<String> materialCost, workCost;
	public String description, totalCost;
	
	/**
	 * 
	 * @param customer that ordered the assignment
	 * @param description of the assignment
	 * @param materialCost list that contains all required materials and associated costs
	 * @param workCostlist that contains required staff and machines and associated costs
	 * @param totalCost of the assignment
	 */
	public AssignmentData(CustomerData customer, String description, ArrayList<String> materialCost, ArrayList<String> workCost, String totalCost) {
		this.customer = customer;
		this.description = description;
		this.materialCost = materialCost;
		this.workCost = workCost;
		this.totalCost = totalCost;
	}
	
	/**
	 * saves the assignment data in a separate text-file
	 */
	public void saveAssignmentData() {
		String time = Long.toString(System.currentTimeMillis());
		
		String fileName = null;
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.contains("win")){ //windows
			fileName = "output\\assignment_data\\" + this.customer.getLastName() + "_"
					+ this.customer.getCustomerID() + "_" + time + ".txt";
		}
		else if (os.contains("osx")){ //apple
		}      
		else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){ //linux/unix
			fileName = "output/assignment_data/" + this.customer.getLastName() + "_"
					+ this.customer.getCustomerID() + "_" + time + ".txt";
		}
		
		File saveData = new File(fileName);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(saveData));
			
			writer.write("Kunden-ID: " + this.customer.getCustomerID());
			writer.newLine();
			writer.write("Nachname: " + this.customer.getLastName());
			writer.newLine();
			writer.write("Vorname: " + this.customer.getFirstName());
			writer.newLine();
			writer.write("Stra\u00DFe: " + this.customer.getStreet());
			writer.newLine();
			writer.write("Nummer: " + this.customer.getNumber());
			writer.newLine();
			writer.write("PLZ: " + this.customer.getZipCode());
			writer.newLine();
			writer.write("Ort: " + this.customer.getLocation());
			writer.newLine();
			writer.newLine();
			
			writer.write("Beschreibung: ");
			writer.newLine();
			writer.write(this.description);
			writer.newLine();
			writer.newLine();
			
			double totalMaterialCost = 0.0;
			writer.write("Materialkosten:");
			writer.newLine();
			
			for(int i = 0; i < this.materialCost.size(); i += 2) {
				//material at index i, belonging cost at index i+1
				writer.write(this.materialCost.get(i) + " " + this.materialCost.get(i+1));
				totalMaterialCost += Double.parseDouble(this.materialCost.get(i+1));
				writer.newLine();
			}
			writer.write("Materialkosten (gesamt): " + Double.toString(totalMaterialCost));
			writer.newLine();
			writer.newLine();
			
			double totalWorkCost = 0.0;
			writer.write("Arbeitskosten:");
			writer.newLine();
			
			for(int i = 0; i < this.workCost.size(); i += 4) {
				//work at index i, hour count at index i+1, cost per hour at index i+2, cost at index i+3
				writer.write(this.workCost.get(i) + " "
							+ this.workCost.get(i+1) + "h" + " "
							+ this.workCost.get(i+2) + "EUR/h" + " "
							+ this.workCost.get(i+3) + "EUR");
				totalWorkCost += Double.parseDouble(this.workCost.get(i+3));
				writer.newLine();
			}
			writer.write("Arbeitskosten (gesamt): " + Double.toString(totalWorkCost));
			writer.newLine();
			writer.newLine();
			
			writer.write(this.totalCost);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
