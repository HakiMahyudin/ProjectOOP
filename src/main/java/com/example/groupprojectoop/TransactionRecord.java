package com.example.groupprojectoop;

import java.time.LocalDate;

public class TransactionRecord {
    private double value;
    private String category;
    private LocalDate date;
    private String note;
    //
    public TransactionRecord(double value, String category, LocalDate date, String note) {
        this.value = value;
        this.category = category;
        this.date = date;
        this.note = note;
    }
    

    public double getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }
    public void setValue(double value) {
    this.value = value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s",this.value,this.category,this.date,this.note);

    }
}
