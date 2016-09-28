package dbLayer;

import modelLayer.Bus;
import modelLayer.Person;
import modelLayer.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasil Nedelchev on 23.4.2016 г..
 */
public class DbReservations {

    private Connection con;
    private PreparedStatement preparedStatement;
    private DbBuses dbBuses;
    private DbPerson dbPerson;

    public DbReservations() {
        con = DbConnection.getInstance().getDBcon();
        dbBuses = new DbBuses();
        dbPerson = new DbPerson();
    }
    
    /**
     * Returns a list of all reservations 
     * @param wClause the clause to be used
     * @return 
     */
    private List<Reservation> miscWhere(String wClause) {
        ResultSet resultSet;
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = buildQuery(wClause);
       try {
            Statement statement = con.createStatement();
            statement.setQueryTimeout(5);
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Reservation reservation = buildReservation(resultSet);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("query exception - select project" + e);
            e.printStackTrace();
        }
        return reservations;
    }

    /**
     * Builds a reservation with the given result set
     * @param resultSet
     * @return 
     */
    private Reservation buildReservation(ResultSet resultSet) {
        Reservation reservation = new Reservation();
        try {
            reservation.setId(resultSet.getInt(1));
            reservation.setPrice(resultSet.getDouble(4));
            java.util.Date utDate =  new java.util.Date(resultSet.getDate(2).getTime());
            reservation.setFrom(utDate);
            utDate = new java.util.Date(resultSet.getDate(3).getTime());
            reservation.setTo(utDate);
            Bus bus = dbBuses.getBusByID(resultSet.getInt(5));
            reservation.setBus(bus);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    /**
     * BUilds a query to access the database
     * @param wClause
     * @return 
     */
    private String buildQuery(String wClause) {
        String query = "SELECT * FROM Reservations ";
        if (wClause.length() > 0) {
            query = query + "where" + wClause;
        }
        return query;
    }

    /**
     * Returns a list of all reservations
     * @return 
     */
    public List<Reservation> getAllReservations() {
        return miscWhere("");
    }

    /**
     * Returns a list of all reservations a person has
     * @param p
     * @return 
     */
    public List<Reservation> getAllReservationsForPerson(Person p) {
        return miscWhere(" pId =" + p.getId());
    }
    
    
    /**
     * Returns a list of all reservation a bus has
     * @param bus
     * @return 
     */
    public List<Reservation> getAllReservationsForBus(Bus bus){
        return miscWhere(" busID =" + bus.getId());
    }

    /**
     * Returns a prepared statement with the given query
     * @param query
     * @return 
     */
    private PreparedStatement getPreparedStmt(String query) {
        try {
            System.out.println("prepareStatement " + query);

            preparedStatement = con.prepareStatement(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Returns a reservation with the given id
     * @param id
     * @return 
     */
    public Reservation getReservationByID(int id) {
        String query = "SELECT * FROM  Reservations WHERE reservationID = ?";
        preparedStatement = getPreparedStmt(query);
        Reservation reservation = new Reservation();
        ResultSet resultSet;
        try {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reservation = buildReservation(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    
    /**
     * Removes a reservation with the id
     * @param id
     * @return
     * @throws SQLException 
     */
    public int deleteReservationbyID(int id) throws SQLException {
        String query = " DELETE FROM Reservations "
                + "WHERE reservationID =? ";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate();
    }

    /**
     * Inserts a reservation in the database
     * @param reservation to be inserted
     * @param pId to be inserted
     * @return 1 if successful
     * @throws SQLException 
     */
    public int createReservation(Reservation reservation,int pId) throws SQLException {
        String query = "IF ((\n" +
                "      select COUNT(*) from\n" +
                "        (\n" +
                "          select reservationID from Reservations\n" +
                "          where\n" +
                "            fromDate  BETWEEN ? and ?\n" +
                "            AND toDate  BETWEEN ? and ?\n" +
                "            AND Reservations.busID = ?) as x)\n" +
                "    <1)\n" +
                "  BEGIN\n" +
                "    insert into Reservations( fromDate, toDate, price, busID, pID)\n" +
                "    Values (?,?,?,?,?) Select Scope_Identity()\n" +
                "\n" +
                "  END\n" +
                "ELSE\n" +
                "  THROW 51000, 'Bus is reserved for this period', 1;";
        
        preparedStatement = getPreparedStmt(query);


        return getPrivateFields(reservation, pId).executeUpdate();
    }
    
    /**
     * Retuns a prepared statment with reservation fields set up
     * @param reservation
     * @param pId
     * @return
     * @throws SQLException 
     */
    private PreparedStatement getPrivateFields(Reservation reservation,int pId) throws SQLException{
        preparedStatement.setDate(1, convertUtilToSql(reservation.getFrom()));
        preparedStatement.setDate(2, convertUtilToSql(reservation.getTo()));
        preparedStatement.setDate(3, convertUtilToSql(reservation.getFrom()));
        preparedStatement.setDate(4, convertUtilToSql(reservation.getTo()));
        preparedStatement.setInt(5, reservation.getBus().getId());

        preparedStatement.setDate(6, convertUtilToSql(reservation.getFrom()));
        preparedStatement.setDate(7, convertUtilToSql(reservation.getTo()));
        preparedStatement.setDouble(8, reservation.getPrice());
        preparedStatement.setInt(9, reservation.getBus().getId());
        preparedStatement.setInt(10, pId);
        return preparedStatement;
    }

    /**
     * Updates reservation in the database 
     * @param reservation to be updated
     * @param pId to be updated
     * @retur 1 if successful
     * @throws SQLException 
     */
    public int updateReservation(Reservation reservation,int pId) throws SQLException {
        String query = "UPDATE Reservations SET fromDate = ?, toDate = ?, price = ?, busID = ?, pID =?"
                + " WHERE reservationID = ?";
            preparedStatement = getPreparedStmt(query);
            preparedStatement = getPrivateFields(reservation, pId);
            preparedStatement.setInt(6, reservation.getId());
            
        return preparedStatement.executeUpdate();
    }
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        return new java.sql.Date(uDate.getTime());
    }
}
