import java.sql.*;

public class Update {

    // use this method to update an animal by a specific property
    // all stored procedures that update on a specific value can be used here by plugging in searchQuery
    public static void updateAnimalProperty(Connection conn, String animalId, String newFieldValue, String searchQuery)
            throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call "+ searchQuery +"(?,?)}");
        cStmt.setString(1, animalId);
        cStmt.setString(2, newFieldValue);

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
}
