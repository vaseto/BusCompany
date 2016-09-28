package ctrlLayer;

import dbLayer.DbDay;
import dbLayer.DbTravel;
import modelLayer.Day;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Vasil Nedelchev on 5.5.2016 г..
 */
public class DayCtrl {
    private DbDay dbDay;
    public DayCtrl(){
        dbDay = new DbDay();
    }
    
    /**
     * Returns a day's id with the given name
     * @param strDay
     * @return
     * @throws SQLException 
     */
    public Integer getDayIDByDay(String strDay) throws SQLException {
        return dbDay.getDayIDbyDay(strDay);
    }

    /**
     * Returns a day by it is id
     * @param id
     * @return
     * @throws SQLException 
     */
    public int deleteDayByID(int id) throws SQLException {
        return dbDay.deleteDaybyID(id);
    }

    /**
     * Returns a day with that name
     * @param strDay
     * @return
     * @throws SQLException 
     */
    public Day getDayByDay(String strDay) throws SQLException {
        return dbDay.getDayByDay(strDay);
    }
    
    /**
     * Returns a list of all days
     * @return 
     */
    public List<Day> getAllDays(){
        return dbDay.getAllDays();
    }
    
    /**
     * Returns a day by the given date
     * @param date
     * @return
     * @throws SQLException 
     */
    public Day getDayByDate(Date date) throws SQLException{
        DateFormat format1 = new SimpleDateFormat("EEEE");
        String finalDay = format1.format(date);
        return getDayByDay(finalDay);
    }

}
