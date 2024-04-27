package com.example.tfg_carlosmilenaquesada.models;

import java.io.Serializable;

public class TicketLine implements Serializable {
    protected String ticket_line_id;
    protected String ticket_id;
    protected String article_id;
    protected float article_quantity;

    public TicketLine() {
    }

    public TicketLine(String ticket_line_id, String ticket_id, String article_id, float article_quantity) {
        this.ticket_line_id = ticket_line_id;
        this.ticket_id = ticket_id;
        this.article_id = article_id;
        this.article_quantity = article_quantity;
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

    public float getArticle_quantity() {
        return article_quantity;
    }

    public void setArticle_quantity(float article_quantity) {
        this.article_quantity = article_quantity;
    }

    @Override
    public String toString() {
        return "TicketLine{" +
                "ticket_line_id='" + ticket_line_id + '\'' +
                ", ticket_id='" + ticket_id + '\'' +
                ", article_id='" + article_id + '\'' +
                ", article_quantity=" + article_quantity +
                '}';
    }
}
