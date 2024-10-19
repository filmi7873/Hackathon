document.addEventListener('DOMContentLoaded', () => {
    const parkingLotSelect = document.getElementById('parking-lot-select');
    const occupancyDisplay = document.getElementById('occupancy-display');

    parkingLotSelect.addEventListener('change', (event) => {
        const selectedLot = event.target.value;
        const data = {}; // Object to hold CSV data

        // Fetch CSV data and populate the data object
        fetch(`lot${selectedLot}_parking.csv`) // Updated to fetch the selected lot's CSV file
            .then(response => response.text())
            .then(csvText => {
                const rows = csvText.split('\n').slice(1); // Skip the header row
                rows.forEach(row => {
                    const [spotId, vehicleWeight, parkingLotNumber] = row.split(','); // Parse all three values
                    if (spotId && vehicleWeight && parkingLotNumber) {
                        data[spotId] = {
                            weight: parseInt(vehicleWeight, 10),
                            lotNumber: parseInt(parkingLotNumber, 10)
                        };
                    }
                });

                // Calculate occupancy percentage for the selected parking lot
                let totalSpots = 0;
                let occupiedSpots = 0;

                for (let spotId = 1; spotId <= 500; spotId++) {
                    totalSpots++;
                    if (data[spotId] && data[spotId].weight > 0) {
                        occupiedSpots++;
                    }
                }

                // Calculate occupancy percentage
                const occupancyPercentage = (occupiedSpots / totalSpots) * 100;

                // Display occupancy percentage
                occupancyDisplay.textContent = `Occupancy Percentage for Lot ${selectedLot}: ${occupancyPercentage.toFixed(2)}%`;
            })
            .catch(error => {
                console.error('Error fetching or processing CSV data:', error);
                occupancyDisplay.textContent = 'Error loading data. Please try again.';
            });
    });
});
