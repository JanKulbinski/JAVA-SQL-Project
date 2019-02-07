package mypackage;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class StoreKeeper extends JPanel {

//	static String daneZBazy;

	StoreKeeper() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel newProducts,buffor,list,showlist;
		newProducts = new JPanel(new FlowLayout());
		buffor = new JPanel();
		list = new JPanel();
		showlist = new JPanel();

		JLabel name,amount,listLabel;
		name = new JLabel("Product id");
		amount = new JLabel("Number of products");
		listLabel = new JLabel("List of products");

		JTextField nameField,amountField;
		nameField = new JTextField(10);
		amountField = new JTextField(5);

		JTextArea display = new JTextArea(19, 34);
		display.setEditable(false); 
		JScrollPane scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton ok,show;
		show = new JButton("Show");
		ok = new JButton("Ok");
		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText("");
				showStore(display);
			}
		});

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProducts(nameField.getText(),amountField.getText());
				nameField.setText("");
				amountField.setText("");
			}
		});

		newProducts.add(name);
		newProducts.add(nameField);
		newProducts.add(amount);
		newProducts.add(amountField);
		newProducts.add(ok);
		add(newProducts);
		add(buffor);
		list.add(listLabel);
		add(list);
		add(scroll);
		showlist.add(show);
		showlist.setBackground(new Color(255, 117, 26));
		add(showlist);


	}


	private void showStore(JTextArea display) {

		String urlConnection = "jdbc:mysql://localhost:3306/Football-Kits-Shop?user=storekeeper&password=storekeeperP";
		String query = "Select * FROM Store ORDER BY product_id";	               
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(urlConnection);
			Class.forName("com.mysql.jdbc.Driver");

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				display.append("ID "+rs.getString(1)+"  items:"+rs.getString(2)+"\n");
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

	}

	private void addProducts(String name, String amount) {

		int numberProducts = Integer.parseInt(amount);
		int id = Integer.parseInt(name);

		String polaczenieURL = "jdbc:mysql://localhost:3306/Football-Kits-Shop?user=storekeeper&password=storekeeperP";
		Connection conn = null; 
		try {
			conn = DriverManager.getConnection(polaczenieURL);
			Class.forName("com.mysql.jdbc.Driver");
			PreparedStatement stmt = conn.prepareStatement("Update Store Set number_of_products = ? Where product_id = ?");
			stmt.setInt(1, numberProducts);
			stmt.setInt(2,id);
			stmt.executeUpdate();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}	catch(ClassNotFoundException e2) {
			e2.printStackTrace();
		}

	}

}

