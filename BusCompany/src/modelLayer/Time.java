package modelLayer;

import java.util.Objects;

/**
 * Created by Vasil Nedelchev on 2.5.2016 г..
 */
public class Time {
    private int id;
    private String departureTime;
    private String arrivalTime;

    public Time( String departureTime) {
        this.departureTime = departureTime;

    }
    public Time( String departureTime, String arrivalTime) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

    }

    public Time() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;
        Time time = (Time) o;
        return Objects.equals(departureTime, time.departureTime) &&
                Objects.equals(arrivalTime, time.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}
