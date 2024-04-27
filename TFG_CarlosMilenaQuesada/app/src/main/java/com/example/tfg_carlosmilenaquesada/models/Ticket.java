package com.example.tfg_carlosmilenaquesada.models;

import java.io.Serializable;

public class Ticket implements Serializable {

    //la id del ticket (en mysql también) será: tiempo en milisegundos del momento de su creación.
    String ticket_id;
    String sale_date;
    String customer_tax_id;
    String ticket_status_id;
    String payment_method_id;

    public Ticket() {
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