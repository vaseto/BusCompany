package modelLayer;

import java.util.Date;

public class Payment {

    private Date date;
    private double amount;
    private int id;

    public Payment(Date date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Payment(Date date, double amount, int id) {
        this.date = date;
        this.amount = amount;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{"
                + "date=" + date
                + ", amount=" + amount
                + '}';
    }
}
