import java.sql.*;
import java.util.Scanner;

public class Read {

    // use this method to find the animals by a specific property
    // all stored procedures that filter on a specific value can be used here by plugging in searchQuery
    public static void getAnimalByProperty(Connection conn, String animalParam, String searchQuery) throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call "+ searchQuery +"(?)}");
        cStmt.setString(1, animalParam);

        ResultSet res = cStmt.executeQuery();
        ResultSetMetaData rsmd = res.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        // print column names
        for (int i = 1; i <= columnsNumber; i++) {
            System.out.print(rsmd.getColumnName(i));
            System.out.print(",  ");
        }
        System.out.println();

        while (res.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = res.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }

    public static void runRead(Connection conn, Scanner in) throws SQLException {
        System.out.print("Do you want to see the entire database of animals? Yes/No: ");
        String showAnimalDatabase = in.next();

        if (showAnimalDatabase.equals("Yes")) {
            Utils.getAllAnimals(conn);
            return;
        }
        System.out.print("Please select one of the following to filter animals on: id, age, breed, outcome_type, sex, species: ");
        String columnFilter = in.next();

        String filteringValue;
        if (columnFilter.equals("id")) {
            System.out.print("Please type the ID value you want to filter on: ");
            filteringValue = in.next();

            getAnimalByProperty(conn, filteringValue, "animal_by_id");
            System.out.println();
            System.out.println();
        }
        if (columnFilter.equals("age")) {
            System.out.print("Please type the age value you want to filter on. Please specify if it is in " +
                    "weeks, months, or years: ");
            in.nextLine();
            filteringValue = in.nextLine();

            getAnimalByProperty(conn, filteringValue, "animal_by_age");
            System.out.println();
            System.out.println();
        }
    }
}
