package org.payroll;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.payroll.preferences.*;
import org.payroll.departments.*;
import org.payroll.employees.*;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	String username;

	Object[] columns = {
			"ID",
			"First Name",
			"Last Name",
			"Email",
			"Department",
			"Net Salary"
	};
	Object[][] data;

	JTable table;
	JScrollPane scrollpane;
	JTextField txt_loggedInAs;
	JMenuBar menubar;
	JMenu fileMenu, tableMenu, employeesMenu, departmentsMenu, preferencesMenu, helpMenu;
	JMenuItem logoutMI, exitMI;    // fileMenu
	JMenuItem reloadMI;    // tableMenu
	JMenuItem newEmployeeMI, updateEmployeeMI, deleteEmployeeMI;    // employeesMenu
	JMenuItem newDepartmentMI, modifyDepartmentMI, deleteDepartmentMI;    // departmentsMenu
	JMenuItem newLoginIdMI, changePasswordMI, deleteLoginIdMI;    // preferencesMenu
	JMenuItem aboutMI;    // helpMenu
	private JTextField txt_new_employee_department;
	private JTextField txt_modify_department_name;
	private JTextField txt_new_department_name;
	DatabaseManager dbManager;

	public MainFrame(String username, DatabaseManager dbManager) {
		this.username = username;
		this.dbManager = dbManager;

		initFrame();
		initComponents();
		configureComponents();
		addActionListeners();
		addComponentsToFrame();
	}

	void initFrame() {
		setTitle("Payroll System ");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	}

	void initComponents() {
		data = dbManager.getEmployees();

		menubar = new JMenuBar();
		txt_loggedInAs = new JTextField("Logged in as " + username);

		// Menus
		fileMenu = new JMenu("File");
		tableMenu = new JMenu("Table");
		employeesMenu = new JMenu("Employees");
		departmentsMenu = new JMenu("Departments");
		preferencesMenu = new JMenu("Preferences");
		helpMenu = new JMenu("Help");

		// File Menu
		logoutMI = new JMenuItem("Logout");
		exitMI = new JMenuItem("Exit");

		// Table Menu
		reloadMI = new JMenuItem("Reload");

		// Employees Menu
		newEmployeeMI = new JMenuItem("New Employee");
		updateEmployeeMI = new JMenuItem("Update Employee");
		deleteEmployeeMI = new JMenuItem("Delete Employee");

		// Departments Menu
		newDepartmentMI = new JMenuItem("New Department");
		modifyDepartmentMI = new JMenuItem("Modify Department");
		deleteDepartmentMI = new JMenuItem("Delete Department");

		// Preferences Menu
		newLoginIdMI = new JMenuItem("New Login ID");
		changePasswordMI = new JMenuItem("Change Password");
		deleteLoginIdMI = new JMenuItem("Delete Login ID");

		// Help Menu
		aboutMI = new JMenuItem("About");

		// Table
		if (data == null) {
			table = new JTable();
		} else {
			table = new JTable(data, columns);
		}

		scrollpane = new JScrollPane(table);

		txt_new_employee_department = new JTextField(18);
		txt_modify_department_name = new JTextField(18);
		txt_new_department_name = new JTextField(18);
	}



	void configureComponents() {
		table.setEnabled(false);
		txt_loggedInAs.setEditable(false);
	}

	void addActionListeners() {
		// File Menu
		logoutMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				(new LoginFrame(dbManager)).setVisible(true);
				dispose();
			}
		});

		exitMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// Table Menu
		reloadMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				(new MainFrame(username, dbManager)).setVisible(true);
				dispose();
			}
		});

		// Employees Menu
		newEmployeeMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewEmployeeFrame newEmployeeFrame = new NewEmployeeFrame(dbManager);
				newEmployeeFrame.setSize(400, 200); // Adjust size here
				newEmployeeFrame.setVisible(true);
			}
		});

		updateEmployeeMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new UpdateEmployeeFrame(dbManager)).setVisible(true);
			}
		});

		deleteEmployeeMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new DeleteEmployeeFrame(dbManager)).setVisible(true);
			}
		});

		// Departments Menu
		newDepartmentMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewDepartmentFrame newDepartmentFrame = new NewDepartmentFrame(dbManager);
				newDepartmentFrame.setSize(400, 200); // Adjust size here
				newDepartmentFrame.setVisible(true);
			}
		});

		modifyDepartmentMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModifyDepartmentFrame modifyDepartmentFrame = new ModifyDepartmentFrame(dbManager);
				modifyDepartmentFrame.setSize(400, 200); // Adjust size here
				modifyDepartmentFrame.setVisible(true);
			}
		});

		deleteDepartmentMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new DeleteDepartmentFrame(dbManager)).setVisible(true);
			}
		});

		// Preferences Menu
		newLoginIdMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewLoginIdFrame(dbManager).setVisible(true);
			}
		});

		changePasswordMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new ChangePasswordFrame(username, dbManager)).setVisible(true);
			}
		});

		deleteLoginIdMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new DeleteLoginIdFrame(dbManager)).setVisible(true); // Here's the modification
			}
		});

		// Help Menu
		aboutMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(
						null,
						"Employee Payroll System",
						"About",
						JOptionPane.INFORMATION_MESSAGE
				);
			}
		});
	}

	void addComponentsToFrame() {
		// File Menu
		fileMenu.add(logoutMI);
		fileMenu.add(exitMI);

		// Table Menu
		tableMenu.add(reloadMI);

		// Employees Menu
		employeesMenu.add(newEmployeeMI);
		employeesMenu.add(updateEmployeeMI);
		employeesMenu.add(deleteEmployeeMI);

		// Departments Menu
		departmentsMenu.add(newDepartmentMI);
		departmentsMenu.add(modifyDepartmentMI);
		departmentsMenu.add(deleteDepartmentMI);

		// Preferences Menu
		preferencesMenu.add(newLoginIdMI);
		preferencesMenu.add(changePasswordMI);
		preferencesMenu.add(deleteLoginIdMI);

		// Help Menu
		helpMenu.add(aboutMI);

		// Menus
		menubar.add(fileMenu);
		menubar.add(tableMenu);
		menubar.add(employeesMenu);
		menubar.add(departmentsMenu);
		menubar.add(preferencesMenu);
		menubar.add(helpMenu);

		setJMenuBar(menubar);
		add(scrollpane, BorderLayout.CENTER);
		add(txt_loggedInAs, BorderLayout.SOUTH);
	}
}
