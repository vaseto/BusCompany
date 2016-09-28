package dbLayer;

import modelLayer.Bus;

import java.sql.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import modelLayer.Travel;

public class DbBuses {

    private Connection con;
    private PreparedStatement preparedStatement;

    public DbBuses() {
        con = DbConnection.getInstance().getDBcon();
    }
    /**
     * Method used to when multiple objects are expected
     * @param wClause
     * @return 
     */
    private List<Bus> miscWhere(String wClause) {
        ResultSet resultSet;
        List<Bus> buses = new ArrayList<>();
        String query = buildQuery(wClause);
        try {
            Statement statement = con.createStatement();
            statement.setQueryTimeout(5);
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Bus bus = buildBus(resultSet);
                buses.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("query exception - select project" + e);
            e.printStackTrace();
        }
        return buses;
    }

    /**
     * Returns a bus which is assigned to a travel at specific day and time
     * @param travel_day_timeIDs
     * @return
     * @throws SQLException 
     */
    public Bus getBusFromTravel(int travel_day_timeIDs) throws SQLException{
        String query = "select busID from [Buses-Travel] where travelDaysTimeID = ?";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, travel_day_timeIDs);
        Integer id = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            id = resultSet.getInt(1);
        }
        if(id != null) {
            return getBusByID(id);
        }else{
            return null;
        }
    }

    /**
     * Returns a list of all rentable busses with seats more than the given
     * @param noOfSeats 
     * @return 
     * @throws SQLException 
     */
    public List<Bus> getAllRentableBusses(int noOfSeats) throws SQLException{
        String query = "select * from Buses where rentable = 'true' and noOfSeats >" + noOfSeats;
      ResultSet results = con.createStatement().executeQuery(query);
      List<Bus> list = new ArrayList<>();
      while(results.next()){
          list.add(buildBus(results));
      }
      return list;
    }

    /**
     * BUilds a bus with the given result set
     * @param resultSet
     * @return 
     */
    private Bus buildBus(ResultSet resultSet) {
        Bus bus = new Bus();
        DbSeat dbSeat = new DbSeat();
        try {

            int busId = resultSet.getInt(1);
            bus.setId(busId);
            bus.setModel(resultSet.getString(2));
            bus.setNoOfSeats(resultSet.getInt(3));
            bus.setYear(resultSet.getDate(4));
            bus.setAvailableForRent(resultSet.getBoolean(5));
            bus.setSeats(dbSeat.getAllSeatsForBus(busId));
            bus.setTravels(null); // TODO: 24/05/2016 set travels null
            bus.setPricePerDay(resultSet.getDouble(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bus;
    }

    /**
     * Return all travels for a bus
     * @param busId
     * @return
     * @throws SQLException 
     */
    private List<Travel> getBusAllTravels(int busId) throws SQLException {
        String query = "select travelDaysTimeID from [Buses-Travel] where busID = " + busId + "";
        ResultSet results = con.prepareStatement(query).executeQuery();
        List<Travel> travels = new ArrayList<>();
        if (results.next()) {
            String query2 = "select travelID from [Travel-Days-Times] where id = " + results.getInt(1);
            ResultSet results2 = con.prepareStatement(query2).executeQuery();
            while (results2.next()) {
                DbTravel dbTravel = new DbTravel();

                travels.add(dbTravel.getTravelById(results2.getInt(1)));
            }

        }
        return travels;
    }

    /**
     * Builds a query for accessing the database
     * @param wClause
     * @return 
     */
    private String buildQuery(String wClause) {
        String query = "SELECT * FROM Buses";
        if (wClause.length() > 0) {
            query = query + "where" + wClause;
        }
        return query;
    }

    /**
     * Returns a list of all busses
     * @return 
     */
    public List<Bus> getAllBuses() {
        ResultSet resultSet;
        ResultSet resultSet2;
        List<Bus> buses = new ArrayList<>();
        Collections.synchronizedList(buses);
        String query = ";with cte as(select *, ntile(2) over(order by busID) as rn from Buses)\n" +
                "select * from cte where rn = 1";
        String query2 = ";with cte as(select *, ntile(2) over(order by busID) as rn from Buses)\n" +
                "select * from cte where rn = 2";
        preparedStatement = getPreparedStmt(query);
        try {
            resultSet = preparedStatement.executeQuery();

            Runnable runnable = () -> {
                try {
                    while (resultSet.next()) {
                        Bus bus = buildBus(resultSet);
                        buses.add(bus);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();



            preparedStatement = getPreparedStmt(query2);
            resultSet2 = preparedStatement.executeQuery();
            while (resultSet2.next()) {
                Bus bus = buildBus(resultSet2);
                buses.add(bus);
            }
            thread.join();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("query exception - select project" + e);
            e.printStackTrace();
        }

        return buses;
    }

    /**
     * Returns a prepared statment with the given query
     * @param query
     * @return 
     */
    private PreparedStatement getPreparedStmt(String query) {
        try {
            preparedStatement = con.prepareStatement(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
    
    
    /**
     * Returns a bus with the given id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Bus getBusByID(int id) throws SQLException {
        String query = "SELECT * FROM  Buses WHERE busID = ?";
        preparedStatement = getPreparedStmt(query);
        Bus bus = new Bus();
        ResultSet resultSet;

        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            bus = buildBus(resultSet);
        }

        return bus;
    }

    /**
     * Delets a bus with the given id
     * @param id
     * @return
     * @throws SQLException 
     */
    public int deleteBusbyID(int id) throws SQLException {
        String query = " DELETE FROM Buses "
                + "WHERE busID =? ";

        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate();
    }

    /**
     * Inserts a bus into the database
     * @param bus
     * @return
     * @throws SQLException 
     */
    public int createBus(Bus bus) throws SQLException {
        String query = "insert into Buses(busID,model,noOfSeats,year,rentable,price_per_day) "
                + " Values (?,?,?,?,?,?) ";

        preparedStatement = getPreparedStmt(query);

        return getPrivateFields(bus).executeUpdate();
    }

    /**
     * Returns a prepared statament with all the private fields of a bus
     * @param bus
     * @return 
     * @throws SQLException 
     */
    private PreparedStatement getPrivateFields(Bus bus) throws SQLException {
        preparedStatement.setInt(1, bus.getId());
        preparedStatement.setString(2, bus.getModel());
        preparedStatement.setInt(3, bus.getNoOfSeats());
        preparedStatement.setDate(4, convertUtilToSql(bus.getYear()));
        preparedStatement.setBoolean(5, bus.isAvailableForRent());
        preparedStatement.setDouble(6,bus.getPricePerDay());
        return preparedStatement;
    }

    /**
     * Updates a bus
     * @param idBeforeUpdate
     * @param bus
     * @return 1 if successful
     * @throws SQLException 
     */
    public int updateBusById(int idBeforeUpdate, Bus bus) throws SQLException {
        String query = "UPDATE Buses SET busID = ?,model = ?, noOfSeats = ?, year = ?, rentable = ?,price_per_day = ?"
                + " WHERE busID = ?";

        preparedStatement = getPreparedStmt(query);

        preparedStatement = getPrivateFields(bus);
        preparedStatement.setInt(7, idBeforeUpdate);
        return preparedStatement.executeUpdate();
    }

    /**
     * Returns sql date from a util date
     * @param uDate
     * @return 
     */
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    public static void main(String[] args) throws SQLException {
        DbBuses dbBuses = new DbBuses();
        String string = "01, 2, 2015";
        DateFormat format = new SimpleDateFormat("MM, d, yyyy", Locale.ENGLISH);
        java.util.Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Bus bus = dbBuses.getBusByID(123);
        System.out.println(bus);
    }

}