import java.sql.*;

public class Read {

    // use this method to find the animals by a specific property
    // all stored procedures that filter on a specific value can be used here by plugging in searchQuery
    public static void getAnimalByProperty(Connection conn, String animalId, String searchQuery) throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call "+ searchQuery +"(?)}");
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
}
