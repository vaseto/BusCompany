package ctrlLayer;

import java.sql.SQLException;
import java.util.List;

import dbLayer.DbBusStation;
import modelLayer.BusStation;

public class BusStationCtrl {
	
	private DbBusStation dbStation;
	
	public BusStationCtrl(){
		dbStation = new DbBusStation();
	}
	/**
	 * 
	 * @param city the parameter to be searched with
	 * @return the BusStation with this city
	 * @throws SQLException if the BusStation is not found
	 */
	public BusStation getStationByCity(String city) throws SQLException{
		return dbStation.getBusStationByCity(city);
	}
	/**
	 * 
	 * @param city crates new BusStation with that city
	 * @return 1 if the station is saved in to the database 
	 * @throws SQLException in case of exception in the database
	 */
	public int createBusStation(String city) throws SQLException{
		return dbStation.insert(new BusStation(city));
	}
	/**
	 * 
	 * @param id the searching parameter 
	 * @return the BusStation with this id
	 * @throws SQLException if the BusStation is not found
	 */
	public BusStation getStationById(int id) throws SQLException{
		return dbStation.getBusStationgById(id);
	}
	
	/**
	 * 
	 * @return List of all BusStations in the database
	 * @throws SQLException in case of mistake in the database
	 */
	public List<BusStation> getAllStations() throws SQLException{
		return dbStation.getAllBusStations();
	}
	
	/**
	 * 
	 * @param city the station to be deleted from the database
	 * @return 1 if the deleting was successful
	 * @throws SQLException in case of mistake in the database
	 */
	public int deleteStation(String city) throws SQLException{
		BusStation busStation = new BusStation(city);
		return dbStation.delete(busStation);
	}
	
	/**
	 * 
	 * @param newCity is the name which will replace the old name of the bus station
	 * @return 1 if the update was successful
	 * @throws SQLException in case of mistake in the database
	 */
	public int updateStation(String newCity, int id) throws SQLException{
		BusStation busStation = new BusStation(newCity, id);
		return dbStation.update(busStation);
	}
	
	
}
