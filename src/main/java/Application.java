import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Application {

    // The name of the MySQL account/user to use
    private final String userName;

    // The password for the MySQL account
    private final String password;

    // The name of the computer/server running MySQL
    private final String serverName = "localhost";

    // The port of the MySQL server (default is 3306)
    private final int portNumber = 3306;

    // The name of the database that is being used
    private final String dbName = "animal_shelter";

    /**
     * Constructor for creating an Application from a username and password
     * @param userName
     * @param password
     */
    public Application(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Creates a connection to the database
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection conn;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        conn = DriverManager.getConnection("jdbc:mysql://"
                        + this.serverName + ":" + this.portNumber + "/" + this.dbName + "?characterEncoding=UTF-8&useSSL=false",
                connectionProps);

        return conn;
    }

    /**
     * Connect to the MySQL database and starts the application, prompting the user to select an action
     * @throws SQLException
     */
    public void run() throws SQLException {

        // Connect to MySQL
        Connection conn;
        try {
            conn = this.getConnection();
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to the database");
            return;
        }
        System.out.println();
        System.out.println("Welcome to the Austin Animal Center database, a repository of over 25,000 animals!");
        System.out.println();

        // Scanner used for reading user input
        Scanner in = new Scanner(System.in);

        // Prompt the user for a command until they choose to exit the program
        while (true) {
            // Gives the user options to CREATE, READ, UPDATE, and DELETE a record from the animal_shelter database
            System.out.println("You may add an animal by typing CREATE, delete an animal by typing DELETE, " +
                    "update an animal by typing UPDATE, and find animals by property by typing READ");
            System.out.println("Please type END if you are done and wish to exit");

            String command = in.next();

            // If user types END, simply exit the program
            if (command.equals("END")) {
                conn.close();
                System.out.println();
                System.out.println("Thank you for visiting the Austin Animal Shelter database! See you later!");
                System.exit(0);
            }
            if (command.equals("CREATE")) {
                System.out.println();
                Create.runCreate(conn, in);
                System.out.println();
            }
            if (command.equals("DELETE")) {
                System.out.println();
                Delete.runDelete(conn, in);
                System.out.println();
            }
            if (command.equals("UPDATE")) {
                System.out.println();
                Update.runUpdate(conn, in);
                System.out.println();
            }
            if (command.equals("READ")) {
                System.out.println();
                Read.runRead(conn, in);
                System.out.println();
            }
        }
    }
}
