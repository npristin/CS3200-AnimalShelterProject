package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * Gets all the animals from the database
     * @param conn
     * @throws SQLException
     */
    public static void getAllAnimals(Connection conn) throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call get_all_animals()}");
        ResultSet res = cStmt.executeQuery();

        // print column names
        String colNames = String.format("%-15s %-15s %-30s %-20s %-20s %-20s %-15s %-10s %-40s %-20s",
            "Animal ID", "Name", "Date Discharged", "Outcome TYpe", "Outcome Subtype", "Sex", "Age", "Species",
            "Breed", "Color");
        System.out.println(colNames);

        while(res.next()){
            String record = String.format("%-15s %-15s %-30s %-20s %-20s %-20s %-15s %-10s %-40s %-20s",
                    res.getString(1), res.getString(2), res.getString(3),
                    res.getString(4), res.getString(5), res.getString(6),
                    res.getString(7), res.getString(8), res.getString(9),
                    res.getString(10));
            System.out.println(record);
        }
    }

    /**
     * Gets the list of valid animal sexes from the database
     * @param conn
     * @return List of String of valid animal sexes
     * @throws SQLException
     */
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

    /**
     * Gets the list of valid outcome types from the database
     * @param conn
     * @return List of String of valid animal outcome types
     * @throws SQLException
     */
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

    /**
     * Gets the list of valid outcome subtype from the database
     * @param conn
     * @return List of String of valid animal outcome subtypes
     * @throws SQLException
     */
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

    /**
     * Gets the list of all the valid breeds from the database
     * @param conn
     * @return List of String of valid animal breeds
     * @throws SQLException
     */
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

    /**
     * Gets a list of breeds for a specific animal (certain breeds associated with cat or dog)
     * @param conn
     * @param animalType
     * @return List of String of valid animal breeds for a specific animal type
     * @throws SQLException
     */
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

    /**
     * Gets the species of an animal with a specified animal ID
     * @param conn
     * @param animalId
     * @return String that returns the species of an animal
     * @throws SQLException
     */
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

    /**
     * Determines if a record with the specified animal ID already exists, and is thus not unique
     * @param conn
     * @param animalId
     * @return Boolean whether an animal ID already exists
     * @throws SQLException
     */
    public static boolean animalIdAlreadyExists(Connection conn, String animalId) throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call animal_by_id(?)}");
        cStmt.setString(1, animalId);

        ResultSet res = cStmt.executeQuery();
        List<String> animalProps = new ArrayList<String>();
        while(res.next()){
            String ID = res.getString("animal_id");
            animalProps.add(ID);
        }
        return animalProps.size() != 0;
    }

    // use this method to find the animals by a specific property
    // all stored procedures that filter on a specific value can be used here by plugging in searchQuery

    /**
     * Gets the animal(s) by the specified property. All stored procedures that filter animals on a specific property
     * and value can be used in this method simply by specifying the stored procedure name in the storedProcedure field
     * @param conn
     * @param animalParam
     * @param storedProcedure
     * @throws SQLException
     */
    public static void getAnimalByProperty(Connection conn, String animalParam, String storedProcedure) throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call " + storedProcedure + "(?)}");
        cStmt.setString(1, animalParam);
        ResultSet res = cStmt.executeQuery();

        // print column names
        String colNames = String.format("%-15s %-15s %-30s %-20s %-20s %-20s %-15s %-10s %-40s %-20s",
                "Animal ID", "Name", "Date Discharged", "Outcome TYpe", "Outcome Subtype", "Sex", "Age", "Species",
                "Breed", "Color");
        System.out.println(colNames);

        while(res.next()){
            String record = String.format("%-15s %-15s %-30s %-20s %-20s %-20s %-15s %-10s %-40s %-20s",
                    res.getString(1), res.getString(2), res.getString(3),
                    res.getString(4), res.getString(5), res.getString(6),
                    res.getString(7), res.getString(8), res.getString(9),
                    res.getString(10));
            System.out.println(record);
        }
    }
}
