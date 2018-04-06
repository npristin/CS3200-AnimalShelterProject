import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void getAllAnimals(Connection conn) throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM animal");
        ResultSetMetaData rsmd = res.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        // print column names
        for (int i = 1; i <= columnsNumber; i++) {
            System.out.print(rsmd.getColumnName(i));
            System.out.print(",  ");
        }
        System.out.println();

        while(res.next()){
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = res.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }

    public static List<String> getValidAnimalSexes(Connection conn) throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT DISTINCT sex FROM animal_sex");

        List<String> sexes = new ArrayList<String>();
        while(res.next()){
            String sex = res.getString("sex");
            sexes.add(sex);
        }
        return sexes;
    }

    public static List<String> getValidOutcomeTypes(Connection conn) throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT type FROM outcome_type");

        List<String> outcomes = new ArrayList<String>();
        while(res.next()){
            String outcome = res.getString("type");
            outcomes.add(outcome);
        }
        return outcomes;
    }

    public static List<String> getValidOutcomeSubtypes(Connection conn) throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT subtype FROM outcome_subtype");

        List<String> outcomes = new ArrayList<String>();
        while(res.next()){
            String outcome = res.getString("subtype");
            outcomes.add(outcome);
        }
        return outcomes;
    }

    public static List<String> getAllBreeds(Connection conn) throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT breed FROM animal_type");

        List<String> breeds = new ArrayList<String>();
        while(res.next()){
            String breed = res.getString("breed");
            breeds.add(breed);
        }
        return breeds;
    }

    public static List<String> getBreedsByAnimalType(Connection conn, String animalType) throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call get_breeds_by_animal_type(?)}");
        cStmt.setString(1, animalType);

        ResultSet res = cStmt.executeQuery();
        List<String> breeds = new ArrayList<String>();
        while(res.next()){
            String breed = res.getString("breed");
            breeds.add(breed);
        }
        return breeds;
    }

    public static String getAnimalSpeciesByAnimalId(Connection conn, String animalId) throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call get_animal_species(?)}");
        cStmt.setString(1, animalId);

        ResultSet res = cStmt.executeQuery();
        List<String> species = new ArrayList<String>();
        while(res.next()){
            String speciesType = res.getString("species");
            species.add(speciesType);
        }
        return species.get(0);
    }
}
