import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

// Workout.java
// Represents a single workout entry with exercise details and a timestamp.
class Workout {
    private String exerciseName;
    private int sets;
    private int reps;
    private double weight; // Weight in kg or lbs
    private Date date;

    // Constructor to initialize a new Workout object.
    public Workout(String exerciseName, int sets, int reps, double weight) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = new Date(); // Automatically set the current date/time
    }

    // Getter for exercise name.
    public String getExerciseName() {
        return exerciseName;
    }

    // Getter for sets.
    public int getSets() {
        return sets;
    }

    // Getter for reps.
    public int getReps() {
        return reps;
    }

    // Getter for weight.
    public double getWeight() {
        return weight;
    }

    // Getter for the workout date.
    public Date getDate() {
        return date;
    }

    // Overrides the toString method to provide a formatted string representation of the workout.
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("Exercise: %s, Sets: %d, Reps: %d, Weight: %.2f, Date: %s",
                exerciseName, sets, reps, weight, formatter.format(date));
    }
}

// GymTracker.java
// Manages a collection of Workout objects and provides methods to add and list them.
public class GymTracker {
    private List<Workout> workouts; // Stores all workout entries
    private Scanner scanner;        // Scanner for reading user input

    // Constructor to initialize the GymTracker.
    public GymTracker() {
        this.workouts = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    // Displays the main menu options to the user.
    private void displayMenu() {
        System.out.println("\n--- Gym Tracking System Menu ---");
        System.out.println("1. Add New Workout");
        System.out.println("2. View Workout History");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    // Adds a new workout entry based on user input.
    public void addWorkout() {
        System.out.println("\n--- Add New Workout ---");
        System.out.print("Enter Exercise Name: ");
        String exerciseName = scanner.nextLine();

        int sets = 0;
        while (true) {
            System.out.print("Enter Number of Sets: ");
            try {
                sets = Integer.parseInt(scanner.nextLine());
                if (sets <= 0) {
                    System.out.println("Sets must be a positive number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number for sets.");
            }
        }

        int reps = 0;
        while (true) {
            System.out.print("Enter Number of Reps: ");
            try {
                reps = Integer.parseInt(scanner.nextLine());
                if (reps <= 0) {
                    System.out.println("Reps must be a positive number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number for reps.");
            }
        }

        double weight = 0.0;
        while (true) {
            System.out.print("Enter Weight (e.g., 75.5): ");
            try {
                weight = Double.parseDouble(scanner.nextLine());
                if (weight < 0) {
                    System.out.println("Weight cannot be negative.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number for weight.");
            }
        }

        Workout newWorkout = new Workout(exerciseName, sets, reps, weight);
        workouts.add(newWorkout);
        System.out.println("Workout added successfully!");
    }

    // Displays all recorded workout entries.
    public void viewWorkoutHistory() {
        System.out.println("\n--- Workout History ---");
        if (workouts.isEmpty()) {
            System.out.println("No workouts logged yet. Add your first workout!");
        } else {
            // Display workouts in reverse chronological order (most recent first)
            for (int i = workouts.size() - 1; i >= 0; i--) {
                System.out.println(workouts.get(i));
            }
        }
    }

    // The main application loop.
    public void run() {
        int choice;
        do {
            displayMenu();
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = -1; // Invalid choice
            }

            switch (choice) {
                case 1:
                    addWorkout();
                    break;
                case 2:
                    viewWorkoutHistory();
                    break;
                case 3:
                    System.out.println("Exiting Gym Tracking System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
        scanner.close(); // Close the scanner when the application exits
    }

    // Main method to start the application.
    public static void main(String[] args) {
        GymTracker app = new GymTracker();
        app.run();
    }
}

