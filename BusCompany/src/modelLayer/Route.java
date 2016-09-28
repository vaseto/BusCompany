package modelLayer;

import java.util.Objects;

public class Route {

    private BusStation from, to;

    private double price, km;
    private int id, duration;

    public Route(BusStation from, BusStation to, double price, double km, int duration) {
        this.from = from;
        this.to = to;
        this.price = price;
        this.km = km;
        this.duration = duration;
    }

    public Route() {

    }

    public BusStation getFrom() {
        return from;
    }

    public void setFrom(BusStation from) {
        this.from = from;
    }

    public BusStation getTo() {
        return to;
    }

    public void setTo(BusStation to) {
        this.to = to;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Route{"
                + "from=" + from
                + ", to=" + to
                + ", price=" + price
                + ", km=" + km
                + ", id=" + id
                + ", duration=" + duration
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Route)) {
            return false;
        }
        Route route = (Route) o;
        return Double.compare(route.price, price) == 0
                && Double.compare(route.km, km) == 0
                && duration == route.duration
                && Objects.equals(from, route.from)
                && Objects.equals(to, route.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, price, km, duration);
    }
}
