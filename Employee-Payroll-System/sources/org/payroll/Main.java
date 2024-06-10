package org.payroll;

import org.payroll.DatabaseManager;
import org.payroll.LoginFrame;

public class Main {

	public static void main(String[] args) {
		String databaseName = "taskapp";
		String username = "root";
		DatabaseManager dbManager = new DatabaseManager(databaseName, username);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginFrame(dbManager).setVisible(true);
			}
		});
	}
}
