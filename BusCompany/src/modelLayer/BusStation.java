package modelLayer;

import java.util.Objects;

public class BusStation {

    private String city;
    private int id;

    public BusStation(String BusStation) {
        this.city = BusStation;
    }

    public BusStation(String city, int id) {
        this.city = city;
        this.id = id;
    }

    public BusStation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BusStation{" + "city=" + city + ", id=" + id + '}';
    }

    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusStation)) return false;
        BusStation that = (BusStation) o;
        return id == that.id &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, id);
    }
}
