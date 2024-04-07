package com.example.tfg_carlosmilenaquesada.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Article implements Serializable {
    private String internalCode;
    private String name;
    private Double saleBasePrice;
    private String vatFraction;
    private LocalDateTime offerStartDate;
    private LocalDateTime offerEndDate;
    private Double offerSaleBasePrice;

    public Article() {
    }

    public Article(String internalCode, String name, Double saleBasePrice, String vatFraction, LocalDateTime offerStartDate, LocalDateTime offerEndDate, Double offerSaleBasePrice) {
        this.internalCode = internalCode;
        this.name = name;
        this.saleBasePrice = saleBasePrice;
        this.vatFraction = vatFraction;
        this.offerStartDate = offerStartDate;
        this.offerEndDate = offerEndDate;
        this.offerSaleBasePrice = offerSaleBasePrice;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSaleBasePrice() {
        return saleBasePrice;
    }

    public void setSaleBasePrice(Double saleBasePrice) {
        this.saleBasePrice = saleBasePrice;
    }

    public String getVatFraction() {
        return vatFraction;
    }

    public void setVatFraction(String vatFraction) {
        this.vatFraction = vatFraction;
    }

    public LocalDateTime getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(LocalDateTime offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public LocalDateTime getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(LocalDateTime offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    public Double getOfferSaleBasePrice() {
        return offerSaleBasePrice;
    }

    public void setOfferSaleBasePrice(Double offerSaleBasePrice) {
        this.offerSaleBasePrice = offerSaleBasePrice;
    }
}
