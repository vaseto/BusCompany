/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrlLayer;

import dbLayer.DbConnection;
import dbLayer.DbReservations;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import modelLayer.Bus;
import modelLayer.Customer;
import modelLayer.Person;
import modelLayer.Reservation;

/**
 *
 * @author Delyan
 */
public class ReservationCtrl {

    private DbReservations dbReservations;

    public ReservationCtrl() {
        dbReservations = new DbReservations();
    }

    /**
     * return all reservation a person has
     * @param p the  person
     * @return  list of reservations
     */
    public List<Reservation> getAllReservationsForPerson(Person p) {
        return dbReservations.getAllReservationsForPerson(p);
    }

    /**
     * returns all reservation a bus has
     * @param bus
     * @return
     */
    public List<Reservation> getAllReservationsForBus(Bus bus) {
        return dbReservations.getAllReservationsForBus(bus);
    }

    /**
     * Checks if a reservation contains a date
     * @param res the reservation to be checked
     * @param from 
     * @param to
     * @return 
     */
    public boolean containsDate(Reservation res, Date from, Date to) {
        Set<Date> reservations = getPeriodSet(res.getFrom(), res.getTo());
        Set<Date> newReservations = getPeriodSet(from, to);
        for (Date date : reservations) {
            if (newReservations.contains(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a set containing all dates for a period
     * @param fromDate
     * @param toDate
     * @return 
     */
    public Set<Date> getPeriodSet(Date fromDate, Date toDate) {
        Set<Date> dates = new HashSet<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        while (cal.getTime().before(toDate)) {
            cal.add(Calendar.DATE, 1);
            dates.add(cal.getTime());
        }
        return dates;
    }

    /**
     * Returns the id of a reservation
     * @param id
     * @return 
     */
    public Reservation getReservation(int id) {
        return dbReservations.getReservationByID(id);
    }

    /**
     * Returns all reservations
     * @return 
     */
    public List<Reservation> getAllReservations() {
        return dbReservations.getAllReservations();
    }

    /**
     * Changes the from date of reservation
     * @param reservation
     * @param fromDate  the new from date
     */
    public void changeFromDate(Reservation reservation, Date fromDate) {
        reservation.setFrom(fromDate);
    }

    /**
     * Changes the to date of reservation 
     * @param reservation
     * @param toDate 
     */
    public void changeToDate(Reservation reservation, Date toDate) {
        reservation.setTo(toDate);
    }
    
    /**
     * Creates a reservation and assign it to a customer
     * @param from 
     * @param to
     * @param price the price of the reservation
     * @param bus the bus to be rented
     * @param person the person who owns the reservation
     * @return 1 if successful
     * @throws SQLException 
     */
    public int createReservation(Date from, Date to, double price, Bus bus, Person person) throws SQLException {
        Reservation reserve = new Reservation(from, to, price, bus);
        PersonCtrl p = new PersonCtrl();
        DbConnection.starTransaction();
        dbReservations.createReservation(reserve, person.getId());
        if (person.getReservations() == null) {
            person.setReservations(new ArrayList<>());
        } else {
            person.getReservations().add(reserve);
        }
        Customer c = p.personToCustomer(person);
        return p.updateCustomer(c);
    }

    /**
     * Removes a reservation with the given id
     * @param reservationId
     * @return
     * @throws SQLException 
     */
    public int deleteReservation(int reservationId) throws SQLException {
        return dbReservations.deleteReservationbyID(reservationId);
    }

    /**
     * Updates a reservation 
     * @param reservation the reservation to be updated
     * @param person the peson to be updated
     * @return 1 if successful
     * @throws SQLException 
     */
    public int updateReservation(Reservation reservation, Person person) throws SQLException {
        return dbReservations.updateReservation(reservation, person.getId());
    }

}
