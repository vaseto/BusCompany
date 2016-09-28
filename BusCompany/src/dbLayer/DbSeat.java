/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import modelLayer.Bus;
import modelLayer.Seat;

/**
 *
 * @author Delyan
 */
public class DbSeat extends DbClass{
    private Connection con;
    private String from = " Seats";

    public DbSeat(){
        con = getCon();
    }

    /**
     * Returns all seats in a bus
     * @param busId the id of the bus
     * @return list of seats
     * @throws SQLException
     */
    public List<Seat> getAllSeatsForBus(int busId) throws SQLException {
        return missWhere("busID ="+ busId);
    }

    /**
     * Return a seat object
     * @param results the set used to build the object
     * @return seat
     * @throws SQLException
     */
    private Seat buildSeat(ResultSet results) throws SQLException {
        return new Seat(results.getInt(2),results.getInt(1));
    }

    /**
     * Method used when a single result is expected
     * @param wClause the clause to be used
     * @return a set object
     * @throws SQLException
     */
    private Seat singleWhere(String wClause) throws SQLException {
        ResultSet results = getResultSet(wClause,from);
        results.next();
        return buildSeat(results);
    }

    /**
     * Method used when more than one results is expected
     * @param wClause the clause to be used for searching
     * @return list of all seats matching the wClause
     * @throws SQLException
     */
    private List<Seat> missWhere(String wClause) throws SQLException {
        List<Seat> list = new ArrayList<>();
        ResultSet results = getResultSet(wClause,from);

        while(results.next())
            list.add(buildSeat(results));

        return list;
    }

    /**
     * Returns a prepare statement for inserting
     * @return
     * @throws SQLException
     */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into Seats (number,busID) values (?,?)");
    }

    /**
     * Returns a prep statement for updating
     * @return
     * @throws SQLException
     */
    private PreparedStatement getPrepStatForUpdate() throws SQLException {
        return con.prepareStatement("Update Seats set number =?, busID = ? where seatID = ? ");
    }

    /**
     * Returns a seat with the given number
     * @param number
     * @return seat
     * @throws SQLException
     */
    public Seat getSeatByNumber(int number) throws SQLException {
        return singleWhere("number = '" + number + "'");
    }

    public Seat getSeatByBus(int busId) throws SQLException{
        return singleWhere("busID =" + busId);
    }

    /**
     * Returns a seat by the seat id
     * @param id
     * @return
     * @throws SQLException
     */
    public Seat getSeatById(int id) throws SQLException{
        return singleWhere("seatId =" + id);
    }

    /**
     *
     * @param seat the seat to be inserted 
     * @param bus the bus to receive the seat 
     * @return 1 if successful
     * @thrsows SQLException
     */
    private int insert(Seat seat,Bus bus) throws SQLException {
        PreparedStatement prSt = getPrepStatForInsert();
        prSt.setInt(1,seat.getNumber());
        prSt.setInt(2, bus.getId());
        return  prSt.executeUpdate();
    }

    /**
     *  insert number of seats into a bus
     * @param bus the bus to receive the seats
     * @param noOfSeats the no of seats to be added
     * @return the number of seats which were inserted
     * @throws SQLException
     */
    public int insertSeatsToBus(Bus bus,int noOfSeats) throws SQLException{
        int index = 0;
        for (int i = 0; i < noOfSeats; i++) {
            index = insert(new Seat(i, bus), bus);
        }
        return index;
    }

    /**
     * Removes a seat from the database
     * @param seat the seat to be deleted
     * @return 1 if successful
     * @throws SQLException
     */
    public int delete(Seat seat) throws SQLException {
        return con.createStatement().executeUpdate("Delete from Seats where seatID = '" + seat.getId() + "'");
    }

    /**
     * Updates the seat and bus in the table
     * @param seat the seat to be updated
     * @param bus the bus to be updated
     * @return 1 if successful
     * @throws SQLException
     */
    public int update(Seat seat,Bus bus) throws SQLException {
        PreparedStatement prSt = getPrepStatForUpdate();
        prSt.setInt(1, seat.getNumber());
        prSt.setInt(2, bus.getId());
        prSt.setInt(3,seat.getNumber());
        return prSt.executeUpdate();
    }

    /**
     * Return a list of the available seats in a bus for a date and time
     * @param busID the bus id
     * @param routeID  the route id
     * @param timeID the time id
     * @param date the date when
     * @return list of seats
     * @throws SQLException
     */
    public List<Seat> getAllAvailableSeats(int busID, int routeID, int timeID, Date date) throws SQLException {
        String query = "\n" +
                "SELECT  q1.seatID from\n" +
                "\n" +
                "  (SELECT seatID from Seats\n" +
                " where busID = ?) as q1\n" +
                "LEFT JOIN\n" +
                "\n" +
                "(SELECT seatID from Tickets\n" +
                "WHERE routeID= ? AND timeID = ? AND date =?)as q2\n" +
                "  ON q1.seatID = q2.seatID WHERE q2.seatID is NULL ";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, busID);
        preparedStatement.setInt(2, routeID);
        preparedStatement.setInt(3, timeID);
        preparedStatement.setDate(4, date);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Seat> seats = new Stack<>();
        while(resultSet.next()){
            seats.add(getSeatById(resultSet.getInt(1)));
        }
        return seats;
    }

}
