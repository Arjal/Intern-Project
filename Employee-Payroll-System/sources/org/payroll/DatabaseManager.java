package org.payroll;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

	private final String connectionString;
	private Connection conn;
	private Statement stmt;

	public DatabaseManager(String databaseName, String username) {
		connectionString = "jdbc:mysql://localhost:3306/" + databaseName;
		String password = "9810981567@rj";
		try {
			conn = DriverManager.getConnection(connectionString, username, password);
			stmt = conn.createStatement();
			stmt.setQueryTimeout(30);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public boolean verifyLoginId(String username, String password) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM login_ids WHERE username=? AND password=?");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	public void createLoginId(String username, String password) {
		try {
			stmt.executeUpdate("INSERT INTO login_ids VALUES(null, \"" + username + "\", \"" + password + "\")");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void deleteLoginId(String username) {
		try {
			stmt.executeUpdate(
					"DELETE FROM login_ids WHERE username=\"" + username + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void changePassword(String username, String newPassword) {
		try {
			stmt.executeUpdate(
					"UPDATE login_ids SET password=\"" + newPassword + "\" WHERE username=\"" + username + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public Boolean existsDepartment(String dep_name) {
		try {
			return stmt.executeQuery(
					"SELECT * FROM departments WHERE dep_name=\"" + dep_name + "\""
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public void newDepartment(String dep_name, int basic_salary, int da_percent, int hra_percent, int pf_percent) {
		int da = (da_percent / 100) * basic_salary;
		int hra = (hra_percent / 100) * basic_salary;
		int pf = (pf_percent / 100) * basic_salary;
		int gross_salary = basic_salary + da + hra + pf;
		int epf = pf / 2;
		int lic = epf / 2;
		int deductions = epf + lic;
		int net_salary = gross_salary - deductions;
		
		try {
			stmt.executeUpdate(
					"INSERT INTO departments VALUES(" +
							"null," +
							"\"" + dep_name + "\" ," +
							Integer.toString(basic_salary) + "," +
							Integer.toString(da) + "," +
							Integer.toString(hra) + "," +
							Integer.toString(pf) + "," +
							Integer.toString(gross_salary) + "," +
							Integer.toString(epf) + "," +
							Integer.toString(lic) + "," +
							Integer.toString(deductions) + "," +
							Integer.toString(net_salary) +
					")"
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void deleteDepartment(String dep_name) {
		try {
			stmt.executeUpdate(
					"DELETE FROM departments WHERE dep_name=\"" + dep_name + "\""
				);
			stmt.executeUpdate(
					"DELETE FROM employees WHERE department=\"" + dep_name + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void updateDepartment(String dep_name, int basic_salary, int da, int hra, int pf) {
		deleteDepartment(dep_name);
		newDepartment(dep_name, basic_salary, da, hra, pf);
	}
	
	public ArrayList<String> getListOfDepartments() {
		ArrayList<String> lst = new ArrayList<String>();
		
		try {
			ResultSet rs = stmt.executeQuery("SELECT dep_name FROM departments");
			
			while (rs.next()) {
				lst.add(rs.getString("dep_name"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return lst;
	}
	
	public int getSalary(String dep_name) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT net_salary FROM departments WHERE dep_name=\"" + dep_name + "\"");
			
			if (rs.next())
				return rs.getInt("net_salary");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return 0;
	}
	
	public Boolean existsEmployeeID(int id) {
		try {
			return stmt.executeQuery(
					"SELECT * FROM employees WHERE id=" + Integer.toString(id)
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public void createEmployee(String fn, String ln, String email, String department) {
		try {
			stmt.executeUpdate("INSERT INTO employees VALUES(" +
					"null," +
					"\"" + fn + "\"," +
					"\"" + ln + "\"," + 
					"\"" + email + "\"," +
					"\"" + department + "\"" +
				")");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void deleteEmployee(int id) {
		try {
			stmt.executeUpdate(
					"DELETE FROM employees WHERE id=" + Integer.toString(id)
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void updateEmployee(int id, String fn, String ln, String email, String department) {
		try {
			stmt.executeUpdate(
					"UPDATE employees SET " +
					"first_name=\"" + fn + "\"," +
					"last_name=\"" + ln + "\"," +
					"email=\"" + email + "\"," +
					"department=\"" + department + "\" " +
					"WHERE id=" + Integer.toString(id)
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public Object[][] getEmployees() {
		ArrayList<Object[]> employees = new ArrayList<Object[]>();
		ResultSet rs;
		
		try {
			rs = stmt.executeQuery(
					"SELECT * FROM employees"
				);
			
			while (rs.next()) {
				Object[] temp = {
					rs.getInt("id"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("email"),
					rs.getString("department"),
					getSalary(rs.getString("department"))
				};
				
				employees.add(temp);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return employees.toArray(new Object[employees.size()][]);
	}
}
