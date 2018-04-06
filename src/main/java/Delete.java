import java.sql.*;
import java.util.Scanner;

public class Delete {

    public static void deleteAnimal(Connection conn, String animalId) throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call delete_animal_by_id(?)}");
        cStmt.setString(1, animalId);

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

    public static void runDelete(Connection conn, Scanner in) throws SQLException {
    }
}
