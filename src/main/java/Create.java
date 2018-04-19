import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Create {

    /**
     * Allows the user to create an animal with certain features based on user input
     * @param conn
     * @param in
     * @throws SQLException
     */
    public static void runCreate(Connection conn, Scanner in) throws SQLException {

        // Prompt user to type a unique ID for the animal they wish to create
        System.out.print("Please enter the ID of the animal you wish to create: ");
        String animalId = in.next();
        while (Utils.animalIdAlreadyExists(conn, animalId)) {
            System.out.print("ID already exists. Please enter the ID of the animal you wish to create: ");
            animalId = in.next();
        }
        System.out.println();

        // Prompt the user to type a NAME for the animal
        System.out.print("Please enter the name of the animal you wish to create: ");
        String animalName = in.next();
        System.out.println();

        // Prompt the user to type the SEX of the animal from the list of valid sexes
        System.out.println("Please enter the sex of the animal you wish to create. The following are" +
                " the valid sexes: ");
        List<String> validSexes = Utils.getValidAnimalSexes(conn);
        for (String sex: validSexes) {
            System.out.println(sex);
        }
        String animalSex = in.nextLine();
        while (!validSexes.contains(animalSex)) {
            System.out.print("Please enter a valid sex from the provided list: ");
            animalSex = in.nextLine();
        }
        System.out.println();

        // Prompt the user to type the AGE of the animal
        System.out.print("Please type the age of the animal you wish to create in weeks, months, or years: ");
        String animalAge = in.nextLine();
        System.out.println();

        // Prompt the user to type if the animal is a cat or a dog
        System.out.print("Please type if the animal is a cat or a dog of the animal you wish to create: ");
        String animalSpecies = in.nextLine();
        while (!(animalSpecies.equals("cat") || animalSpecies.equals("dog"))) {
            System.out.print("Please enter either cat or dog: ");
            animalSpecies = in.nextLine();
        }
        System.out.println();

        // List the valid breeds and prompt the user to type a valid breed for the animal they're creating
        List<String> validBreeds = Utils.getBreedsByAnimalType(conn, animalSpecies);
        for (String breed: validBreeds) {
            System.out.println(breed);
        }
        System.out.println("Please enter the breed of the animal you wish to create. The above are " +
                "the valid breeds: ");
        String animalBreed = in.nextLine();
        while (!validBreeds.contains(animalBreed)) {
            System.out.print("Please enter a valid breed from the provided list: ");
            animalBreed = in.nextLine();
        }
        System.out.println();

        // Prompt the user to type the color of the animal
        System.out.print("Please enter the color of the animal you wish to create: ");
        String animalColor = in.nextLine();
        System.out.println();

        // Prompt the user to type the date the animal was discharged
        System.out.print("Please enter the date the animal you wish to create was discharged: ");
        String dateDischarged = in.nextLine();
        System.out.println();

        // Prompt the user to type the outcome of the animal based on the valid options
        System.out.println("Please enter the outcome type of the animal you wish to create. The following are" +
                " the valid outcome types: ");
        List<String> validOutcomeTypes = Utils.getValidOutcomeTypes(conn);
        for (String outcome: validOutcomeTypes) {
            System.out.println(outcome);
        }
        String outcomeType = in.nextLine();
        while (!validOutcomeTypes.contains(outcomeType)) {
            System.out.print("Please enter a valid outcome type from the provided list: ");
            outcomeType = in.nextLine();
        }
        System.out.println();

        // Prompt the user to type the outcome subtype of the animal based on the valid options (or they can choose
        // to not specify an outcome subtype)
        List<String> outcomeSubtypes = Utils.getValidOutcomeSubtypes(conn);
        for (String subtype: outcomeSubtypes) {
            System.out.println(subtype);
        }
        System.out.println("OPTIONAL: Enter the outcome subtype of the animal you wish to create. If you do not want" +
                " to choose a subtype, type NONE. Above are the valid subtypes: ");
        String outcomeSubtype = in.nextLine();
        while (!(outcomeSubtypes.contains(outcomeSubtype) || outcomeSubtype.equals("NONE"))) {
            System.out.print("Please enter a valid outcome subtype from the provided list: ");
            outcomeSubtype = in.nextLine();
        }
        if (outcomeSubtype.equals("NONE")){
            outcomeSubtype = "";
        }
        System.out.println();

        // Create the animal in the database based on the features specified by the user
        Create.createAnimal(conn, animalId, animalName, animalSex, animalAge, animalBreed, animalSpecies, animalColor,
                dateDischarged, outcomeType, outcomeSubtype);
    }

    /**
     * Creates an animal in the database based on specified features
     * @param conn
     * @param animalId
     * @param animalName
     * @param animalSex
     * @param animalAge
     * @param animalBreed
     * @param animalType
     * @param animalColor
     * @param dateDischarged
     * @param outcomeType
     * @param outcomeSubtype
     * @throws SQLException
     */
    private static void createAnimal(Connection conn, String animalId, String animalName, String animalSex,
                                    String animalAge, String animalBreed, String animalType, String animalColor,
                                    String dateDischarged, String outcomeType, String outcomeSubtype)
            throws SQLException {
        CallableStatement cStmt = conn.prepareCall("{call add_animal_data(?,?,?,?,?,?,?,?,?,?)}");
        cStmt.setString(1, animalId);
        cStmt.setString(2, animalName);
        cStmt.setString(3, animalSex);
        cStmt.setString(4, animalAge);
        cStmt.setString(5, animalBreed);
        cStmt.setString(6, animalType);
        cStmt.setString(7, animalColor);
        cStmt.setString(8, dateDischarged);
        cStmt.setString(9, outcomeType);
        cStmt.setString(10, outcomeSubtype);

        cStmt.executeQuery();

        System.out.println("The following animal information is persisted in the database: ");
        Utils.getAnimalByProperty(conn, animalId, "animal_by_id");
    }
}
