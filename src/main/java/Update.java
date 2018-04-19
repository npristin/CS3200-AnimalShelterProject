import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Update {

    public static void runUpdate(Connection conn, Scanner in) throws SQLException {

        System.out.print("Please enter the ID of the animal you wish to update: ");
        String animalId = in.next();
        while (!Utils.animalIdAlreadyExists(conn, animalId)) {
            System.out.print("Please enter a valid ID of the animal you want to update: ");
            animalId = in.next();
        }
        System.out.println();

        List<String> fields = new ArrayList<String>();
        fields.add("age");
        fields.add("color");
        fields.add("breed");
        fields.add("date_discharged");
        fields.add("name");
        fields.add("sex");
        fields.add("outcome_type");
        fields.add("outcome_subtype");

        System.out.println("Of the following, which attribute of the animal do you wish to update? - " +
                "age, color, breed, date_discharged, name, sex, outcome_type, outcome_subtype: ");
        String fieldToUpdate = in.next();
        while (!fields.contains(fieldToUpdate)) {
            System.out.print("Please enter a valid attribute from the provided list: ");
            fieldToUpdate = in.next();
        }
        System.out.println();

        String newFieldValue;
        if (fieldToUpdate.equals("age")) {
            System.out.print("Please write the new field value you wish to update to. Please specify if it is in " +
                    "weeks, months, or years: ");
            in.nextLine();
            newFieldValue = in.nextLine();

            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_age");
        }
        else if (fieldToUpdate.equals("color")) {
            System.out.println("Please write the new field value you wish to update to: ");
            in.nextLine();
            newFieldValue = in.nextLine();

            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_color");
        }
        else if (fieldToUpdate.equals("breed")) {
            String animalSpecies = Utils.getAnimalSpeciesByAnimalId(conn, animalId);
            List<String> validBreeds = Utils.getBreedsByAnimalType(conn, animalSpecies);
            for (String breed: validBreeds) {
                System.out.println(breed);
            }
            System.out.print("Please write the new field value you wish to update to. Please select one of the valid " +
                    "breeds from above: ");
            in.nextLine();
            newFieldValue = in.nextLine();
            while (!validBreeds.contains(newFieldValue)) {
                System.out.print("Please enter a valid breed from the provided list: ");
                newFieldValue = in.nextLine();
            }
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_breed");
        }
        else if (fieldToUpdate.equals("date_discharged")) {
            System.out.println("Please write the new field value you wish to update to: ");
            in.nextLine();
            newFieldValue = in.nextLine();

            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_date_discharged");
        }
        else if (fieldToUpdate.equals("name")) {
            System.out.println("Please write the new field value you wish to update to: ");
            in.nextLine();
            newFieldValue = in.nextLine();

            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_name");
        }
        else if (fieldToUpdate.equals("sex")) {
            List<String> validSexes = Utils.getValidAnimalSexes(conn);
            for (String sex: validSexes) {
                System.out.println(sex);
            }
            System.out.print("Please write the new field value you wish to update to. Make sure to select one of " +
                    "the above valid sexes: ");
            in.nextLine();
            newFieldValue = in.nextLine();
            while (!validSexes.contains(newFieldValue)) {
                System.out.print("Please enter a valid sex from the provided list: ");
                newFieldValue = in.nextLine();
            }
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_sex");
        }
        else if (fieldToUpdate.equals("outcome_type")) {
            List<String> validOutcomeTypes = Utils.getValidOutcomeTypes(conn);
            for (String outcome: validOutcomeTypes) {
                System.out.println(outcome);
            }
            System.out.print("Please write the new field value you wish to update to: Make sure to select one of " +
                    "the above valid outcome types: ");
            in.nextLine();
            newFieldValue = in.nextLine();
            while (!validOutcomeTypes.contains(newFieldValue)) {
                System.out.print("Please enter a valid outcome type from the provided list: ");
                newFieldValue = in.nextLine();
            }
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_outcome_type");
        }
        else if (fieldToUpdate.equals("outcome_subtype")) {
            List<String> outcomeSubtypes = Utils.getValidOutcomeSubtypes(conn);
            for (String subtype: outcomeSubtypes) {
                System.out.println(subtype);
            }
            System.out.print("Please write the new field value you wish to update to. Make sure to select one of " +
                    "the above valid outcome subtypes: ");
            in.nextLine();
            newFieldValue = in.nextLine();
            while (!(outcomeSubtypes.contains(newFieldValue))) {
                System.out.print("Please enter a valid outcome subtype from the provided list: ");
                newFieldValue = in.nextLine();
            }
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_outcome_subtype");
        } else {
            System.out.println("Cannot process request, returning to home.");
        }
    }

    // use this method to update an animal by a specific property
    // all stored procedures that update on a specific value can be used here by plugging in searchQuery
    private static void updateAnimalProperty(Connection conn, String animalId, String newFieldValue, String searchQuery)
            throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call "+ searchQuery +"(?,?)}");
        cStmt.setString(1, animalId);
        cStmt.setString(2, newFieldValue);

        cStmt.executeQuery();
        System.out.println("The following animal information is updated in the database: ");
        Utils.getAnimalByProperty(conn, animalId, "animal_by_id");
    }
}
