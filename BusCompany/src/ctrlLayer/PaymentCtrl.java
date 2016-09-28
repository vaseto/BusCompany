/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrlLayer;

import dbLayer.DbCard_Payment;
import dbLayer.DbConnection;
import dbLayer.DbPayment;
import dbLayer.DbReservations;
import dbLayer.DbTicket;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import modelLayer.Payment;
import modelLayer.Person;
import modelLayer.Reservation;
import modelLayer.Ticket;

/**
 *
 * @author Delyan
 */
public class PaymentCtrl {

    private DbPayment dBPayment;
    private DbCard_Payment dbCardPayment = new DbCard_Payment();
    public PaymentCtrl() {
        dBPayment = new DbPayment();
    }

    /**
     * Creates payment and assign a ticket to a person
     * @param ticket 
     * @param person
     * @param date
     * @param amount
     * @return 1 if successful
     * @throws SQLException 
     */
    public int buyTicket(Ticket ticket, Person person, Date date, double amount) throws SQLException {
        Payment p = new Payment(date, amount);
            int index = 0;
            try {
                DbTicket dbTicket = new DbTicket();
                DbConnection.starTransaction();
                p = dBPayment.insert(p, 2);
                dbCardPayment.insert(person.getCardInfo(), getPayment(p.getId()));
                index = dbTicket.insert(ticket, person.getId());
                DbConnection.commitTransaction();

            return index;
            }catch (Exception e){
                DbConnection.rollBackTransaction();
                throw new RuntimeException(e);
            }


    }

    /**
     * Creates a reservation and assign it to a person
     * @param date
     * @param amount
     * @param person
     * @param reservation
     * @return 1 if successful
     * @throws SQLException 
     */
    public int createReservation(Date date, double amount, Person person, Reservation reservation) throws SQLException {
        Payment payment = new Payment(date, amount);
        if (payment.getAmount() >= reservation.getPrice()) {
            int index = 0;
            try {
                DbReservations dbReservations = new DbReservations();
                DbConnection.starTransaction();
                payment = dBPayment.insert(payment, 1);
                index = +dbCardPayment.insert(person.getCardInfo(), getPayment(payment.getId()));
                dbReservations.createReservation(reservation, person.getId());
                DbConnection.commitTransaction();
            }catch (Exception e){
                DbConnection.rollBackTransaction();
                throw new RuntimeException(e);
            }
            return index;
        } else {
            return -1;
        }
    }

    /**
     * Returns a payment by the id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Payment getPayment(int id) throws SQLException {
        return dBPayment.getPayment(id);
    }

    /**
     * Removes a payment
     * @param payment
     * @return
     * @throws SQLException 
     */
    public int deletePayment(Payment payment) throws SQLException {
        return dBPayment.delete(payment);
    }

    /**
     * Updates a payment
     * @param payment
     * @return
     * @throws SQLException 
     */
    public int updatePayment(Payment payment) throws SQLException {
        return dBPayment.update(payment);
    }

    /**
     * Returns all ticket payments
     * @return
     * @throws SQLException 
     */
    public List<Payment> getAllTicketPayments() throws SQLException {
        return dBPayment.getAllPaymentsForTickets();

    }

    /**
     * Returns all reservation payments
     * @return
     * @throws SQLException 
     */
    public List<Payment> getAllReservationPayments() throws SQLException {
        return dBPayment.getAllPaymentsForReservations();
    }

}
