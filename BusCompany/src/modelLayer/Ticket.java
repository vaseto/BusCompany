package modelLayer;

import java.util.Date;

public class Ticket {

    private double price;
    private Route route;
    private Category category;
    private int id;
    private Time time;
    private Date date;
    private Seat seat;

    public Ticket(Route route, Category category, int id, Time time, Date date, Seat seat) {
        this.price = route.getPrice() - (route.getPrice()/category.getDiscount());
        this.route = route;
        this.category = category;
        this.id = id;
        this.time = time;
        this.date = date;
        this.seat = seat;
    }

    public Ticket(Route route,Category category, Time time, Date date, Seat seat) {
        double percent = (route.getPrice()*category.getDiscount()) /100;
        this.price = route.getPrice() - percent;
        this.route = route;
        this.category = category;
        this.time = time;
        this.date = date;
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "Ticket{" + "price=" + price + ", route=" + route + ", person=" + " category=" + category + ", id=" + id + ", time=" + time + ", date=" + date + ", seat=" + seat + '}';
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    
}