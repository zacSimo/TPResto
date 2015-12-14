package unice.mbds.org.tpresto.model;

import java.io.Serializable;

/**
 * Created by Zac on 12/12/2015.
 */
public class Product implements Serializable {
    private String name;
    private String description;
    private String  price;
    private String calories;
    private String type;
    private String discount;
    private String picture;
    private String dessert;
    private String createdAt;
    private String updatedAt;
    private String id;

    public Product() {
    }

    public Product(String name, String description, String price, String calories, String type,
                   String discount, String picture,String dessert, String createdAt, String updatedAt, String id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.calories = calories;
        this.type = type;
        this.discount = discount;
        this.picture = picture;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
        this.dessert=dessert;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCalories() {
        return calories;
    }

    public String getType() {
        return type;
    }

    public String getDiscount() {
        return discount;
    }

    public String getPicture() {
        return picture;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getId() {
        return id;
    }
    public String getDessert(){
        return dessert;
    }
}
