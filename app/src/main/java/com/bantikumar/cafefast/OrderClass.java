package com.bantikumar.cafefast;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class OrderClass {
    private int orderId;
    private List<SelectedItem> items;
    private String placeBy;
    private String requirement;
    private char status;
    private java.sql.Timestamp start_date;

    public OrderClass(int orderId, List<SelectedItem> items, String placeBy, String requirement, char status, java.sql.Timestamp start_date) {
        this.orderId = orderId;
        this.items = items;
        this.placeBy = placeBy;
        this.requirement = requirement;
        this.status = status;
        this.start_date = start_date;
        this.start_date = new Timestamp(new Date().getTime());
    }
    public OrderClass(List<SelectedItem> items, String placeBy, String requirement, java.sql.Timestamp start_date) {
        this.items = items;
        this.placeBy = placeBy;
        this.requirement = requirement;
        this.start_date = start_date;
        status='I';
        this.start_date = new Timestamp(new Date().getTime());
    }

    public OrderClass(int orderId, String placeBy, String requirement, char status, java.sql.Timestamp start_date){
        this.orderId = orderId;
        this.placeBy = placeBy;
        this.requirement = requirement;
        this.status = status;
        this.start_date = start_date;
    }

        public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<SelectedItem> getItems() {
        return items;
    }

    public void setItems(List<SelectedItem> items) {
        this.items = items;
    }

    public String getPlaceBy() {
        return placeBy;
    }

    public void setPlaceBy(String placeBy) {
        this.placeBy = placeBy;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }


    public java.sql.Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(java.sql.Timestamp start_date) {
        this.start_date = start_date;
    }

    public double getTotalAmount(){
        double total = 0;
        if(items!=null) {
            for (SelectedItem item : items) {
                total += item.getQuantity() * item.getItem().getPrice();
            }
        }
        return total;
    }
    public OrderClass(){

    }
}
