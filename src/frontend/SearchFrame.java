package frontend;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import backend.CustomerData;

/**
 * 
 * @author Samuel Holderbach
 * 
 */
public class SearchFrame extends JFrame implements ListSelectionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JList<String> customerList;
	
	private boolean disposedAndOpened = false;
	
	/**
	 * the frame to search for an existing customer
	 */
	public SearchFrame() {
		this.setTitle("Auftragsmanager");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initMenu();
		initUI();
	}
	
	/**
	 * sets the frame design and actions that can be performed
	 */
	public void initUI() {
		Container contentPane = this.getContentPane();
		contentPane.setLayout(null);
		
		int xBorder = 10;
		int yBorder = 10;
		int margin = 5;
		int elementWidth = 190;
		int elementHeight = 25;
		
		JLabel searchLabel = new JLabel("Kunden-ID oder PLZ eingeben");
		searchLabel.setBounds(xBorder, yBorder, elementWidth*2, elementHeight);
		contentPane.add(searchLabel);
		
		yBorder += elementHeight + margin;
		
		JTextField searchField = new JTextField(30);
		searchField.setBounds(xBorder, yBorder, elementWidth, elementHeight);
		contentPane.add(searchField);
		
		xBorder += elementWidth + margin;
		
		JButton searchButton = new JButton("Suchen");
		searchButton.setBounds(xBorder, yBorder, elementWidth/2, elementHeight);
		contentPane.add(searchButton);
		
		xBorder -= elementWidth + margin;
		yBorder += elementHeight + margin;
		
		JScrollPane listScrollPane = new JScrollPane();
		listScrollPane.setBounds(xBorder, yBorder, elementWidth*3, elementHeight*8);
		contentPane.add(listScrollPane);
		
		yBorder += elementHeight*8 + margin;
		
		JButton assignmentButton = new JButton("Angebot erstellen");
		assignmentButton.setBounds(xBorder, yBorder, elementWidth, elementHeight);
		contentPane.add(assignmentButton);
		assignmentButton.setVisible(true);
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				ArrayList<CustomerData> customers = CustomerData.searchCustomer(searchField.getText());
				DefaultListModel<String> customerListModel = new DefaultListModel<String>();
				
				for (CustomerData customer : customers) {
					customerListModel.addElement(customer.getLastName() + " || "
							+ customer.getFirstName() + " || "
							+ "ID: " + customer.getCustomerID());
				}
				customerList = new JList<String>(customerListModel);
				listScrollPane.setViewportView(customerList);
				
				customerList.addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent selectionEvent) {
						if (customerList.getMinSelectionIndex() == customerList.getMaxSelectionIndex()) {
								
							assignmentButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent actionEvent) {
									if (!disposedAndOpened) {
										CustomerData customer = customers.get(customerList.getMinSelectionIndex());
										dispose();
										AssignmentFrame frame = new AssignmentFrame(customer);
										frame.setVisible(true);
										disposedAndOpened = true;
									}
								}
							});
						}
					}
				});
			}
		});
	}
	
	/**
	 * initializes the menu bar
	 */
	public void initMenu() {
		JMenuBar menubar = new JMenuBar();
		
		JMenu offerMenu = new JMenu("Angebot");
		
		JMenuItem menuItemSave = new JMenuItem("Speichern");
		menuItemSave.setEnabled(false);
		JMenuItem menuItemPrint = new JMenuItem("Drucken");
		menuItemPrint.setEnabled(false);
		
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
		
		menuItemLogOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
				LogInFrame frame = new LogInFrame();
				frame.setVisible(true);
			}
		});
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

