package com.example.tfg_carlosmilenaquesada.models.customer;

public class Customer {
    String customer_tax_id;
    String legal_company_name;
    String name;
    String legal_company_address;
    String legal_country;
    String legal_location;
    String legal_zip_code;

    public Customer() {
    }

    public Customer(String customer_tax_id, String legal_company_name, String name, String legal_company_address, String legal_country, String legal_location, String legal_zip_code) {
        this.customer_tax_id = customer_tax_id;
        this.legal_company_name = legal_company_name;
        this.name = name;
        this.legal_company_address = legal_company_address;
        this.legal_country = legal_country;
        this.legal_location = legal_location;
        this.legal_zip_code = legal_zip_code;
    }

    public String getCustomer_tax_id() {
        return customer_tax_id;
    }

    public void setCustomer_tax_id(String customer_tax_id) {
        this.customer_tax_id = customer_tax_id;
    }

    public String getLegal_company_name() {
        return legal_company_name;
    }

    public void setLegal_company_name(String legal_company_name) {
        this.legal_company_name = legal_company_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegal_company_address() {
        return legal_company_address;
    }

    public void setLegal_company_address(String legal_company_address) {
        this.legal_company_address = legal_company_address;
    }

    public String getLegal_country() {
        return legal_country;
    }

    public void setLegal_country(String legal_country) {
        this.legal_country = legal_country;
    }

    public String getLegal_location() {
        return legal_location;
    }

    public void setLegal_location(String legal_location) {
        this.legal_location = legal_location;
    }

    public String getLegal_zip_code() {
        return legal_zip_code;
    }

    public void setLegal_zip_code(String legal_zip_code) {
        this.legal_zip_code = legal_zip_code;
    }
}
