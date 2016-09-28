package ctrlLayer;

import dbLayer.DbTime;
import modelLayer.Time;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vasil Nedelchev on 5.5.2016 г..
 */
public class TimeCtrl {
    private DbTime dbTime;
    public TimeCtrl() {
        dbTime = new DbTime();
    }
    /**
     * Gets the id of a time object
     * @param departureTime
     * @param arrivalTime
     * @return
     * @throws SQLException 
     */
    public Integer getTimeID(String departureTime, String arrivalTime) throws SQLException {
        return dbTime.getTimeID(departureTime, arrivalTime);
    }
    /**
     * gets time with the given departure time
     * @param departureTime
     * @return 
     */
    public Time getTime(String departureTime){
        return dbTime.getTimeBy(departureTime);
    }
    
    /**
     * Gets a time object with the given timeId
     * @param timeID
     * @return
     * @throws SQLException 
     */
    public Time getTimeById(int timeID) throws SQLException{
        return dbTime.getTimeByID(timeID);
    }
    /**
     * Delets a time by its id
     * @param id
     * @return
     * @throws SQLException 
     */
    public int deleteTimeByID(int id) throws SQLException {
        return dbTime.deleteTimeByID(id);
    }
    
    /**
     * Returns a list of all times
     * @return 
     */
    public List<Time> getAllTimes(){
        return dbTime.getAllTimes();
    }
    /**
     * Creates a time
     * @param departureTime
     * @param arrivalTime
     * @return 
     */
    public Time createTime(String departureTime,String arrivalTime) {
        Time time = new Time(departureTime, arrivalTime);
        return time;
    }
    public Time createTime(String departureTime) {
        Time time = new Time(departureTime);
        return time;
    }



}
