package ctrlLayer;

import dbLayer.DbBuses;
import dbLayer.DbTravel;
import dbLayer.DbTravelBuses;
import dbLayer.DuplicateException;
import modelLayer.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Created by Vasil Nedelchev on 5.5.2016 г..
 */
public class TravelCtrl {

    private DbTravel dbTravel;
    private RouteCtrl routeCtrl;
    private DayCtrl dayCtrl;

    public TravelCtrl() {
        dbTravel = new DbTravel();
        routeCtrl = new RouteCtrl();
        dayCtrl = new DayCtrl();
    }

    public List<Travel> getAllTravels() throws SQLException {
        return dbTravel.getAllTravels();
    }

    public Travel getTravelByID(int id) throws SQLException {
        return dbTravel.getTravelById(id);
    }

    public int deleteTravelByID(int id) throws SQLException {
        return dbTravel.deleteTravelByID(id);
    }

    public Map<Day, List<Time>> getDaysSheduleForTravelByID(int travelID) throws SQLException {
        return dbTravel.getDaysShedule(travelID);
    }

    public Map<Integer, Route> getRoutesOfTravelByTravelID(int travelID) throws SQLException {
        return dbTravel.getRoutes(travelID);
    }

    public Travel insertTravle(Map<Integer, Route> routes, Map<Day, List<Time>> daysShedule,  Map<Day, HashMap<Time,Integer>> busShcedules) throws DuplicateException, SQLException {
        TravelBuses travelBuses = new TravelBuses();
        Travel travel = new Travel(routes, daysShedule);
        calculateTime(travel.getDaysShedule(), travel.getRoutes());
        Travel travel2 =  dbTravel.insertTravel(travel);
        travelBuses.insertIntoTravelBuses(busShcedules,travel2);
        return travel2;
    }

    public String calculateTime(Map<Day, List<Time>> daysShedule, Map<Integer, Route> routes) throws SQLException {
        String arrivalTime = "";

        //System.out.printf("%d:%02d", hours, minutes);
        int duration = 0;
        int busStationID = 0;

        for (Map.Entry<Integer, Route> route : routes.entrySet()) {
            if (route.getKey() == 1) {
                duration += route.getValue().getDuration();
                busStationID = route.getValue().getTo().getId();
            }
            if (route.getKey() >= 2) {
                Route route1 = routeCtrl.getRouteByID(routeCtrl.getRouteID(busStationID,
                        route.getValue().getTo().getId()));
                if (route1.getDuration() != 0) {
                    duration += route1.getDuration();
                    busStationID = route.getValue().getTo().getId();
                }
            }
        }

        for (Map.Entry<Day, List<Time>> entry : daysShedule.entrySet()) {

            for (Time time : entry.getValue()) {
                String departureTime = time.getDepartureTime();
                arrivalTime = calculateTime(departureTime, duration);

                time.setArrivalTime(arrivalTime);
                System.out.println(arrivalTime + "time");
            }
        }

        return arrivalTime;
    }

    public List<Integer> getTravelIDsRelatedwithRouteId(Route route) throws SQLException{
       return dbTravel.getTravelIDsRelatedWithRouteID(route);
    }

    public HashMap<Travel, HashMap<Bus,Time>> getPossibleTravelsByRouteAndDate(Route route,Date date) throws SQLException {
        List<Integer> list = new ArrayList<>();
        List<?>[] arrOfLists = new List<?>[]{list};

        Runnable runnable = () -> {
            try {
                arrOfLists[0] = getTravelIDsRelatedwithRouteId(route);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Day day = dayCtrl.getDayByDate(date);
        Instant start = Instant.now();
        List<Integer> travelIDsDay = dbTravel.getTravelIDsByDayID(day.getDayID());
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> common = (List<Integer>) arrOfLists[0];
        common.retainAll(travelIDsDay);
        List<Travel> travels = new ArrayList<>();

        common.forEach(n -> {
            try {
                travels.add(getTravelByID(n));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        HashMap<Bus,Time> busTime = new HashMap<>();
        HashMap<Travel, HashMap<Bus,Time>> travelTime = new HashMap<>();
        travels.forEach(t -> {
            try {
                travelTime.put(t, getTimes(t, route));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        Instant end = Instant.now();
        System.out.println("travel ctrl get possible travles: "+Duration.between(start, end));
        return travelTime;
    }

    public HashMap<Bus,Time> getTimes(Travel travel, Route mainRoute) throws SQLException {
        HashMap<Bus,Time> busTimes = new HashMap<>();
        DbBuses dbBuses = new DbBuses();
        TreeMap<Integer, Route> routes = (TreeMap<Integer, Route>) travel.getRoutes();
        HashMap<Day, List<Time>> schedules = (HashMap<Day, List<Time>>) travel.getDaysShedule();
        for (HashMap.Entry<Day, List<Time>> entry : schedules.entrySet()) {
            String day = entry.getKey().getDay();
            int dayID = dayCtrl.getDayIDByDay(day);
            for (Time time : entry.getValue()) {

                int duration = 0;
                int fromBusStationID = 0;
                String departureTime = time.getDepartureTime();
                String arrivalTime = "";
                Route routeTwo = new Route();
                HashMap<Integer, String> fromIDs = new HashMap<>();
                int fromTempID = 0;
                int tempToCityForEach[] = {-1};
                String mapArrivalTime[] = {""};
                int routeDayTimeID = dbTravel.getTravel_Days_TimeID(dayID, travel.getId(), time.getId());

                Bus bus = dbBuses.getBusFromTravel(routeDayTimeID);
                if (bus != null) {

                    for (Map.Entry<Integer, Route> route : routes.entrySet()) {
                        if (route.getKey() == 1) {
/*                        routeTwo = routeCtrl.getRouteByID(routeCtrl.getRouteID(route.getValue().getFrom().getId(),
                                route.getValue().getTo().getId()));*/
                            duration += route.getValue().getDuration();
                            fromBusStationID = route.getValue().getTo().getId();
                        }
                        if (route.getKey() >= 2) {
                            routeTwo = routeCtrl.getRouteByID(routeCtrl.getRouteID(fromBusStationID,
                                    route.getValue().getTo().getId()));
                            if (routeTwo.getDuration() != 0) {
                                fromTempID = fromBusStationID;// for fromIDs
                                duration += routeTwo.getDuration();
                                fromBusStationID = route.getValue().getTo().getId();
                                tempToCityForEach[0] = route.getValue().getTo().getId();
                            }
                        }
                        arrivalTime = calculateTime(departureTime, duration);
                        mapArrivalTime[0] = arrivalTime;

                        Route routeOne = routeCtrl.getRouteFromTo(route.getValue().getFrom().getCity(), route.getValue().getTo().getCity());

                        if (routeOne.equals(mainRoute)) {

                            Time time1 = new Time(time.getDepartureTime(), arrivalTime);
                            busTimes.put(bus, time1);
                        }

                        if (route.getKey() >= 2) {
                            if (routeTwo.equals(mainRoute)) {
                                Time time1 = new Time(departureTime, arrivalTime);
                                busTimes.put(bus, time1);
                            }
                        }
                        fromIDs.forEach((k, v) -> {
                            int mapRouteID = 0;
                            try {
                                mapRouteID = routeCtrl.getRouteID(k, tempToCityForEach[0]);
                                Route mapRoute = routeCtrl.getRouteByID(mapRouteID);
                                if (mapRoute.equals(mainRoute)) {
                                    Time time1 = new Time(v, mapArrivalTime[0]);
                                    busTimes.put(bus, time1);
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        if (route.getKey() >= 2) {
                            fromIDs.put(fromTempID, departureTime);
                        }

                        departureTime = arrivalTime;
                        duration = 0;
                    }
                }
            }
        }

        return busTimes;
    }

    public int getTravel_Days_TimeID(int dayID,int travelID, int timeID) throws SQLException{
        return dbTravel.getTravel_Days_TimeID(dayID, travelID, timeID);
    }

    private String calculateTime(String departureTime, int duration) {
        String arrivalTime = "";
        String[] hourMin = departureTime.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        mins = mins + hoursInMins;
        int newTime = mins + duration;
        int hours = newTime / 60; //since both are ints, you get an int
        int minutes = newTime % 60;
        arrivalTime = String.format("%02d", hours) + ":"
                + String.format("%02d", minutes);
        return arrivalTime;
    }

    public static void main(String[] args) {
        TravelCtrl travelCtrl = new TravelCtrl();

        Date date = new Date();

        try {
            String input_date = "13/5/2016";
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date dt1 = format1.parse(input_date);
            RouteCtrl routeCtrl = new RouteCtrl();
            BusStationCtrl busStationCtrl = new BusStationCtrl();

            int routeID = routeCtrl.getRouteID(busStationCtrl.getStationByCity("Svilengrad").getId(),busStationCtrl.getStationByCity("Sofia").getId());
            Route route = routeCtrl.getRouteByID(routeID);
            List<Integer> routes = travelCtrl.getTravelIDsRelatedwithRouteId(route);
            travelCtrl.getPossibleTravelsByRouteAndDate(route,dt1).entrySet().stream().forEach(m -> {
                System.out.println(m.getKey());
                System.out.println();
               m.getValue().entrySet().forEach((k)->{
                    System.out.println(k.getKey());
                    System.out.println(k.getValue());
                });
            });

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
