package com.bantikumar.cafefast;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private int itemId;
    private String itemName;
    private double price;
    String itemDescription;
    private int availableQuantity;
    List<Category> categoryList;
    boolean isFavourite;

    public Item(int itemId,double price){
        this.itemId = itemId;
        this.price = price;
    }
    public Item(int itemId,double price,String itemName){
        this.itemId = itemId;
        this.price = price;
        this.itemName = itemName;
    }
    public Item(int itemId){
        this.itemId = itemId;
    }
    public Item(int itemId, String itemName,String Descrription, double price, int availableQuantity, List<Category> categoryList,boolean isFavourite) {
        this.itemId = itemId;
        this.itemDescription=Descrription;
        this.itemName = itemName;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.isFavourite=isFavourite;
        this.categoryList =categoryList;
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

    public void setCategoryList (List<Category> categoryList){
        this.categoryList = categoryList;
    }

    public List<Category> getCategoryList(){
        return categoryList;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
