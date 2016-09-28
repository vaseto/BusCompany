package modelLayer;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Bus {

    private int noOfSeats;
    private int id;
    private Date year;
    private String  model;
    private boolean availableForRent;
    private List<Seat> seats;
    private List<Travel> travels;
    private double pricePerDay;
    
    // a parameterized constructor
    public Bus(int id, String model, int noOfSeats, Date year, boolean availableForRent, List<Seat> seats,double pricePerDay) {
        this.id = id;
        this.model = model;
        this.noOfSeats = noOfSeats;
        this.year = year;
        this.availableForRent = availableForRent;
        this.seats = seats;
        this.pricePerDay = pricePerDay;
    }

    public Bus(int id, String model, int noOfSeats, Date year, boolean availableForRent,double pricePerDay) {
        this.id = id;
        this.model = model;
        this.noOfSeats = noOfSeats;
        this.year = year;
        this.availableForRent = availableForRent;
        this.pricePerDay = pricePerDay;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public List<Travel> getTravels() {
        return travels;
    }
    

    public void setTravels(List<Travel> travels) {
        this.travels = travels;
    }

    public Bus() {

    }

    public String getModel() {
        return model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public boolean isAvailableForRent() {
        return availableForRent;
    }

    public void setAvailableForRent(boolean availableForRent) {
        this.availableForRent = availableForRent;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Bus{" + "noOfSeats=" + noOfSeats + ", id=" + id + ", year=" + year + ", model=" + model + ", availableForRent=" + availableForRent + ", travels=" + travels + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.noOfSeats;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.year);
        hash = 53 * hash + Objects.hashCode(this.model);
        hash = 53 * hash + (this.availableForRent ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.seats);
        hash = 53 * hash + Objects.hashCode(this.travels);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.pricePerDay) ^ (Double.doubleToLongBits(this.pricePerDay) >>> 32));
        return hash;
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
        final Bus other = (Bus) obj;
        if (this.noOfSeats != other.noOfSeats) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (this.availableForRent != other.availableForRent) {
            return false;
        }
        if (Double.doubleToLongBits(this.pricePerDay) != Double.doubleToLongBits(other.pricePerDay)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.seats, other.seats)) {
            return false;
        }
        return Objects.equals(this.travels, other.travels);
    }

 

}
