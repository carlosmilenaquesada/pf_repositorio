package com.example.tfg_carlosmilenaquesada.controllers.tools;

import android.content.ContentValues;



import com.example.tfg_carlosmilenaquesada.models.customer.Customer;

public class Tools {

    public static ContentValues getContentValuesFromCustomer(Customer customer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("customer_tax_id", customer.getCustomer_tax_id());
        contentValues.put("legal_company_name", customer.getLegal_company_name());
        contentValues.put("name", customer.getName());
        contentValues.put("legal_company_address", customer.getLegal_company_address());
        contentValues.put("legal_country", customer.getLegal_country());
        contentValues.put("legal_location", customer.getLegal_location());
        contentValues.put("legal_zip_code", customer.getLegal_zip_code());
        return contentValues;
    }
}
