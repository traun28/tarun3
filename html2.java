import javax.swing.*; // Import Swing components for GUI
import java.awt.*;      // Import AWT for layout managers and basic GUI elements
import java.awt.event.ActionEvent; // Event handling for buttons
import java.awt.event.ActionListener; // Listener for button clicks
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

// Workout.java (re-used from previous console app, but included here for completeness)
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
    public String getExerciseName() { return exerciseName; }
    // Getter for sets.
    public int getSets() { return sets; }
i    // Getter for reps.
    public int getReps() { return reps; }
    // Getter for weight.
    public double getWeight() { return weight; }
    // Getter for the workout date.
    public Date getDate() { return date; }

    // Overrides the toString method to provide a formatted string representation of the workout.
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("Exercise: %s, Sets: %d, Reps: %d, Weight: %.2f, Date: %s",
                exerciseName, sets, reps, weight, formatter.format(date));
    }
}

// GymTrackerGUI.java
// Main class for the GUI Gym Tracking Application using Swing.
public class GymTrackerGUI extends JFrame implements ActionListener {
    private List<Workout> workouts; // Stores all workout entries
    private JTextField exerciseNameField, setsField, repsField, weightField; // Input fields
    private JTextArea displayArea; // Area to display workout history
    private JButton addWorkoutButton, viewHistoryButton; // Action buttons

    // Constructor to set up the GUI frame and components.
    public GymTrackerGUI() {
        super("Gym Tracking System"); // Set the window title
        workouts = new ArrayList<>(); // Initialize the workout list

        // Set up the main frame properties
        setSize(700, 500); // Set initial window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout for main layout
        setLocationRelativeTo(null); // Center the window on the screen

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5 rows, 2 columns, with gaps
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Workout")); // Add a titled border

        // Initialize input fields
        exerciseNameField = new JTextField(20);
        setsField = new JTextField(5);
        repsField = new JTextField(5);
        weightField = new JTextField(5);

        // Add labels and fields to the input panel
        inputPanel.add(new JLabel("Exercise Name:"));
        inputPanel.add(exerciseNameField);
        inputPanel.add(new JLabel("Sets:"));
        inputPanel.add(setsField);
        inputPanel.add(new JLabel("Reps:"));
        inputPanel.add(repsField);
        inputPanel.add(new JLabel("Weight (kg/lbs):"));
        inputPanel.add(weightField);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center buttons with gaps
        addWorkoutButton = new JButton("Add Workout");
        viewHistoryButton = new JButton("View Workout History");

        // Add action listeners to buttons
        addWorkoutButton.addActionListener(this);
        viewHistoryButton.addActionListener(this);

        // Add buttons to the button panel
        buttonPanel.add(addWorkoutButton);
        buttonPanel.add(viewHistoryButton);

        // Add the button panel to the input panel (below the fields)
        inputPanel.add(buttonPanel); // This will occupy the last row of the GridLayout

        // --- Display Area ---
        displayArea = new JTextArea(15, 50); // Rows, columns
        displayArea.setEditable(false); // Make it read-only
        displayArea.setLineWrap(true); // Enable line wrapping
        displayArea.setWrapStyleWord(true); // Wrap at word boundaries
        JScrollPane scrollPane = new JScrollPane(displayArea); // Add scrollability
        scrollPane.setBorder(BorderFactory.createTitledBorder("Workout History")); // Titled border

        // Add panels to the main frame
        add(inputPanel, BorderLayout.NORTH); // Input panel at the top
        add(scrollPane, BorderLayout.CENTER); // Display area in the center

        setVisible(true); // Make the frame visible
    }

    // Handles button click events.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addWorkoutButton) {
            addNewWorkout();
        } else if (e.getSource() == viewHistoryButton) {
            displayWorkoutHistory();
        }
    }

    // Logic to add a new workout from GUI input.
    private void addNewWorkout() {
        try {
            String exerciseName = exerciseNameField.getText().trim();
            int sets = Integer.parseInt(setsField.getText().trim());
            int reps = Integer.parseInt(repsField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());

            if (exerciseName.isEmpty() || sets <= 0 || reps <= 0 || weight < 0) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid data for all fields (sets, reps must be positive, weight non-negative).",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Workout newWorkout = new Workout(exerciseName, sets, reps, weight);
            workouts.add(newWorkout);
            JOptionPane.showMessageDialog(this, "Workout added successfully!");

            // Clear fields after adding
            exerciseNameField.setText("");
            setsField.setText("");
            repsField.setText("");
            weightField.setText("");

            displayWorkoutHistory(); // Refresh history display
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid number format. Please enter numbers for sets, reps, and weight.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Logic to display workout history in the text area.
    private void displayWorkoutHistory() {
        displayArea.setText(""); // Clear previous content
        if (workouts.isEmpty()) {
            displayArea.append("No workouts logged yet. Add your first workout!");
        } else {
            // Display workouts in reverse chronological order (most recent first)
            for (int i = workouts.size() - 1; i >= 0; i--) {
                displayArea.append(workouts.get(i).toString() + "\n");
            }
        }
    }

    // Main method to run the GUI application.
    public static void main(String[] args) {
        // Ensure GUI updates are done on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new GymTrackerGUI();
        });
    }
}
/ Workout.java (re-used from previous console app, but included here for completeness)
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
    public String getExerciseName() { return exerciseName; }
    // Getter for sets.
    public int getSets() { return sets; }
    // Getter for reps.
    public int getReps() { return reps; }
    // Getter for weight.
    public double getWeight() { return weight; }
    // Getter for the workout date.
    public Date getDate() { return date; }

    // Overrides the toString method to provide a formatted string representation of the workout.
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
@@@

