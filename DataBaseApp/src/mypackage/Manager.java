package mypackage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.mysql.jdbc.CallableStatement;

public class Manager extends JPanel {

	private String urlConnection = "jdbc:mysql://localhost:3306/Football-Kits-Shop?user=manager&password=managerP";
	private Connection conn = null;

	Manager() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		JPanel buttons, buttons2, buttons3, buffor1, employees, list;			
		buttons = new JPanel();
		buttons2 = new JPanel();
		buffor1 = new JPanel();
		employees = new JPanel();
		list = new JPanel();
		buttons3 = new JPanel();

		JButton countComission, resetComission, hypestProduct, restore, backup;
		JButton add, delete, show;
		countComission = new JButton("Count commission");
		resetComission = new JButton("Reset comissionn");
		//	hypestProduct = new JButton("Hypest product");
		restore = new JButton("Restore");
		backup = new JButton("Backup");
		add = new JButton("Add employee");
		delete = new JButton("Delete employee");
		show = new JButton("Show employees");



		JTextField fistNameField,lastNameField,idField,salaryField,roleField;
		fistNameField = new JTextField(10);
		lastNameField = new JTextField(10);
		idField = new JTextField(2);
		salaryField = new JTextField(3);
		roleField = new JTextField(6);

		JLabel firstName,lastName,employeesLabel,employeeId,salary,role;
		employeeId = new JLabel("Id:");
		firstName = new JLabel("First name:");
		lastName = new JLabel("Last name :");
		salary = new JLabel("Salary :");
		role = new JLabel("Role :");

		JTextArea display = new JTextArea(19, 34);
		display.setEditable(false); 
		JScrollPane scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		buttons.setLayout(new FlowLayout());
		buttons.add(countComission);
		buttons.add(resetComission);

		buttons.add(restore);
		buttons.add(backup);

		buttons2.add(show);
		buttons2.setBackground(new Color(179, 255, 179));

		employees.setLayout(new FlowLayout());
		employees.add(employeeId);
		employees.add(idField);
		employees.add(firstName);
		employees.add(fistNameField);
		employees.add(lastName);
		employees.add(lastNameField);	
		employees.add(salary);
		employees.add(salaryField);
		employees.add(role);
		employees.add(roleField);

		buttons3.add(add);
		buttons3.add(delete);


		add(buttons);
		add(employees);
		add(buttons3);
		add(list);
		add(scroll);
		add(buttons2);

		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				updateEmployee(Integer.parseInt(idField.getText()),"","",0,"",true);
				fistNameField.setText("");
				lastNameField.setText("");
				idField.setText("");
				salaryField.setText("");
				roleField.setText("");
			}

		});

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				updateEmployee(Integer.parseInt(idField.getText()),fistNameField.getText(),lastNameField.getText(),
						Integer.parseInt(salaryField.getText()),roleField.getText(),false);
				fistNameField.setText("");
				lastNameField.setText("");
				idField.setText("");
				salaryField.setText("");
				roleField.setText("");
			}

		});

		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				display.setText("");
				showEmployees(display);
			}

		});

		countComission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				countProcedure();
			}

		});

		resetComission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				resetProcedure();
			}

		});

		backup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				makeBackup(display);
			}

		});

		restore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				readDbCopy(display);
			}

		});
	}

	private void showEmployees(JTextArea display) {

		try {
			conn = DriverManager.getConnection(urlConnection);
			Class.forName("com.mysql.jdbc.Driver");

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * FROM employees_for_manager");

			while (rs.next()) {
				display.append(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4)+"\n");
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

	private void updateEmployee(int id, String name, String lastName, int salary, String role, Boolean fired) {
		try {
			PreparedStatement stmt;
			conn = DriverManager.getConnection(urlConnection);
			Class.forName("com.mysql.jdbc.Driver");

			if(fired) {
				stmt = conn.prepareStatement("DELETE FROM Employees WHERE employee_id = ?");
				stmt.setInt(1, id);
			} else {
				stmt = conn.prepareStatement("INSERT INTO Employees Values (?,?,?,?,0,?,'pass')");
				stmt.setInt(1, id);
				stmt.setString(2,name);
				stmt.setString(3,lastName);
				stmt.setInt(4, salary);
				stmt.setString(5, role);
			}
			stmt.executeUpdate();
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

	private void countProcedure() {
		try {

			conn = DriverManager.getConnection(urlConnection);
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.CallableStatement stmt = conn.prepareCall("CALL count_commision()");
			stmt.execute();
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

	private void resetProcedure() {
		try {

			conn = DriverManager.getConnection(urlConnection);
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.CallableStatement stmt = conn.prepareCall("CALL reset_commision()");
			stmt.execute();
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

	private void makeBackup(JTextArea display) {

		Process runtimeProcess;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("DD:MM:YY");
		String date = sdf.format(cal.getTime());
		String[] cmd = {
				"/bin/sh",
				"-c",
				"mysqldump -u root -proot Football-Kits-Shop > BACKUP.sql"
		};
		try {
			runtimeProcess = Runtime.getRuntime().exec(cmd);
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0) {
				display.setText("Backup created successfully");
			} else {
				display.setText("Could not create the backup");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void readDbCopy (JTextArea display)   {

		Process runtimeProcess;
		String[] cmd = {
				"/bin/sh",
				"-c",
				"mysql -u root -proot Football-Kits-Shop < BACKUP.sql"
		};
		try {
			runtimeProcess = Runtime.getRuntime().exec(cmd);
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0) {
				display.setText("Restore successfully");
			} else {
				display.setText("Could not restore ");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		};
	}
}


