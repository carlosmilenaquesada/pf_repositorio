package com.example.tfg_carlosmilenaquesada.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Ticket implements Serializable {

    //la id del ticket (en mysql también) será: tiempo en milisegundos del momento de su creación.
    private String ticket_id;
    private String sale_date;
    private String customer_tax_id;
    private String ticket_status_id;
    private String payment_method_id;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Ticket(String customer_tax_id, String ticket_status_id, String payment_method_id) {
        long currentTimeInMillis = System.currentTimeMillis();
        this.ticket_id = String.valueOf(currentTimeInMillis);
        this.sale_date = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTimeInMillis), ZoneId.systemDefault()).toString();
        this.customer_tax_id = customer_tax_id;
        this.ticket_status_id = ticket_status_id;
        this.payment_method_id = payment_method_id;
    }

    public Ticket(String ticket_id, String sale_date, String customer_tax_id, String ticket_status_id, String payment_method_id) {
        this.ticket_id = ticket_id;
        this.sale_date = sale_date;
        this.customer_tax_id = customer_tax_id;
        this.ticket_status_id = ticket_status_id;
        this.payment_method_id = payment_method_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getSale_date() {
        return sale_date;
    }

    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }

    public String getCustomer_tax_id() {
        return customer_tax_id;
    }

    public void setCustomer_tax_id(String customer_tax_id) {
        this.customer_tax_id = customer_tax_id;
    }

    public String getTicket_status_id() {
        return ticket_status_id;
    }

    public void setTicket_status_id(String ticket_status_id) {
        this.ticket_status_id = ticket_status_id;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id='" + ticket_id + '\'' +
                ", sale_date='" + sale_date + '\'' +
                ", customer_tax_id='" + customer_tax_id + '\'' +
                ", ticket_status_id='" + ticket_status_id + '\'' +
                ", payment_method_id='" + payment_method_id + '\'' +
                '}';
    }
}
