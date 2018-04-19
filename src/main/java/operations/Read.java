package operations;

import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Read {

    /**
     * Allows the user to find animals based on specified features, or to simply access the entire database
     * @param conn
     * @param in
     * @throws SQLException
     */
    public static void runRead(Connection conn, Scanner in) throws SQLException {

        // First offers the user to see the entire database - can say yes or no
        System.out.print("Do you want to see the entire database of animals? Yes/No: ");
        String showAnimalDatabase = in.next();

        if (showAnimalDatabase.equals("Yes")) {
            Utils.getAllAnimals(conn);
            System.out.println();

            System.out.print("Would you like to now explore animals by property? Yes/No: ");
            String keepExploring = in.next();
            if (keepExploring.equals("No")) {
                return;
            }
        }
        System.out.println();

        // Generate a list of all possible filters
        List<String> filters = new ArrayList<String>();
        filters.add("id");
        filters.add("age");
        filters.add("breed");
        filters.add("sex");
        filters.add("outcome_type");
        filters.add("species");

        // Prompts user to select a valid filter to filter the animal database on
        System.out.print("Please select one of the following to filter animals on: id, age, breed, outcome_type, sex, species: ");
        String columnFilter = in.next();
        while (!filters.contains(columnFilter)) {
            System.out.print("Please enter a valid attribute from the provided list: ");
            columnFilter = in.next();
        }
        System.out.println();
        String filteringValue;

        // If the column to filter on is ID, get the animals with the specified ID
        if (columnFilter.equals("id")) {
            System.out.print("Please type the ID value you want to filter on: ");
            filteringValue = in.next();

            Utils.getAnimalByProperty(conn, filteringValue, "animal_by_id");
        }
        // If the column to filter on is age, get the animals with the specified age
        else if (columnFilter.equals("age")) {
            System.out.print("Please type the age value you want to filter on. Please specify if it is in " +
                    "weeks, months, or years: ");
            in.nextLine();
            filteringValue = in.nextLine();

            Utils.getAnimalByProperty(conn, filteringValue, "animal_by_age");
        }
        // If the column to filter on is breed, get the animals with the specified breed
        else if (columnFilter.equals("breed")) {
            List<String> validBreeds = Utils.getAllBreeds(conn);
            for (String breed: validBreeds) {
                System.out.println(breed);
            }
            System.out.print("Please type the breed you want to filter on. Please select from the above list of valid " +
                    "breeds: ");
            in.nextLine();
            filteringValue = in.nextLine();
            while (!validBreeds.contains(filteringValue)) {
                System.out.print("Please enter a valid breed from the provided list: ");
                filteringValue = in.nextLine();
            }

            Utils.getAnimalByProperty(conn, filteringValue, "animal_by_breed");
        }
        // If the column to filter on is outcome type, get the animals with the specified outcome type
        else if (columnFilter.equals("outcome_type")) {
            List<String> outcomes = Utils.getValidOutcomeTypes(conn);
            for (String outcome : outcomes) {
                System.out.println(outcome);
            }
            System.out.print("Please type the outcome type you want to filter on. Please select from the above list of valid " +
                    "outcome types: ");
            in.nextLine();
            filteringValue = in.nextLine();
            while (!outcomes.contains(filteringValue)) {
                System.out.print("Please enter a valid outcome type from the provided list: ");
                filteringValue = in.nextLine();
            }

            Utils.getAnimalByProperty(conn, filteringValue, "animal_by_outcome_type");
        }
        // If the column to filter on is sex, get the animals with the specified sex
        else if (columnFilter.equals("sex")) {
            List<String> validSexes = Utils.getValidAnimalSexes(conn);
            for (String sex: validSexes) {
                System.out.println(sex);
            }
            System.out.print("Please type the animal sex you want to filter on. Please select from the above list of valid " +
                    "animal sexes: ");
            in.nextLine();
            filteringValue = in.nextLine();
            while (!validSexes.contains(filteringValue)) {
                System.out.print("Please enter a valid animal sex from the provided list: ");
                filteringValue = in.nextLine();
            }

            Utils.getAnimalByProperty(conn, filteringValue, "animal_by_sex");
        }
        // If the column to filter on is species, get the animals with the specified species
        else if (columnFilter.equals("species")) {
            System.out.print("Please type the species value you want to filter on (Cat or Dog): ");
            filteringValue = in.next();

            Utils.getAnimalByProperty(conn, filteringValue, "animal_by_species");
        } else {
            System.out.println("Cannot process request, returning to home.");
        }
    }
}
