import java.sql.*;
import java.util.Scanner;

public class Delete {

    public static void runDelete(Connection conn, Scanner in) throws SQLException {
        System.out.print("Please enter the ID of the animal you wish to delete: ");
        String animalId = in.next();

        deleteAnimal(conn, animalId);
        System.out.println();
    }

    private static void deleteAnimal(Connection conn, String animalId) throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call delete_animal_by_id(?)}");
        cStmt.setString(1, animalId);

        cStmt.executeQuery();
        System.out.println("Check that the following record has no entries to ensure record got deleted: ");
        Utils.getAnimalByProperty(conn, animalId, "animal_by_id");
    }
}
