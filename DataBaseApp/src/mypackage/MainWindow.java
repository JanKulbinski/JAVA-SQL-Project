package mypackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class MainWindow {

	static JPanel selected;
	static JFrame pass;
	static JLabel idE, passL;
	static JButton ok;
	static JTextField idEField;
	static JPasswordField passField;

	public static void main (String args[]) {

		JFrame mainFrame = new JFrame();

		mainFrame.setLayout(new BorderLayout());
		mainFrame.setSize(new Dimension(720,650));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel buttons = new JPanel(new FlowLayout());	
		JButton storeKeeper = new JButton("Storekeeper");
		JButton manager = new JButton("Manager");
		JButton clerk = new JButton("Clerk");
		manager.setBackground(new Color(179, 255, 179));
		storeKeeper.setBackground(new Color(255, 117, 26));
		clerk.setBackground(new Color(153, 153, 255));

		buttons.add(storeKeeper);
		buttons.add(manager);	
		buttons.add(clerk);
		buttons.setBackground(new Color(0, 150, 204));

		selected = new JPanel();
		selected.setBackground(new Color(0, 57, 77));


		mainFrame.add(buttons,BorderLayout.NORTH);
		mainFrame.add(selected,BorderLayout.CENTER);

		storeKeeper.addActionListener (
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						login();
						ok.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ev) {
								int id = Integer.parseInt(idEField.getText());
								String password = new String(passField.getPassword());

								try {
									java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Football-Kits-Shop?user=manager&password=managerP");
									Class.forName("com.mysql.jdbc.Driver");

									java.sql.PreparedStatement stmt = conn.prepareStatement("Select password,role FROM Employees Where employee_id = ?");
									stmt.setInt(1, id);
									ResultSet rs = stmt.executeQuery();
									if(rs.next()) {
										if(rs.getString(1).equals(password) && rs.getString(2).equals("storekeeper")) {

											mainFrame.remove(selected);
											selected = new StoreKeeper();
											mainFrame.add(selected);
											mainFrame.revalidate();
										}
									}
									conn.close();
								}

								catch(ClassNotFoundException e) {
									e.printStackTrace();
									System.out.println("Problem ze sterownikiem");
								}

								catch(SQLException e2) {
									e2.printStackTrace();
									System.out.println("SQLException: " + e2.getMessage());
									System.out.println("SQLState: " + e2.getSQLState());
									System.out.println("VendorError: " + e2.getErrorCode());
								}
								pass.dispose();
							}
						});
					}
				});


		manager.addActionListener (
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						login();
						ok.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ev) {
								int id = Integer.parseInt(idEField.getText());
								String password = new String(passField.getPassword());

								try {
									java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Football-Kits-Shop?user=manager&password=managerP");
									Class.forName("com.mysql.jdbc.Driver");

									java.sql.PreparedStatement stmt = conn.prepareStatement("Select password,role FROM Employees Where employee_id = ?");
									stmt.setInt(1, id);
									ResultSet rs = stmt.executeQuery();
									if(rs.next()) {
										if(rs.getString(1).equals(password) && rs.getString(2).equals("manager")) {

											mainFrame.remove(selected);
											selected = new Manager();
											mainFrame.add(selected);
											mainFrame.revalidate();
										}
									}
									conn.close();
								}

								catch(ClassNotFoundException e) {
									e.printStackTrace();
									System.out.println("Problem ze sterownikiem");
								}

								catch(SQLException e2) {
									e2.printStackTrace();
									System.out.println("SQLException: " + e2.getMessage());
									System.out.println("SQLState: " + e2.getSQLState());
									System.out.println("VendorError: " + e2.getErrorCode());
								}
								pass.dispose();
							}
						});
					}
				});

		clerk.addActionListener (
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						login();
						ok.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ev) {
								int id = Integer.parseInt(idEField.getText());
								String password = new String(passField.getPassword());

								try {
									java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Football-Kits-Shop?user=manager&password=managerP");
									Class.forName("com.mysql.jdbc.Driver");

									java.sql.PreparedStatement stmt = conn.prepareStatement("Select password,role FROM Employees Where employee_id = ?");
									stmt.setInt(1, id);
									ResultSet rs = stmt.executeQuery();
									if(rs.next()) {
										if(rs.getString(1).equals(password) && rs.getString(2).equals("clerk")) {

											mainFrame.remove(selected);
											selected = new Clerk(id);
											mainFrame.add(selected);
											mainFrame.revalidate();
										}
									}
									conn.close();
								}

								catch(ClassNotFoundException e) {
									e.printStackTrace();
									System.out.println("Problem ze sterownikiem");
								}

								catch(SQLException e2) {
									e2.printStackTrace();
									System.out.println("SQLException: " + e2.getMessage());
									System.out.println("SQLState: " + e2.getSQLState());
									System.out.println("VendorError: " + e2.getErrorCode());
								}
								pass.dispose();
							}
						});
					}
				});

		mainFrame.setVisible(true);
	}

	private static void login() {

		pass= new JFrame();
		pass.setLayout(new FlowLayout());
		pass.setSize(new Dimension(350,100));


		idE = new JLabel("Employee ID");
		passL = new JLabel("Password");

		ok = new JButton("ok");

		idEField= new JTextField(3);
		passField= new JPasswordField(8);


		passField.setEchoChar('*');

		pass.add(idE);
		pass.add(idEField);	
		pass.add(passL);
		pass.add(passField);
		pass.add(ok);
		pass.setVisible(true);
	}
}



