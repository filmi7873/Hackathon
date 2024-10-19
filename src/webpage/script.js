document.addEventListener('DOMContentLoaded', () => {
    const parkingLotSelect = document.getElementById('parking-lot-select');
    const occupancyDisplay = document.getElementById('occupancy-display');
    const occupiedSpotsList = document.getElementById('occupied-spots-list');

    // Predefined data for each parking lot
    const parkingData = {
        1: `
            parking_spot_id,vehicle_weight
            1,0
            2,2419
            3,0
            4,3452
            5,0
            6,2785
            7,0
            8,2291
            9,0
            10,0
        `,
        2: `
            parking_spot_id,vehicle_weight
            1,3450
            2,0
            3,0
            4,2100
            5,0
            6,0
            7,2900
            8,0
            9,3000
            10,0
        `,
        3: `
            parking_spot_id,vehicle_weight
            1,0
            2,0
            3,0
            4,4500
            5,0
            6,0
            7,0
            8,2300
            9,0
            10,0
        `
    };

    parkingLotSelect.addEventListener('change', (event) => {
        const selectedLot = event.target.value;

        // Clear previous results
        occupancyDisplay.textContent = 'Calculating...';
        occupiedSpotsList.innerHTML = '';

        if (!selectedLot || !parkingData[selectedLot]) {
            occupancyDisplay.textContent = 'N/A - Please select a valid parking lot';
            return;
        }

        // Use the corresponding data for the selected parking lot
        const csvText = parkingData[selectedLot];
        const rows = csvText.trim().split('\n').slice(1); // Skip header row

        let totalSpots = 0;
        let occupiedSpots = 0;

        rows.forEach(row => {
            const [spotId, vehicleWeight] = row.split(',');
            if (spotId && vehicleWeight) {
                const weight = parseInt(vehicleWeight, 10);

                if (!isNaN(weight)) {
                    totalSpots++;
                    if (weight > 0) {
                        occupiedSpots++;
                        // Add spot ID to the occupied spots list
                        const listItem = document.createElement('li');
                        listItem.textContent = `Spot ${spotId} is occupied.`;
                        occupiedSpotsList.appendChild(listItem);
                    }
                }
            }
        });

        // Avoid division by zero and handle edge cases
        if (totalSpots === 0) {
            occupancyDisplay.textContent = 'No data available for the selected lot';
        } else {
            const occupancyPercentage = (occupiedSpots / totalSpots) * 100;
            occupancyDisplay.textContent = `Occupancy Percentage: ${occupancyPercentage.toFixed(2)}%`;
        }
    });
});
