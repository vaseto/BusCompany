package ctrlLayer;

import dbLayer.DbTravelBuses;
import modelLayer.Bus;
import modelLayer.Day;
import modelLayer.Time;
import modelLayer.Travel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vasil Nedelchev on 20/05/2016.
 */
public class TravelBuses {

    public TravelBuses(){

    }
    /**
     * returns a bus for a travel on a specific day and time
     * @param travelID
     * @param timeID
     * @param dayID
     * @return
     * @throws SQLException 
     */
    public Bus getBusForTravel(int travelID, int timeID, int dayID) throws SQLException {
        DbTravelBuses dbTravelBuses = new DbTravelBuses();
        int travleDaysTimeID = dbTravelBuses.getTravelDaysTimeID(travelID,timeID,dayID);
        BusesCtrl busesCtrl = new BusesCtrl();
        return busesCtrl.getBusForTravel(travleDaysTimeID);
    }

    /**
     * Inserts into the table
     * @param busShcedules
     * @param travel2 
     */
    public void insertIntoTravelBuses(Map<Day, HashMap<Time, Integer>> busShcedules, Travel travel2) {
        TimeCtrl timeCtrl = new TimeCtrl();
        DbTravelBuses dbTravelBuses = new DbTravelBuses();
        busShcedules.forEach((k,v) ->{
            int dayID = k.getDayID();
            v.forEach((key,value)->{
                try {
                    int timeID = timeCtrl.getTimeID(key.getDepartureTime(),key.getArrivalTime());
                    int travleDaysTimeID = dbTravelBuses.getTravelDaysTimeID(travel2.getId(),timeID,dayID);
                    System.out.println(value +" travedt id " + travleDaysTimeID);
                    int insertIntoTravelBusResult = dbTravelBuses.insertIntoTravelBuses(value,travleDaysTimeID);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
        });

    }
}
