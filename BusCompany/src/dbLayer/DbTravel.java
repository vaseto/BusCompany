package dbLayer;

import modelLayer.*;
import modelLayer.Time;

import java.sql.*;
import java.util.*;

/**
 * Created by Vasil Nedelchev on 29.4.2016 г..
 */
public class DbTravel {
    private Connection con;
    private DbRoute dbRoute;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private DbTime dbTime;
    private DbDay dbDay;
    public DbTravel() {
        con = DbConnection.getInstance().getDBcon();
        dbRoute = new DbRoute();
        dbTime = new DbTime();
        dbDay = new DbDay();
    }

    /**
     * Builds a query for accessing the database
     * @param wClause
     * @return 
     */
    private String buildQuery(String wClause) {
        String query = "SELECT * FROM Travels ";
        if (wClause.length() > 0) {
            query = query + "where " + wClause;
        }
        return query;
    }


    /**
     * Returns a list of travels mathcing a wClause
     * @param wClause
     * @return
     * @throws SQLException 
     */
    private List<Travel> miscWhere(String wClause) throws SQLException {
        List<Travel> list = new ArrayList<>();
        ResultSet results ;
        String query = buildQuery(wClause);
     //   System.out.println("dbTravels" + query);
        try {
            Statement statement = con.createStatement();
            statement.setQueryTimeout(5);
            results = statement.executeQuery(query);
            while (results.next()) {
                list.add(buildTravel(results));
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
     * Returns a list of travels
     * @return
     * @throws SQLException 
     */
    public List<Travel> getAllTravels() throws SQLException {
        List<Travel> list = new ArrayList<>();
        Collections.synchronizedList(list);// for report
        ResultSet results ;
        ResultSet results2 ;
        String query1 = ";with cte as(select *, ntile(2) over(order by trID) as rn from Travels)\n" +
                "select * from cte where rn = 1";
        String query2 = ";with cte as(select *, ntile(2) over(order by trID) as rn from Travels)\n" +
                "select * from cte where rn = 2";

        preparedStatement = getPreparedStmt(query1);
        results = preparedStatement.executeQuery();


        Runnable runnable = () -> {
            try {
                while (results.next()) {
                    list.add(buildTravel(results));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        preparedStatement = getPreparedStmt(query2);
        results2 = preparedStatement.executeQuery();
        while (results2.next()) {
            list.add(buildTravel(results2));
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return list;

    }

    /**
     * Builds a travel object with the given resul set
     * @param results
     * @return
     * @throws SQLException 
     */
    private Travel buildTravel(ResultSet results) throws SQLException {
        Travel travel = new Travel();
        try {
            Runnable runnable = () -> {
                try {
                    travel.setMainRoute(dbRoute.getRouteById(results.getInt(4)));
                    travel.setDaysShedule(getDaysShedule(results.getInt(1)));

                }
                 catch (SQLException e) {
                    e.printStackTrace();
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();


            travel.setId(results.getInt(1));
            travel.setNumStops(results.getInt(2));
            travel.setKm(results.getDouble(3));

            travel.setRoutes(getRoutes(results.getInt(1)));
            thread.join();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return travel;
    }

    public Map<Day, List<Time>> getDaysShedule(int travelID) throws SQLException {
        String query = "SELECT dayID, timeID FROM [Travel-Days-Times] WHERE travelID =?";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, travelID);
        ResultSet resultSet = preparedStatement.executeQuery();
        Map<Day, List<modelLayer.Time>> daysShedule = new HashMap<>();

        while (resultSet.next()) {

            Day day = dbDay.getDayByID(resultSet.getInt(1));
            List<Time> times =new  ArrayList<>();
            if(daysShedule.containsKey(day)) {
                times = daysShedule.get(day);
            }
            times.add(dbTime.getTimeByID(resultSet.getInt(2)));
            daysShedule.put(day,times);

        }
        return daysShedule;
    }
    public Map<Integer,Route> getRoutes(int travelID) throws SQLException {
        ResultSet resultSet;
        String query = "SELECT routeID,stopNumber FROM [Travel-route] WHERE trID = ?";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, travelID);
        resultSet = preparedStatement.executeQuery();
        Map<Integer, Route> routes = new TreeMap<>();
        while(resultSet.next()){
            routes.put(resultSet.getInt(2),dbRoute.getRouteById(resultSet.getInt(1)));
        }
        return routes;
    }
    private PreparedStatement getPreparedStmt(String query) {
        try {
            preparedStatement = con.prepareStatement(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
    
    /**
     * Returns a travel with the given id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Travel getTravelById(int id) throws SQLException{
        String query = "SELECT * FROM  Travels WHERE trID = ?";
        preparedStatement = getPreparedStmt(query);
        Travel travel = new Travel();
        ResultSet resultSet = null;
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            travel = buildTravel(resultSet);
        }

        return travel;
    }



    /**
     * delets a travel with that id
     * @param id
     * @return 1 if successful
     * @throws SQLException 
     */
    public int deleteTravelByID(int id) throws SQLException {
        String query = " DELETE FROM Travels "
                + "WHERE trID =? ";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, id);
        int test = preparedStatement.executeUpdate();
        return test;
    }

   /* public void deleteSubRoutes(int routeID, String day, String departureTime, String arrivalTime) throws SQLException {
        int dayId = insertOrGetDay(day);
        int hoursId  = insertOrGerHour(departureTime, arrivalTime);
        deleteFromRouteDaysTimes(routeID,dayId,hoursId);
    }*/


    /**
     * Inserts a travel into the table
     * @param travel
     * @return
     * @throws SQLException
     * @throws DuplicateException 
     */
    public Travel insertTravel(Travel travel) throws SQLException, DuplicateException {

        String query = "INSERT INTO Travels (numStrops, km, mainRouteID)" +
                "VALUES (?,?,?) Select SCOPE_IDENTITY()";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, travel.getNumStops());
        preparedStatement.setDouble(2, travel.getKm());
        preparedStatement.setInt(3, travel.getMainRoute().getId());

        Integer travelID = null;
        ResultSet results;
        int test = preparedStatement.executeUpdate();
        results = preparedStatement.getGeneratedKeys();
        if (results.next()) {
            travelID = (results.getInt(1));
            travel.setId(travelID);
        }

        test += insertIntoTravelRoute(travel);
        test += insertIntoTravelDaysTimes(travel);

 
        return travel;
    }
    private int insertIntoTravelRoute(Travel travel) throws SQLException, DuplicateException {

        String query = "INSERT INTO [Travel-route](trID, routeID, stopNumber) VALUES (?,?,?)";
        int test = 0;
        for (Map.Entry<Integer,Route> entry: travel.getRoutes().entrySet()) {
            int stopNumber = entry.getKey();
            int routeID = entry.getValue().getId();
            preparedStatement = getPreparedStmt(query);

            preparedStatement.setInt(1, travel.getId());
            preparedStatement.setInt(3, stopNumber);
            preparedStatement.setInt(2, routeID);

            test=  preparedStatement.executeUpdate();
            if (test < 1) {
                throw new DuplicateException("The travel-route is already in the database");
            }
        }
        return test;
    }

    private int insertIntoTravelDaysTimes(Travel travel) throws SQLException, DuplicateException {
        String query = "INSERT INTO [Travel-Days-Times](travelID, dayID, timeID) VALUES (?,?,?)";
        Integer test = null;
        preparedStatement = getPreparedStmt(query);
        for(Map.Entry<Day, List<Time>> entry: travel.getDaysShedule().entrySet()) {
            int dayID = dbDay.getDayIDbyDay(entry.getKey().getDay());
            for (Time time : entry.getValue()) {
                int timeID = dbTime.getTimeID(time.getDepartureTime(), time.getArrivalTime());
                preparedStatement.setInt(1, travel.getId());
                preparedStatement.setInt(2, dayID);
                preparedStatement.setInt(3, timeID);

                 test = preparedStatement.executeUpdate();
                if (test < 1) {
                    throw new DuplicateException("The travel-route is already in the database");
                }
            }

        }
        return  test;
    }
    public List<Integer> getTravelIDsRelatedWithRouteID(Route route) throws SQLException {
        String query = "SELECT f.trID FROM\n" +
                "\n" +
                "\n" +
                "(SELECT trID FROM [Travel-route]\n" +
                "WHERE routeID = ?) as f\n" +
                "UNION\n" +
                "(select query1.trID FROM\n" +
                "\n" +
                "  (SELECT trID,stopNumber FROM [Travel-route] as t\n" +
                "    INNER JOIN  Routes r ON\n" +
                "                           t.routeID = r.routeID WHERE\n" +
                "    toBusStationID = ?) AS query1\n" +
                "  INNER JOIN\n" +
                "  (SELECT trID,stopNumber FROM [Travel-route] as t\n" +
                "    INNER JOIN  Routes r ON\n" +
                "                           t.routeID = r.routeID WHERE\n" +
                "    toBusStationID = ?) as query2\n" +
                "    ON query1.trID = query2.trID AND query1.stopNumber < query2.stopNumber)";

        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1,route.getId());
        preparedStatement.setInt(2,route.getFrom().getId());
        preparedStatement.setInt(3,route.getTo().getId());
        ResultSet resultSet =preparedStatement.executeQuery();
        List<Integer> list = new LinkedList<>();
        while (resultSet.next()){
            list.add(resultSet.getInt(1));
        }
        return list;
    }
    
    public int getTravel_Days_TimeID(int dayID,int travelID,int timeID) throws SQLException{
        String query = "select id From [Travel-Days-Times] where dayID = ? AND travelID = ? and timeID = ?";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, dayID);
        preparedStatement.setInt(2, travelID);
        preparedStatement.setInt(3, timeID);
        ResultSet resultSet= preparedStatement.executeQuery();
        Integer id = null;
        while(resultSet.next()){
            id = resultSet.getInt(1);
        }
        return id;
    }

    public List<Integer> getTravelIDsByDayID(int dayID) throws SQLException {
        String query = "select travelID FROM [Travel-Days-Times] WHERE dayID = ?";
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1,dayID);
        ResultSet resultSet =preparedStatement.executeQuery();
        List<Integer> list = new LinkedList<>();
        while (resultSet.next()){
            list.add(resultSet.getInt(1));
        }
        return list;
    }
    




    public void updateTravel( Travel newTravel, Travel oldTravel) throws SQLException, DuplicateException {
        /*String query = "UPDATE Routes SET fromBusStationID = ?, toBusStationID = ?, price = ?, km = ?"
                + " WHERE routeID = ?";
        int routeID = getRouteID(oldRoute.getFrom().getId(), oldRoute.getTo().getId());
        oldRoute.setId(routeID);
        newRoute.setId(routeID);
        System.out.println(routeID);
        preparedStatement = getPreparedStmt(query);
        preparedStatement.setInt(1, newRoute.getFrom().getId());
        preparedStatement.setInt(2, newRoute.getTo().getId());
        preparedStatement.setDouble(3, newRoute.getPrice());
        preparedStatement.setDouble(4, newRoute.getKm());
        preparedStatement.setInt(5, newRoute.getId());
        preparedStatement.executeUpdate();

        if(!newRoute.getDay().equals(oldRoute.getDay()) || !newRoute.getArrivalTime().equals(oldRoute.getArrivalTime())
                || !newRoute.getDepartureTime().equals(oldRoute.getDepartureTime())){

            int oldTimeID = insertOrGerHour(oldRoute.getDepartureTime(), oldRoute.getArrivalTime());
            int oldDayID = insertOrGetDay(oldRoute.getDay());
            System.out.println(oldDayID +" "+ oldTimeID + " "+oldRoute.getId());
            deleteFromRouteDaysTimes(oldRoute.getId(),oldDayID,oldTimeID);
            int dayId = insertOrGetDay(newRoute.getDay());
            int hoursId  = insertOrGerHour(newRoute.getDepartureTime(), newRoute.getArrivalTime());
            insertIntoRouteDaysTimes(newRoute.getId(), dayId, hoursId);
        }

        System.out.println("Updated Route table !");
*/

    }

}
