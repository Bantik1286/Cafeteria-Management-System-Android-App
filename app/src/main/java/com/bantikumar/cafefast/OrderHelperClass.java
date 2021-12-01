package com.bantikumar.cafefast;

public class OrderHelperClass {
    private char status;
    private  int order_id;
    private double total_amount;
    // Date ; : TODO : Date Object


    public OrderHelperClass( int order_id,char status, double total_amount) {
        this.status = status;
        this.order_id = order_id;
        this.total_amount = total_amount;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}
