package ctrlLayer;

import dbLayer.DbSeat;
import modelLayer.Seat;
import modelLayer.Time;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import modelLayer.Bus;

/**
 * Created by Vasil Nedelchev on 18/05/2016.
 */
public class SeatCtrl {
    private DbSeat dbSeat ;
    public SeatCtrl(){
        dbSeat = new DbSeat();
    }
    /**
     * Returns a list of all available seats in a bus for a route
     * @param busID
     * @param routeID
     * @param date
     * @param time
     * @return
     * @throws SQLException 
     */
    public List<Seat> getAllAvailableSeats(int busID, int routeID, Date date, Time time) throws SQLException {
        TimeCtrl timeCtrl = new TimeCtrl();
        int timeID = timeCtrl.getTimeID(time.getDepartureTime(),time.getArrivalTime());
        java.sql.Date date1 = convertUtilToSql(date );

        return dbSeat.getAllAvailableSeats(busID,routeID,timeID,date1);
    }
    /**
     * Returns a seat in bus by its number
     * @param bus
     * @param number
     * @return
     */
    public Seat getBusSeatByNumber(Bus bus,int number){
        return bus.getSeats().get(number-1);
    }

    /**
     * Inserts a number of seats into a bus
     * @param bus
     * @param noOfSeats
     * @return
     * @throws SQLException
     */
    public int insertNoOfSeatsToBus(Bus bus,int noOfSeats) throws SQLException{
        return dbSeat.insertSeatsToBus(bus,noOfSeats);
    }

    private static java.sql.Date convertUtilToSql(java.util.Date uDate){
        return new java.sql.Date(uDate.getTime());
    }
    public  static void main(String[] args){
        SeatCtrl seatCtrl = new SeatCtrl();
        String string = "May 13, 2016";
        Time time = new Time("13:12","16:12");
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Seat>seats = null;
        try {
            seats = seatCtrl.getAllAvailableSeats(123,112,date,time);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(seats);
    }
}



