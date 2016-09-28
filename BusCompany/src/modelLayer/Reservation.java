package modelLayer;

import java.util.Date;
import java.util.Objects;

public class Reservation {

    private Date from, to;
    private double price;
    private int id;
    private Bus bus;

    public Reservation(Date from, Date to, double price, Bus bus) {
        this.from = from;
        this.to = to;
        this.price = price;
        this.bus = bus;
    }

    public Reservation() {


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.from);
        hash = 11 * hash + Objects.hashCode(this.to);
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 11 * hash + this.id;
        hash = 11 * hash + Objects.hashCode(this.bus);
        return hash;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        return Objects.equals(this.bus, other.bus);
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "from=" + from +
                ", to=" + to +
                ", price=" + price +
                ", id=" + id +
                ", bus=" + bus +
                '}';
    }
}
