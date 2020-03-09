package com.ateam.petworld.models;

public class Sitter extends User {

    private double payPerDay;
    private double distanceFromOwner;

    public double getDistanceFromOwner() {
        return distanceFromOwner;
    }

    public void setDistanceFromOwner(double distanceFromOwner) {
        this.distanceFromOwner = distanceFromOwner;
    }

    public double getPayPerDay() {
        return payPerDay;
    }

    public void setPayPerDay(double payPerDay) {
        this.payPerDay = payPerDay;
    }
}
