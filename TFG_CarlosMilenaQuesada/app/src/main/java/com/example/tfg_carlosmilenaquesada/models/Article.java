package com.example.tfg_carlosmilenaquesada.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Article implements Serializable {
    private String id;
    private String internalCode;
    private String barcodeId;
    private String name;
    private String familyId;
    private String categoryId;
    private Double basePrice;
    private String vatPercentId;
    private Double stock;
    private Double sold;
    private LocalDateTime offerStartDate;
    private LocalDateTime offerEndDate;
    private Double offerBasePrice;

    public Article() {
    }

    public Article(String id, String internalCode, String barcodeId, String name, String familyId, String categoryId, Double basePrice, String vatPercentId, Double stock, Double sold, LocalDateTime offerStartDate, LocalDateTime offerEndDate, Double offerBasePrice) {
        this.id = id;
        this.internalCode = internalCode;
        this.barcodeId = barcodeId;
        this.name = name;
        this.familyId = familyId;
        this.categoryId = categoryId;
        this.basePrice = basePrice;
        this.vatPercentId = vatPercentId;
        this.stock = stock;
        this.sold = sold;
        this.offerStartDate = offerStartDate;
        this.offerEndDate = offerEndDate;
        this.offerBasePrice = offerBasePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public String getVatPercentId() {
        return vatPercentId;
    }

    public void setVatPercentId(String vatPercentId) {
        this.vatPercentId = vatPercentId;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getSold() {
        return sold;
    }

    public void setSold(Double sold) {
        this.sold = sold;
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

    public Double getOfferBasePrice() {
        return offerBasePrice;
    }

    public void setOfferBasePrice(Double offerBasePrice) {
        this.offerBasePrice = offerBasePrice;
    }
}
