package mypackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Clerk extends JPanel {

	String urlConnection = "jdbc:mysql://localhost:3306/Football-Kits-Shop?user=manager&password=managerP";
	java.sql.Connection conn;

	JList<String> list;
	DefaultListModel<String> nationalities;

	JSlider priceSlider;
	JScrollPane listScroller;
	JPanel checkBoxes,order,showPanel;
	JCheckBox shirt,socks,full,shorts;
	JLabel priceLabel, idClientL, idProductL, amountL;
	JTextArea display;
	JTextField idClientF, idProductF, amountF;
	JButton show, orderButton;
	JScrollPane scroll;
	
	int id;
	
	Clerk(int id) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setNationalities();
		
		this.id =id;
		
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(23, 23));

		shirt = new JCheckBox("Shirt");
		socks = new JCheckBox("Socks");
		shorts = new JCheckBox("Shorts");
		full = new JCheckBox("Full");		

		checkBoxes= new JPanel();
		checkBoxes.add(shirt);
		checkBoxes.add(socks);
		checkBoxes.add(shorts);
		checkBoxes.add(full);

		priceLabel = new JLabel("Price");
		idClientL = new JLabel("Client ID");
		idProductL = new JLabel("Product ID");
		amountL = new JLabel("Amount");

		idClientF = new JTextField(3);
		idProductF = new JTextField(3);
		amountF = new JTextField(3);
		
		orderButton = new JButton("Order");

		order= new JPanel();
		order.add(idClientL);
		order.add(idClientF);
		order.add(idProductL);
		order.add(idProductF);
		order.add(amountL);
		order.add(amountF);
		order.add(orderButton);
		order.setBackground(new Color(153, 153, 255));

		priceSlider = new JSlider();
		priceSlider.setOrientation(SwingConstants.HORIZONTAL);
		priceSlider.setMaximum(500);
		priceSlider.setMinimum(1);
		priceSlider.setMajorTickSpacing(50);
		priceSlider.setMinorTickSpacing(10);
		priceSlider.setPaintTicks(true);
		priceSlider.setPaintLabels(true);

		display = new JTextArea(13, 34);
		display.setEditable(false); 
		display.setFont(new Font("monospaced", Font.PLAIN, 12));
		
		scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		show = new JButton("Search");
		showPanel = new JPanel(new FlowLayout());
		showPanel.add(show);

		add(listScroller);
		add(priceLabel);
		add(priceSlider);
		add(checkBoxes);
		add(scroll);
		add(showPanel);
		add(order);
		
		show.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				display.setText("");
				searchProducts();
			}
		});
		
		orderButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				makeOrder();
				idClientF.setText("");
				idProductF.setText("");
				amountF.setText("");
			}
		});
	}
	
	private void setNationalities() {
		nationalities = new DefaultListModel<String>();

		try {
			conn = DriverManager.getConnection(urlConnection);
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Statement stmt = conn.createStatement();

			String query = "SELECT DISTINCT nationality FROM Players ORDER BY nationality ASC";
			ResultSet result = stmt.executeQuery(query);

			while(result.next()) {
				nationalities.addElement(result.getString(1));
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


		list = new JList<String>(nationalities);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);

	}
	
	private void makeOrder() {
		try {
			conn = DriverManager.getConnection(urlConnection);
			conn.setAutoCommit(false);
			
			java.sql.PreparedStatement stmt = conn.prepareStatement("Insert into Orders(employee_id,product_id,client_id,amount) Values (?,?,?,?)");
			stmt.setInt(1,id);
			stmt.setInt(2,Integer.parseInt(idProductF.getText()));
			stmt.setInt(3,Integer.parseInt(idClientF.getText()));
			stmt.setInt(4,Integer.parseInt(amountF.getText()));
			stmt.executeUpdate();
			
			java.sql.PreparedStatement stmt2 = conn.prepareStatement("Update Store SET number_of_products = number_of_products - ? WHERE product_id = ?");
			stmt2.setInt(2,Integer.parseInt(idProductF.getText()));
			stmt2.setInt(1,Integer.parseInt(amountF.getText()));
			stmt2.executeUpdate();
			
			
			conn.commit();
			conn.close();
		}

			catch(SQLException e2) {
				e2.printStackTrace();
				System.out.println("SQLException: " + e2.getMessage());
				System.out.println("SQLState: " + e2.getSQLState());
				System.out.println("VendorError: " + e2.getErrorCode());
			}
		
	}

	
	private void searchProducts() {
		String country = list.getSelectedValue();
		String clotheType = "(";
		int price = priceSlider.getValue();
		
		if(shirt.isSelected()) 
			clotheType = clotheType + "'shirt', ";
		if(socks.isSelected()) 
			clotheType = clotheType + "'socks', ";
		if(shorts.isSelected()) 
			clotheType = clotheType + "'shorts', ";
		if(full.isSelected()) 
			clotheType = clotheType + "'full', ";

		clotheType = clotheType + "'hack'"; // provide legit sql query even when none clothetype was selected
		
		String query = "Select product_id,last_name,club,price,clothe_type from Products join Players on Products.player_id = Players.player_id"
				+" Where price < " + Integer.toString(price)+ " and nationality = '" + country + "' and clothe_type in " + clotheType+ ")"
				+" Order by last_name ASC ";
		
		try {
			conn = DriverManager.getConnection(urlConnection);
			java.sql.Statement stmt = conn.createStatement();
			
			ResultSet res = stmt.executeQuery(query);

			while(res.next()) {
			String idP  = Integer.toString(res.getInt(1));
			String lastName  = res.getString(2);
			String club  = res.getString(3);
			String p = Float.toString(res.getFloat(4));
			String type  = res.getString(5); 

			
			display.append(idP+" "+lastName+" "+club+" "+type +" "+p+" \n");
			
			}
			conn.close();
		}

			catch(SQLException e2) {
				e2.printStackTrace();
				System.out.println("SQLException: " + e2.getMessage());
				System.out.println("SQLState: " + e2.getSQLState());
				System.out.println("VendorError: " + e2.getErrorCode());
			}
		
	}
}
