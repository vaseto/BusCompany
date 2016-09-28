package dbLayer

import modelLayer.BusStation
import modelLayer.Route
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * Created by Vasil Nedelchev on 26.4.2016 г..
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)// specify execution order using annotations and ordering by method name
class DbRouteTest extends GroovyTestCase {
    private DbRoute dbRoute;
    private DbBusStation dbBusStation;
    private Route route;
    private  Route route2;



    @Override
    @Before
    void setUp() {
        super.setUp()
        dbRoute = new DbRoute();
        dbBusStation = new DbBusStation();
        route = createRoute();
        route2 = new Route(dbBusStation.getBusStationByCity("Lubimec"), dbBusStation.getBusStationByCity("Harmanli"),
                300,400,50);

    }

    Route createRoute() {

        dbBusStation = new DbBusStation();



        route = new Route(dbBusStation.getBusStationByCity("Harmanli"), dbBusStation.getBusStationByCity("Vidin"),
        33.2,111,120);
        return route;
    }
    @Test
    void test1InsertRoute() {
        createRoute();
        dbRoute.insertRoute(route);
    }
    @Test
    void test2GetRouteID() {
        Integer id = dbRoute.getRouteID(route.from.getId(), route.to.getId(),route.getPrice());
        assertTrue(id != 0 || id != null);

    }
    @Test
    void test3GetRouteById() {
        Route route1 = dbRoute.getRouteById(dbRoute.getRouteID(route.from.getId(), route.to.getId(),route.getPrice()));
        assert route == route1;

    }
    void test4UpdateRoute() {
        dbRoute.updateRoute(route2,route);
        /*int id =dbRoute.getSubRouteIDFromView(route.from.getId(), route.to.getId(),
                route.getDay(), route.getDepartureTime(), route.getArrivalTime());*/

    }
 /*   @Test
    void test5DeleteSubRoads() {
        dbRoute.deleteSubRoads(route2);
    }*/

    @Test
    void test6DeleteRouteByID() {
        dbRoute.deleteRouteByID(dbRoute.getRouteID(route2.getFrom().getId(), route2.getTo().getId(),route2.getPrice()));
    }

  /*  void testDeleteSubRoads1() {
        int routeID = dbRoute.getSubRouteIDFromView(route.getFrom().getId(), route.getTo().getId(),
                route.getDay(), route.getDepartureTime(), route.getArrivalTime());
        dbRoute.deleteSubRoads(routeID,route.getDay(),route.getDepartureTime(),route.getArrivalTime());
    }*/
}
