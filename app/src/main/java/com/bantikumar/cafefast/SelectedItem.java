package com.bantikumar.cafefast;

public class SelectedItem {
    Item item;
    int quantity;
    String requirement;

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

    public SelectedItem(Item item, int quantity,String requirement) {
        this.requirement = requirement;
        this.item = item;
        this.quantity = quantity;
    }
}
