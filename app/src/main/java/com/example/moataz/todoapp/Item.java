package com.example.moataz.todoapp;

public class Item {
    String note;
    String date;
    int id;

    public Item(String note, String date) {
        this.note = note;
        this.date = date;
    }

    public Item(String note, String date, int id) {
        this.note = note;
        this.date = date;
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }
}
