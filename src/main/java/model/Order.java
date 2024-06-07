package model;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private String username;
    private Double burritonumber;
    private Double friesnumber;
    private Double sodanumber;
    private Double mealnumber;
    private Double totalprice;
    private Double waitingTime;
    private Timestamp orderTime;
    private String status;
    public Order(){}


    public Order(String username, Double burritonumber, Double friesnumber, Double sodanumber, Double mealnumber, Double totalprice, Double waitingTime, String status) {
        this.username = username;
        this.burritonumber = burritonumber;
        this.friesnumber = friesnumber;
        this.sodanumber = sodanumber;
        this.mealnumber = mealnumber;
        this.totalprice = totalprice;
        this.waitingTime = waitingTime;
        this.status = status;
    }

    // Getters and setters
    public String getStatus() {  // Add getter for status
        return status;
    }

    public void setStatus(String status) {  // Add setter for status
        this.status = status;
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getBurritonumber() {
        return burritonumber;
    }

    public void setBurritonumber(Double burritonumber) {
        this.burritonumber = burritonumber;
    }

    public Double getFriesnumber() {
        return friesnumber;
    }

    public void setFriesnumber(Double friesnumber) {
        this.friesnumber = friesnumber;
    }

    public Double getSodanumber() {
        return sodanumber;
    }

    public void setSodanumber(Double sodanumber) {
        this.sodanumber = sodanumber;
    }

    public Double getTotalprice() {
        return totalprice;
    }
    public void setMealnumber(double mealnumber) {
        this.mealnumber = mealnumber;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public Double getWaitingTime() {
        return waitingTime;
    }
    public Double getMealnumber() {

        return mealnumber;
    }

    public void setWaitingTime(Double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }
    @Override
    public String toString() {
        return "Order ID: " + orderId + "\n" +
                "Burrito Number: " + burritonumber + "\n" +
                "Fries Number: " + friesnumber + "\n" +
                "Soda Number: " + sodanumber + "\n" +
                "Meal Number: " + mealnumber + "\n" +
                "Total Price: " + totalprice + "\n" +
                "Waiting Time: " + waitingTime + "\n" +
                "Order Time: " + orderTime + "\n" +
                "Status: " + status;  // Include status in toString method
    }

}

