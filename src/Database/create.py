import sqlite3
from datetime import datetime

# Create the database and table
def create_database():
    conn = sqlite3.connect('parking_lot.db')
    cursor = conn.cursor()
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS parking_lot (
            vehicle_id INTEGER PRIMARY KEY AUTOINCREMENT,
            entry_time TEXT,
            exit_time TEXT,
            is_parked BOOLEAN
        )
    ''')
    conn.commit()
    conn.close()
    print("Database and table created!")

# Insert vehicle entry data
def add_vehicle_entry():
    conn = sqlite3.connect('parking_lot.db')
    cursor = conn.cursor()
    entry_time = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    cursor.execute('''
        INSERT INTO parking_lot (entry_time, is_parked) 
        VALUES (?, ?)
    ''', (entry_time, True))
    conn.commit()
    conn.close()
    print("Vehicle entry recorded at:", entry_time)

# Update vehicle exit data
def update_vehicle_exit(vehicle_id):
    conn = sqlite3.connect('parking_lot.db')
    cursor = conn.cursor()
    exit_time = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    cursor.execute('''
        UPDATE parking_lot 
        SET exit_time = ?, is_parked = ? 
        WHERE vehicle_id = ?
    ''', (exit_time, False, vehicle_id))
    conn.commit()
    conn.close()
    print(f"Vehicle {vehicle_id} exit recorded at:", exit_time)

# View parking lot status
def view_parking_lot_status():
    conn = sqlite3.connect('parking_lot.db')
    cursor = conn.cursor()
    cursor.execute('SELECT * FROM parking_lot')
    rows = cursor.fetchall()
    for row in rows:
        print(row)
    conn.close()

# Main section to call functions
if __name__ == "__main__":
    create_database()  # Call this once to create the DB
    add_vehicle_entry()  # When a vehicle enters, call this function
    view_parking_lot_status()  # View the current parking lot data
    # update_vehicle_exit(vehicle_id=1)  # Call this when a vehicle exits
