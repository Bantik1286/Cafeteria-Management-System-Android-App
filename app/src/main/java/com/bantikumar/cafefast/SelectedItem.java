package com.bantikumar.cafefast;

public class SelectedItem {
    Item item;
    int quantity;
    double totalPrice;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SelectedItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
