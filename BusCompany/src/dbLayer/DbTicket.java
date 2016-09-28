/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelLayer.Ticket;

/**
 *
 * @author Delyan
 */
public class DbTicket extends DbClass{

    private Connection con;
    private String from = "Tickets";
    public DbTicket() {
        con = getCon();
    }

    /**
     * Return all tickets
     * @return
     * @throws SQLException 
     */
    public List<Ticket> getAllTickets() throws SQLException {
        return missWhere("");
    }

    /**
     * Returns all tickets for the given route
     * @param routeID the id of the route 
     * @return
     * @throws SQLException 
     */
    public List<Ticket> getAllTicketsForRoute(int routeID) throws SQLException {
        return missWhere("routeID ='"+ routeID + "'");
    }
    
    /**
     * Return all tickets for a person 
     * @param pID the id of the person
     * @return 
     * @throws SQLException 
     */
    public List<Ticket> getAllTicketsForPerson(int pID) throws SQLException {
        return missWhere("pID = '"+ pID +"'");
    }
    
    /**
     * Builds a ticket with the result set
     * @param results 
     * @return Ticket
     * @throws SQLException 
     */
    private Ticket buildTicket(ResultSet results) throws SQLException {
        DbRoute dbRoute = new DbRoute();
        DbCategory dbCategory = new DbCategory();
        DbTime dbTime = new DbTime();
        DbSeat dbSeat = new DbSeat();
        try{     
        return new Ticket(dbRoute.getRouteById(results.getInt(4)), dbCategory.getCategoryById(results.getInt(3)), results.getInt(1), dbTime.getTimeByID(results.getInt(6)),
                results.getDate(7), dbSeat.getSeatById(results.getInt(8)));
        }catch(Exception e){
            return null;
        }
    }
        
    
    /**
     * Method used when a single result set is expected
     * @param wClause the clause to be searched with
     * @return Ticket
     * @throws SQLException 
     */
    private Ticket singleWhere(String wClause) throws SQLException {
        ResultSet results = getResultSet(wClause,from);
        results.next();
        return buildTicket(results);
    }

    /**
     * Method used when more than 1 result is expected
     * @param wClause the clause to be searched with 
     * @return list of all tickets matching the wClause
     * @throws SQLException 
     */
    private List<Ticket> missWhere(String wClause) throws SQLException {
        List<Ticket> list = new ArrayList<>();
        ResultSet results = getResultSet(wClause,from);
        while (results.next()) 
            list.add(buildTicket(results));

        return list;
    }

    /**
     * Returns prepared statement for inserting tickets 
     * @return
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into Tickets (price,categoryID,routeID,pID,timeID,date,seatID) values (?,?,?,?,?,?,?) Select Scope_Identity()");
    }

    /**
     * Returns a prepared statement for updating tickets
     * @return
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForUpdate() throws SQLException {
        return con.prepareStatement("Update Tickets set price = ?,categoryId = ?,routeID = ?, pID = ?,timeID = ?, date = ?, seatID = ? where ticketID = ? ");
    }
    
    /**
     * Returns a ticket with the id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Ticket getTicket(int id) throws SQLException {
        return singleWhere("ticketID = '" + id + "'");
    }
    
    /**
     * Returns a prepared statment with all the information
     * @param prSt the statement which is going to be used
     * @param ticket the ticket object
     * @param pId the id of the person 
     * @return the prepare statment
     * @throws SQLException 
     */
    private PreparedStatement getPrivateFields(PreparedStatement prSt,Ticket ticket,int pId) throws SQLException{
        prSt.setDouble(1,ticket.getPrice());
        prSt.setInt(2,ticket.getCategory().getId());
        prSt.setInt(3, ticket.getRoute().getId());
        prSt.setInt(4, pId);
        prSt.setInt(5,ticket.getTime().getId());
        prSt.setDate(6,convertUtilToSql(ticket.getDate()));
        prSt.setInt(7,ticket.getSeat().getId());
        return prSt;
    }
    
    /**
     * inserts a ticket into the database
     * @param ticket the ticket to be inserted
     * @param pId the id of the Person to which the ticket belongs
     * @return 1 if successful
     * @throws SQLException 
     */
    public int insert(Ticket ticket,int pId) throws SQLException{
        return getPrivateFields(getPrepStatForInsert(), ticket,pId).executeUpdate();
    }
    
    /**
     *  Transforms util date to sql date
     * @param uDate
     * @return 
     */
      private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        return new java.sql.Date(uDate.getTime()); 
    }
    
      /**
       * Updates a ticket's record 
       * @param ticket the ticket to which the record belongs
       * @param pId the id of the Person who owns the ticket
       * @return 1 if successful
       * @throws SQLException 
       */
    public int update(Ticket ticket,int pId) throws SQLException {
        PreparedStatement prSt = getPrivateFields(getPrepStatForUpdate(), ticket,pId);
        prSt.setInt(8, ticket.getId());
        return prSt.executeUpdate();
    }
    
    /**
     * Removes a record representing the ticket
     * @param ticket
     * @return
     * @throws SQLException 
     */
    public int delete(Ticket ticket) throws SQLException {
        return con.createStatement().executeUpdate("Delete from Tickets where ticketID = '" + ticket.getId() + "'");
    }
    
    public static void main(String args[]){
        DbTicket dbT = new DbTicket();
    }

}
