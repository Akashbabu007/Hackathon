package com.example.hackathon;

public class Customer {
public Customer() {

}
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public String getStore() {
        return store;
    }

    public String getPhoneNumber() { return phoneno; }

    public String name;
    public String date;
    public String timeslot;
    public String store;
    public String phoneno;

    public Customer(String name,String date,String timeslot,String store, String phoneno) {
        this.name=name;
        this.date=date;
        this.timeslot=timeslot;
        this.store=store;
        this.phoneno=phoneno;
    }

}
