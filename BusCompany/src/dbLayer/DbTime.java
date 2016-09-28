package dbLayer;

import modelLayer.Time;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasil Nedelchev on 2.5.2016 г..
 */
public class DbTime {
    private Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DbTime() {
        con = DbConnection.getInstance().getDBcon();
    }
    
    /**
     * Retuns a list of all times
     * @param wClause
     * @return 
     */
    private List<Time> miscWhere(String wClause) {
        ResultSet resultSet;
        List<Time> times = new ArrayList<>();
        String query = buildQuery(wClause);
  //      System.out.println("dbTimes" + query);
        try {
            Statement statement = con.createStatement();
            statement.setQueryTimeout(5);
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Time time = buildTime(resultSet);
                times.add(time);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("query exception - select project" + e);
            e.printStackTrace();
        }
        return times;
    }

    /**
     * Builds a time object with the resultset
     * @param resultSet
     * @return
     * @throws SQLException 
     */
    private Time buildTime(ResultSet resultSet) throws SQLException {
        Time time = new Time();

        time.setId((resultSet.getInt(1)));
        time.setDepartureTime(resultSet.getString(2));
        time.setArrivalTime(resultSet.getString(3));
        return time;

    }
    
    /**
     * Returns a prepared statement with the given query
     * @param query
     * @return 
     */
    private PreparedStatement getPreparedStmt(String query) {
        try {
//            System.out.println("prepareStatement " + query);

            preparedStatement = con.prepareStatement(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
    /**
     * Returns a list of all times
     * @return 
     */
    public List<Time> getAllTimes(){
        return miscWhere("");
    }

    /**
     * Builds a query for accessing the database
     * @param wClause
     * @return 
     */
    private String buildQuery(String wClause) {
        String query = "SELECT * FROM Times";
        if (wClause.length() > 0) {
            query = query + "where" + wClause;
        }
        return query;
    }
    
    /**
     * Returns a time object with the given id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Time getTimeByID(int id) throws SQLException {
        String query = "SELECT * FROM  Times WHERE id = ?";
        preparedStatement = getPreparedStmt(query);
        Time time = new Time();
        ResultSet resultSet;
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            time = buildTime(resultSet);
        }
        return time;
    }
    
    /**
     * Creates a time with that departure time
     * @param departureTime
     * @return the created time 
     */
    public Time getTimeBy(String departureTime ){
        Time time = new Time(departureTime);
        return time;
    }
    /**
     * Returns a time object id with the given departure and arrival times
     * @param departureTime
     * @param arrivalTime
     * @return
     * @throws SQLException 
     */
    public Integer getTimeID(String departureTime, String arrivalTime) throws SQLException {
        String query = "if exists(select id FROM Times WHERE departureTime= ? and arrivalTime= ?) " +
                "select id FROM  Times WHERE departureTime = ? and arrivalTime = ?";
        preparedStatement = getPreparedStmt(query);
        Time day = new Time();
        ResultSet resultSet;
        preparedStatement.setString(1, departureTime);
        preparedStatement.setString(2, arrivalTime);
        preparedStatement.setString(3, departureTime);
        preparedStatement.setString(4, arrivalTime);

        Integer id = null;
        boolean test = preparedStatement.execute();
            if(test){
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }else {

                Time time = new Time(departureTime,arrivalTime);
               id = createTime(time);
            }


        return id;
    }
    
    /**
     * Removes a time object with the given id
     * @param id 
     * @return 1 if successful
     * @throws SQLException 
     */
    public int deleteTimeByID(int id) throws SQLException {
        String query = " DELETE FROM Times "
                + "WHERE id =? ";

        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, id);
        int test = preparedStatement.executeUpdate();

        System.out.println("Times are deleted from table Times with  id: "+ id+" !");
        return test;
    }
    
    /**
     * Inserts a time object into the database
     * @param time
     * @return 1 if succesful
     * @throws SQLException 
     */
    public int createTime(Time time) throws SQLException {
        String query = "insert into Times(departureTime, arrivalTime) "

                + " Values (?,?) Select Scope_Identity()";


        preparedStatement = getPreparedStmt(query);

        preparedStatement.setString(1, time.getDepartureTime());
        preparedStatement.setString(2,time.getArrivalTime());

        preparedStatement.executeUpdate();
        preparedStatement.getGeneratedKeys();
        resultSet = preparedStatement.getGeneratedKeys();
        Integer id = null;
        if (resultSet.next()) {
            time.setId((resultSet.getInt(1)));
            id = resultSet.getInt(1);

        }
        System.out.println("Record is inserted into Times tables!");
        return id;

    }
    public static void main(String[] args) throws SQLException {
        DbTime test = new DbTime();
        System.out.println(test.getTimeID("8:45", "15:34"));
    }
}
