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
import modelLayer.BusStation;

/**
 *
 * @author Delyan
 */
public class DbBusStation extends DbClass{

    private Connection con;
    private String from = "BusStations";

    public DbBusStation() {
        con = getCon();
    }
    
    /**
     * 
     * @return all buss stations
     * @throws SQLException 
     */
    public List<BusStation> getAllBusStations() throws SQLException {
        return missWhere("");
    }
    
    /**
     * Builds a bus station from the result set
     * @param results from the query executed
     * @return BusStation object
     * @throws SQLException 
     */
    private BusStation buildStation(ResultSet results) throws SQLException {
       return new BusStation(results.getString(2),results.getInt(1));
    }
    
    /**
     * 
     * @param wClause the clause to be searched by
     * @return Bus station
     * @throws SQLException 
     */
    private BusStation singleWhere(String wClause) throws SQLException {
        ResultSet results = getResultSet(wClause,from);
        results.next();
        return buildStation(results);
    }
    
    /**
     * 
     * @param wClause the clause to be searched by 
     * @return list of bus stations which matched the wClause
     * @throws SQLException 
     */
    private List<BusStation> missWhere(String wClause) throws SQLException {
        List<BusStation> list = new ArrayList<>();
        ResultSet results = getResultSet(wClause,from);

        while(results.next())
            list.add(buildStation(results));  

        return list;
    }
    
    /**
     * Statement for inserting BusStations into the database
     * @return newly create prepare statement for insert
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into BusStations (city) values (?) Select Scope_Identity()");
    }

    /**
     * Statement for updating the database record
     * @return
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForUpdate() throws SQLException {
        return con.prepareStatement("Update BusStations set city = ? where busStationID = ? ");
    }
    
    /**
     *  Finds a bus station with the given id
     * @param id the id of the bus station to be searched for
     * @return BusStaion with the specified id
     * @throws SQLException 
     */
    public BusStation getBusStationgById(int id) throws SQLException {
        return singleWhere("busStationID  = '" + id + "'");
    }
    /**
     *  Finds a bus station with the given name
     * @param city the city of the bus station to be searched for
     * @return BusStation which matches the city
     * @throws SQLException 
     */
    public BusStation getBusStationByCity(String city) throws SQLException {
        return singleWhere("city ='" + city + "'");
    }
    /**
     * Breaks and bus station object and adds it to the database
     * @param busStation the object to be inserted in the database
     * @return  the result of the executing of the prepared statement. Should 1 if it is successful
     * @throws SQLException 
     */
    public int insert(BusStation busStation) throws SQLException {
        PreparedStatement prSt = getPrepStatForInsert();
        prSt.setString(1, busStation.getCity());
        return prSt.executeUpdate();
    }
    /**
     *  Remove a record from the database matching the bus station id
     * @param busStation to be removed from the database
     * @return 1 if it is successfully removed
     * @throws SQLException 
     */
    public int delete(BusStation busStation) throws SQLException {
        return con.createStatement().executeUpdate("Delete from BusStations where busStationID = '" + busStation.getId() + "'");
    }
    /**
     * Updates a record in the database
     * @param busStation the station to be updated
     * @return 1 if it is successfully
     * @throws SQLException 
     */
    public int update(BusStation busStation) throws SQLException {
        PreparedStatement prSt = getPrepStatForUpdate();
        prSt.setString(1, busStation.getCity());
        prSt.setInt(2, busStation.getId());
        return prSt.executeUpdate();
    }


    public static void main(String args[]) throws SQLException {
        DbBusStation staions = new DbBusStation();
    }
}
