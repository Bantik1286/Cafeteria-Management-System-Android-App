package com.bantikumar.cafefast;

public class Item {
    private int itemId;
    private String itemName;
    private double price;
    String itemDescription;
    private int availableQuantity;
    private int category_id;
    boolean isFavourite;

    public Item(int itemId, String itemName,String Descrription, double price, int availableQuantity, int category_id,boolean isFavourite) {
        this.itemId = itemId;
        this.itemDescription=Descrription;
        this.itemName = itemName;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.category_id = category_id;
        this.isFavourite=isFavourite;
    }

    public void setItemDescription(String description){
        this.itemDescription=description;
    }

    public String getItemDescription(){
        return itemDescription;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
