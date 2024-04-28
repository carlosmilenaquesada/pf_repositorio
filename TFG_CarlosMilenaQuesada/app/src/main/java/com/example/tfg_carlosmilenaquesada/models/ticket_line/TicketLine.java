package com.example.tfg_carlosmilenaquesada.models.ticket_line;

import java.io.Serializable;

public class TicketLine implements Serializable {
    String ticket_line_id;
    String ticket_id;
    String article_id;
    String article_name;
    float article_quantity;
    float unit_sale_base_price;
    String is_in_offer;
    float vat_fraction;

    public TicketLine() {
    }

    public TicketLine(String ticket_line_id, String ticket_id, String article_id, String article_name, float article_quantity, float unit_sale_base_price, String is_in_offer, float vat_fraction) {
        this.ticket_line_id = ticket_line_id;
        this.ticket_id = ticket_id;
        this.article_id = article_id;
        this.article_name = article_name;
        this.article_quantity = article_quantity;
        this.unit_sale_base_price = unit_sale_base_price;
        this.is_in_offer = is_in_offer;
        this.vat_fraction = vat_fraction;
    }

    public String getTicket_line_id() {
        return ticket_line_id;
    }

    public void setTicket_line_id(String ticket_line_id) {
        this.ticket_line_id = ticket_line_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

    public float getArticle_quantity() {
        return article_quantity;
    }

    public void setArticle_quantity(float article_quantity) {
        this.article_quantity = article_quantity;
    }

    public float getUnit_sale_base_price() {
        return unit_sale_base_price;
    }

    public void setUnit_sale_base_price(float unit_sale_base_price) {
        this.unit_sale_base_price = unit_sale_base_price;
    }

    public String getIs_in_offer() {
        return is_in_offer;
    }

    public void setIs_in_offer(String is_in_offer) {
        this.is_in_offer = is_in_offer;
    }

    public float getVat_fraction() {
        return vat_fraction;
    }

    public void setVat_fraction(float vat_fraction) {
        this.vat_fraction = vat_fraction;
    }

    @Override
    public String toString() {
        return "TicketLine{" +
                "ticket_line_id='" + ticket_line_id + '\'' +
                ", ticket_id='" + ticket_id + '\'' +
                ", article_id='" + article_id + '\'' +
                ", article_name='" + article_name + '\'' +
                ", article_quantity=" + article_quantity +
                ", unit_sale_base_price=" + unit_sale_base_price +
                ", is_in_offer='" + is_in_offer + '\'' +
                ", vat_fraction=" + vat_fraction +
                '}';
    }
}
