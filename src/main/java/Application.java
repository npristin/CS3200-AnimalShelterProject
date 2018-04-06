import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Application {

    /** The name of the MySQL account to use (or empty for anonymous) */
    private final String userName;

    /** The password for the MySQL account (or empty for anonymous) */
    private final String password;

    /** The name of the computer running MySQL */
    private final String serverName = "localhost";

    /** The port of the MySQL server (default is 3306) */
    private final int portNumber = 3306;

    /** The name of the database we are testing with (this default is installed with MySQL) */
    private final String dbName = "animal_shelter";

    public Application(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Get a new database connection
     *
     * @return
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
     * Connect to MySQL and run the application
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

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("You may add an animal by typing CREATE, delete an animal by typing DELETE," +
                    "update an animal by typing UPDATE, and find an animal by typing READ");
            System.out.println("Please type END if you are done and wish to exit");

            String command = in.next();

            if (command.equals("END")) {
                conn.close();
                System.exit(0);
            }
            if (command.equals("CREATE")) {
                Create.runCreate(conn, in);
            }
            if (command.equals("DELETE")) {
                Delete.runDelete(conn, in);
            }
            if (command.equals("UPDATE")) {
                Update.runUpdate(conn, in);
            }
            if (command.equals("READ")) {
                Update.runUpdate(conn, in);
            }
            conn.close();
        }
    }
}
