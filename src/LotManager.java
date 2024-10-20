import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class LotManager {

    PriorityQueue<ParkingSpot> parkingQueue;

    // Comparator to ensure empty spots (false) come before taken spots (true)
    class ParkingSpotsOccupancy implements Comparator<ParkingSpot> {
        public int compare(ParkingSpot parking1, ParkingSpot parking2) {
            return Boolean.compare(parking1.getSpotValue(), parking2.getSpotValue());
            // false (empty) should be "less than" true (taken)
        }
    }

    public LotManager() {
        parkingQueue = new PriorityQueue<>(new ParkingSpotsOccupancy());
    }

    public void addSpot(Integer weightedSensor, Boolean takenSpot, Boolean indicatorLight, Integer parkingSpotNum) {
        ParkingSpot newSpot = new ParkingSpot(weightedSensor, takenSpot, indicatorLight, parkingSpotNum);
        parkingQueue.add(newSpot);
    }

    public void listEmptySpots(String parkingLot) {
        PriorityQueue<ParkingSpot> tempQueue = new PriorityQueue<>(parkingQueue);
        ArrayList<Integer> emptySpots = new ArrayList<>();

        while (!tempQueue.isEmpty()) {
            ParkingSpot spot = tempQueue.poll();

            // Stop when the first taken spot is encountered
            if (spot.getSpotValue()) { // takenSpot = true
                break;
            }

            emptySpots.add(spot.parkingSpotNum); // Collect empty spot numbers
        }

        // Sort the empty spots for organized output
        emptySpots.sort(Integer::compareTo);

        System.out.println("Number of Empty Spots for " + parkingLot + ": " + emptySpots.size());
        for (Integer spotNum : emptySpots) {
            System.out.println("Spot # " + spotNum + " is empty.");
        }
    }

    public Boolean getTakenStatus(Integer weightedSensor) {
        // Spot is taken if sensor value is greater than 0, empty if it's 0
        return weightedSensor != 0;
    }

    public void addToList(Integer weightedSensor, Integer parkingSpotNum) {
        Boolean takenSpot = getTakenStatus(weightedSensor);
        Boolean indicatorLight = !takenSpot; // Light on if the spot is empty
        addSpot(weightedSensor, takenSpot, indicatorLight, parkingSpotNum);
    }

    public void programStart(String parkingLot) {
        Integer weightedSensor, parkingSpotNum;
        String csvFile = parkingLot; // Path to your CSV file
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip the first line (dummy line)

            while ((line = br.readLine()) != null) {
                // Split the line into parts
                String[] data = line.split(csvSplitBy);
                parkingSpotNum = Integer.parseInt(data[0].trim()); // Parking spot ID
                weightedSensor = Integer.parseInt(data[1].trim()); // Vehicle weight

                // Add each spot to the list
                addToList(weightedSensor, parkingSpotNum);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        listEmptySpots(parkingLot); // After reading the file, list all empty spots
        parkingQueue.clear();
    }
}
