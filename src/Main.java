import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Main {

    private static final int TOTAL_SPOTS = 500; // Total number of parking spots
    private static final int MIN_WEIGHT = 800; // Minimum vehicle weight (in kg)
    private static final int MAX_WEIGHT = 3500; // Maximum vehicle weight (in kg)

    public static void updateCSV(String csvFilePath) throws IOException {
        FileWriter writer = new FileWriter(csvFilePath);
        writer.write("parking_spot_id,vehicle_weight\n");

        // Generate random data for all spots
        Random random = new Random();
        for (int i = 1; i <= TOTAL_SPOTS; i++) {
            int vehicleWeight;
            if (random.nextBoolean()) {
                vehicleWeight = 0; // Set weight to 0
            } else {
                vehicleWeight = random.nextInt(MAX_WEIGHT - MIN_WEIGHT + 1) + MIN_WEIGHT; // Random weight within specified range
            }
            writer.write(i + "," + vehicleWeight + "\n"); // Write parking spot ID and vehicle weight
        }
        writer.close();
        System.out.println("CSV file '" + csvFilePath + "' created with " + TOTAL_SPOTS + " rows.");
    }

    public static void updateRandomSpots(String csvFilePath) throws IOException {
        Random random = new Random();
        int numberOfSpotsToChange = random.nextInt(100) + 1; // Random number between 1 and 100

        // Create an array to store current weights
        int[] currentWeights = new int[TOTAL_SPOTS];

        // Read current weights from the existing CSV file
        BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
        String line;
        reader.readLine(); // Skip header
        for (int i = 0; i < TOTAL_SPOTS; i++) {
            line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                currentWeights[i] = Integer.parseInt(parts[1]); // Store the current vehicle weight
            }
        }
        reader.close();

        // Update random spots with new weights
        for (int i = 0; i < numberOfSpotsToChange; i++) {
            int spotId = random.nextInt(TOTAL_SPOTS) + 1; // Randomly select a parking spot ID
            int newVehicleWeight;
            if (random.nextBoolean()) {
                newVehicleWeight = 0; // Set weight to 0
            } else {
                newVehicleWeight = random.nextInt(MAX_WEIGHT - MIN_WEIGHT + 1) + MIN_WEIGHT; // Random weight within specified range
            }
            currentWeights[spotId - 1] = newVehicleWeight; // Update only the selected weight
        }

        // Rewrite the CSV file with the updated weights
        FileWriter writer = new FileWriter(csvFilePath);
        writer.write("parking_spot_id,vehicle_weight\n");
        for (int i = 1; i <= TOTAL_SPOTS; i++) {
            writer.write(i + "," + currentWeights[i - 1] + "\n"); // Write the updated weights
        }
        writer.close();
        System.out.println("CSV file '" + csvFilePath + "' updated with random changes for " + numberOfSpotsToChange + " spots.");
    }

    public static void main(String[] args) throws IOException {
        String csvLot1 = "lot1_parking.csv"; 
        String csvLot2 = "lot2_parking.csv"; 
        String csvLot3 = "lot3_parking.csv"; 
        LotManager lotManager = new LotManager();

        updateCSV(csvLot1); // Generate the initial CSV file
        updateRandomSpots(csvLot1); // Update random spots in the CSV file

        updateCSV(csvLot2); // Generate the initial CSV file
        updateRandomSpots(csvLot2); // Update random spots in the CSV file

        updateCSV(csvLot3); // Generate the initial CSV file
        updateRandomSpots(csvLot3); // Update random spots in the CSV file

        lotManager.programStart(csvLot1);
        lotManager.programStart(csvLot2);
        lotManager.programStart(csvLot3);
        Timer timer = new Timer();
TimerTask task = new TimerTask() {
    @Override
    public void run() {
        try {
            updateRandomSpots(csvLot1); // Update random spots in the CSV file

            updateRandomSpots(csvLot2); // Update random spots in the CSV file

            updateCSV(csvLot3); // Generate the initial CSV file
            updateRandomSpots(csvLot3); // Update random spots in the CSV file

            // Call the programStart method to process the updated CSV
            lotManager.programStart(csvLot1);
            lotManager.programStart(csvLot2);
            lotManager.programStart(csvLot3);
        } catch (IOException e) {
            e.printStackTrace();  // Handle any IO exceptions
        }
    }
    
};
timer.scheduleAtFixedRate(task, 0, 5000);
    }
}
