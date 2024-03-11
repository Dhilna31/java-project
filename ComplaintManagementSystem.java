import java.sql.*;
import java.util.Scanner;

public class ComplaintManagementSystem {
   
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/complaint_db?characterEncoding=utf8";
    static final String USERNAME = "root";
    static final String PASSWORD = "";

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
	try { 
            Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the database.");

            while (true) {
                System.out.println("\n1. Register\n2. Login\n3. AdminRegister\n4. Adminlogin\n5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        registerUser(connection);
                        break;
                    case 2:
                        loginUser(connection);
                        break;
		    case 3:
                        registerAdmin(connection);
                        break;
                    case 4:
                        loginAdmin(connection);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
	} catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC driver: " + e.getMessage());
        }
    }

    private static void registerUser(Connection connection) throws SQLException {
        System.out.println("\n--- User Registration ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("Failed to register user.");
            }
        }
    }

    private static void loginUser(Connection connection) throws SQLException {
        System.out.println("\n--- User Login ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String selectQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login successful.");
                showComplaintMenu(connection);
            } else {
                System.out.println("Invalid username or password.");
            }
        }
    }


    private static void showAdminComplaintMenu(Connection connection) throws SQLException {
        while (true) {
            System.out.println("\n1. View All Complaints\n2. Update Complaints\n3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewAllComplaints(connection);
                    break;
		case 2:
                    updateStatus(connection);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void updateStatus(Connection connection) throws SQLException {
        System.out.println("\n--- Update Status ---");
        System.out.print("Enter complaint ID: ");
        int complaintId = scanner.nextInt();
        scanner.nextLine(); 
	System.out.println("Please enter any status from \n1. OPEN \n2. IN_PROGRESS \n3. COMPLETED");
        System.out.print("Enter your option: ");
        String newStatus = scanner.nextLine();
	String value = null;
	switch (newStatus) {
		case "1": 
			value = "OPEN";
			break;
		case "2":
			value="IN PROGRESS";
			break;
		case "3":
			value="COMPLETED";
			break;
		default:
			System.out.println("select 1,2 or 3");
	}
		

        String updateQuery = "UPDATE complaints SET status = ? WHERE complaint_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, complaintId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Status updated successfully.");
            } else {
                System.out.println("Failed to update status. Complaint ID not found.");
            }
        }
    }

    private static void showComplaintMenu(Connection connection) throws SQLException {
        while (true) {
            System.out.println("\n1. Submit Complaint\n2. View Complaints\n3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    submitComplaint(connection);
                    break;
                case 2:
                    viewComplaints(connection);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void submitComplaint(Connection connection) throws SQLException {
        System.out.println("\n--- Submit Complaint ---");
        System.out.print("Enter complaint description: ");
        String description = scanner.nextLine();
	System.out.print("Enter userid to register complaint: ");
        String userId = scanner.nextLine();

        String insertQuery = "INSERT INTO complaints (description,user_id,status) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, description);
	    preparedStatement.setString(2, userId);
	    preparedStatement.setString(3,"NEW");
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Complaint submitted successfully.");
            } else {
                System.out.println("Failed to submit complaint.");
            }
        }
    }
 private static void registerAdmin(Connection connection) throws SQLException {
        System.out.println("\n--- Admin Registration ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String insertQuery = "INSERT INTO admin (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin registered successfully.");
            } else {
                System.out.println("Failed to register admin.");
            }
        }
    }

    private static void loginAdmin(Connection connection) throws SQLException {
        System.out.println("\n--- Admin Login ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String selectQuery = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login successful.");
                showAdminComplaintMenu(connection);
            } else {
                System.out.println("Invalid username or password.");
            }
        }
    }
  private static void viewAllComplaints(Connection connection) throws SQLException {
        System.out.println("\n--- View Complaints ---");
        String selectQuery = "SELECT * FROM complaints";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("complaint_id");
                String description = resultSet.getString("description");
		String status = resultSet.getString("status");
                System.out.println("Complaint Id : " + id + ", Description: " + description + ", Status : " + status ); }
        }
    }

    private static void viewComplaints(Connection connection) throws SQLException {
        System.out.println("\n--- View Complaints ---");
	System.out.print("Enter userid to register complaint: ");
        String userId = scanner.nextLine();
        String selectQuery = "SELECT * FROM complaints where user_id="+userId;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("complaint_id");
                String description = resultSet.getString("description");
		String status = resultSet.getString("status");
                System.out.println("Complaint Id : " + id + ", Description: " + description + ", Status : " + status );
            }
        }
    }
}
