/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrlLayer;

import dbLayer.DbBuses;
import dbLayer.DbConnection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import modelLayer.Bus;
import modelLayer.Reservation;


/**
 *
 * @author Delyan
 */
public class BusesCtrl {

    private DbBuses dbBuses;
    private ReservationCtrl reservCtrl = new ReservationCtrl();
    public BusesCtrl() {
        dbBuses = new DbBuses();
    }

    /**
     * Creates a bus with seats and save them in the database 
     * @param id the idd of the bus
     * @param model the model of the bus
     * @param noOfSeats the number of the seats in the bus
     * @param year the year of the bus 
     * @param rentable if the bus can be rent
     * @param pricePerDay price per day for renting
     * @return 1 if successful
     * @throws SQLException 
     */
    public int createBus(int id, String model, int noOfSeats, Date year, boolean rentable,double pricePerDay) throws SQLException {
       SeatCtrl seatCtrl = new SeatCtrl();
        Bus bus = new Bus(id, model, noOfSeats, year, rentable, null,pricePerDay);
        DbConnection.starTransaction();
        int index = dbBuses.createBus(bus);
        seatCtrl.insertNoOfSeatsToBus(bus, noOfSeats);
        DbConnection.commitTransaction();
        return index;
    }
    
    /**
     * Calculate the price for renting 
     * @param bus the bus to be rent
     * @param from From which date 
     * @param to to which date is the rent
     * @return  the price of the rent
     */
    public double getBusPriceForTimePeriod(Bus bus,Date from, Date to){       
        return bus.getPricePerDay()*reservCtrl.getPeriodSet(from, to).size();
    }

    /**
     * Returns all busses which can be rented with noOfseats greater than the specified
     * @param noOfSeats the number of seats in the bus
     * @return a list of all available seats
     * @throws SQLException 
     */
    public List<Bus> getAllRentableBuses(int noOfSeats) throws SQLException {
        return dbBuses.getAllRentableBusses(noOfSeats);
    }

    /**
     * Returns all busses which are free to rent 
     * @param noOfSeats the number of seats in the busses 
     * @param from available from 
     * @param to avalaible to 
     * @return a list of busses
     * @throws SQLException 
     */
    public List<Bus> getAllBussesWhichAreFreeToRent(int noOfSeats, Date from, Date to) throws SQLException {
        List<Bus> buses = getAllRentableBuses(noOfSeats);
        List<Reservation> reservations = reservCtrl.getAllReservations();
        reservations.forEach(reservation -> {
            if (reservCtrl.containsDate(reservation, from, to)) {
                buses.remove(reservation.getBus());
            }
        });

        return buses;
    }

    /**
     * Returns a bus which is assigned to a travel 
     * @param travelDayTimeID the travels id
     * @return bus
     * @throws SQLException 
     */
    public Bus getBusForTravel(int travelDayTimeID) throws SQLException {
        return dbBuses.getBusFromTravel(travelDayTimeID);
    }

    /**
     * Updates the bus
     * @param id the old id of the bus
     * @param bus to be updated
     * @return 1 if successful
     * @throws SQLException 
     */
    public int updateBus(int id, Bus bus) throws SQLException {
        return dbBuses.updateBusById(id, bus);
    }
    
    /**
     * return a bus with the given id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Bus getBus(int id) throws SQLException {
        return dbBuses.getBusByID(id);
    }
    
    /**
     * Removes a bus with the given id
     * @param busId
     * @return
     * @throws SQLException 
     */
    public int deleteBus(int busId) throws SQLException {
        return dbBuses.deleteBusbyID(busId);
    }

    /**
     * Create a new bus without adding it to the database
     * @param id
     * @param model
     * @param noOfSeats
     * @param date
     * @param rentable
     * @param pricePerDay
     * @return 
     */
    public Bus createBusWithoutAddidngToDatabase(int id, String model, int noOfSeats, Date date, boolean rentable,double pricePerDay) {
        return new Bus(id, model, noOfSeats, date, rentable,pricePerDay);
    }
    
    /**
     * Return all busses
     * @return 
     */
    public List<Bus> getAllBuses() {
        return dbBuses.getAllBuses();
    }

    /**
     * Returns the model of the bus
     * @param bus
     * @return 
     */
    public String getBusModel(Bus bus) {
        return bus.getModel();
    }

    /**
     * Returns the id of the bus
     * @param bus
     * @return 
     */
    public int getBusId(Bus bus) {
        return bus.getId();
    }

    /**
     * Returns the year of the bus
     * @param bus
     * @return 
     */
    public Date getBusYear(Bus bus) {
        return bus.getYear();
    }

    /**
     * Returns the number of seats in the bus
     * @param bus
     * @return 
     */
    public int getBusNoOfSeats(Bus bus) {
        return bus.getNoOfSeats();
    }

    /**
     * Returns if the bus is available for rent
     * @param bus
     * @return 
     */
    public boolean getBusAvailable(Bus bus) {
        return bus.isAvailableForRent();
    }
    
    /**
     * Returns the price of renting the bus for a day
     * @param bus
     * @return 
     */
    public double getBusPricePerDay(Bus bus){
        return bus.getPricePerDay();
    }

    public Bus getBusForParticularTravel(int travelTimeDayID) throws SQLException {
        return dbBuses.getBusFromTravel(travelTimeDayID);
    }

    public static void main(String args[]) throws SQLException, ParseException {
        BusesCtrl busCtrl = new BusesCtrl();
        String string = "01, 2, 2015";
        DateFormat format = new SimpleDateFormat("MM, d, yyyy", Locale.ENGLISH);
      Date date=  format.parse(string);
        System.out.println(busCtrl.createBus(6, "Benz", 40, date, true, 5));
    }
}
