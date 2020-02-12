package frontend;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import backend.AssignmentData;
import backend.CustomerData;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * 
 * @author Enes Oezver
 * @author Cemil Camci
 * @author Samuel Holderbach
 * @author Satoakis Eitschberger
 * 
 */
public class AssignmentFrame extends JFrame implements ActionListener, TableModelListener {

	private static final long serialVersionUID = 1L;
	public JTextField lastNameTF, firstNameTF, streetTF, numberTF, zipCodeTF, locationTF;
	public JLabel descLBL;
	public JTextPane descTP;
	private JTable materialTable, workTable;
	public DefaultTableModel materialTableModel, workTableModel;
	private JLabel totalCostLBL = new JLabel("Gesamtkosten: ");
	public double totalCost;
	public CustomerData currentCustomer = null;

	/**
	 * the frame to create an assignment for a new customer
	 */
	public AssignmentFrame() {
		this.initialize();
	}
	
	/**
	 * the frame to create an assignment for an existing customer
	 * 
	 * @param currentCustomer the customer to create an assignment for
	 */
	public AssignmentFrame(CustomerData currentCustomer) {
		this.initialize();
		this.currentCustomer = currentCustomer;
		this.lastNameTF.setText(currentCustomer.getLastName());
		this.firstNameTF.setText(currentCustomer.getFirstName());
		this.streetTF.setText(currentCustomer.getStreet());
		this.numberTF.setText(currentCustomer.getNumber());
		this.zipCodeTF.setText(currentCustomer.getZipCode());
		this.locationTF.setText(currentCustomer.getLocation());
	}
	
	public void initialize() {
		setResizable(false);
		this.setTitle("Auftragsmanager");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initMenu();
		initUI();
	}
	
	/**
	 * initializes the default menu bar at the top of the frame
	 * 
	 * @author Samuel Holderbach
	 * 
	 */
	public void initMenu() {
		JMenuBar menubar = new JMenuBar();

		JMenu offerMenu = new JMenu("Angebot");

		JMenuItem menuItemSave = new JMenuItem("Speichern");
		JMenuItem menuItemPrint = new JMenuItem("Drucken");

		JMenu newMenu = new JMenu("Neu");

		JMenuItem menuItemNewCustomer = new JMenuItem("Neuer Kunde");
		JMenuItem menuItemExCustomer = new JMenuItem("Nach Kunden suchen");

		JMenu System = new JMenu("System");

		JMenuItem menuItemLogOut = new JMenuItem("Log Out");

		offerMenu.add(newMenu);
		offerMenu.add(menuItemSave);
		offerMenu.add(menuItemPrint);

		newMenu.add(menuItemNewCustomer);
		newMenu.add(menuItemExCustomer);

		System.add(menuItemLogOut);

		menubar.add(offerMenu);
		menubar.add(System);

		setJMenuBar(menubar);
		
		menuItemNewCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
				AssignmentFrame frame = new AssignmentFrame();
				frame.setVisible(true);
			}
		});

		menuItemExCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
				SearchFrame frame = new SearchFrame();
				frame.setVisible(true);
			}
		});

		menuItemSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				saveData();
			}
		});
		
		menuItemLogOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
				LogInFrame frame = new LogInFrame();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * sets the design of the frame and actions that can be performed
	 * 
	 * @author Enes Oezver
	 * @author Cemil Camci
	 * @author Samuel Holderbach
	 * 
	 */
	public void initUI() {
		getContentPane().setLayout(null);
		
		JLabel lastNameLBL = new JLabel("Nachname:");
		lastNameLBL.setBounds(12, 12, 80, 16);
		getContentPane().add(lastNameLBL);
		
		this.lastNameTF = new JTextField();
		this.lastNameTF.setBounds(100, 10, 114, 20);
		getContentPane().add(this.lastNameTF);
		this.lastNameTF.setColumns(10);
		
		JLabel firstNameLBL = new JLabel("Vorname:");
		firstNameLBL.setBounds(240, 12, 70, 16);
		getContentPane().add(firstNameLBL);
		
		this.firstNameTF = new JTextField();
		this.firstNameTF.setBounds(318, 10, 114, 20);
		getContentPane().add(this.firstNameTF);
		this.firstNameTF.setColumns(10);
		
		JLabel streetLBL = new JLabel("Stra\u00DFe:");
		streetLBL.setBounds(12, 44, 55, 16);
		getContentPane().add(streetLBL);
		
		this.streetTF = new JTextField();
		this.streetTF.setBounds(100, 42, 114, 20);
		getContentPane().add(this.streetTF);
		this.streetTF.setColumns(10);
		
		JLabel numberLBL = new JLabel("Nr.:");
		numberLBL.setBounds(240, 44, 29, 16);
		getContentPane().add(numberLBL);
		
		this.numberTF = new JTextField();
		this.numberTF.setBounds(318, 42, 41, 20);
		getContentPane().add(this.numberTF);
		this.numberTF.setColumns(10);
		
		JLabel zipCodeLBL = new JLabel("PLZ:");
		zipCodeLBL.setBounds(12, 76, 55, 16);
		getContentPane().add(zipCodeLBL);
		
		this.zipCodeTF = new JTextField();
		this.zipCodeTF.setBounds(100, 74, 114, 20);
		getContentPane().add(this.zipCodeTF);
		this.zipCodeTF.setColumns(10);
		
		JLabel locationLBL = new JLabel("Ort:");
		locationLBL.setBounds(240, 76, 29, 16);
		getContentPane().add(locationLBL);
		
		this.locationTF = new JTextField();
		this.locationTF.setBounds(318, 74, 114, 20);
		getContentPane().add(this.locationTF);
		this.locationTF.setColumns(10);
		
		this.descLBL = new JLabel("Beschreibung:");
		this.descLBL.setBounds(12, 104, 85, 16);
		getContentPane().add(this.descLBL);
		
		JScrollPane descScrollPane = new JScrollPane();
		descScrollPane.setBounds(115, 106, 414, 41);
		getContentPane().add(descScrollPane);
		
		this.descTP = new JTextPane();
		descScrollPane.setViewportView(this.descTP);

		//initialize material table
		JLabel materialLBL = new JLabel("Materialkosten:");
		materialLBL.setBounds(12, 157, 93, 16);
		getContentPane().add(materialLBL);

		JScrollPane materialScrollPane = new JScrollPane();
		materialScrollPane.setBounds(12, 177, 188, 79);
		getContentPane().add(materialScrollPane);

		Object[][] row = { null, null };
		String[] materialTableColumns = { "Material", "Kosten[EUR]" };

		this.materialTableModel = new DefaultTableModel(materialTableColumns, 0) {
			private static final long serialVersionUID = 1L;
			
			Class[] columnTypes = new Class[] { String.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return this.columnTypes[columnIndex];
			}
		};

		this.materialTable = new JTable();
		this.materialTable.setCellSelectionEnabled(true);
		this.materialTable.setModel(this.materialTableModel);
		this.materialTable.getColumnModel().getColumn(0).setResizable(false);
		this.materialTable.getColumnModel().getColumn(1).setResizable(false);
		materialScrollPane.setViewportView(this.materialTable);

		JButton insertMaterial = new JButton("Einf\u00FCgen");
		insertMaterial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				materialTableModel.addRow(row);
			}
		});
		
		insertMaterial.setBounds(12, 257, 92, 26);
		getContentPane().add(insertMaterial);
		
		JButton deleteMaterial = new JButton("L\u00F6schen");
		deleteMaterial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				for (int i = 0; i < materialTable.getSelectedRows().length; i++) {
					materialTableModel.removeRow(materialTable.getSelectedRows()[i]);
				}
			}
		});
		
		deleteMaterial.setBounds(108, 257, 92, 26);
		getContentPane().add(deleteMaterial);

		//initialize work table
		JLabel workLBL = new JLabel("Arbeitskosten:");
		workLBL.setBounds(217, 157, 93, 16);
		getContentPane().add(workLBL);
		
		JScrollPane workScrollPane = new JScrollPane();
		workScrollPane.setBounds(217, 177, 312, 79);
		getContentPane().add(workScrollPane);

		String[] workTableColumns = { "Benennung", "Stunden", "Einzelpreis", "Kosten[EUR]" };
		
		this.workTableModel = new DefaultTableModel(workTableColumns, 0) {
			private static final long serialVersionUID = 1L;
			
			Class[] columnTypes = new Class[] { String.class, Double.class, Double.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return this.columnTypes[columnIndex];
			}
		};
		
		this.workTable = new JTable();
		this.workTable.setModel(this.workTableModel);
		this.workTable.getColumnModel().getColumn(0).setResizable(false);
		this.workTable.getColumnModel().getColumn(1).setResizable(false);
		this.workTable.getColumnModel().getColumn(2).setResizable(false);
		this.workTable.getColumnModel().getColumn(3).setResizable(false);
		this.workTable.setCellSelectionEnabled(true);
		workScrollPane.setViewportView(this.workTable);

		JButton insertWork = new JButton("Einf\u00FCgen");
		insertWork.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				workTableModel.addRow(row);
			}
		});
		
		insertWork.setBounds(217, 257, 92, 26);
		getContentPane().add(insertWork);

		JButton deleteWork = new JButton("L\u00F6schen");
		deleteWork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < workTable.getSelectedRows().length; i++) {
					workTableModel.removeRow(workTable.getSelectedRows()[i]);
				}
			}
		});
		
		deleteWork.setBounds(313, 257, 92, 26);
		getContentPane().add(deleteWork);
		
		/*
		 * calculates cost for one person or machine
		 * @author: Dominik Krenz
		 */
		
		this.workTableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent event) {
				int currentRow = workTable.getSelectedRow();
				
				if ((event.getColumn() == 1 || event.getColumn() == 2)
						&& workTableModel.getValueAt(currentRow, 1) != null
						&& workTableModel.getValueAt(currentRow, 2) != null) {
					
					double hrs = (double) workTableModel.getValueAt(currentRow, 1); //time in hours
					double prc = (double) workTableModel.getValueAt(currentRow, 2); //cost per hour
					
					if (workTableModel.isCellEditable(currentRow, 3)) {
						workTableModel.setValueAt(hrs * prc, currentRow, 3);
						workTableModel.fireTableCellUpdated(currentRow, 3);
					}
				}
			}
		});
		
		this.totalCostLBL.setBounds(118, 301, 161, 16);
		getContentPane().add(this.totalCostLBL);

		JButton calcTotalCost = new JButton("Berechnen");
		
		calcTotalCost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				calcTotalCost();
			}
		});
		
		calcTotalCost.setBounds(12, 297, 98, 26);
		getContentPane().add(calcTotalCost);
	}
	
	/**
	 * calculates the total cost for an assignment
	 * 
	 * @author Enes Oezver
	 * @author Cemil Camci
	 * 
	 */
	public void calcTotalCost() {
		this.totalCost = 0.0;
		
		for (int i = 0; i < this.materialTable.getModel().getRowCount(); i++) {
			if (this.materialTable.getModel().getValueAt(i, 1) != null) {
				this.totalCost = this.totalCost + (double) this.materialTable.getModel().getValueAt(i, 1);

			}
		}
		
		for (int i = 0; i < this.workTable.getModel().getRowCount(); i++) {
			if (this.workTable.getModel().getValueAt(i, 3) != null) {
				this.totalCost = this.totalCost + (double) this.workTable.getModel().getValueAt(i, 3);
			}
		}
		this.totalCostLBL.setText("Gesamtkosten: " + this.totalCost);
	}
	
	/**
	 * writes the customer and assignment data into a text file
	 * 
	 * @author Dominik Krenz
	 * 
	 */
	public void saveData() {
		CustomerData customer = null;
		
		if (this.currentCustomer == null) {
			customer = new CustomerData(this.lastNameTF.getText(), 
					this.firstNameTF.getText(),
					this.streetTF.getText(),
					this.numberTF.getText(),
					this.zipCodeTF.getText(),
					this.locationTF.getText());
			
			customer.saveCustomerData();
			
		} else {
			customer = this.currentCustomer;
		}
		
		ArrayList <String> materialTableContent = new ArrayList<>();
		for (int i = 0; i < this.materialTable.getModel().getRowCount(); i++) {
			for (int j = 0; j < this.materialTable.getModel().getColumnCount(); j++) {
				materialTableContent.add(this.materialTable.getModel().getValueAt(i, j).toString());
			}
		}
		
		ArrayList <String> workTableContent = new ArrayList<>();
		for (int i = 0; i < this.workTable.getModel().getRowCount(); i++) {
			for (int j = 0; j < this.workTable.getModel().getColumnCount(); j++) {
				workTableContent.add(this.workTable.getModel().getValueAt(i, j).toString());
			}
		}
		
		AssignmentData assignment = new AssignmentData(customer,
				this.descTP.getText(),
				materialTableContent,
				workTableContent,
				this.totalCostLBL.getText());
		
		assignment.saveAssignmentData();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
