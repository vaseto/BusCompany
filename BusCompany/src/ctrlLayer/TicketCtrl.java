package ctrlLayer;

import dbLayer.DbTicket;
import modelLayer.Route;
import modelLayer.Ticket;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import modelLayer.Category;
import modelLayer.Seat;
import modelLayer.Time;

public class TicketCtrl {

    private DbTicket dbTicket;

    public TicketCtrl() {
        dbTicket = new DbTicket();
    }

    /**
     *
     * @return All in the database
     * @throws SQLException
     */
    public List<Ticket> getAllTickets() throws SQLException {
        return dbTicket.getAllTickets();
    }

    /**
     *
     * @param pID The person id
     * @return all the tickets a person has bought
     * @throws SQLException
     */
    public List<Ticket> getAllTicketsForPerson(int pID) throws SQLException {
        return dbTicket.getAllTicketsForPerson(pID);
    }

    /**
     *
     * @param routeID The Id of the route
     * @return all the Tickets belonging to a route
     * @throws SQLException
     */
    public List<Ticket> getAllTicketsForRoute(int routeID) throws SQLException {
        return dbTicket.getAllTicketsForRoute(routeID);
    }

    /**
     *
     * @param ticket the ticket to be deleted
     * @return 1 if the delete is done
     * @throws SQLException
     */
    public int removeTicket(Ticket ticket) throws SQLException {
        return dbTicket.delete(ticket);
    }

    /**
     *
     * @param ticket The ticket to be updated
     * @param pID
     * @return 1 if the ticket was update successfully
     * @throws SQLException
     */
    public int updateTicket(Ticket ticket, int pID) throws SQLException {
        return dbTicket.update(ticket, pID);
    }

    /**
     *
     * @param id The id to be used for searching
     * @return the ticket
     * @throws SQLException
     */
    public Ticket getTicketById(int id) throws SQLException {
        return dbTicket.getTicket(id);
    }

    /**
     *
     * @param price The price of the new Ticket
     * @param route The route to which the ticket belongs
     * @param category
     * @param time
     * @param date
     * @param seat
     * @param pID
     * @return 1 if the ticket is created in the database
     * @throws SQLException
     */
    public int createTicket(double price, Route route, Category category, Time time, Date date, Seat seat, int pID) throws SQLException {
        return dbTicket.insert(new Ticket(route, category, time, date, seat), pID);
    }
    
    public Ticket createTicketWithoutAddingToDatabase(Route route,Category category,Time time,Date date,Seat seat){
        return new Ticket(route, category, time, date, seat);
    }

}
