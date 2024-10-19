import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;


public class ParkingSpot{
   Integer weightedSensor;
   Boolean takenSpot;
   Boolean indicatorLight;
   Integer spotsPerLine;
   Integer parkingSpotNum;
   Double occupancyOfLot; // will be the only var not inside of the list/queue/whatever


   public ParkingSpot(Integer weightedSensor, Boolean takenSpot, Boolean indicatorLight, Integer parkingSpotNum) {
       this.weightedSensor = weightedSensor;
       this.takenSpot = takenSpot;
       this.indicatorLight = indicatorLight;
       this.parkingSpotNum = parkingSpotNum;
       }
  
   public void setParkingSpotNum() {
       this.parkingSpotNum = parkingSpotNum;
   }
  
   public Integer getParkingSpotNum() {
       return parkingSpotNum;
   }
  
  
   public void setSensorWeight() {
       this.weightedSensor = weightedSensor;
   }
  
   public Integer getSensorWeight() {
       return weightedSensor;
   }
  
   public void setSpotValue() {
       this.takenSpot = takenSpot;
   }
  
   public Boolean getSpotValue() {
       return takenSpot;
   }
  
   public void setIndicatorLightValue() {
       this.indicatorLight = indicatorLight;
   }
  
   public Boolean getIndicatorValue() {
       return indicatorLight;
   }
    
}
