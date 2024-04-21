package com.example.tfg_carlosmilenaquesada.models;

import java.io.Serializable;

public class ArticleLine implements Serializable{
    String name;
    float unitPrice;
    float quantity;
    float totalLineAmount;



    public ArticleLine() {
    }

    public ArticleLine(String name, float unitPrice, float quantity, float totalLineAmount) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalLineAmount = totalLineAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getTotalLineAmount() {
        return totalLineAmount;
    }

    public void setTotalLineAmount(float totalLineAmount) {
        this.totalLineAmount = totalLineAmount;
    }

    @Override
    public String toString() {
        return "ArticleLine{" +
                "name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", totalLineAmount=" + totalLineAmount +
                '}';
    }
}
