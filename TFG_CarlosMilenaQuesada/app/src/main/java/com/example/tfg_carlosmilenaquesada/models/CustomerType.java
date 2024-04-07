package com.example.tfg_carlosmilenaquesada.models;

public class CustomerType {
    private String id;
    private String description;

    public CustomerType() {
    }

    public CustomerType(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

