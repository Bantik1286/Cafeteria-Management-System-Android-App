package com.bantikumar.cafefast;

import java.util.Date;
import java.util.List;

public class OrderClass {
    private int orderId;
    private List<SelectedItem> items;
    private String placeBy;
    private String requirement;
    private String completedBy;
    private char status;
    private Date start_date;

    public OrderClass(int orderId, List<SelectedItem> items, String placeBy, String requirement, String completedBy, char status, Date start_date) {
        this.orderId = orderId;
        this.items = items;
        this.placeBy = placeBy;
        this.requirement = requirement;
        this.completedBy = completedBy;
        this.status = status;
        this.start_date = start_date;
    }
    public OrderClass(List<SelectedItem> items, String placeBy, String requirement, Date start_date) {
        this.items = items;
        this.placeBy = placeBy;
        this.requirement = requirement;
        this.start_date = start_date;
        status='I';
    }

    public OrderClass(int orderId, String placeBy, String requirement, String completedBy, char status, Date start_date){
        this.orderId = orderId;
        this.placeBy = placeBy;
        this.requirement = requirement;
        this.completedBy = completedBy;
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

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }


    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
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
