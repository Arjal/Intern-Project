package org.payroll;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	JLabel lblInfo, lblUsername, lblPassword;
	JButton btnLogin, btnExit;
	JTextField txtUsername;
	JPasswordField txtPassword;

	DatabaseManager dbManager; // Database manager instance

	public LoginFrame(DatabaseManager dbManager) {
		this.dbManager = dbManager; // Initialize the database manager
		initializeFrame();
		initializeComponents();
		addActionListeners();
		addComponentsToFrame();
	}

	private void initializeFrame() {
		setTitle("Login");
		setSize(400, 200); // Adjusted frame size
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new GridBagLayout()); // Changed layout manager to GridBagLayout
		getContentPane().setBackground(new Color(240, 240, 240)); // Set background color
	}

	private void initializeComponents() {
		lblInfo = new JLabel("Employee Payroll System");
		lblUsername = new JLabel("Username: ");
		lblPassword = new JLabel("Password: ");
		txtUsername = new JTextField(20); // Increased size to hold more characters
		txtPassword = new JPasswordField(20); // Increased size to hold more characters
		btnExit = new JButton("Exit");
		btnLogin = new JButton("Login");

		// Set font and foreground color for labels
		Font labelFont = new Font("Arial", Font.BOLD, 14);
		lblInfo.setFont(labelFont);
		lblUsername.setFont(labelFont);
		lblPassword.setFont(labelFont);
		lblInfo.setForeground(Color.BLUE);
		lblUsername.setForeground(Color.BLACK);
		lblPassword.setForeground(Color.BLACK);

		// Set font for buttons
		Font buttonFont = new Font("Arial", Font.PLAIN, 12);
		btnExit.setFont(buttonFont);
		btnLogin.setFont(buttonFont);

		// Set background color for buttons
		btnExit.setBackground(Color.RED);
		btnLogin.setBackground(Color.GREEN);

		// Set foreground color for buttons
		btnExit.setForeground(Color.WHITE);
		btnLogin.setForeground(Color.WHITE);
	}

	private void addActionListeners() {
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = new String(txtPassword.getPassword());

				// Verify login credentials against database
				if (dbManager.verifyLoginId(username, password)) {
					loginSuccessful(username);
				} else {
					loginFailed();
				}
			}
		});
	}

	private void addComponentsToFrame() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(lblInfo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(lblUsername, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		add(txtUsername, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(lblPassword, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		add(txtPassword, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(btnExit, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(btnLogin, gbc);
	}

	private void loginSuccessful(String username) {
		JOptionPane.showMessageDialog(
				null,
				"Login Successful",
				"Login Successful",
				JOptionPane.INFORMATION_MESSAGE
		);

		setVisible(false);
		(new MainFrame(username, dbManager)).setVisible(true); // Pass username to MainFrame
		dispose();
	}

	private void loginFailed() {
		JOptionPane.showMessageDialog(
				null,
				"Wrong username or password",
				"Login Failed",
				JOptionPane.ERROR_MESSAGE
		);

		txtUsername.setText("");
		txtPassword.setText("");
	}
}






