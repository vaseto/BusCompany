package modelLayer;

public class Seat {

    // instance variables
    private int number, id;

    // a parameterized constructor

    public Seat(int number, int id) {
        this.number = number;
        this.id = id;
    }

    public Seat(int number, Bus bus) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Seat{" + "number=" + number + ", id=" + id + "";
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
        final Seat other = (Seat) obj;
        return this.id == other.id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}