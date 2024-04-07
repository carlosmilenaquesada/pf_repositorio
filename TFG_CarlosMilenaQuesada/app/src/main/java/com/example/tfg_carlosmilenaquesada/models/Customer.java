package com.example.tfg_carlosmilenaquesada.models;

public class Customer {
    private String legalTaxationId;
    private String legalCompanyName;
    private String name;
    private String legalCompanyAddress;
    private String legalCountry;
    private String legalLocation;
    private String legalZipCode;

    public Customer() {
    }

    public Customer(String legalTaxationId, String legalCompanyName, String name, String legalCompanyAddress, String legalCountry, String legalLocation, String legalZipCode) {
        this.legalTaxationId = legalTaxationId;
        this.legalCompanyName = legalCompanyName;
        this.name = name;
        this.legalCompanyAddress = legalCompanyAddress;
        this.legalCountry = legalCountry;
        this.legalLocation = legalLocation;
        this.legalZipCode = legalZipCode;
    }

    public String getLegalTaxationId() {
        return legalTaxationId;
    }

    public void setLegalTaxationId(String legalTaxationId) {
        this.legalTaxationId = legalTaxationId;
    }

    public String getLegalCompanyName() {
        return legalCompanyName;
    }

    public void setLegalCompanyName(String legalCompanyName) {
        this.legalCompanyName = legalCompanyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalCompanyAddress() {
        return legalCompanyAddress;
    }

    public void setLegalCompanyAddress(String legalCompanyAddress) {
        this.legalCompanyAddress = legalCompanyAddress;
    }

    public String getLegalCountry() {
        return legalCountry;
    }

    public void setLegalCountry(String legalCountry) {
        this.legalCountry = legalCountry;
    }

    public String getLegalLocation() {
        return legalLocation;
    }

    public void setLegalLocation(String legalLocation) {
        this.legalLocation = legalLocation;
    }

    public String getLegalZipCode() {
        return legalZipCode;
    }

    public void setLegalZipCode(String legalZipCode) {
        this.legalZipCode = legalZipCode;
    }
}
