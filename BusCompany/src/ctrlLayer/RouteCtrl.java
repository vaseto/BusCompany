package ctrlLayer;

import dbLayer.DbRoute;
import dbLayer.DuplicateException;
import modelLayer.BusStation;
import modelLayer.Route;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vasil Nedelchev on 4.5.2016 г..
 */
public class RouteCtrl {
    private DbRoute dbRoute;
    public RouteCtrl() {
        dbRoute = new DbRoute();
    }
    /**
     * Return all routes
     * @return
     * @throws SQLException 
     */
    public List<Route> getAllRoutes() throws SQLException {
        return dbRoute.getAllRoutes();
    }
    /**
     * return a list of routes from a given bus station
     * @param busStationID the id of the station
     * @return
     * @throws SQLException 
     */
    public List<Route> getRoutesFrom(int busStationID) throws SQLException {
        BusStation busStation = new BusStation();
        busStation.setId(busStationID);
        return dbRoute.getRoutesFrom(busStation);
    }
    
    /**
     * return a list of routes from a given bus station
     * @param station
     * @return
     * @throws SQLException 
     */
    public List<Route> getRoutesFrom(BusStation station) throws SQLException{
        return dbRoute.getRoutesFrom(station);
    }
    /**
     * return a list of routes from a given bus station
     * @param city the city of the station
     * @return
     * @throws SQLException 
     */
    public List<Route> gerRoutesFrom(String city) throws SQLException {
        BusStation busStation = new BusStation(city);
        return dbRoute.getRoutesFromCity(busStation);
    }
    
    /**
     * Returns a route with that id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Route getRouteByID(int id) throws SQLException {
        return dbRoute.getRouteById(id);
    }
    /**
     * Deletes a route with that id
     * @param routeID
     * @return
     * @throws SQLException 
     */
    public int deleteRouteByID(int routeID) throws SQLException {
        return dbRoute.deleteRouteByID(routeID);
    }
    
    
    /**
     * Returns a route which have the specified busStations 
     * @param fromCity the fromCity of the station
     * @param toCity the toCity of the station
     * @return 
     * @throws SQLException 
     */
    public Route getRouteFromTo(String fromCity, String toCity) throws SQLException {
        BusStationCtrl stationCtrl = new BusStationCtrl();
        List<BusStation> busStations = stationCtrl.getAllStations();
        Integer fromID = busStations.stream()
                .filter(busStation -> fromCity.equals(busStation.getCity()))
                .map(BusStation::getId)
                .findAny()
                .orElse(null);
        Integer toID = busStations.stream()
                .filter(busStation -> toCity.equals(busStation.getCity()))
                .map(BusStation::getId)
                .findAny()
                .orElse(null);
     return  getRouteByID(getRouteID(fromID, toID));
    }
    
    /**
     * Creates and inserts a new route
     * @param fromBusStationID
     * @param toBusStationID
     * @param price
     * @param km
     * @param duration
     * @return
     * @throws DuplicateException
     * @throws SQLException 
     */
    public Route insertRoute(int fromBusStationID, int toBusStationID, double price, double km, int duration) throws DuplicateException, SQLException {
        BusStation busStation = new BusStation();
        BusStation busStation1 = new BusStation();
        busStation.setId(fromBusStationID);
        busStation1.setId(toBusStationID);

        Route route = new Route(busStation, busStation1,price,km,duration);
        route =  dbRoute.insertRoute(route);
        return route;
    }
    
    /**
     * Returns the id of the route with the given stations
     * @param fromBusStationID
     * @param toBusStationID
     * @return
     * @throws SQLException 
     */
    public int getRouteID(int fromBusStationID, int toBusStationID) throws SQLException {
        return dbRoute.getRouteID(fromBusStationID, toBusStationID);
    }

    public int updateRoute(int routeID, int newFromBusStationID, int newToBusStationID, double newPrice,
                            double newKm, int newDuration) throws DuplicateException, SQLException {
        BusStation busStation = new BusStation();
        BusStation busStation1 = new BusStation();
        busStation.setId(newFromBusStationID);
        busStation1.setId(newToBusStationID);
        Route route = new Route(busStation, busStation1, newPrice, newKm, newDuration);
        route.setId(routeID);
        return dbRoute.updateRoute(route);
    }
    
    public int getRouteID(Route route){
        return route.getId();
    }
    
    public BusStation getFromStation(Route route){
        return route.getFrom();
    }
    
    public BusStation getToStation(Route route){
        return route.getTo();
    }
    
    public double getPrice(Route route){
       return route.getPrice();
    }
    
    public double getKm(Route route){
        return  route.getKm();
    }   
    
    public int getDuration(Route route){
        return route.getDuration();
    }
    

}
