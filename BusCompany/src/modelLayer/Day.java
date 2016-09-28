package modelLayer;

import java.util.Objects;

/**
 * Created by Vasil Nedelchev on 2.5.2016 г..
 */
public class Day {
    private int dayID;
    private String day;

    public Day(String day) {
        this.day = day;

    }
    public Day(){}


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDayID() {
        return dayID;
    }

    public void setDayID(int dayID) {
        this.dayID = dayID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Day)) return false;
        Day day1 = (Day) o;
        return Objects.equals(day, day1.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day);
    }

    @Override
    public String toString() {
        return "Day{" +
                "dayID=" + dayID +
                ", day='" + day + '\'' +
                '}';
    }
}
