package dbLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vasil Nedelchev on 20/05/2016.
 */
public class DbTravelBuses {
    private Connection con;
    private PreparedStatement preparedStatement;
    public DbTravelBuses(){
        con = DbConnection.getInstance().getDBcon();
    }
    
    /**
     * Returns a prepared statement with the given query
     * @param query
     * @return 
     */
    private PreparedStatement getPreparedStmt(String query) {
        try {
            //          System.out.println("prepareStatement " + query);

            preparedStatement = con.prepareStatement(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
    
    /**
     * Returns a id of the travelDaysTime 
     * @param travelID travels id
     * @param timeID times id
     * @param dayID days id
     * @return the id
     * @throws SQLException 
     */
    public int getTravelDaysTimeID(int travelID, int timeID, int dayID) throws SQLException {
        String query = "Select id from [Travel-Days-Times] WHERE " +
                "travelID = ? AND timeID = ? and [Travel-Days-Times].dayID = ?";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1,travelID);
        preparedStatement.setInt(2,timeID);
        preparedStatement.setInt(3,dayID);
        ResultSet resultSet = preparedStatement.executeQuery();
        Integer id = null;
        while (resultSet.next()){
           id = resultSet.getInt(1);
        }
        return id;
    }
    
    /**
     * Inserts into travelBusses
     * @param busID the id of the bus
     * @param travelDaysTimeID the id 
     * @return 1 if successful
     * @throws SQLException 
     */
    public int insertIntoTravelBuses(int busID, int travelDaysTimeID) throws SQLException {
        String query = "INSERT INTO [Buses-Travel] (busID, travelDaysTimeID) " +
                "VALUES (?,?)";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, busID);
        preparedStatement.setInt(2 ,travelDaysTimeID);
        Integer test = preparedStatement.executeUpdate();
        return test;
    }

}
