package com.data;

import java.util.Date;
import java.util.UUID;

public class Message {
    private String id;
    private String text;
    private Date date;

    public Message(String text) {
        this.text = text;
        this.date = new Date();
        setId();
    }

    public Message(String id, String text, Date date) {
        this.text = text;
        this.date = date;
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
