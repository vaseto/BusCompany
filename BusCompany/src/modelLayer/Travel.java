package modelLayer;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Travel {

    private double km;
    private int numStops;
    private Map<Integer, Route> routes;
    private Map<Day, List<Time>> daysShedule;// day - departure Hours
    private int id;
    private Route mainRoute;


    public Travel(Map<Integer, Route> routes, Map<Day, List<Time>> daysShedule) {

        this.routes = routes;
        this.daysShedule = daysShedule;
        this.mainRoute = routes.get(routes.size());

        int i = 0;

        for (Route route : routes.values()) {
            km = km + route.getKm();
            i++;

        }

        this.numStops = i;

        //should calculate arrival time
    }


    public Travel(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public int getNumStops() {
        return numStops;
    }

    public void setNumStops(int numStops) {
        this.numStops = numStops;
    }


    public Map<Day, List<Time>> getDaysShedule() {
        return daysShedule;
    }

    public void setDaysShedule(Map<Day, List<Time>> daysShedule) {
        this.daysShedule = daysShedule;
    }

    public Route getMainRoute() {
        return mainRoute;
    }

    public void setMainRoute(Route mainRoute) {
        this.mainRoute = mainRoute;
    }

    public Map<Integer, Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<Integer, Route> routes) {
        this.routes = routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Travel)) return false;
        Travel travel = (Travel) o;
        return Double.compare(travel.km, km) == 0 &&
                numStops == travel.numStops &&
                Objects.equals(routes, travel.routes) &&
                Objects.equals(daysShedule, travel.daysShedule) &&
                Objects.equals(mainRoute, travel.mainRoute);
    }

    @Override
    public String toString() {
        return "Travel{" +
                "km=" + km +
                ", numStops=" + numStops +
                ", routes=" + routes +
                ", daysShedule=" + daysShedule +
                ", id=" + id +
                ", mainRoute=" + mainRoute +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(km, numStops, routes, daysShedule, mainRoute);
    }
}
