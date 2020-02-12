package frontend;

import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author Samuel Holderbach
 *
 */
public class MainFrame extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * the main frame which opens after a succesful login
	 */
	public MainFrame() {
		this.setTitle("Auftragsmanager");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initMenu();
	}
	
	/**
	 * sets the frame design and actions that can be performed
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
