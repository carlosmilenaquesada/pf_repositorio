package com.example.tfg_carlosmilenaquesada.models;

import java.io.Serializable;

public class ArticleLine implements Serializable {
    private String articleId;
    private String name;
    private float unitBasePrice;
    private boolean isInOffer;
    private float quantity;
    private float vatFraction;

    public ArticleLine() {
    }

    public ArticleLine(String articleId, String name, float unitBasePrice, boolean isInOffer, float quantity, float vatFraction) {
        this.articleId = articleId;
        this.name = name;
        this.unitBasePrice = unitBasePrice;
        this.isInOffer = isInOffer;
        this.quantity = quantity;
        this.vatFraction = vatFraction;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnitBasePrice() {
        return unitBasePrice;
    }

    public void setUnitBasePrice(float unitBasePrice) {
        this.unitBasePrice = unitBasePrice;
    }

    public boolean isInOffer() {
        return isInOffer;
    }

    public void setInOffer(boolean inOffer) {
        isInOffer = inOffer;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getVatFraction() {
        return vatFraction;
    }

    public void setVatFraction(float vatFraction) {
        this.vatFraction = vatFraction;
    }


    @Override
    public String toString() {
        return "ArticleLine{" +
                "articleId='" + articleId + '\'' +
                ", name='" + name + '\'' +
                ", unitBasePrice=" + unitBasePrice +
                ", isInOffer=" + isInOffer +
                ", quantity=" + quantity +
                ", vatFraction=" + vatFraction +
                '}';
    }
}
