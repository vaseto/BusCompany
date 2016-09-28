/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelLayer.BusStation;

import modelLayer.Route;

/**
 *
 * @author viva
 */
public class DbRoute {

    private Connection con;
    private DbBusStation dbBusStation;
    private PreparedStatement preparedStatement;

    public DbRoute() {
        con = DbConnection.getInstance().getDBcon();
        dbBusStation = new DbBusStation();
    }

    /**
     * Builds a query for searching
     *
     * @param wClause the clause to be used for searching
     * @return the query
     */
    private String buildQuery(String wClause) {
        String query = "SELECT * FROM Routes ";
        if (wClause.length() > 0) {
            query = query + "where " + wClause;
        }
        return query;
    }

    /**
     * Method used when more than one result is expected
     *
     * @param wClause
     * @return
     * @throws SQLException
     */
    private List<Route> miscWhere(String wClause) throws SQLException {
        List<Route> list = new ArrayList<>();
        ResultSet results;
        String query = buildQuery(wClause);
        //   System.out.println("dbRoutes" + query);
        try {
            Statement statement = con.createStatement();
            statement.setQueryTimeout(5);
            results = statement.executeQuery(query);
            while (results.next()) {
                list.add(buildRoute(results));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("query exception - select project" + e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Return all routes
     *
     * @return
     * @throws SQLException
     */
    public List<Route> getAllRoutes() throws SQLException {
        return miscWhere("");
    }

    /**
     *
     * @param station the stating from which to be searched
     * @return list of routes starting from the station
     * @throws SQLException
     */
    public List<Route> getRoutesFrom(BusStation station) throws SQLException {
        return miscWhere("fromBusStationID ='" + station.getId() + "'");
    }

    /**
     * Finds a the station and returns a list of all routes starting from there
     * Returns a list of all routes starting from the station
     *
     * @param station
     * @return
     * @throws SQLException
     */
    public List<Route> getRoutesFromCity(BusStation station) throws SQLException {
        int id = dbBusStation.getBusStationByCity(station.getCity()).getId();
        return miscWhere("fromBusStationID ='" + id + "'");
    }

    /**
     * Builds a route object from the given result set
     *
     * @param results the result set
     * @return the route object
     * @throws SQLException
     */
    private Route buildRoute(ResultSet results) throws SQLException {
        Route route = new Route();

        route.setFrom(dbBusStation.getBusStationgById(results.getInt(2)));
        route.setTo(dbBusStation.getBusStationgById(results.getInt(3)));

        route.setId(results.getInt(1));
        route.setPrice(results.getDouble(4));
        route.setKm(results.getDouble(5));
        route.setDuration(results.getInt(6));

        return route;
    }

    private PreparedStatement getPreparedStmt(String query) {
        try {
            preparedStatement = con.prepareStatement(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public Route getRouteById(int id) throws SQLException {
        ResultSet resultSet;
        String query = "SELECT * FROM  Routes WHERE routeID = ?";
        preparedStatement = getPreparedStmt(query);
        Route route = new Route();

        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            route = buildRoute(resultSet);
        }

        return route;
    }

    /**
     * delete all "subRoutes" where routeId = id
     *
     * @param id
     * @throws SQLException
     */
    public int deleteRouteByID(int id) throws SQLException {
        String query = " DELETE FROM Routes "
                + "WHERE routeID =? ";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, id);
        int test = preparedStatement.executeUpdate();
        // System.out.println("Route is deleted from table Routes with  id: "+ id+" !");
        return test;
    }

    public Route insertRoute(Route route) throws SQLException, DuplicateException {
        String query = "IF  exists(select fromBusStationID,toBusStationID,price,km,duration from Routes"
                + " WHERE fromBusStationID = ? and toBusStationID = ? and Routes.price = ? and Routes.km = ? AND Routes.duration =?) "
                + " SELECT routeID FROM  Routes"
                + " WHERE fromBusStationID = ? and toBusStationID = ? and Routes.price = ? and Routes.km = ? and Routes.duration=?"
                + " else INSERT into Routes (fromBusStationID,toBusStationID,price,km,duration)"
                + " values (?,?,?,?,?) Select Scope_Identity() ";

        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, route.getFrom().getId());
        preparedStatement.setInt(2, route.getTo().getId());
        preparedStatement.setDouble(3, route.getPrice());
        preparedStatement.setDouble(4, route.getKm());
        preparedStatement.setInt(5, route.getDuration());

        preparedStatement.setInt(6, route.getFrom().getId());
        preparedStatement.setInt(7, route.getTo().getId());
        preparedStatement.setDouble(8, route.getPrice());
        preparedStatement.setDouble(9, route.getKm());
        preparedStatement.setInt(10, route.getDuration());
        preparedStatement.setInt(11, route.getFrom().getId());
        preparedStatement.setInt(12, route.getTo().getId());
        preparedStatement.setDouble(13, route.getPrice());
        preparedStatement.setDouble(14, route.getKm());
        preparedStatement.setInt(15, route.getDuration());
        boolean test = preparedStatement.execute();
        Integer routeID = null;
        ResultSet results;
        if (test) {
            results = preparedStatement.executeQuery();
            if (results.next()) {
                routeID = results.getInt(1);
                throw new DuplicateException("this route already exist with id: " + routeID);
            }
        } else {
            //isItInserted = preparedStatement.executeUpdate();
            results = preparedStatement.getGeneratedKeys();
            if (results.next()) {
                routeID = (results.getInt(1));
                route.setId(routeID);
            }
        }
        return route;
    }

    public int updateRoute(Route newRoute) throws SQLException, DuplicateException {
        String query = "UPDATE Routes SET fromBusStationID = ?, toBusStationID = ?, price = ?, km = ?, duration=?"
                + " WHERE routeID = ?";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, newRoute.getFrom().getId());
        preparedStatement.setInt(2, newRoute.getTo().getId());
        preparedStatement.setDouble(3, newRoute.getPrice());
        preparedStatement.setDouble(4, newRoute.getKm());
        preparedStatement.setInt(5, newRoute.getDuration());
        preparedStatement.setInt(6, newRoute.getId());
        int test = preparedStatement.executeUpdate();

        //     System.out.println("Updated Route table !");
        return test;
    }

    public int getRouteID(int fromBusStationID, int toBusStationID) throws SQLException {
        String query = "SELECT routeID FROM Routes "
                + "WHERE fromBusStationID = ? AND toBusStationID = ?";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, fromBusStationID);
        preparedStatement.setInt(2, toBusStationID);

        ResultSet resultSet = preparedStatement.executeQuery();
        Integer id = null;
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

}
