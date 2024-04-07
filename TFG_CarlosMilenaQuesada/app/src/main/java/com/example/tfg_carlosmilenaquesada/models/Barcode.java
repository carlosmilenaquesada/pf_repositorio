package com.example.tfg_carlosmilenaquesada.models;

import java.io.Serializable;

public class Barcode implements Serializable {
    private String barcode;
    private String internalCode;

    public Barcode() {
    }

    public Barcode(String barcode, String internalCode) {
        this.barcode = barcode;
        this.internalCode = internalCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }
}
