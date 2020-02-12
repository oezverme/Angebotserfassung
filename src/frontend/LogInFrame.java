package frontend;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.*;

/**
 * 
 * @author Dominik Krenz
 * 
 */
public class LogInFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private static HashMap<String, String> IDPW = new HashMap<String, String>();
	
	/**
	 * the frame to sign in as a user
	 */
	public LogInFrame() {
		this.setTitle("Auftragsmanager");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initUI();
	}
	
	/**
	 * sets the frame design and actions that can be performed
	 */
	public void initUI() {
		JButton logIn = new JButton();
		
		JLabel IDLabel = new JLabel("ID:");
		JLabel PWLabel = new JLabel("Passwort:");
		
	    JTextField IDField = new JTextField("", 15);
	    JPasswordField PWField = new JPasswordField(15);
	    
		getContentPane().setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    c.gridx = 1;
	    c.gridy = 0;
	    add(IDField, c);
	    
	    c.gridx = 1;
	    c.gridy = 1;
	    add(PWField, c);
	    
	    logIn.setText("Log In");
	    c.gridx = 2;
	    c.gridy = 1;
	    add(logIn, c);
	    
	    c.gridx = 0;
	    c.gridy = 0;
	    add(IDLabel, c);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    add(PWLabel, c);
	    
	    logIn.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e) {
	    		logIn(IDField.getText(), PWField.getPassword());
	    	}
	    });
	}
	
	/**
	 * @param ID the user ID
	 * @param PW the user password
	 */
	public boolean logIn(String ID, char[] PW) {
		String PWString = new String(PW);
		
		if(IDPW.containsKey(ID) && IDPW.get(ID).equals(PWString)) {
			dispose();
	        MainFrame frame = new MainFrame();
	        frame.setVisible(true);	
	        return true;
		} else {
		    JOptionPane.showMessageDialog(null, "Logindaten fehlerhaft - falsche Benutzer ID oder falsches Passwort");
			return false;
		}
	}
	
	public static void main(String[] args) {
		try {
			//load ID and PW data
			IDPW.put("Admin", "1234");
			
			Properties properties = new Properties();
			String os = System.getProperty("os.name").toLowerCase();
			
			if (os.contains("win")){ //windows
				properties.load(new FileInputStream(".\\src\\resources\\users.properties"));
			}
			else if (os.contains("osx")){ //apple
			}      
			else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){ //linux/unix
				properties.load(new FileInputStream("./src/resources/users.properties"));
			}
			
			for (String key : properties.stringPropertyNames()) {
				IDPW.put(key, properties.get(key).toString());
			}
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
		} catch (FileNotFoundException e) {
		    JOptionPane.showMessageDialog(null, "Warnung - es existieren bisher keine gespeicherten Logindaten");
			e.printStackTrace();
			
		} catch (Exception e) {
			return;
		}
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				LogInFrame frame = new LogInFrame();
				frame.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

