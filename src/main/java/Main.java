import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    /**
     * Connect to the DB and run the application
     * @param args
     */
    public static void main(String[] args) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the MySQL username: ");
        String username = in.next();
        System.out.print("Please enter the MySQL password: ");
        String password = in.next();

        Application app = new Application(username, password);
        app.run();
    }
}
