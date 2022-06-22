package pt.ua.icm.tc.laundryathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private int id;
    private String date;
    private boolean isCompleted;
    private double totalPrice;

    public Order() {
        this.id = 0;
        this.date = "";
        this.isCompleted = false;
        this.totalPrice = 0;
    }

    public Order(int id, String date, boolean isCompleted, double totalPrice) {
        this.id = id;
        this.date = date;
        this.isCompleted = isCompleted;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
