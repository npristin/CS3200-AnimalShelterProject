import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Update {

    /**
     * Allows the user to update a feature about an animal
     * @param conn
     * @param in
     * @throws SQLException
     */
    public static void runUpdate(Connection conn, Scanner in) throws SQLException {

        // Prompts user to type the ID of an existing, valid animal
        System.out.print("Please enter the ID of the animal you wish to update: ");
        String animalId = in.next();
        while (!Utils.animalIdAlreadyExists(conn, animalId)) {
            System.out.print("Please enter a valid ID of the animal you want to update: ");
            animalId = in.next();
        }
        System.out.println();

        // Compile a list of all the fields available for update
        List<String> fields = new ArrayList<String>();
        fields.add("age");
        fields.add("color");
        fields.add("breed");
        fields.add("date_discharged");
        fields.add("name");
        fields.add("sex");
        fields.add("outcome_type");
        fields.add("outcome_subtype");

        // Prompts the user to select the field/attribute they wish to update
        System.out.println("Of the following, which attribute of the animal do you wish to update? - " +
                "age, color, breed, date_discharged, name, sex, outcome_type, outcome_subtype: ");
        String fieldToUpdate = in.next();
        while (!fields.contains(fieldToUpdate)) {
            System.out.print("Please enter a valid attribute from the provided list: ");
            fieldToUpdate = in.next();
        }
        System.out.println();
        String newFieldValue;

        // If the field to update is age, prompt the user for the new age and update the property
        if (fieldToUpdate.equals("age")) {
            System.out.print("Please write the new field value you wish to update to. Please specify if it is in " +
                    "weeks, months, or years: ");
            in.nextLine();
            newFieldValue = in.nextLine();

            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_age");
        }
        // If the field to update is color, prompt the user for the new color and update the property
        else if (fieldToUpdate.equals("color")) {
            System.out.println("Please write the new field value you wish to update to: ");
            in.nextLine();
            newFieldValue = in.nextLine();

            // updates the property
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_color");
        }
        // If the field to update is breed, prompt the user to select a valid breed and update the field
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

            // updates the property
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_breed");
        }
        // If the field to update is date discharted, prompt the user for the new date and update the property
        else if (fieldToUpdate.equals("date_discharged")) {
            System.out.println("Please write the new field value you wish to update to: ");
            in.nextLine();
            newFieldValue = in.nextLine();

            // updates the property
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_date_discharged");
        }
        // If the field to update is name, prompt the user for the new name and update the property
        else if (fieldToUpdate.equals("name")) {
            System.out.println("Please write the new field value you wish to update to: ");
            in.nextLine();
            newFieldValue = in.nextLine();

            // updates the property
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_name");
        }
        // If the field to update is sex, prompt the user for the new sex from the list of valid sexes and update
        // the animal property
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

            // updates the property
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_sex");
        }
        // If the field to update is outcome type, prompt the user for the new outcome type from a list of valid
        // outcome types and update the property
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

            // updates the property
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_outcome_type");
        }
        // If the field to update is outcome subtype, prompt the user for the new outcome subtype from a list of valid
        // outcome subtypes and update the property
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

            // updates the property
            updateAnimalProperty(conn, animalId, newFieldValue, "update_animal_outcome_subtype");
        } else {
            System.out.println("Cannot process request, returning to home.");
        }
    }

    // use this method to update an animal by a specific property
    // all stored procedures that update on a specific value can be used here by plugging in searchQuery

    /**
     * Updates an animal by a specific property. All stored procedures that update on a specific value can be used
     * in this method simply by specifying the stored procedure name in the storedProcedure field
     * @param conn
     * @param animalId
     * @param newFieldValue
     * @param storedProcedure
     * @throws SQLException
     */
    private static void updateAnimalProperty(Connection conn, String animalId, String newFieldValue, String storedProcedure)
            throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call "+ storedProcedure +"(?,?)}");
        cStmt.setString(1, animalId);
        cStmt.setString(2, newFieldValue);

        cStmt.executeQuery();
        System.out.println("The following animal information is updated in the database: ");
        Utils.getAnimalByProperty(conn, animalId, "animal_by_id");
    }
}
